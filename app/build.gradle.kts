plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id ("kotlin-kapt")
    id ("dagger.hilt.android.plugin")
    id ("com.huawei.agconnect")
}

android {
    namespace = "com.fast.zai.loans"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.fast.zai.loans"
        minSdk = 21
        targetSdk = 34
        versionCode = 1
        versionName = "1"
        multiDexEnabled =  true
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
        signingConfig = signingConfigs.getByName("debug")
    }

    signingConfigs {
        getByName("debug") {
            storeFile = file("ID72.keystore")
            keyAlias = "com.fast.zai.loans"
            storePassword = "mypass"
            keyPassword = "mypass"
        }
        create("release") {
            keyAlias = "com.fast.zai.loans"
            keyPassword = "mypass"
            storeFile = file("ID72.keystore")
            storePassword = "mypass"
            enableV2Signing = true
        }
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
            signingConfig = signingConfigs.getByName("release")
        }
        debug {
            signingConfig = signingConfigs.getByName("debug")
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.4.3"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {

    implementation("androidx.core:core-ktx:1.9.0")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.6.2")
    implementation("androidx.activity:activity-compose:1.7.2")
    implementation(platform("androidx.compose:compose-bom:2023.03.00"))
    implementation("androidx.compose.ui:ui")
    implementation("androidx.compose.ui:ui-graphics")
    implementation("androidx.compose.ui:ui-tooling-preview")
    implementation("androidx.compose.material3:material3")
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.10.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")

    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
    androidTestImplementation(platform("androidx.compose:compose-bom:2023.03.00"))
    androidTestImplementation("androidx.compose.ui:ui-test-junit4")
    debugImplementation("androidx.compose.ui:ui-tooling")
    debugImplementation("androidx.compose.ui:ui-test-manifest")

    // CameraX
    implementation ("androidx.camera:camera-camera2:1.2.3")
    implementation ("androidx.camera:camera-lifecycle:1.2.3")
    implementation ("androidx.camera:camera-view:1.4.0-alpha01")

    // Icons
    implementation ("androidx.compose.material:material-icons-extended:1.5.3")

    // Coil
    implementation ("io.coil-kt:coil-compose:2.4.0")

    //Permissions
    implementation ("com.google.accompanist:accompanist-permissions:0.31.5-beta")

    implementation ("io.github.vanpra.compose-material-dialogs:datetime:0.8.1-rc")
    coreLibraryDesugaring ("com.android.tools:desugar_jdk_libs:2.0.3")

    //Dagger
    implementation ("com.google.dagger:hilt-android:2.47")
    annotationProcessor ("com.google.dagger:hilt-compiler:2.47")
    kapt ("com.google.dagger:hilt-compiler:2.47")
    implementation ("androidx.hilt:hilt-navigation-compose:1.0.0")

    //retrofit
    implementation ("com.google.code.gson:gson:2.10.1")
    implementation ("com.squareup.retrofit2:retrofit:2.9.0")
    implementation ("com.squareup.retrofit2:converter-gson:2.9.0")



    //AppMetrica
    implementation ("com.yandex.android:mobmetricalib:5.3.0")

    //MyTracker
    implementation ("com.my.tracker:mytracker-sdk:3.1.1")

    //Appsflyer
    implementation ("com.appsflyer:af-android-sdk:6.12.2")


    //dataStore
    implementation("androidx.datastore:datastore-preferences:1.0.0")

    //HMS
    implementation ("com.huawei.hms:push:6.11.0.300")
    implementation ("com.huawei.hms:hmscoreinstaller:6.7.0.300")
    implementation ("com.huawei.hms:ads-identifier:3.4.62.300")
    implementation ("com.huawei.hms:ads-installreferrer:3.4.62.300")
}


kapt{
    correctErrorTypes = true
}
