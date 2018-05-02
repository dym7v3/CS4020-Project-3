package com.example.dennismoyseyev.hangman
import android.content.Context
import android.widget.EditText
import android.widget.Toast
import java.util.*

//The data model.
class gameModel(context: Context){
    val random : Random
    val phrase_num: Int
    var phrase: String
    var char_arry: CharArray
    private val orginal_phrase_arry: CharArray
    var letters_used: String = ""
    private var Mycontext: Context = context
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
        var valid_string = false
        var add_to_used_letters = true
        val convert_string_to_char_array: CharArray = inputString.toLowerCase().replace(" ", "  ").toCharArray()

        when {
            inputString.isEmpty() ->
            {
                Toast.makeText(this.Mycontext, "You need to enter a letter or a phrase", Toast.LENGTH_SHORT).show()
            }
            inputString.length == 1 ->
            {
                for (letter in orginal_phrase_arry.indices)
                {
                    if(orginal_phrase_arry.get(letter).equals(convert_string_to_char_array.get(0)))
                    {
                        char_arry.set(letter,convert_string_to_char_array.get(0))
                        found_letter = true
                    }
                }
                for (letter in letters_used.indices)
                {
                    if (letters_used.get(letter).equals(convert_string_to_char_array.get(0)))
                    {
                        Toast.makeText(this.Mycontext, "You already used this letter.", Toast.LENGTH_SHORT).show()
                        add_to_used_letters=false
                    }
                }
                //Checks if the letter should be added to the amount of letters used.
                if(add_to_used_letters)
                {
                    letters_used += " " + convert_string_to_char_array.get(0)
                }
            }
            else ->
            {
                //Matches every letter to each other to check if the user guessed the phrase.
                if (orginal_phrase_arry.size == (convert_string_to_char_array.size))
                {
                    for (letter in orginal_phrase_arry.indices) {
                        if (orginal_phrase_arry.get(letter).equals(convert_string_to_char_array.get(letter))) {
                            valid_string = true
                            found_letter = true
                        } else {
                            valid_string = false
                            found_letter = false
                        }
                    }
                }
                else {
                    valid_string = false
                    found_letter = false
                }
            }
        }

        //This means that it looked for the letter or phrase and it was not found in the character sequence
        if(!found_letter)
        {
            attempts-=1
        }

        //If the program logical get here then that mean that the user won the game.
        if(valid_string)
        {
            letters_used=""
            char_arry=phrase.toCharArray()
            Toast.makeText(this.Mycontext, "You won!", Toast.LENGTH_SHORT).show()
        }


    }


}