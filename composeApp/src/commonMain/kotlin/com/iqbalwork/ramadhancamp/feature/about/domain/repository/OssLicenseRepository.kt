package com.iqbalwork.ramadhancamp.feature.about.domain.repository

import com.iqbalwork.ramadhancamp.feature.about.domain.model.OssLicense

interface OssLicenseRepository {
    fun getOssLicenses(): List<OssLicense>
    fun getAppVersion(): String
}
