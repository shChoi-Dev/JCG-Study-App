package com.example.certstudy.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface IncorrectQuizDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(incorrectQuiz: IncorrectQuiz)

    @Query("SELECT * FROM incorrect_quiz ORDER BY id DESC")
    fun getAll(): Flow<List<IncorrectQuiz>>

    @Delete
    suspend fun delete(incorrectQuiz: IncorrectQuiz)
}
