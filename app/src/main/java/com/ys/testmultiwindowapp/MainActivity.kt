package com.ys.testmultiwindowapp

import android.Manifest.permission.SYSTEM_ALERT_WINDOW
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings
import androidx.activity.result.contract.ActivityResultContracts

class MainActivity : AppCompatActivity() {
    private val settingsLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
        checkOverlayPermission()
    }

    private val launcher = registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
        if (isGranted) {
            startForegroundService(Intent(this, OverlayService::class.java))
        } else {
            goSettings()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        checkOverlayPermission()
    }

    private fun goSettings(){
        settingsLauncher.launch(Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION))
    }

    private fun checkOverlayPermission(){
        if(Settings.canDrawOverlays(this)){
            startForegroundService(Intent(this, OverlayService::class.java))
            finish()
        } else {
            launcher.launch(SYSTEM_ALERT_WINDOW)
        }
    }
}