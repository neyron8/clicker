package com.example.mvvm_app.db

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface PlayerDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addData(player: PlayerEntity)

    @Update
    fun updateData(player: PlayerEntity)

    @Delete
    fun deleteData(player: PlayerEntity)

    @Query("SELECT * FROM user_table WHERE id = :id")
    fun getData (id : Int) : PlayerEntity

    /*@Query("SELECT * FROM user_table ORDER BY id ASC")
    fun readAllData(): LiveData<List<User>>*/
}