package com.example.mvvm_app.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.mvvm_app.model.Roll

class RollViewModel: ViewModel() {
    private val roll_object: Roll = Roll(0)

    private val _roll_value : MutableLiveData<Int> = MutableLiveData<Int>()

    init{
        _roll_value.value = roll_object.value
    }

    val roll_value : LiveData<Int>
        get() = _roll_value

    fun roll(){
        roll_object.roll()
        _roll_value.value = roll_object.value
    }
}