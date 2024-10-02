package com.example.batteryshowinfo

import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.util.Log
import android.view.animation.AnimationUtils
import androidx.appcompat.app.AppCompatActivity
import com.example.batteryshowinfo.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity(), BatteryListener {

    private lateinit var binding: ActivityMainBinding
    private lateinit var batteryReceiver: BatteryReceiver
    private val STATE_APP = "StateAppMainActivity"

    // Variables interfaz
    private var currentStateBattery = binding.currentStateBattery
    private var energySourceBattery = binding.energySourceBattery
    private var percentageBattery = binding.percentageBattery
    private var batteryImage = binding.batteryImage
    private var generalStateBattery = binding.generalStateBattery
    private var temperatureBattery = binding.temperatureBattery
    // batteryImage.setImageResource(R.drawable.baseline_battery_0_bar_24)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // animation
        val mainLayout = binding.mainLayout
        val animation = AnimationUtils.loadAnimation(this, R.anim.fade_in_and_translate)
        mainLayout.startAnimation(animation)



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

    }

    override fun onStop() {
        super.onStop()
        Log.d(STATE_APP, "Stop")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(STATE_APP, "Destroy")
        unregisterReceiver(batteryReceiver)
    }

    override fun onBatteryInfoReceived(
        isCharging: Boolean,
        energySourceBattery: String,
        batteryPct: Float,
        batteryState: String,
        batteryTemperature: Int
    ) {

        val batteryInfo = """
            Cargando: $isCharging
            Tipo de carga: $energySourceBattery
            Porcentaje de batería: $batteryPct%
            Estado batería: $batteryState
            Temperatura: $batteryTemperature °C
        """
        // Log.d("MainActivityShowInfo", batteryInfo)
    }
}