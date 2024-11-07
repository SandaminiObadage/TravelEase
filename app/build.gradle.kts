plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.kotlin.android)
    alias(libs.plugins.google.gms.google.services)
    alias(libs.plugins.google.android.libraries.mapsplatform.secrets.gradle.plugin)
    kotlin("plugin.serialization") version "1.8.0"


}

android {
    namespace = "com.example.traveleasemad"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.traveleasemad"
        minSdk = 24
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
    kotlinOptions {
        jvmTarget = "1.8"
    }
    android {
        // Other configurations
        buildFeatures {
            viewBinding = true
        }

    }

    dependencies {

        implementation(libs.androidx.core.ktx)
        implementation(libs.androidx.appcompat)
        implementation(libs.material)
        implementation(libs.androidx.activity)
        implementation(libs.androidx.constraintlayout)
        implementation(libs.firebase.auth)
        implementation(libs.play.services.maps)
        implementation(libs.androidx.legacy.support.v4)
        implementation(libs.androidx.lifecycle.livedata.ktx)
        implementation(libs.androidx.lifecycle.viewmodel.ktx)
        implementation(libs.androidx.fragment.ktx)
        implementation(libs.androidx.recyclerview)
        testImplementation(libs.junit)
        androidTestImplementation(libs.androidx.junit)
        androidTestImplementation(libs.androidx.espresso.core)
//        implementation("com.google.android.gms:play-services-maps:18.1.0")
//        implementation("com.google.android.gms:play-services-location:21.0.1")
        implementation ("com.google.android.gms:play-services-maps:18.0.2")
        implementation ("com.google.android.gms:play-services-location:21.0.1")
        implementation ("com.google.maps.android:android-maps-utils:2.3.0")
        implementation ("com.squareup.okhttp3:okhttp:4.9.2")
        implementation ("com.google.maps.android:android-maps-utils:2.2.3")
        implementation ("androidx.constraintlayout:constraintlayout:2.1.4")
        implementation ("androidx.recyclerview:recyclerview:1.2.1")
        implementation ("androidx.appcompat:appcompat:1.7.0")
        implementation ("androidx.appcompat:appcompat:1.6.1")

        implementation   ("com.github.bumptech.glide:glide:4.12.0")
        implementation(platform("io.github.jan-tennert.supabase:bom:2.6.1"))
        implementation("io.github.jan-tennert.supabase:postgrest-kt")
        implementation("io.ktor:ktor-client-android:3.0.0-rc-1")
        implementation("io.github.jan-tennert.supabase:realtime-kt")
        implementation ("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.6.0")

        implementation ("com.github.bumptech.glide:glide:4.14.2")
        annotationProcessor ("com.github.bumptech.glide:compiler:4.14.2")


    }
}
dependencies {
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
    implementation(libs.firebase.database.ktx)
    implementation(libs.androidx.ui.text.android)
}
