import org.jetbrains.compose.desktop.application.dsl.TargetFormat
import org.jetbrains.kotlin.gradle.ExperimentalWasmDsl
import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.kotlinMultiplatformLibrary)
    alias(libs.plugins.composeMultiplatform)
    alias(libs.plugins.composeCompiler)
    alias(libs.plugins.composeHotReload)
}

kotlin {
//    androidTarget {
//        compilerOptions {
//            jvmTarget.set(JvmTarget.JVM_11)
//        }
//    }
    
    androidLibrary {
        namespace = "com.iqbalwork.ramadhancamp"
        compileSdk = libs.versions.android.compileSdk.get().toInt()
        experimentalProperties["android.experimental.kmp.enableAndroidResources"] = true
        compilerOptions {
            jvmTarget.set(JvmTarget.JVM_11)
        }

        androidResources {
            enable = true
        }
    }
    
    listOf(
        iosArm64(),
        iosSimulatorArm64()
    ).forEach { iosTarget ->
        iosTarget.binaries.framework {
            baseName = "ComposeApp"
            isStatic = true
        }
    }
    
    jvm()
    
    js {
        browser()
        binaries.executable()
    }
    
    @OptIn(ExperimentalWasmDsl::class)
    wasmJs {
        browser()
        binaries.executable()
    }
    
    sourceSets {
        androidMain.dependencies {
            api(libs.compose.uiToolingPreview)
            api(libs.androidx.activity.compose)
        }
        commonMain.dependencies {
            api(libs.compose.runtime)
            api(libs.compose.foundation)
            api(libs.compose.material3)
            api(libs.compose.ui)
            api(libs.compose.components.resources)
            api(libs.compose.uiToolingPreview)
            api(libs.androidx.lifecycle.viewmodelCompose)
            api(libs.androidx.lifecycle.runtimeCompose)
        }
        commonTest.dependencies {
            api(libs.kotlin.test)
        }
        jvmMain.dependencies {
            api(compose.desktop.currentOs)
            api(libs.kotlinx.coroutinesSwing)
        }
    }
}

//android {
//    namespace = "com.iqbalwork.ramadhancamp"
//    compileSdk = libs.versions.android.compileSdk.get().toInt()
//
//    defaultConfig {
//        applicationId = "com.iqbalwork.ramadhancamp"
//        minSdk = libs.versions.android.minSdk.get().toInt()
//        targetSdk = libs.versions.android.targetSdk.get().toInt()
//        versionCode = 1
//        versionName = "1.0"
//    }
//    packaging {
//        resources {
//            excludes += "/META-INF/{AL2.0,LGPL2.1}"
//        }
//    }
//    buildTypes {
//        getByName("release") {
//            isMinifyEnabled = false
//        }
//    }
//    compileOptions {
//        sourceCompatibility = JavaVersion.VERSION_11
//        targetCompatibility = JavaVersion.VERSION_11
//    }
//}

compose.desktop {
    application {
        mainClass = "com.iqbalwork.ramadhancamp.MainKt"

        nativeDistributions {
            targetFormats(TargetFormat.Dmg, TargetFormat.Msi, TargetFormat.Deb)
            packageName = "com.iqbalwork.ramadhancamp"
            packageVersion = "1.0.0"
        }
    }
}
