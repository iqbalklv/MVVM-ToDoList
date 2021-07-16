package com.miqbalkalevi.todolist.data

import androidx.room.*
import com.miqbalkalevi.todolist.ui.tasks.SortOrder
import kotlinx.coroutines.flow.Flow

@Dao
interface TaskDao {

    fun getTasks(query: String, sortOrder: SortOrder, hideCompleted: Boolean): Flow<List<Task>> =
        when(sortOrder){
            SortOrder.BY_NAME -> getTasksSortedByName(query, hideCompleted)
            SortOrder.BY_DATE -> getTasksSortedByDateCreated(query, hideCompleted)
        }

    @Query("SELECT * FROM table_task WHERE (isDone != :hideCompleted or isDone = 0) AND name LIKE '%' ||  :searchQuery || '%' ORDER BY isImportant DESC, name ASC")
    fun getTasksSortedByName(searchQuery: String, hideCompleted: Boolean): Flow<List<Task>>

    @Query("SELECT * FROM table_task WHERE (isDone != :hideCompleted or isDone = 0) AND name LIKE '%' ||  :searchQuery || '%' ORDER BY isImportant DESC, created ASC")
    fun getTasksSortedByDateCreated(searchQuery: String, hideCompleted: Boolean): Flow<List<Task>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(task: Task)

    @Update
    suspend fun update(task: Task)

    @Delete
    suspend fun delete(task: Task)

    //suspend: move the function to another thread to avoid blocking.
}