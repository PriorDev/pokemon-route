import org.gradle.internal.impldep.org.junit.Test

plugins {
    id("com.android.application")
    kotlin("android")
    id("kotlin-kapt")
    id("dagger.hilt.android.plugin")
    id("io.gitlab.arturbosch.detekt") version "1.23.1"
    alias(libs.plugins.kotlin.serialization)
    id("com.apollographql.apollo3").version("3.7.3")
    alias(libs.plugins.compose.compiler)
    id("de.mannodermaus.android-junit5") version "1.9.3.0"
}

apollo {
    service("service") {
        packageName.set("com.priorDev")
    }
}

android {
    namespace = "com.priorDev.pokerroutejc"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.priorDev.pokerroutejc"
        minSdk = 29
        targetSdk = 33
        versionCode = 4
        versionName = "1.2"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.14"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

detekt {
    toolVersion = "1.23.1" // Use the version that matches the plugin
    buildUponDefaultConfig = true // Start with default config
    config.setFrom(files("../detekt.yml"))
}

dependencies {
    implementation(libs.core.ktx)
    implementation(libs.activity.compose)
    implementation(libs.composeUi)
    implementation(libs.compose.preview)

    androidTestImplementation(libs.compose.test.junit)
    debugImplementation(libs.ui.tooling)
    debugImplementation(libs.compose.test.manifest)

    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)

    //Material
    implementation(libs.material)
    implementation(libs.material3)

    //lifecycle
    implementation(libs.lifecycle.runtime.ktx)
    implementation(libs.lifecycle.runtime.compose)
    //Life data
    implementation(libs.runtime.livedata)
    //SerializedName
    implementation(libs.gson)
    // Retrofit
    implementation(libs.retrofit)
    implementation(libs.converter.gson)
    //Dager-hilt
    implementation(libs.dagger.hitl)
    kapt(libs.dagger.hilt.compiler)
    implementation(libs.hilt.navigation.compose)
    implementation(libs.kotlinx.serialization.json)
    //Nav Controller
    implementation(libs.navigation.compose)
    //Room
    implementation(libs.room.ktx)
    implementation(libs.room.paging)
    kapt(libs.room.compiler)
    //System ui Controll
    implementation(libs.accompanist.systemuicontroller)
    //Cargar imagenes desde internet
    implementation(libs.coil.compose)
    implementation(libs.palette)
    // Paging
    implementation(libs.paging.runtime.ktx)
    implementation(libs.paging.compose)
    // Detekt
    detektPlugins(libs.bundles.detekt)
    //Apollor
    implementation(libs.apollo.runtime)
    //Ktor
    implementation(libs.ktor.client.core)
    implementation(libs.ktor.client.android)
    implementation(libs.ktor.client.serialization)
    implementation(libs.ktor.client.logging)
    implementation(libs.ktor.client.content.negotiation)
    implementation(libs.ktor.serialization.json)
    //Test
    testImplementation(libs.junit)
    testImplementation(libs.junit.jupiter)
    testRuntimeOnly(libs.junit.jupiter.engine)
    testImplementation(libs.junit.jupiter.params)
    testImplementation(libs.assertk)
    testImplementation(libs.mockk)
    androidTestImplementation(libs.mockk.android)
    //Test coroutines
    testImplementation(libs.kotlinx.coroutines.test)
}