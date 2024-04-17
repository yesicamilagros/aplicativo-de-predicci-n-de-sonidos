plugins {
    alias(libs.plugins.androidApplication)
    id("com.chaquo.python")
}

android {
    namespace = "com.exam.myapplication3"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.exam.myapplication3"
        minSdk = 21
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

        ndk {
            // On Apple silicon, you can omit x86_64.
            abiFilters += listOf("arm64-v8a", "x86_64")
        }


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
        mlModelBinding = true
    }
    chaquopy {
        defaultConfig { buildPython("C:/Users/Usuario/AppData/Local/Programs/Python/Python38-32/python.exe")
               version = "3.8"
            pip {
                // A requirement specifier, with or without a version number:
                install("numpy")
                install("scipy")
                install("python_speech_features")
                install("tflite-runtime")


                }

        }

        sourceSets { getByName("main") {
            srcDir("src/main/python")
        }}
    }




}

dependencies {

    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.activity)
    implementation(libs.constraintlayout)
    implementation(libs.tensorflow.lite.support)
    implementation(libs.tensorflow.lite.metadata)
    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)
}