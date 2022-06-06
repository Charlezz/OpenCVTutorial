private const val debugApi = "debugApi"

private const val debugImplementation = "debugImplementation"

private const val originImplementation = "originImplementation"
private const val testImplementation = "testImplementation"
private const val androidTestImplementation = "androidTestImplementation"
private const val lintChecks = "lintChecks"

private const val implementation = "implementation"
private const val ksp = "ksp"
private const val kapt = "kapt"
private const val compileOnly = "compileOnly"

const val DATA = ":data"
const val DOMAIN = ":domain"
const val OPENCV = ":opencv"

object Dependencies {

    const val COMPILE_SDK = 32
    const val MIN_SDK = 26
    const val TARGET_SDK = 32
    const val BUILD_TOOLS = "31.0.0"



    object Mockito{
        const val VERSION = "4.3.1"
        const val MOCKITO_CORE = "org.mockito:mockito-core:$VERSION"
        const val MOCKITO_INLINE = "org.mockito:mockito-inline:$VERSION"
    }

    object Jetbrains {

        object Kotlin {
            const val VERSION = "1.6.21"
            const val KOTLIN_GRADLE_PLUGIN = "org.jetbrains.kotlin:kotlin-gradle-plugin:$VERSION"
            const val KOTLIN_SERIALIZATION = "org.jetbrains.kotlin:kotlin-serialization:$VERSION"
            const val STD_LIB = "org.jetbrains.kotlin:kotlin-stdlib:$VERSION"
            const val STD_LIB_7 = "org.jetbrains.kotlin:kotlin-stdlib-jdk7:$VERSION"
            const val STD_LIB_8 = "org.jetbrains.kotlin:kotlin-stdlib-jdk8:$VERSION"
            const val KOTLIN_REFLECT = "org.jetbrains.kotlin:kotlin-reflect:${VERSION}"

        }

        object KotlinX {
            const val VERSION = "1.6.1"
            const val COROUTINE_ANDROID = "org.jetbrains.kotlinx:kotlinx-coroutines-android:$VERSION"
            const val COROUTINE_TEST = "org.jetbrains.kotlinx:kotlinx-coroutines-test:$VERSION"
            const val COROUTINE_CORE = "org.jetbrains.kotlinx:kotlinx-coroutines-core:$VERSION"

            const val KOTLINX_COROUTINES_CORE = "org.jetbrains.kotlinx:kotlinx-coroutines-core:$VERSION"

            const val KOTLINX_SERIALIZATION_JSON = "org.jetbrains.kotlinx:kotlinx-serialization-json:1.3.3"
        }
    }

    object Google {
        object Android{
            object GMS {
                const val PLAY_SERVICES_AUTH = "com.google.android.gms:play-services-auth:20.2.0"
            }

            object Material{

                // https://github.com/material-components/material-components-android-compose-theme-adapter/releases
                const val MATERIAL = "com.google.android.material:material:1.6.0"
                const val COMPOSE_THEME_ADAPTER = "com.google.android.material:compose-theme-adapter:1.1.6"
                const val COMPOSE_THEME_ADAPTER3 = "com.google.android.material:compose-theme-adapter-3:1.0.3"
            }
        }

        /**
         * Jetpack Compose를 위한 유틸
         * https://github.com/google/accompanist
         */
        object Accompanist {
            const val VERSION = "0.24.6-alpha"
            const val insets = "com.google.accompanist:accompanist-insets:$VERSION"
            const val systemuicontroller = "com.google.accompanist:accompanist-systemuicontroller:$VERSION"
            const val flowlayouts = "com.google.accompanist:accompanist-flowlayout:$VERSION"
            const val pager = "com.google.accompanist:accompanist-pager:$VERSION"
        }

        object Dagger {
            const val VERSION = "2.41"

            const val HILT_ANDROID_GRADLE_PLUGIN = "com.google.dagger:hilt-android-gradle-plugin:$VERSION"
            const val HILT_ANDROID = "com.google.dagger:hilt-android:$VERSION"
            const val HILT_COMPILER = "com.google.dagger:hilt-compiler:$VERSION"
        }


    }

    object Junit {
        const val VERSION = "4.13.2"
        const val JUNIT = "junit:junit:$VERSION"
    }

    object AndroidX {
        const val CORE = "androidx.core:core-ktx:1.8.0"




        const val ANNOTATION = "androidx.annotation:annotation:1.3.0"
        const val APPCOMPAT = "androidx.appcompat:appcompat:1.4.2"

