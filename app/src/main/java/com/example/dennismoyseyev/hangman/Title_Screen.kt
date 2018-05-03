package com.example.dennismoyseyev.hangman

import android.app.*
import android.content.Context
import android.content.Intent
import android.content.pm.ActivityInfo
import android.os.Build
import android.os.Bundle
import android.os.SystemClock
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_title__screen.*

class Title_Screen : Activity() {
    lateinit var builder : Notification.Builder

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        setContentView(R.layout.activity_title__screen)
        bind_button()
    }

    //Starts a new activity when the button is pressed. It makes an intent.
    private fun bind_button()
    {
        start_button.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }

    private fun scheduleNotification(notification: Notification, delay: Int) {

        val notificationIntent = Intent(this, HangManNotification::class.java)
        notificationIntent.putExtra(HangManNotification.NOTIFICATION_ID, 1)
        notificationIntent.putExtra(HangManNotification.NOTIFICATION, notification)
        val pendingIntent = PendingIntent.getBroadcast(this, 0, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT)

        val futureInMillis = SystemClock.elapsedRealtime() + delay
        val alarmManager = getSystemService(Context.ALARM_SERVICE) as AlarmManager
        alarmManager.set(AlarmManager.ELAPSED_REALTIME_WAKEUP, futureInMillis, pendingIntent)
    }

    private fun getNotification(content: String): Notification {
        builder = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            Notification.Builder(this,"com.example.dennismoyseyev.hangman")
                    .setContentTitle("HangMan")
                    .setContentText("Come back and play the game again!! Don't Lose the Game!")
                    .setSmallIcon(R.mipmap.ic_launcher_round)
        } else {
            Notification.Builder(this)
                    .setContentTitle("HangMan")
                    .setContentText("Come back and play the game again!! Don't Lose the Game!")
                    .setSmallIcon(R.mipmap.ic_launcher_round)
        }
        return builder.build()
    }

    override fun onBackPressed()
    {
        scheduleNotification(getNotification("Come back and play the Game!"), 30000)
        super.onBackPressed()
    }

}
