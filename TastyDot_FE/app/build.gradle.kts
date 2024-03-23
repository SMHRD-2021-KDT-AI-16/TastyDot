plugins {
    id("com.android.application")
}

android {
    namespace = "com.project.project3"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.project.project3"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

        ndk.abiFilters.add("x86") // x86 ABI 추가
        ndk.abiFilters.add("arm64-v8a")
        ndk.abiFilters.add("armeabi-v7a")
        ndk.abiFilters.add("armeabi")

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
    implementation ("com.squareup.retrofit2:retrofit:2.9.0")
    implementation ("com.squareup.retrofit2:converter-gson:2.9.0") // Gson 변환기를 사용할 경우
    implementation ("com.squareup.retrofit2:converter-scalars:2.9.0")

    // 이미지 가져오는 Glide 라이브러리 추가함
    implementation ("com.github.bumptech.glide:glide:4.12.0")
    implementation(files("libs\\libDaumMapAndroid.jar"))
    annotationProcessor ("com.github.bumptech.glide:compiler:4.12.0")

    implementation("com.kakao.maps.open:android:2.9.5")
    implementation("androidx.viewpager2:viewpager2:1.0.0")
    implementation("me.relex:circleindicator:2.1.6")
    implementation("com.android.volley:volley:1.2.0")
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.11.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    implementation("androidx.legacy:legacy-support-v4:1.0.0")
    implementation("androidx.recyclerview:recyclerview:1.3.2")
    implementation("com.google.android.gms:play-services-maps:18.2.0")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
}