package com.example.dennismoyseyev.hangman
import android.content.Context
import android.content.res.Resources
import android.widget.EditText
import android.widget.Toast
import com.example.dennismoyseyev.hangman.R.id.input_text
import java.nio.file.Files.find
import java.util.Random

//The data model.
class gameModel(context: Context){
    val random : Random
    val phrase_num: Int
    var phrase: String
    var char_arry: CharArray
    val orginal_phrase_arry: CharArray
    var input: String = ""
    var Mycontext: Context = context
    var attempts: Int = 10


    //The initializing block of code.
    init {
        random = Random()
        phrase_num = rand(1, 10)
        phrase = which_phrase(phrase_num)
        orginal_phrase_arry = phrase.toCharArray()
        char_arry = phrase.toCharArray()
        process_text() //Processes the text so that it is hidden from the user.
    }

    //generates random number between 1 and 10. To randomly select from the 10 phrases.
    private fun rand(from: Int, to: Int) : Int {
        return random.nextInt(to - from) + from
    }

    //This function runs when the game is started and it randomly selects a phrase.
    private fun which_phrase(num: Int) : String {
        return when (num) {
            1 -> "flying  up  in  the  sky"
            2 -> "down  in  the  dumps"
            3 -> "birds  of  a  feather  flock  together"
            4 -> "just  a  drop  in  the  bucket"
            5 -> "head  over  heels  in  love"
            6 -> "draw  the  shortest  straw"
            7 -> "house  of  cards"
            8 -> "salt  of  the  earth"
            9 -> "clean  slate"
            10 -> "get  your  goat"
            else -> {
                "ERROR: When trying to get a phrase for the game. "
            }
        }
    }
    //This covers the letters of the text.
    private fun process_text()
    {
        for (i in char_arry.indices)
        {
            if (char_arry.get(i).equals(' '))
            {
                //Does nothing because this is already a space in the phrase.
            }
            else
            {
                //This will switch the letter for the actually leetter to a ~ to hide the text.
                char_arry.set(i, '~')
            }
        }
    }

    //This will check the letters that were pressed.
    internal fun check_input(inputString: String)
    {
        var found_letter = false
        val convert_string_to_char_array: CharArray = inputString.toCharArray()

        when {
            inputString.length<0 ->
            {
                Toast.makeText(this.Mycontext, "You need to enter a letter or a phrase", Toast.LENGTH_SHORT).show()
            }
            inputString.length==1 ->
            {
                for (letter in orginal_phrase_arry.indices)
                {
                    if(orginal_phrase_arry.get(letter).equals(convert_string_to_char_array.get(0)))
                    {
                        char_arry.set(letter,convert_string_to_char_array.get(0))
                        found_letter = true
                    }
                }
            }
            else -> Toast.makeText(this.Mycontext, "Need to check the whole string against the other string.", Toast.LENGTH_SHORT).show()
        }

        if(!found_letter)
        {
            attempts-=1
        }

    }



}