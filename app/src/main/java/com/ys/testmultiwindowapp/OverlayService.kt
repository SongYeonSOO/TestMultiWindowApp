package com.ys.testmultiwindowapp

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.Context
import android.content.Intent
import android.graphics.PixelFormat
import android.os.Build
import android.os.IBinder
import android.view.*
import android.widget.ImageButton
import androidx.core.app.NotificationCompat


class OverlayService : Service() {
    private var view: View? = null

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    override fun onCreate() {
        super.onCreate()
        createNotificationChannel()
        val inflater = getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

        val wm = getSystemService(WINDOW_SERVICE) as WindowManager

        val params = WindowManager.LayoutParams(
            WindowManager.LayoutParams.WRAP_CONTENT,
            WindowManager.LayoutParams.WRAP_CONTENT,
            WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY,
            WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE or
                    WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL or
                    WindowManager.LayoutParams.FLAG_WATCH_OUTSIDE_TOUCH,
            PixelFormat.TRANSLUCENT
        )
        params.gravity = Gravity.RIGHT or Gravity.BOTTOM
        view = inflater.inflate(R.layout.layout_overlay, null).apply {
            findViewById<ImageButton>(R.id.btn_img)?.setOnTouchListener { view, motionEvent ->
                when (motionEvent.action) {
                    MotionEvent.ACTION_DOWN -> android.util.Log.d("test", "DOWN ")
                    MotionEvent.ACTION_UP -> android.util.Log.d("test", "UP")
                    MotionEvent.ACTION_MOVE -> android.util.Log.d("test", "MOVE")
                }
                return@setOnTouchListener false
            }
        }.also {
            wm.addView(it, params)
        }
    }

    private fun createNotificationChannel(){
        val strId = "CHANNEL_ID"
        val strTitle = "CHANNEL_NAME"
        val notificationManager =
            getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        var channel = notificationManager.getNotificationChannel(strId)
        if (channel == null) {
            channel = NotificationChannel(strId, strTitle, NotificationManager.IMPORTANCE_HIGH)
            notificationManager.createNotificationChannel(channel)
        }
        val notification: Notification = NotificationCompat.Builder(this, strId).build()
        startForeground(1, notification)
    }

    override fun onDestroy() {
        super.onDestroy()
        stopForeground(true)
        (getSystemService(WINDOW_SERVICE) as? WindowManager)?.run {
            view ?: return@run
            this.removeView(view)
            view = null
        }
    }
}