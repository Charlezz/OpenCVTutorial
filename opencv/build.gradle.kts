plugins {
    id("com.android.library")
}

android {
    compileSdk = Dependencies.COMPILE_SDK

    defaultConfig {
        minSdk = Dependencies.MIN_SDK
        targetSdk = Dependencies.TARGET_SDK

        externalNativeBuild {
            cmake {
                arguments.add("-DANDROID_STL=c++_shared")
                targets.add("opencv_jni_shared")
            }
        }
    }

    buildTypes {
        debug {
            packagingOptions {
                jniLibs.keepDebugSymbols.add("**/*.so") // controlled by OpenCV CMake scripts
            }
        }
        release {
            packagingOptions {
                jniLibs.keepDebugSymbols.add("**/*.so") // controlled by OpenCV CMake scripts
            }
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android.txt"), "proguard-rules.txt")
        }
    }
    compileOptions {
        sourceCompatibility(JavaVersion.VERSION_11)
        targetCompatibility(JavaVersion.VERSION_11)
    }

    sourceSets["main"].jniLibs.srcDirs("native/libs")
    sourceSets["main"].java.srcDirs("java/src")
    sourceSets["main"].aidl.srcDirs("java/src")
    sourceSets["main"].res.srcDirs("java/res")
    sourceSets["main"].manifest.srcFile("java/AndroidManifest.xml")

    externalNativeBuild {
        cmake {
            path = file("${project.projectDir}/libcxx_helper/CMakeLists.txt")
        }
    }
}

dependencies {
}
