package com.example.mvvm_app.viewmodel

import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.mvvm_app.model.Monster

class MonsterViewModel(mons: Monster): ViewModel() {
    private val monster = mons
    private val _hp : MutableLiveData<Int> = MutableLiveData<Int>()
    private val _killed = MutableLiveData<Boolean>()

    init{
        _hp.value = monster.hp
        _killed.value = false
    }

    val hp : LiveData<Int>
        get() = _hp

    val killed: LiveData<Boolean>
        get() = _killed

    fun hit(damage : Int){
        monster.takeDamage(damage)
        _hp.value = monster.hp
    }

    fun checkDeath(){
        if(_hp.value!! <= 0){
            _killed.value = true
        }
    }


}

class MyViewModelFactory(
    private val mons: Monster
): ViewModelProvider.NewInstanceFactory() {
    override fun <T: ViewModel> create(modelClass:Class<T>): T {
        return MonsterViewModel(mons) as T
    }
}