        object Fragment{
            const val FRAGMENT_KTX = "androidx.fragment:fragment-ktx:1.4.2"
        }

        object DataStore{
            const val DATASTORE_PREFERENCES = "androidx.datastore:datastore-preferences:1.0.0"
        }

        object Hilt{
            val VERSION = "1.0.0"
            val HILT_COMPILER = "androidx.hilt:hilt-compiler:$VERSION"
            val HILT_NAVIGATION_COMPOSE = "androidx.hilt:hilt-navigation-compose:$VERSION"
        }

        object Activity {
            const val VERSION = "1.4.0"
            const val ACTIVITY_KTX = "androidx.activity:activity-ktx:$VERSION"
            // Integration with activities
            const val ACTIVITY_COMPOSE = "androidx.activity:activity-compose:$VERSION"
        }

        object Lifecycle {
            // https://androidx.tech/artifacts/lifecycle/lifecycle-viewmodel-compose/
            // Integration with ViewModels
            const val VERSION = "2.4.1"
            const val LIFECYCLE_RUNTIME_KTX = "androidx.lifecycle:lifecycle-runtime-ktx:$VERSION"
            const val LIFECYCLE_VIEWMODEL_COMPOSE = "androidx.lifecycle:lifecycle-viewmodel-compose:$VERSION"
        }

        object ConstraintLayout {
            const val CONSTRAINTLAYOUT = "androidx.constraintlayout:constraintlayout:2.1.4"
            const val CONSTRAINTLAYOUT_COMPOSE = "androidx.constraintlayout:constraintlayout-compose:1.0.0"

        }

        object Compose {
            // https://developer.android.com/jetpack/androidx/releases/compose#declaring_dependencies
            const val KOTLIN_COMPILER_EXTENSION_VERSION = "1.2.0-beta01"

            object Foundation{
                // https://androidx.tech/artifacts/compose.foundation/*
                const val VERSION = KOTLIN_COMPILER_EXTENSION_VERSION
                // Foundation (Border, Background, Box, Image, Scroll, shapes, animations, etc.)
                const val FOUNDATION = "androidx.compose.foundation:foundation:$VERSION"
                const val FOUNDATION_LAYOUT = "androidx.compose.foundation:foundation-layout:$VERSION"
            }
            object UI{
                // https://androidx.tech/artifacts/compose.ui/*
                const val VERSION = KOTLIN_COMPILER_EXTENSION_VERSION
                const val UI = "androidx.compose.ui:ui:$VERSION"
                // Tooling support (Previews, etc.)
                const val UI_TOOLING = "androidx.compose.ui:ui-tooling:$VERSION"
                const val UI_TOOLING_PREVIEW = "androidx.compose.ui:ui-tooling-preview:$VERSION"
                const val UI_TEST_JUNIT4 = "androidx.compose.ui:ui-test-junit4:$VERSION"
                const val UI_TEST_MANIFEST = "androidx.compose.ui:ui-test-manifest:$VERSION"
            }

            object Material{
                // https://androidx.tech/artifacts/compose.material/*
                const val VERSION = KOTLIN_COMPILER_EXTENSION_VERSION
                const val MATERIAL = "androidx.compose.material:material:$VERSION"
                // Material design icons
                const val MATERIAL_ICONS_CORE = "androidx.compose.material:material-icons-core:$VERSION"
                const val MATERIAL_ICONS_EXTENDED = "androidx.compose.material:material-icons-extended:$VERSION"
            }
            object Material3{
                const val MATERIAL3 = "androidx.compose.material3:material3:1.0.0-alpha12"
            }

            object Runtime{
                // https://androidx.tech/artifacts/compose.runtime/runtime/
                const val VERSION = KOTLIN_COMPILER_EXTENSION_VERSION
                const val RUNTIME = "androidx.compose.runtime:runtime:$VERSION"
                // Integration with observables
                const val LIVEDATA = "androidx.compose.runtime:runtime-livedata:$VERSION"
                const val RUNTIME_RXJAVA2 = "androidx.compose.runtime:runtime-rxjava2:$VERSION"
            }
        }

        object Test {
            object Ext{
                const val VERSION = "1.1.3"
                const val JUNIT_KTX = "androidx.test.ext:junit-ktx:$VERSION"
            }
            object Espresso{
                const val VERSION = "3.4.0"
                const val ESPRESSO_CORE = "androidx.test.espresso:espresso-core:$VERSION"
            }

