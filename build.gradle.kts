
allprojects {
    repositories {
        google()
        mavenCentral()
        maven("https://jitpack.io")
    }
}

buildscript {
    repositories {
        google()
        mavenCentral()
        maven("https://jitpack.io")
    }
    dependencies {
        classpath ("com.android.tools.build:gradle:7.1.3")
        classpath(Dependencies.Hilt.ANDROID_GRADLE_PLUGIN)
        classpath ("org.jetbrains.kotlin:kotlin-gradle-plugin:${Dependencies.Kotlin.VERSION}")
        classpath ("androidx.navigation:navigation-safe-args-gradle-plugin:${Dependencies.Navigation.VERSION}")
    }
}