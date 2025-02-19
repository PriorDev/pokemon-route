plugins {
    id("com.android.application")
    kotlin("android")
    id("kotlin-kapt")
    id("dagger.hilt.android.plugin")
}

android {
    namespace = "com.prior_dev.pokerroutejc"
    compileSdk = 33

    defaultConfig {
        applicationId = "com.prior_dev.pokerroutejc"
        minSdk = 24
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
        kotlinCompilerExtensionVersion = "1.2.0"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {
    implementation("androidx.core:core-ktx:1.10.1")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.6.1")
    implementation("androidx.activity:activity-compose:1.7.2")
    implementation(libs.composeUi)
    implementation(libs.compose.preview)

    androidTestImplementation(libs.compose.test.junit)
    debugImplementation(libs.ui.tooling)
    debugImplementation(libs.compose.test.manifest)

    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")

    //Test
    testImplementation(libs.junit)

    //Material
    implementation("androidx.compose.material:material:1.2.0")
    implementation("androidx.compose.material3:material3:1.1.1")

    //lifecycle
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.6.1")
    implementation("androidx.lifecycle:lifecycle-runtime-compose:2.6.1")
    //Life data
    implementation("androidx.compose.runtime:runtime-livedata:1.5.0")
    //SerializedName
    implementation("com.google.code.gson:gson:2.9.0")
    // Retrofit
    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    implementation("com.squareup.retrofit2:converter-gson:2.9.0")
    //Dager-hilt
    implementation(libs.dagger.hitl)
    kapt(libs.dagger.hilt.compiler)
    implementation("androidx.hilt:hilt-navigation-compose:1.0.0")
    //Nav Controller
    implementation("androidx.navigation:navigation-compose:2.5.2")
    //Room
    implementation("androidx.room:room-ktx:2.5.2")
    kapt("androidx.room:room-compiler:2.5.2")
    //System ui Controll
    implementation("com.google.accompanist:accompanist-systemuicontroller:0.30.0")
    //Cargar imagenes desde internet
    implementation("io.coil-kt:coil-compose:2.2.2")
    implementation("androidx.palette:palette:1.0.0")
    //Test coroutines
    testImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.6.4")
}