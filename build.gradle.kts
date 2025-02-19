buildscript {
    repositories {
        google()
        mavenCentral()
    }

    dependencies {
        classpath(libs.google.service)
        classpath(libs.gradle)
        classpath(libs.kotlin.gradle)
    }
}

plugins {
    id("com.android.application") version "8.7.3" apply false
    id("com.android.library") version "8.7.3" apply false
    alias(libs.plugins.org.jetbrains.kotlin.android) apply false
    alias(libs.plugins.compose.compiler) apply false
}

tasks.register("clean", Delete::class) {
    delete(rootProject.layout.buildDirectory.get().asFile)
}
