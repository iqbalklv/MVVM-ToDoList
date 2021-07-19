package com.miqbalkalevi.todolist.data

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize
import java.text.DateFormat

@Entity(tableName = "table_task")
@Parcelize
data class Task(
    val name: String,
    val isImportant: Boolean = false,
    val isDone: Boolean = false,
    val created: Long = System.currentTimeMillis(),
    @PrimaryKey(autoGenerate = true) val id: Int = 0

): Parcelable {
    val createdFormatted: String
        get() = DateFormat.getDateInstance(DateFormat.FULL).format(created)
}