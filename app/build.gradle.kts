plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
}

android {
    namespace = "org.virtuevoip.client"
    compileSdk = 34

    defaultConfig {
        applicationId = "org.virtuevoip.client"
        minSdk = 26
        targetSdk = 34
        versionCode = 1
        versionName = "1.0-alpha"

        ndk {
            abiFilters += listOf("arm64-v8a", "x86_64")
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }

    kotlinOptions {
        jvmTarget = "17"
    }

    lint {
        abortOnError = false
    }
}

dependencies {
    implementation("org.linphone:linphone-sdk-android:5.3.77")
    implementation("com.github.WireGuard:wireguard-android:1.0.20230707")
    implementation("androidx.core:core-ktx:1.13.1")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.8.5")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.8.1")
}
