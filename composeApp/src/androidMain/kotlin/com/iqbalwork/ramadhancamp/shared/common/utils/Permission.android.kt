package com.iqbalwork.ramadhancamp.shared.common.utils

import dev.jordond.compass.permissions.LocationPermissionController
import dev.jordond.compass.permissions.mobile.openSettings

actual fun goToDeviceSettings() {
    LocationPermissionController.openSettings()
}