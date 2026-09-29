pluginManagement {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
}

dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.PREFER_SETTINGS)
    repositories {
        google()
        mavenCentral()
        maven {
            url = java.net.URI("https://download.linphone.org/maven_repository/")
        }
        maven {
            url = java.net.URI("https://jitpack.io")
        }
        maven {
            url = java.net.URI("https://git.zx2c4.com/wireguard-android/snapshot/")
        }
    }
}

rootProject.name = "VirtueVoIP"
include(":app")
