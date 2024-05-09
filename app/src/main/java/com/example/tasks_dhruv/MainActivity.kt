package com.example.tasks_dhruv

import android.content.SharedPreferences
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.tasks_dhruv.adapters.TaskDataAdapter
import com.example.tasks_dhruv.databinding.ActivityMainBinding
import com.example.tasks_dhruv.models.Task
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class MainActivity : AppCompatActivity() {
    lateinit var editor: SharedPreferences.Editor
    lateinit var pref: SharedPreferences
    lateinit var binding : ActivityMainBinding
    lateinit var adapter: TaskDataAdapter

    var taskList: MutableList<Task> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        pref = getSharedPreferences("TaskPrefs", MODE_PRIVATE)
        editor = pref.edit()

        taskList.addAll(loadTasksFromSharedPreferences())

        adapter = TaskDataAdapter(taskList, deleteTask)

        binding.rvTasks.adapter = adapter

        binding.rvTasks.layoutManager = LinearLayoutManager(this)

        binding.rvTasks.addItemDecoration(
            DividerItemDecoration(
                this,
                LinearLayoutManager.VERTICAL
            )
        )

        binding.btnAddTask.setOnClickListener {
            addTask()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.action_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.mi_delete_all_tasks -> {
                deleteAllTasks()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun addTask(){
        val taskName:String = binding.etTaskName.text.toString()
        val isMHighPriority:Boolean = binding.swIsHighPirority.isChecked

        val taskToAdd: Task = Task(taskName, isMHighPriority)

        taskList.add(taskToAdd)
        saveTasksToSharedPreferences()
        adapter.notifyDataSetChanged()

        binding.etTaskName.setText("")
        binding.swIsHighPirority.isChecked = false
    }

    private fun deleteAllTasks() {
        taskList.clear()
        editor.clear()
        editor.apply()
        adapter.notifyDataSetChanged()
    }

    val deleteTask = {
            rowNumber:Int ->
        taskList.removeAt(rowNumber)
        saveTasksToSharedPreferences()
        adapter.notifyDataSetChanged()
    }

    private fun saveTasksToSharedPreferences() {
        val gson = Gson()
        val json = gson.toJson(taskList)
        editor.putString("KEY_TASK_LIST", json)
        editor.apply()
    }

    private fun loadTasksFromSharedPreferences(): MutableList<Task> {
        val gson = Gson()
        var taskListFromPrefrence: MutableList<Task> = mutableListOf()
        val json = pref.getString("KEY_TASK_LIST", null)
        val type = object : TypeToken<MutableList<Task>>() {}.type
        if (json != null) {
            taskListFromPrefrence.addAll(gson.fromJson(json, type))
            return taskListFromPrefrence
        }
        return taskListFromPrefrence
    }
}