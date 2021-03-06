package com.example.dennismoyseyev.hangman

import android.app.*
import android.content.Context
import android.content.Intent
import android.content.pm.ActivityInfo
import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.EditText
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*
import android.app.AlarmManager
import android.os.SystemClock
import android.app.PendingIntent



class MainActivity : Activity()
{
    lateinit var notificationman: NotificationManager
    lateinit var builder : Notification.Builder
    lateinit var input_text: EditText
    private var model: gameModel = gameModel(this) //The data model.


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        setContentView(R.layout.activity_main)
        my_pharse.text = String(model.char_arry)
        input_text = findViewById(R.id.input_text)
        bind_button()
        notificationman = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
    }


    //This will be the event handler that will look at the text and process it.
    override fun onResume() {
        super.onResume()
        input_text.setOnEditorActionListener({ _, action, _ ->
            val handled = false
            if (action == EditorInfo.IME_ACTION_DONE) {
                model.check_input(input_text.text.toString())
                my_pharse.text = String(model.char_arry)
                amount_attempts_left.text = model.attempts.toString()
                display_letters.text = model.letters_used
                input_text.text.clear()
                if (model.attempts < 10) {
                    showHangMan()
                }
            }
            handled
        })
    }

    private fun showHangMan() {
        when (model.attempts) {
            9 -> Ground.visibility = View.VISIBLE
            8 -> Pole.visibility = View.VISIBLE
            7 -> top.visibility = View.VISIBLE
            6 -> Rope.visibility = View.VISIBLE
            5 -> Head.visibility = View.VISIBLE
            4 -> Body.visibility = View.VISIBLE
            3 -> Left_Arm.visibility = View.VISIBLE
            2 -> Right_Arm.visibility = View.VISIBLE
            1 -> Left_Leg.visibility = View.VISIBLE
            0 -> Right_Leg.visibility = View.VISIBLE
        }

        if (model.attempts < 1) {
            Toast.makeText(this, "You lost!", Toast.LENGTH_SHORT).show()
            input_text.isEnabled = false
            quit_button.text = getString(R.string.play_again)
        }


    }

    //The button that will check if the user really wants to exit.
    private fun bind_button() {
        quit_button.setOnClickListener {
            val builder = android.app.AlertDialog.Builder(this)
            builder.setCancelable(false)
            if (model.attempts < 1)
            {
                builder.setMessage("Do you want to Play Again?")
            }
            else
            {
                builder.setMessage("Are you sure you want to Exit?")
            }
        builder.setPositiveButton("Yes") { _, _ ->
            if(model.attempts<1)
            {
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
                finish()
            }
            else
                finish()
        }
        builder.setNegativeButton("No") { dialog, _ ->
            dialog.cancel()
        }
        val alert = builder.create()
        alert.show()
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


