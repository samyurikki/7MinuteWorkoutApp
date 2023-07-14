package com.learning.a7minutesworkoutapp

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow


@Dao
interface HistoryDao {
    @Insert
    suspend fun createExercise(historyEntity: HistoryEntity)

    @Query("SELECT * FROM `history_table`")
     fun fetchAllDates(): Flow<List<HistoryEntity>>

     }