package com.ys.testmultiwindowapp

import android.app.PictureInPictureParams
import android.content.res.Configuration
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.annotation.RequiresApi

class MainActivity : AppCompatActivity() {
    @RequiresApi(Build.VERSION_CODES.S)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        findViewById<Button>(R.id.btn_1).setOnClickListener {
            Toast.makeText(this@MainActivity, "btn1", Toast.LENGTH_SHORT).show()
        }

        findViewById<Button>(R.id.btn_2).setOnClickListener {
            Toast.makeText(this@MainActivity, "btn1", Toast.LENGTH_SHORT).show()
        }
        findViewById<Button>(R.id.btn_3).setOnClickListener {
            Toast.makeText(this@MainActivity, "btn1", Toast.LENGTH_SHORT).show()
        }


        enterPictureInPictureMode(PictureInPictureParams.Builder()
            .setAutoEnterEnabled(true)
            .build())
    }



    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
    }
}