pluginManagement {
    repositories {
        google()
        mavenCentral()
    }
    plugins {
        id("com.android.application") version "8.6.0"
        id("org.jetbrains.kotlin.android") version "1.9.10"
        id("androidx.navigation.safeargs.kotlin") version "2.7.4"
    }
}

dependencyResolutionManagement {
    repositories {
        google()
        mavenCentral()
    }
}

rootProject.name = "StudentMan3"
include(":app")