            const val CORE_KTX = "androidx.test:core-ktx:1.7.0"
            const val CORE_TESTING = "androidx.arch.core:core-testing:2.1.0"
        }


        object Navigation {
            const val VERSION = "2.5.0-rc01"
            const val NAVIGATION_SAFE_ARGS_GRADLE_PLUGIN = "androidx.navigation:navigation-safe-args-gradle-plugin:$VERSION"
            const val NAVIGATION_COMPOSE = "androidx.navigation:navigation-compose:$VERSION"
            const val NAVIGATION_FRAGMENT_KTX = "androidx.navigation:navigation-fragment-ktx:$VERSION"
            const val NAVIGATION_UI = "androidx.navigation:navigation-ui-ktx:$VERSION"
            const val DYNAMIC_FEATURE_FRAGMENT = "androidx.navigation:navigation-dynamic-features-fragment:$VERSION"
        }

    }

    object SquareUp{
        object OkHttp3{
            const val OKHTTP_URLCONNECTION = "com.squareup.okhttp3:okhttp-urlconnection:4.4.1"
        }
        object LeakCanary{
            const val LEAKCANARY_ANDROID = "com.squareup.leakcanary:leakcanary-android:2.8.1"
        }

        object Retrofit2{
            const val VERSION = "2.9.0"
            const val RETROFIT = "com.squareup.retrofit2:retrofit:$VERSION"
            const val CONVERTER_MOSHI = "com.squareup.retrofit2:converter-moshi:$VERSION"
            const val CONVERTER_GSON = "com.squareup.retrofit2:converter-gson:$VERSION"
        }

        object Moshi{
            const val VERSION = "1.13.0"
            const val MOSHI = "com.squareup.moshi:moshi:$VERSION"
            const val MOSHI_KOTLIN_CODEGEN = "com.squareup.moshi:moshi-kotlin-codegen:$VERSION"
        }
    }

    object Lifecycle {
        const val VERSION = "2.4.1"
        const val VIEWMODEL = "androidx.lifecycle:lifecycle-viewmodel-ktx:$VERSION"
        const val VIEWMODEL_SAVEDSTATE = "androidx.lifecycle:lifecycle-viewmodel-savedstate:$VERSION"
        const val LIFECYCLE_RUNTIME_KTX = "androidx.lifecycle:lifecycle-runtime-ktx:$VERSION"
        const val PROCESS = "androidx.lifecycle:lifecycle-process:$VERSION"
        const val COMMON_JAVA8 = "androidx.lifecycle:lifecycle-common-java8:$VERSION"
    }


    object Android {
        object Tools {
            object Build{
                const val GRADLE = "com.android.tools.build:gradle:7.2.1"
            }
        }
    }

    object Coil{
        const val VERSION = "2.0.0"
        const val COIL = "io.coil-kt:coil:$VERSION"
        const val COIL_BASE = "io.coil-kt:coil-base:$VERSION"
        const val COIL_COMPOSE = "io.coil-kt:coil-compose:$VERSION"
        const val COIL_COMPOSE_BASE = "io.coil-kt:coil-compose-base:$VERSION"
        const val COIL_GIF = "io.coil-kt:coil-gif:$VERSION"
        const val COIL_SVG = "io.coil-kt:coil-svg:$VERSION"
        const val COIL_VIDEO = "io.coil-kt:coil-video:$VERSION"
    }

    object JakeWharton{
        object Retrofit{
            const val RETROFIT2_KOTLINX_SERIALIZATION_CONVERTER = "com.jakewharton.retrofit:retrofit2-kotlinx-serialization-converter:0.8.0"
        }
        object Timber{
            const val TIMBER = "com.jakewharton.timber:timber:5.0.1"
        }
    }

    object JavaX {
        object Inject{
            const val JAVAX_INJECT = "javax.inject:javax.inject:1"
        }
    }

    object Github{
        object LISAWRAY{
            object GROUPIE {
                const val VERSION = "2.10.1"
                const val GROUPIE = "com.github.lisawray.groupie:groupie:$VERSION"
                const val GROUPIE_VIEWBINDING = "com.github.lisawray.groupie:groupie-viewbinding:$VERSION"

            }
        }
        object PhilJay{
            const val MPAndroidChart = "com.github.PhilJay:MPAndroidChart:v3.1.0"
        }
    }
}