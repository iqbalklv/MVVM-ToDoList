package com.miqbalkalevi.todolist.ui.addedittask

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.miqbalkalevi.todolist.R
import com.miqbalkalevi.todolist.databinding.FragmentAddEditTaskBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AddEditTaskFragment : Fragment(R.layout.fragment_add_edit_task) {

    private val viewModel: AddEditTaskViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val binding = FragmentAddEditTaskBinding.bind(view)

        binding.apply {
            etTaskName.setText(viewModel.taskName)
            cbImportant.isChecked = viewModel.taskImportance
            cbImportant.jumpDrawablesToCurrentState()
            tvTaskDateCreated.isVisible = viewModel.task != null
            tvTaskDateCreated.text = "Created on ${viewModel.task?.createdFormatted}"

        }
    }
}