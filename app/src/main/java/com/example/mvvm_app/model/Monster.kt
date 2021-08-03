package com.example.mvvm_app.model

import android.os.Parcel
import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
class Monster(val imageId: Int, val monsterIcon: Int, var name: String, var hp: Int, val reward : Int) : Parcelable {
    fun takeDamage(damage: Int){
        hp -= damage
    }
}
// ID = name of png/asset
// 1 = 1.png