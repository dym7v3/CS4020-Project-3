package com.example.dennismoyseyev.hangman

import android.app.Activity
import android.content.pm.ActivityInfo
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.view.KeyEvent
import android.view.inputmethod.EditorInfo
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*



class MainActivity : Activity() {
    lateinit var input_text: EditText
    var model: gameModel = gameModel(this) //The data model.

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        setContentView(R.layout.activity_main)
        my_pharse.text = String(model.char_arry)
        input_text = findViewById(R.id.input_text) as EditText
    }


    //This will be the event handler that will look at the text and process it.
    override fun onResume() {
        super.onResume()
        input_text.setOnEditorActionListener( { textView, action, event ->
            var handled = false
            if (action == EditorInfo.IME_ACTION_DONE)
            {
                model.check_input(input_text.text.toString())
                my_pharse.text = String(model.char_arry)
                amount_attempts_left.text = model.attempts.toString()
                display_letters.text = model.letters_used
                input_text.text.clear()
            }
            handled
        })
    }

}
