package com.iqbalwork.ramadhancamp.feature.about.data.repository

import com.iqbalwork.ramadhancamp.feature.about.domain.model.OssLicense
import com.iqbalwork.ramadhancamp.feature.about.domain.repository.OssLicenseRepository

class OssLicenseRepositoryImpl : OssLicenseRepository {

    override fun getOssLicenses(): List<OssLicense> = listOf(
        OssLicense(
            name = "Kotlin",
            author = "JetBrains",
            licenseType = "Apache 2.0",
            url = "https://kotlinlang.org/",
        ),
        OssLicense(
            name = "Kotlinx Coroutines",
            author = "JetBrains",
            licenseType = "Apache 2.0",
            url = "https://github.com/Kotlin/kotlinx.coroutines",
        ),
        OssLicense(
            name = "Kotlinx Serialization",
            author = "JetBrains",
            licenseType = "Apache 2.0",
            url = "https://github.com/Kotlin/kotlinx.serialization",
        ),
        OssLicense(
            name = "Kotlinx Datetime",
            author = "JetBrains",
            licenseType = "Apache 2.0",
            url = "https://github.com/Kotlin/kotlinx-datetime",
        ),
        OssLicense(
            name = "Compose Multiplatform",
            author = "JetBrains",
            licenseType = "Apache 2.0",
            url = "https://www.jetbrains.com/lp/compose-multiplatform/",
        ),
        OssLicense(
            name = "Material 3",
            author = "Google/AndroidX",
            licenseType = "Apache 2.0",
            url = "https://developer.android.com/jetpack/compose",
        ),
        OssLicense(
            name = "Navigation 3",
            author = "JetBrains/AndroidX",
            licenseType = "Apache 2.0",
            url = "https://developer.android.com/jetpack/compose",
        ),
        OssLicense(
            name = "Lifecycle ViewModel",
            author = "AndroidX",
            licenseType = "Apache 2.0",
            url = "https://developer.android.com/jetpack/compose",
        ),
        OssLicense(
            name = "Material Icons Extended",
            author = "JetBrains",
            licenseType = "Apache 2.0",
            url = "https://developer.android.com/jetpack/compose",
        ),
        OssLicense(
            name = "Koin",
            author = "Insert-Koin",
            licenseType = "Apache 2.0",
            url = "https://insert-koin.io/",
        ),
        OssLicense(
            name = "Ktor",
            author = "JetBrains",
            licenseType = "Apache 2.0",
            url = "https://ktor.io/",
        ),
        OssLicense(
            name = "Room",
            author = "AndroidX",
            licenseType = "Apache 2.0",
            url = "https://developer.android.com/training/data-storage/room",
        ),
        OssLicense(
            name = "SQLDelight",
            author = "Cash App",
            licenseType = "Apache 2.0",
            url = "https://cashapp.github.io/sqldelight/",
        ),
        OssLicense(
            name = "Multiplatform Settings",
            author = "Russhwolf",
            licenseType = "Apache 2.0",
            url = "https://github.com/russhwolf/multiplatform-settings",
        ),
        OssLicense(
            name = "Napier",
            author = "Aakira",
            licenseType = "Apache 2.0",
            url = "https://github.com/AAkira/Napier",
        ),
        OssLicense(
            name = "Coil",
            author = "Coil-kt",
            licenseType = "Apache 2.0",
            url = "https://coil-kt.org/",
        ),
        OssLicense(
            name = "Compass",
            author = "Jordond",
            licenseType = "Apache 2.0",
            url = "https://github.com/jordond/Compass",
        ),
        OssLicense(
            name = "Moko Permissions",
            author = "IceRock",
            licenseType = "Apache 2.0",
            url = "https://github.com/icerockdev/moko",
        ),
        OssLicense(
            name = "Alarmee",
            author = "Tweener",
            licenseType = "Apache 2.0",
            url = "https://github.com/Tweener/alarmee",
        ),
        OssLicense(
            name = "ConstraintLayout Compose",
            author = "Annexflow",
            licenseType = "Apache 2.0",
            url = "https://github.com/nicstange/compose-constraintlayout-multiplatform",
        ),
        OssLicense(
            name = "Inspektify",
            author = "Bvantur",
            licenseType = "Apache 2.0",
            url = "https://github.com/bvantur/inspektify",
        ),
        OssLicense(
            name = "BuildKonfig",
            author = "CodingFeline",
            licenseType = "Apache 2.0",
            url = "https://github.com/nicstange/buildkonfig",
        ),
        OssLicense(
            name = "Compose Multiplatform Media Player",
            author = "ChainTech",
            licenseType = "Apache 2.0",
            url = "https://github.com/nicstange/compose-multiplatform-media-player",
        ),
        OssLicense(
            name = "eQuran.id API",
            author = "eQuran.id",
            licenseType = "See Terms",
            url = "https://equran.id/terms",
        ),
        OssLicense(
            name = "AndroidX SQLite",
            author = "Google/AndroidX",
            licenseType = "Apache 2.0",
            url = "https://developer.android.com/reference/kotlin/androidx/sqlite/package-summary",
        ),
    )

    override fun getAppVersion(): String = "1.0.0"
}
