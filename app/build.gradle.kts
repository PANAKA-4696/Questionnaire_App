plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
}

android {
    namespace = "jp.ac.neec.it.k023c0024.questionnaire_app"
    compileSdk = 36

    defaultConfig {
        applicationId = "jp.ac.neec.it.k023c0024.questionnaire_app"
        minSdk = 24
        targetSdk = 35
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
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "17"
    }
}

dependencies {
    // この一行があるか確認
    implementation("com.google.android.gms:play-services-auth:21.2.0") // 括弧を追加

    // Drive APIも後で使うので必要
    implementation("com.google.api-client:google-api-client-android:2.4.0") // 括弧を追加
    implementation("com.google.apis:google-api-services-drive:v3-rev20240725-2.0.0") // 括弧を追加

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)

    // Coroutinesを追加
    implementation(libs.kotlinx.coroutines.android)
}