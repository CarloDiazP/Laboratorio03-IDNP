package com.example.batteryshowinfo

import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.view.animation.AnimationUtils
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.batteryshowinfo.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var batteryReceiver: BatteryReceiver

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
        // Registrando receiver

        batteryReceiver = BatteryReceiver()

        // Registrar el receiver para obtener la información de la batería
        // Intent proporcionado por documentación de AndroidStudio
        val filter = IntentFilter(Intent.ACTION_BATTERY_CHANGED)
        registerReceiver(batteryReceiver, filter)

    }

    override fun onDestroy() {
        super.onDestroy()
        unregisterReceiver(batteryReceiver)
    }
}