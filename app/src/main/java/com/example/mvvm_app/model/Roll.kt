package com.example.mvvm_app.model

import android.util.Log
import java.lang.IllegalArgumentException

class Roll (n: Int) {
    var value: Int = 0

    init {
        if(n in 0..6){
            value = n
        } else {
            Log.e("Die", "Illegal die value $n")
            throw IllegalArgumentException("Illegal die value $n")
        }
    }

    fun roll() {
        value = (1..6).random()
    }
}