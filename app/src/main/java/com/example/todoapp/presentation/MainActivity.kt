package com.example.todoapp.presentation

import android.os.Bundle
import android.widget.Adapter
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import com.example.todoapp.R
import com.example.todoapp.data.local.ToDoItem
import com.example.todoapp.databinding.ActivityMainBinding
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {
    private val viewModel : TodoViewModel by viewModel()

    private lateinit var binding : ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val adapter = TodoAdapter(viewModel)
        binding.recyclerView.adapter = adapter
        observeViewModel(adapter)

        viewModel.getAllTodos()

        handleUserInput()

        binding.chipGroup.setOnCheckedStateChangeListener { _, checkedIds ->
            val checkedId = checkedIds.firstOrNull()

            val weekday = when (checkedId) {
                R.id.chip1 -> "Monday"
                R.id.chip2 -> "Tuesday"
                R.id.chip3 -> "Wednesday"
                R.id.chip4 -> "Thursday"
                R.id.chip5 -> "Friday"
                R.id.chip6 -> "Saturday"
                R.id.chip7 -> "Sunday"

                else -> null
            }

            if (weekday != null) {
                Toast.makeText(applicationContext, "Weekday selected: $weekday", Toast.LENGTH_SHORT).show()
                viewModel.getFilteredTodos(weekday)
            } else {
                viewModel.getFilteredTodos(null)
            }
        }
    }

    private fun handleUserInput() {
        binding.button.setOnClickListener{
            val task = binding.editTextText.text.toString()
            val weekday = binding.spinner.selectedItem.toString()
            if(task.isNotEmpty()){
                val todoItem = ToDoItem(task = task, dayOfWeek = weekday)
                viewModel.insert(todoItem)
                binding.editTextText.text.clear()
            } else {
                Toast.makeText(applicationContext, "Please enter a TODO item", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun observeViewModel(adapter : TodoAdapter){
        lifecycleScope.launch {
            viewModel.allTodos.collectLatest {
                adapter.updateList(it)
            }
        }
    }
}