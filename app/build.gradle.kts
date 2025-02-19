plugins {
    id("com.android.application")
    kotlin("android")
    id("kotlin-kapt")
    id("dagger.hilt.android.plugin")
}

android {
    namespace = "com.prior_dev.pokerroutejc"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.prior_dev.pokerroutejc"
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
        kotlinCompilerExtensionVersion = "1.5.2"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
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

    //Test
    testImplementation(libs.junit)

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
    //Test coroutines
    testImplementation(libs.kotlinx.coroutines.test)
    // Paging
    implementation(libs.paging.runtime.ktx)
    implementation(libs.paging.compose)
}