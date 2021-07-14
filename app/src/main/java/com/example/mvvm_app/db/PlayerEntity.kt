package com.example.mvvm_app.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user_table")
class PlayerEntity(
    @PrimaryKey
    @ColumnInfo(name = "id") val id : Int,
    @ColumnInfo(name = "damage") var damage : Int,
    @ColumnInfo(name = "money") var money : Int
)
{
    fun increaseDamage(num : Int){
        damage += num
    }
    fun increaseMoney(num : Int){
        money += num
    }
}