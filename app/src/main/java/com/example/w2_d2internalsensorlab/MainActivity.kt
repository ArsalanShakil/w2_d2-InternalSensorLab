package com.example.w2_d2internalsensorlab

import android.graphics.Color
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatDelegate

class MainActivity : AppCompatActivity(), SensorEventListener {

    private lateinit var sensorManager: SensorManager
    private lateinit var square: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Keeps phone in light mode
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)

        square = findViewById(R.id.tv_square)

        setUpSensorStuff()
    }

    private fun setUpSensorStuff() {
        // Create the sensor manager
        sensorManager = getSystemService(SENSOR_SERVICE) as SensorManager

        // Specify the sensor you want to listen to
        sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)?.also { accelerometer ->
            sensorManager.registerListener(
                this,
                accelerometer,
                SensorManager.SENSOR_DELAY_FASTEST,
                SensorManager.SENSOR_DELAY_FASTEST
            )
        }
    }

    override fun onSensorChanged(event: SensorEvent?) {
        // Checks for the sensor we have registered
        if (event?.sensor?.type == Sensor.TYPE_ACCELEROMETER) {
            //Log.d("Main", "onSensorChanged: sides ${event.values[0]} front/back ${event.values[1]} ")

            // Sides = Tilting phone left(10) and right(-10)
            val sides = event.values[0]

            // Up/Down = Tilting phone up(10), flat (0), upside-down(-10)
            val upDown = event.values[1]

            val zAxis = event.values[2]



            // Changes the colour of the square if it's completely flat
            val color = if (upDown.toInt() == 0 && sides.toInt() == 0 && zAxis.toInt() == 0) {
                //phone falling
                square.text = "\uD83D\uDE40"
                Color.GREEN
            } else if (upDown.toInt() == 0 && sides.toInt() == 0 && zAxis.toInt() != 0) {
                //phone is resting
                square.text = "resting"
                Color.BLUE
            } else if (upDown.toInt() > 0 && sides.toInt() == 0 && zAxis.toInt() < 9){
                //phone is upright
                square.text = "⬇️"
                Color.YELLOW
            } else if (upDown.toInt() < 0 && sides.toInt() == 0 && zAxis.toInt() < 9){
                //phone is upright
                square.text = "⬆️"
                Color.YELLOW
            }
            else if (upDown.toInt() == 0 && sides.toInt() > 0 && zAxis.toInt() < 9){
                //phone is upright sideway or in landscape upDown.toInt() == 0
                square.text = "⬅️"
                Color.CYAN
            } else if (upDown.toInt() == 0 && sides.toInt() < 0 && zAxis.toInt() < 9){
                //phone is upright sideway or in landscape upDown.toInt() == 0
                square.text = "➡️"
                Color.CYAN
            } else if (upDown.toInt() > 0 && sides.toInt() < 0 && zAxis.toInt() < 9){
                //phone is upright sideway or in landscape upDown.toInt() == 0
                square.text = "↘️"
                Color.CYAN
            }
            else if (upDown.toInt() > 0 && sides.toInt() > 0 && zAxis.toInt() < 9){
                //phone is upright sideway or in landscape upDown.toInt() == 0
                square.text = "↙️"
                Color.CYAN
            }
            else if (upDown.toInt() < 0 && sides.toInt() < 0 && zAxis.toInt() < 9){
                //phone is upright sideway or in landscape upDown.toInt() == 0
                square.text = "↗️"
                Color.CYAN
            }
            else if (upDown.toInt() < 0 && sides.toInt() > 0 && zAxis.toInt() < 9){
                //phone is upright sideway or in landscape upDown.toInt() == 0
                square.text = "↖️️"
                Color.CYAN
            }
            else {
                Color.CYAN
            }
            square.setBackgroundColor(color)

            //square.text = "up/down ${upDown.toInt()}\nleft/right ${sides.toInt()}\n" + "z/z ${zAxis.toInt()}"
        }
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
        return
    }

    override fun onDestroy() {
        sensorManager.unregisterListener(this)
        super.onDestroy()
    }
}
