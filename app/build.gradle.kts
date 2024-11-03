plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)


    alias(libs.plugins.ksp)
    alias(libs.plugins.dagger.hilt)

    alias(libs.plugins.kotlinx.serialization)
}

android {
    namespace = "cz.janvesely.nba"
    compileSdk = 35

    defaultConfig {
        applicationId = "cz.janvesely.nba"
        minSdk = 28
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")

            isMinifyEnabled = true
            isShrinkResources = true

            buildConfigField("String", "GATEWAY_ENDPOINT", "\"https://api.balldontlie.io/v1/\"")
            buildConfigField("String", "GATEWAY_TOKEN", "\"707c1920-2ff8-4f4f-b254-329257838cac\"")
            signingConfig = signingConfigs.getByName("debug")
        }

        debug {
            buildConfigField("String", "GATEWAY_ENDPOINT", "\"https://api.balldontlie.io/v1/\"")
            buildConfigField("String", "GATEWAY_TOKEN", "\"707c1920-2ff8-4f4f-b254-329257838cac\"")
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
        buildConfig = true
    }

    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {
    //Paging
    implementation (libs.androidx.paging.runtime)
    implementation (libs.androidx.paging.compose)
    implementation(libs.androidx.navigation.compose)

    //Glide
    implementation(libs.glide)
    implementation(libs.glide.compose)

    //Dependency injection
    implementation(libs.dagger.hilt)
    ksp(libs.dagger.hilt.compiler)
    ksp(libs.androidx.hilt.compiler)
    implementation(libs.androidx.hilt.navigation)

    //Core
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)

    //Compose
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    implementation(libs.androidx.activity.compose)
    implementation(libs.androidx.material)

    //Retrofit
    implementation(libs.retrofit)
    implementation(libs.retrofit.kotlinx.serialization)
    implementation(libs.kotlinx.serialization.json)
    implementation(libs.logging.interceptor)

    debugImplementation(libs.androidx.ui.tooling)
}