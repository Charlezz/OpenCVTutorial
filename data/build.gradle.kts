plugins {
    id("com.android.library")
    kotlin("android")
}

android {
    compileSdk = Project.compileSdk

    defaultConfig {
        minSdk = Project.minSdk
        targetSdk = Project.targetSdk

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = JavaVersion.VERSION_11.toString()
    }
    namespace = "com.charlezz.notepad.data"
}

dependencies {
    implementation(project(DOMAIN))

    implementation(Dependencies.SquareUp.Retrofit2.RETROFIT)
    implementation(Dependencies.SquareUp.Retrofit2.CONVERTER_MOSHI)
    implementation(Dependencies.AndroidX.CORE)
    implementation(Dependencies.AndroidX.APPCOMPAT)
    implementation(Dependencies.Google.Android.Material.MATERIAL)
    testImplementation(Dependencies.Junit.JUNIT)
    androidTestImplementation(Dependencies.AndroidX.Test.Ext.JUNIT_KTX)
    androidTestImplementation(Dependencies.AndroidX.Test.Espresso.ESPRESSO_CORE)
    implementation(Dependencies.JavaX.Inject.JAVAX_INJECT)
    implementation(Dependencies.Jetbrains.KotlinX.KOTLINX_COROUTINES_CORE)

}