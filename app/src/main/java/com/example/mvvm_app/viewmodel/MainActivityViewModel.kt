package com.example.mvvm_app.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.mvvm_app.MainActivity
import com.example.mvvm_app.db.PlayerDb
import com.example.mvvm_app.db.PlayerEntity
import com.example.mvvm_app.model.Monster

class MainActivityViewModel(app: Application) : AndroidViewModel(app) {

    fun insertPlayer(player: PlayerEntity){
        val playerDao = PlayerDb.getDatabase(getApplication()).userDao()
        playerDao.addData(player)
    }

    fun updatePlayer(player: PlayerEntity){
        val playerDao = PlayerDb.getDatabase(getApplication()).userDao()
        playerDao.updateData(player)
    }

    fun deletePlayer(player: PlayerEntity){
        val playerDao = PlayerDb.getDatabase(getApplication()).userDao()
        playerDao.deleteData(player)
    }

    fun getPlayer(id : Int): PlayerEntity {
        val playerDao = PlayerDb.getDatabase(getApplication()).userDao()
        return playerDao.getData(id)
    }


    /*fun getAllUsersObservers(): MutableLiveData<List<UserEntity>> {
        return allUsers
    }*/

    /*fun getAllUsers() {
        val userDao = RoomAppDb.getAppDatabase((getApplication()))?.userDao()
        val list = userDao?.getAllUserInfo()
        allUsers.postValue(list)
    }*/

    /*fun updateUserInfo(entity: UserEntity){
        val userDao = RoomAppDb.getAppDatabase(getApplication())?.userDao()
        userDao?.updateUser(entity)
        getAllUsers()
    }

    fun deleteUserInfo(entity: UserEntity){
        val userDao = RoomAppDb.getAppDatabase(getApplication())?.userDao()
        userDao?.deleteUser(entity)
        getAllUsers()
    }*/
}