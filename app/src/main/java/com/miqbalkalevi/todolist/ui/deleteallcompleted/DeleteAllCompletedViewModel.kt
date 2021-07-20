package com.miqbalkalevi.todolist.ui.deleteallcompleted

import androidx.lifecycle.ViewModel
import com.miqbalkalevi.todolist.data.TaskDao
import com.miqbalkalevi.todolist.di.ApplicationScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DeleteAllCompletedViewModel @Inject constructor(
    private val taskDao: TaskDao,
    @ApplicationScope private val applicationScope: CoroutineScope
) : ViewModel() {

    fun onConfirmClick() = applicationScope.launch {
        taskDao.deleteAllCompletedTask()
    }
}