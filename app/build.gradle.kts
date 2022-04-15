import Dependencies.applyHilt
import Dependencies.applyNavigation

plugins {
    id("com.android.application")
    kotlin("android")
    kotlin("kapt")
    id("androidx.navigation.safeargs.kotlin")
    id("kotlin-parcelize")
    id("dagger.hilt.android.plugin")
}

android {
    compileSdk = Dependencies.COMPILE_SDK
    buildToolsVersion = Dependencies.BUILD_TOOLS
    buildFeatures.dataBinding = true

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
//    kotlinOptions {
//        jvmTarget = JavaVersion.VERSION_11.toString()
//    }
}

dependencies {
    implementation(fileTree(mapOf("dir" to "libs", "include" to listOf("*.jar"))))
    implementation("org.jetbrains.kotlin:kotlin-stdlib:1.5.31")

    implementation("androidx.core:core-ktx:1.7.0")
    implementation("androidx.appcompat:appcompat:1.3.1")
    implementation("com.google.android.material:material:1.4.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.1")
    implementation(project(":opencv"))
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.3")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.4.0")

    implementation("androidx.fragment:fragment-ktx:1.3.6")


    //groupie
    implementation ("com.github.lisawray.groupie:groupie:2.9.0")
    implementation ("com.github.lisawray.groupie:groupie-viewbinding:2.9.0")

    //leak canary
    debugImplementation ("com.squareup.leakcanary:leakcanary-android:2.8.1")

    //pickle
//    implementation("com.charlezz:pickle:+")

    //timber
    implementation ("com.jakewharton.timber:timber:4.7.1")

    //MPAndroidChart
    implementation ("com.github.PhilJay:MPAndroidChart:v3.1.0")
    implementation ("org.jetbrains.kotlin:kotlin-reflect:${Dependencies.Kotlin.VERSION}")

    //CameraX
    val camerax_version = "1.0.0"
    // CameraX core library using camera2 implementation
    implementation ("androidx.camera:camera-camera2:$camerax_version")
    // CameraX Lifecycle Library
    implementation ("androidx.camera:camera-lifecycle:$camerax_version")
    // CameraX View class
    implementation ("androidx.camera:camera-view:1.0.0-alpha30")

    applyNavigation()
    applyHilt()

}
kapt {
    correctErrorTypes = true
}