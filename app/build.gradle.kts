plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.ksp)
    alias(libs.plugins.hilt)
    alias(libs.plugins.firebase.crashlitycs)
    alias(libs.plugins.gms.googleServices)
    alias(libs.plugins.jetbrains.kotlin.serialization)
    alias(libs.plugins.room)
}

android {
    namespace = "com.dice.mileagetracker"
    compileSdk = libs.versions.compileSdk.get().toInt()

    defaultConfig {
        applicationId = "com.dice.mileagetracker"
        minSdk = libs.versions.minSdk.get().toInt()
        targetSdk = libs.versions.targetSdk.get().toInt()
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }
    buildFeatures {
        buildConfig = true
        compose = true
    }
    room {
        schemaDirectory("$projectDir/schemas")
    }
}

dependencies {
    // kotlin - base
    implementation(libs.androidx.core.ktx)

    // hilt
    implementation(libs.hilt.android)
    implementation(libs.hilt.navigation.compose)
    ksp(libs.hilt.android.compiler)

    // lifecycle
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.lifecycle.extensions)

    // compose dependencies
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    debugImplementation(libs.androidx.ui.tooling)

    // material 3
    implementation(libs.androidx.material3)

    // navigation
    implementation(libs.androidx.navigation.compose)

    // firebase integration
    implementation(platform(libs.com.google.firebase.bom))
    implementation(libs.com.google.firebase.firestore.ktx)
    implementation(libs.com.google.firebase.config.ktx)
    implementation(libs.com.google.firebase.crashlytics.ktx)
    implementation(libs.com.google.firebase.analytics.ktx)

    // serialization
    implementation(libs.kotlinx.serialization.json)

    // google maps
    implementation(libs.maps.compose)
    implementation (libs.play.services.maps)
    implementation (libs.play.services.location)

    // room
    implementation(libs.room.runtime)
    implementation(libs.room.ktx)
    ksp(libs.room.compiler)
    annotationProcessor(libs.room.compiler)

    // system ui controller
    implementation(libs.system.ui)

}