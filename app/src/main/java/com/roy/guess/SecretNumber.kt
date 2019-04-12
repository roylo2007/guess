package com.roy.guess

import androidx.appcompat.app.AlertDialog
import kotlin.random.Random

class SecretNumber{
    var number = Random.nextInt(10) + 1
    var count = 0

    fun validate(guess_number : Int): Int{
        val diff = guess_number - number
        count ++
        return diff
    }

    fun reset(){
        number = Random.nextInt(10) + 1
        count = 0
    }
}