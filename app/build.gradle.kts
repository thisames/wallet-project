plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.spotless) // Adicionando o plugin Spotless
}

android {
    namespace = "com.phonereplay.wallet_project"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.phonereplay.wallet_project"
        minSdk = 28
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    buildFeatures {
        viewBinding = true
    }
}

dependencies {
    implementation(libs.zxing.android.embedded)
    implementation(libs.mpandroidchart)
    implementation(libs.retrofit)
    implementation(libs.converter.gson)
    implementation(libs.bitcoinj.core)
    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.activity)
    implementation(libs.constraintlayout)
    implementation(libs.navigation.fragment)
    implementation(libs.navigation.ui)
    implementation(files("libs/WheelPicker-1.1.2.aar"))
    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)
}

spotless {
    java {
        googleJavaFormat("1.15.0")
        target("src/**/*.java")
    }
    kotlin {
        ktlint()
        target("src/**/*.kt")
    }
}
