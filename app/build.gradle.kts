plugins {
  alias(libs.plugins.android.application)
  alias(libs.plugins.kotlin.android)
  kotlin("plugin.serialization") version "2.0.20"
  id("kotlin-parcelize")
  id("com.google.devtools.ksp")
}

android {
  namespace = "dev.erhahahaa.eventapp"
  compileSdk = 34

  defaultConfig {
    applicationId = "dev.erhahahaa.eventapp"
    minSdk = 24
    targetSdk = 34
    versionCode = 1
    versionName = "1.0"

    testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
  }

  buildTypes {
    release {
      isMinifyEnabled = true
      proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
    }
  }
  compileOptions {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
  }

  kotlinOptions { jvmTarget = "17" }

  buildFeatures { viewBinding = true }
}

val mockitoAgent = configurations.create("mockitoAgent")

dependencies {
  implementation(libs.androidx.core.ktx)
  implementation(libs.androidx.appcompat)
  implementation(libs.material)
  implementation(libs.androidx.activity)
  implementation(libs.androidx.constraintlayout)
  implementation(libs.androidx.navigation.runtime.ktx)
  implementation(libs.androidx.ui.text.android)
  implementation(libs.androidx.legacy.support.v4)
  implementation(libs.androidx.recyclerview)
  implementation(libs.androidx.room.ktx)
  testImplementation(libs.junit)
  testImplementation(libs.junit.jupiter)
  androidTestImplementation(libs.androidx.junit)
  androidTestImplementation(libs.androidx.espresso.core)

  implementation(libs.retrofit)
  implementation(libs.kotlinx.serialization.json)
  implementation(libs.converter.kotlinx.serialization)
  implementation(libs.glide)
  implementation(libs.androidx.navigation.ui.ktx)
  implementation(libs.androidx.navigation.fragment.ktx)
  implementation(libs.androidx.core.ktx)
  implementation(libs.androidx.room.common)

  testImplementation(libs.mockito.core)
  testImplementation(libs.mockito.kotlin)
  implementation(libs.kotlin.test)
  mockitoAgent(libs.mockito.core) { isTransitive = false }
}

tasks.withType<Test> { jvmArgs("-javaagent:${mockitoAgent.asPath}") }
