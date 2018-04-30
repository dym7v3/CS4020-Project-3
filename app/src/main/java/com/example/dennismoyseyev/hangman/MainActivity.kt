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

//The data model.
var model: gameModel = gameModel()


class MainActivity : Activity() {
    lateinit var field: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        setContentView(R.layout.activity_main)
        my_pharse.text = String(model.char_arry)
        field = findViewById(R.id.input_text) as EditText
    }



    override fun onResume() {
        super.onResume()
        field.setOnEditorActionListener( { textView, action, event ->
            var handled = false
            if (action == EditorInfo.IME_ACTION_DONE) {
                Toast.makeText(getApplicationContext(), "Your toast message.", Toast.LENGTH_SHORT).show()
            }
            handled
        })
    }

}
