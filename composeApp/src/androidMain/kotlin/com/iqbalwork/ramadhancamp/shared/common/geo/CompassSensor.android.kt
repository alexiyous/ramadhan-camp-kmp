package com.iqbalwork.ramadhancamp.shared.common.geo

import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import android.content.Context

class AndroidCompassSensor(
    private val context: Context
) : CompassSensor, SensorEventListener {

    private val sensorManager: SensorManager =
        context.getSystemService(Context.SENSOR_SERVICE) as SensorManager

    private val rotationVectorSensor: Sensor? =
        sensorManager.getDefaultSensor(Sensor.TYPE_ROTATION_VECTOR)

    private val _heading = MutableStateFlow(0f)
    override val heading: Flow<Float> = _heading.asStateFlow()
    override val isAvailable: Boolean = sensorManager.getDefaultSensor(Sensor.TYPE_ROTATION_VECTOR) != null

    private val rotationMatrix = FloatArray(9)
    private val orientationAngles = FloatArray(3)

    override fun start() {
        rotationVectorSensor?.let { sensor ->
            sensorManager.registerListener(this, sensor, SensorManager.SENSOR_DELAY_UI)
        }
    }

    override fun stop() {
        sensorManager.unregisterListener(this)
    }

    override fun onSensorChanged(event: SensorEvent?) {
        if (event?.sensor?.type == Sensor.TYPE_ROTATION_VECTOR) {
            SensorManager.getRotationMatrixFromVector(rotationMatrix, event.values)
            SensorManager.getOrientation(rotationMatrix, orientationAngles)
            
            // Convert radians to degrees
            var azimuth = Math.toDegrees(orientationAngles[0].toDouble()).toFloat()
            if (azimuth < 0) {
                azimuth += 360f
            }
            _heading.value = azimuth
        }
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {}
}

class AndroidCompassSensorFactory : KoinComponent {
    private val context: Context by inject()
    fun create(): CompassSensor = AndroidCompassSensor(context)
}

actual fun createCompassSensor(): CompassSensor {
    return AndroidCompassSensorFactory().create()
}
