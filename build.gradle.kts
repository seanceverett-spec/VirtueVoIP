plugins {
    id("com.android.application") version "8.5.2" apply false
    id("org.jetbrains.kotlin.android") version "1.9.24" apply false
}

allprojects {
    repositories {
        google()
        mavenCentral()
        maven {
            url = uri("https://download.linphone.org/maven_repository/")
        }
        maven {
            url = uri("https://jitpack.io")
        }
    }
}
