import org.gradle.kotlin.dsl.commonMain
import org.gradle.kotlin.dsl.get
import org.jetbrains.compose.desktop.application.dsl.TargetFormat
import org.jetbrains.kotlin.gradle.ExperimentalWasmDsl
import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.kotlinMultiplatformLibrary)
    alias(libs.plugins.composeMultiplatform)
    alias(libs.plugins.composeCompiler)
    alias(libs.plugins.composeHotReload)
    alias(libs.plugins.kotlinx.serialization)
}

kotlin {

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
            api(libs.compose.uiTooling)
            api(libs.androidx.activity.compose)
            implementation(libs.koin.android)

            // Ktor
            implementation(libs.ktor.client.okhttp)

            // geo
            implementation(libs.compass.geocoder.mobile)
            implementation(libs.compass.geolocation.mobile)
            implementation(libs.permissions.mobile)
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
            api(libs.napier)

            // Navigation
            implementation(libs.navigation3.ui)
            implementation(libs.navigation3.material3.adaptiveNavigation3)
            implementation(libs.navigation3.lifecycle.viewmodelNavigation3)
         /*   implementation(libs.navigation3.browser)*/

            // DI
            implementation(project.dependencies.platform(libs.koin.bom))
            implementation(libs.koin.core)
            implementation(libs.koin.compose)
            implementation(libs.koin.compose.viewmodel)

            //Ktor
            implementation(libs.ktor.client.auth)
            implementation(libs.ktor.client.core)
            implementation(libs.ktor.client.logging)
            implementation(libs.ktor.client.content.negotiation)
            implementation(libs.ktor.serialization.kotlinx.json)
            implementation(libs.ktor.client.encoding)

            implementation(projects.shared)
            implementation(projects.core.data)
            implementation(projects.core.uikit)

            // Geocoding
            implementation(libs.compass.geocoder)

            // Geolocation
            implementation(libs.compass.geolocation)

            // UI
            implementation(libs.material.icons.extended)

            // Settings Pref
            implementation(libs.multiplatform.settings)
            implementation(libs.multiplatform.settings.coroutines)

        }
        commonTest.dependencies {
            api(libs.kotlin.test)
        }
        jvmMain.dependencies {
            api(compose.desktop.currentOs)
            api(libs.kotlinx.coroutinesSwing)
        }
        nativeMain.dependencies {
            implementation(libs.compass.geocoder.mobile)
            implementation(libs.compass.geolocation.mobile)
            implementation(libs.permissions.mobile)

            implementation(libs.ktor.client.darwin)
        }
        wasmJsMain.dependencies {
            implementation(libs.compass.geocoder.web.googlemaps)
            implementation(libs.compass.geocoder.web)
        }
    }
}

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
