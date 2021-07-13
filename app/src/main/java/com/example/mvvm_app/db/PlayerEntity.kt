package com.example.mvvm_app.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user_table")
data class PlayerEntity(
    @PrimaryKey
    @ColumnInfo(name = "id") val id : Int,
    @ColumnInfo(name = "damage") val damage : Int,
    @ColumnInfo(name = "money") val money : Int
)