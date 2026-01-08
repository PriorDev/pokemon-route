plugins {
    id("com.android.application")
    kotlin("android")
    alias(libs.plugins.ksp)
    id("io.gitlab.arturbosch.detekt") version "1.23.7"
    alias(libs.plugins.kotlin.serialization)
    id("com.apollographql.apollo3").version("3.8.5")
    alias(libs.plugins.compose.compiler)
    id("de.mannodermaus.android-junit5") version "1.11.0.0"
}

apollo {
    service("service") {
        packageName.set("com.priorDev")
    }
}

android {
    namespace = "com.priorDev.pokerroutejc"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.priorDev.pokerroutejc"
        minSdk = 29
        targetSdk = 35
        versionCode = 5
        versionName = "1.3"

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
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
        freeCompilerArgs += "-Xcontext-receivers"
    }
    buildFeatures {
        compose = true
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

detekt {
    toolVersion = "1.23.7" // Use the version that matches the plugin
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
    implementation(libs.material.icons.extended)
    //lifecycle
    implementation(libs.lifecycle.runtime.ktx)
    implementation(libs.lifecycle.runtime.compose)
    implementation(libs.kotlinx.serialization.json)
    //Nav Controller
    implementation(libs.navigation.compose)
    //Room
    implementation(libs.room.ktx)
    implementation(libs.room.paging)
    ksp(libs.room.compiler)
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
    testImplementation(libs.turbine)
    testImplementation(libs.mockk)
    androidTestImplementation(libs.mockk.android)
    //Test coroutines
    testImplementation(libs.kotlinx.coroutines.test)
    // Koin
    implementation(libs.koin.android)
    implementation(libs.koin.androidx.navigation)
    implementation(libs.koin.androidx.compose)
    testImplementation(libs.koin.test.junit4)
    // Data store
    implementation(libs.datastore.preferences)
}