package com.learning.a7minutesworkoutapp

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName="history_table")
data class HistoryEntity(
    @PrimaryKey
    val date: String
)
