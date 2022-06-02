package com.example.sensorslist

import android.hardware.Sensor
import android.hardware.SensorManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.Spinner

class MainActivity : AppCompatActivity() {

    val environmentSensorsTypes = listOf<Int>(
                Sensor.TYPE_AMBIENT_TEMPERATURE,
                Sensor.TYPE_LIGHT,
                Sensor.TYPE_PRESSURE,
                Sensor.TYPE_RELATIVE_HUMIDITY,
            )
    val positionSensors = listOf<Int>(
                Sensor.TYPE_GAME_ROTATION_VECTOR,
                Sensor.TYPE_GEOMAGNETIC_ROTATION_VECTOR,
                Sensor.TYPE_MAGNETIC_FIELD,
                Sensor.TYPE_MAGNETIC_FIELD_UNCALIBRATED,
                Sensor.TYPE_PROXIMITY
            )
    val motionSensors = listOf<Int>(
                Sensor.TYPE_ACCELEROMETER,
                Sensor.TYPE_GRAVITY,
                Sensor.TYPE_GYROSCOPE,
                Sensor.TYPE_GYROSCOPE_UNCALIBRATED,
                Sensor.TYPE_LINEAR_ACCELERATION,
                Sensor.TYPE_ROTATION_VECTOR,
                Sensor.TYPE_SIGNIFICANT_MOTION,
                Sensor.TYPE_STEP_COUNTER,
                Sensor.TYPE_STEP_DETECTOR
            )
    lateinit var spinner: Spinner
    lateinit var sensorsLv: ListView
    lateinit var sensorsList: List<Sensor>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val sm = getSystemService(SENSOR_SERVICE) as SensorManager
        sensorsList = sm.getSensorList(Sensor.TYPE_ALL)
        spinner = findViewById(R.id.spinner)
        sensorsLv = findViewById(R.id.sensors_lv)
        ArrayAdapter.createFromResource(
            this,
            R.array.sensors_types,
            android.R.layout.simple_spinner_item
        ).also {adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spinner.adapter = adapter
            spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {

                override fun onNothingSelected(parent: AdapterView<*>?) {
                    TODO("Not yet implemented")
                }

                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    onItemSelected(position)
                }
            }
        }
    }

    fun onItemSelected(position: Int) {
        val sensorsToDisplay = mutableListOf<String>()
        for (sensor in sensorsList) {
            if (position == 0) {
                if (sensor.type in environmentSensorsTypes) {
                    sensorsToDisplay.add(sensor.name)
                }
            }
            else if (position == 1) {
                if (sensor.type in positionSensors) {
                    sensorsToDisplay.add(sensor.name)
                }
            }
            else if (position == 2) {
                if (sensor.type in motionSensors) {
                    sensorsToDisplay.add(sensor.name)
                }
            }
        }
        val adapter = ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, sensorsToDisplay)
        sensorsLv.adapter = adapter
    }
}