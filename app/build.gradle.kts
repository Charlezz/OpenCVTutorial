plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    kotlin("kapt")
    id("androidx.navigation.safeargs.kotlin")
    id("kotlin-parcelize")
    id("dagger.hilt.android.plugin")
}

android {
    compileSdk = Dependencies.COMPILE_SDK
    buildToolsVersion = Dependencies.BUILD_TOOLS

    defaultConfig {
        applicationId = "com.charlezz.opencvtutorial"
        minSdk = Dependencies.MIN_SDK
        targetSdk = Dependencies.TARGET_SDK
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility(JavaVersion.VERSION_11)
        targetCompatibility(JavaVersion.VERSION_11)
    }
    kotlinOptions {
        jvmTarget = JavaVersion.VERSION_11.toString()
    }
    buildFeatures {
        compose = true
        dataBinding = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = Dependencies.AndroidX.Compose.KOTLIN_COMPILER_EXTENSION_VERSION
    }
}

dependencies {
    implementation(fileTree(mapOf("dir" to "libs", "include" to listOf("*.jar"))))

    implementation(project(OPENCV))
    implementation(project(DATA))
    implementation(project(DOMAIN))

    implementation(Dependencies.AndroidX.CORE)
    implementation(Dependencies.AndroidX.APPCOMPAT)
    implementation(Dependencies.AndroidX.ConstraintLayout.CONSTRAINTLAYOUT)
    implementation(Dependencies.AndroidX.Fragment.FRAGMENT_KTX)


    //groupie
    implementation (Dependencies.Github.LISAWRAY.GROUPIE.GROUPIE)
    implementation (Dependencies.Github.LISAWRAY.GROUPIE.GROUPIE_VIEWBINDING)

    //leak canary
    debugImplementation (Dependencies.SquareUp.LeakCanary.LEAKCANARY_ANDROID)

    //timber
    implementation (Dependencies.JakeWharton.Timber.TIMBER)

    //MPAndroidChart
    implementation (Dependencies.Github.PhilJay.MPAndroidChart)
    implementation (Dependencies.Jetbrains.Kotlin.KOTLIN_REFLECT)

    //CameraX
    val camerax_version = "1.1.0-rc02"
    // CameraX core library using camera2 implementation
    implementation ("androidx.camera:camera-camera2:$camerax_version")
    // CameraX Lifecycle Library
    implementation ("androidx.camera:camera-lifecycle:$camerax_version")
    // CameraX View class
    implementation ("androidx.camera:camera-view:$camerax_version")

    implementation(Dependencies.AndroidX.Navigation.NAVIGATION_COMPOSE)
    implementation(Dependencies.AndroidX.Navigation.NAVIGATION_UI)
    implementation(Dependencies.AndroidX.Navigation.NAVIGATION_FRAGMENT_KTX)


    implementation(Dependencies.AndroidX.Compose.UI.UI)
    implementation(Dependencies.AndroidX.Compose.Material3.MATERIAL3)
    implementation(Dependencies.AndroidX.Compose.UI.UI_TOOLING_PREVIEW)
    implementation(Dependencies.Lifecycle.LIFECYCLE_RUNTIME_KTX)
    implementation(Dependencies.AndroidX.Activity.ACTIVITY_COMPOSE)
    implementation(Dependencies.AndroidX.Lifecycle.LIFECYCLE_RUNTIME_KTX)
    implementation(Dependencies.AndroidX.Activity.ACTIVITY_COMPOSE)
    testImplementation(Dependencies.Junit.JUNIT)
    androidTestImplementation(Dependencies.AndroidX.Test.Ext.JUNIT_KTX)
    androidTestImplementation(Dependencies.AndroidX.Test.Espresso.ESPRESSO_CORE)
    androidTestImplementation(Dependencies.AndroidX.Compose.UI.UI_TEST_JUNIT4)
    debugImplementation(Dependencies.AndroidX.Compose.UI.UI_TOOLING)
    debugImplementation(Dependencies.AndroidX.Compose.UI.UI_TEST_MANIFEST)

    // compose navigation
    implementation(Dependencies.AndroidX.Navigation.NAVIGATION_COMPOSE)

    // Hilt
    implementation(Dependencies.Google.Dagger.HILT_ANDROID)
    kapt(Dependencies.Google.Dagger.HILT_COMPILER)
    kapt(Dependencies.AndroidX.Hilt.HILT_COMPILER)
    implementation(Dependencies.AndroidX.Hilt.HILT_NAVIGATION_COMPOSE)

    // Coil
    implementation(Dependencies.Coil.COIL_COMPOSE)


}
kapt {
    correctErrorTypes = true
}