package com.example.batteryshowinfo

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.BatteryManager
import android.util.Log
import android.widget.Toast

class BatteryReceiver(private val listener: BatteryListener) : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {

        // Obtener informacíon de la batería
        val currentState: Int = intent.getIntExtra(BatteryManager.EXTRA_STATUS, -1) ?: -1
        val isCharging: Boolean = currentState == BatteryManager.BATTERY_STATUS_CHARGING || currentState == BatteryManager.BATTERY_STATUS_FULL

        // Ver el tipo de fuente de carga
        val chargePlug: Int = intent.getIntExtra(BatteryManager.EXTRA_PLUGGED, -1) ?: -1
        val usbCharge: Boolean = chargePlug == BatteryManager.BATTERY_PLUGGED_USB
        val acCharge: Boolean = chargePlug == BatteryManager.BATTERY_PLUGGED_AC

        // Selecciona el tipo de fuente de carga
        val energySourceBattery = when {
            // las variables actual como booleano, la que sea tru retornará el string señalado
            usbCharge -> "USB"
            acCharge -> "AC"
            else -> "No charging"
        }
        // Se obtiene el % de la batería
        val level: Int = intent.getIntExtra(BatteryManager.EXTRA_LEVEL, -1)
        val scale: Int = intent.getIntExtra(BatteryManager.EXTRA_SCALE, -1)
        val batteryPct: Float = level * 100 / scale.toFloat()

        // Obtener información del estado de la batería
        val batteryHealth = intent.getIntExtra(BatteryManager.EXTRA_HEALTH, BatteryManager.BATTERY_HEALTH_UNKNOWN)
        val batteryState = when (batteryHealth) {
            BatteryManager.BATTERY_HEALTH_GOOD -> "En buen estado"
            BatteryManager.BATTERY_HEALTH_COLD -> "Fría"
            BatteryManager.BATTERY_HEALTH_DEAD -> "Muerta"
            BatteryManager.BATTERY_HEALTH_OVERHEAT -> "Sobrecalentada"
            BatteryManager.BATTERY_HEALTH_OVER_VOLTAGE -> "Sobre voltaje"
            BatteryManager.BATTERY_HEALTH_UNSPECIFIED_FAILURE -> "Fallo no especificado"
            BatteryManager.BATTERY_HEALTH_UNKNOWN -> "Desconocido"
            else -> "Desconocido"
        }

        // Obtener temperatura
        val batteryTemperature: Int = intent.getIntExtra(BatteryManager.EXTRA_TEMPERATURE, -1) / 10


        /////////////////////////////////////////////
        /*
        val batteryInfo = """
            Cargando: $isCharging
            Tipo de carga: $energySourceBattery
            Porcentaje de batería: $batteryPct%
            Estado batería: $batteryState
            Temperatura: $batteryTemperature °C
        """
*/
        // Mostrar la información  de prueba desde Log
        listener.onBatteryInfoReceived(isCharging, energySourceBattery, batteryPct, batteryState, batteryTemperature)
        //Log.d("BatteryInfo", batteryInfo)


    }
}