package com.example.batteryshowinfo

import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.util.Log
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.batteryshowinfo.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity(), BatteryListener {

    private lateinit var binding: ActivityMainBinding
    private lateinit var batteryReceiver: BatteryReceiver
    private val STATE_APP = "StateAppMainActivity"

    // Variables interfaz
    private lateinit var currentStateBattery: TextView
    private lateinit var energySourceBattery: TextView
    private lateinit var percentageBattery: TextView
    private lateinit var batteryImage: ImageView
    private lateinit var generalStateBattery: TextView
    private lateinit var temperatureBattery: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // animation
        val mainLayout = binding.mainLayout
        val animation = AnimationUtils.loadAnimation(this, R.anim.fade_in_and_translate)
        mainLayout.startAnimation(animation)

        // Se establece conexion con los componentes de la interfaz
        currentStateBattery = binding.currentStateBattery
        energySourceBattery = binding.energySourceBattery
        percentageBattery = binding.percentageBattery
        batteryImage = binding.batteryImage
        generalStateBattery = binding.generalStateBattery
        temperatureBattery = binding.temperatureBattery

    }

    override fun onStart() {
        super.onStart()
        Log.d(STATE_APP, "Start")
        // Registrando receiver
        batteryReceiver = BatteryReceiver(this)

        // Registrar el receiver para obtener la información de la batería
        // Intent proporcionado por documentación de AndroidStudio
        val filter = IntentFilter(Intent.ACTION_BATTERY_CHANGED)
        registerReceiver(batteryReceiver, filter)
        Log.d("ReceiverState", "Registro satisfactorio")
    }

    override fun onStop() {
        super.onStop()
        Log.d(STATE_APP, "Stop")
        Log.d("ReceiverState", "Desregistro satisfactorio")
        unregisterReceiver(batteryReceiver)
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(STATE_APP, "Destroy")

    }

    override fun onBatteryInfoReceived(
        isCharging: Boolean,
        energySourceBattery: String,
        batteryPct: Float,
        batteryState: String,
        batteryTemperature: Int
    ) {
        // Mostrar datos en interfaz
        currentStateBattery.text =
            when {
                isCharging -> "El dispositivo está cargando"
                else -> "El dispositivo no está cargando"
            }
        batteryImage.setImageResource(
            when {
                batteryPct.toInt() == 0 -> R.drawable.baseline_battery_0_bar_24
                batteryPct.toInt() in 1..20 -> R.drawable.baseline_battery_2_bar_24
                batteryPct.toInt() in 21 .. 40 -> R.drawable.baseline_battery_3_bar_24
                batteryPct.toInt() in 41 .. 60 -> R.drawable.baseline_battery_4_bar_24
                batteryPct.toInt() in 61 .. 80 -> R.drawable.baseline_battery_5_bar_24
                batteryPct.toInt() in 81 .. 99 -> R.drawable.baseline_battery_6_bar_24
                else -> R.drawable.baseline_battery_full_24
            }
        )
        this.energySourceBattery.text = energySourceBattery
        percentageBattery.text = "${batteryPct.toInt()}%"
        generalStateBattery.text = batteryState
        temperatureBattery.text = "$batteryTemperature °C"

    }
}