package com.example.doowat.sensor

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.util.Log
import androidx.compose.foundation.layout.ContextualFlowRow

const val TAG = "Temperature Sensor"
abstract class AndroidSensor(
    val context: Context,
    val sensorFeature: String,
    val sensorType: Int
): SensorEventListener{


    private val doesSensorExist: Boolean = context.packageManager.hasSystemFeature(sensorFeature)
    private lateinit var sensorManager: SensorManager
    private var sensor: Sensor? = null
    var onSensorValueChanged: ((List<Float>) -> Unit)? = null

    fun startListening(){
        if (!doesSensorExist){
            Log.d(TAG, "Sensor does not Exist")
            return
        }
        if (!::sensorManager.isInitialized && sensor == null){
            sensorManager = context.getSystemService(Context.SENSOR_SERVICE) as SensorManager
            sensor = sensorManager.getDefaultSensor(sensorType)
            Log.d(TAG, "Sensor initialized")
        }

        sensorManager.registerListener(this, sensor, SensorManager.SENSOR_DELAY_NORMAL)
        Log.d(TAG, "listener registered")
    }

    fun stopListening(){
        if (!doesSensorExist || !::sensorManager.isInitialized){
            return
        }
        sensorManager.unregisterListener(this)
        Log.d(TAG, "listener unregistered")
    }



    override fun onSensorChanged(event: SensorEvent?) {
       if (!doesSensorExist){
           return
       }
        if (event?.sensor?.type == sensorType){
            onSensorValueChanged?.invoke(event.values.toList())
            Log.d(TAG, "onSensorValueChanged invoked with values ${event.values.toList()}")
        }
    }

    override fun onAccuracyChanged(sensor: Sensor?, p1: Int) {

    }

    fun getSensorValues(listener: (List<Float>) -> Unit){
        if (!doesSensorExist){
            return
        }
        onSensorValueChanged = listener
        Log.d(TAG, "listener $listener assigned to onSensorValueChanged")
    }
}