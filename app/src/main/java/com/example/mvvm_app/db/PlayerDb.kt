package com.example.mvvm_app.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [PlayerEntity::class], version = 1, exportSchema = false)
abstract class PlayerDb : RoomDatabase() {

    abstract fun userDao(): PlayerDao

    companion object {
        @Volatile
        private var INSTANCE: PlayerDb? = null

        fun getDatabase(context: Context): PlayerDb{
            val tempInstance = INSTANCE
            if(tempInstance != null){
                return tempInstance
            }
            synchronized(this){
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    PlayerDb::class.java,
                    "player_db"
                ).allowMainThreadQueries().build()
                INSTANCE = instance
                return instance
            }
        }
    }

}
