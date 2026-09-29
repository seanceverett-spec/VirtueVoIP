pluginManagement {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
}

dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
        maven {
            url = java.net.URI("https://download.linphone.org/maven_repository/")
            content {
                includeGroup("org.linphone")
                includeGroup("org.linphone.bundled")
            }
        }
    }
}

rootProject.name = "VirtueVoIP"
include(":app")
