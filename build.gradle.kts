buildscript {
    repositories {
        google()
        mavenCentral()
        maven("https://jitpack.io")
    }
    dependencies {
        classpath(Dependencies.Android.Tools.Build.GRADLE)
        classpath(Dependencies.Jetbrains.Kotlin.KOTLIN_GRADLE_PLUGIN)
        classpath(Dependencies.Google.Dagger.HILT_ANDROID_GRADLE_PLUGIN)
        classpath(Dependencies.Jetbrains.Kotlin.KOTLIN_SERIALIZATION)
        classpath(Dependencies.AndroidX.Navigation.NAVIGATION_SAFE_ARGS_GRADLE_PLUGIN)
    }
}

tasks.register("clean", Delete::class) {
    delete(rootProject.buildDir)
}