plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id ("kotlin-kapt")
    id ("dagger.hilt.android.plugin")
    id("com.google.gms.google-services")
    id("com.google.firebase.crashlytics")
}

android {
    namespace = "lo.zaemtoperson.gola"
    compileSdk = 33

    defaultConfig {
        applicationId = "lo.zaemtoperson.gola"
        minSdk = 21
        targetSdk = 33
        versionCode = 1
        versionName = "1.0"
        multiDexEnabled =  true
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
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
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.6.1")
    implementation("androidx.activity:activity-compose:1.7.2")
    implementation(platform("androidx.compose:compose-bom:2023.03.00"))
    implementation("androidx.compose.ui:ui")
    implementation("androidx.compose.ui:ui-graphics")
    implementation("androidx.compose.ui:ui-tooling-preview")
    implementation("androidx.compose.material3:material3")

    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
    androidTestImplementation(platform("androidx.compose:compose-bom:2023.03.00"))
    androidTestImplementation("androidx.compose.ui:ui-test-junit4")
    debugImplementation("androidx.compose.ui:ui-tooling")
    debugImplementation("androidx.compose.ui:ui-test-manifest")

    // CameraX
    implementation ("androidx.camera:camera-camera2:1.0.1")
    implementation ("androidx.camera:camera-lifecycle:1.0.1")
    implementation ("androidx.camera:camera-view:1.0.0-alpha27")

    // Icons
    implementation ("androidx.compose.material:material-icons-extended:1.4.3")

    // Coil
    implementation ("io.coil-kt:coil-compose:2.4.0")

    //Permissions
    implementation ("com.google.accompanist:accompanist-permissions:0.31.5-beta")

    implementation ("io.github.vanpra.compose-material-dialogs:datetime:0.8.1-rc")
    coreLibraryDesugaring ("com.android.tools:desugar_jdk_libs:1.1.6")

    //Dagger
    implementation ("com.google.dagger:hilt-android:2.45")
    annotationProcessor ("com.google.dagger:hilt-compiler:2.45")
    kapt ("com.google.dagger:hilt-compiler:2.45")
    implementation ("androidx.hilt:hilt-navigation-compose:1.0.0")

    //retrofit
    implementation ("com.google.code.gson:gson:2.9.0")
    implementation ("com.squareup.retrofit2:retrofit:2.9.0")
    implementation ("com.squareup.retrofit2:converter-gson:2.9.0")

    implementation ("com.google.code.gson:gson:2.9.0")

    //firebase
    implementation("com.google.firebase:firebase-config-ktx:21.4.1")
    implementation("com.google.firebase:firebase-crashlytics-ktx:18.4.0")
    implementation("com.google.firebase:firebase-analytics-ktx:21.3.0")
    implementation("com.google.firebase:firebase-messaging-ktx:23.2.0")

    //AppMetrica
    implementation ("com.yandex.android:mobmetricalib:5.3.0")

    //MyTracker
    implementation ("com.my.tracker:mytracker-sdk:3.0.0")

    //Appsflyer
    implementation ("com.appsflyer:af-android-sdk:6.12.2")

    //UserX
    implementation ("pro.userx:UserX:4.2.1")
}


kapt{
    correctErrorTypes = true
}
