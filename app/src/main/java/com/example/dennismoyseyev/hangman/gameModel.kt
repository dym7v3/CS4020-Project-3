package com.example.dennismoyseyev.hangman
import java.util.Random

//The data model.
class gameModel{
    val random : Random
    val phrase_num: Int
    var phrase: String
    var char_arry: CharArray
    val orginal_phrase_arry: CharArray

    //The initializing block of code.
    init {
        random = Random()
        phrase_num = rand(1, 10)
        phrase = which_phrase(phrase_num)
        orginal_phrase_arry = phrase.toCharArray()
        char_arry = phrase.toCharArray()
        process_text()
    }

    //generates random number between 1 and 10. To randomly select from the 10 phrases.
    private fun rand(from: Int, to: Int) : Int {
        return random.nextInt(to - from) + from
    }

    //This function runs when the game is started and it randomly selects a phrase.
    private fun which_phrase(num: Int) : String {
        return when (num) {
            1 -> "memory like an elephant"
            2 -> "down in the dumps"
            3 -> "birds of a feather flock together"
            4 -> "just a drop in the bucket"
            5 -> "head over heels in love"
            6 -> "draw the shortest straw"
            7 -> "house of cards"
            8 -> "salt of the earth"
            9 -> "clean slate"
            10 -> "get your goat"
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




}