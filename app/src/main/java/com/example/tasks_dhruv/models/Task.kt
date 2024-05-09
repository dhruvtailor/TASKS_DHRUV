package com.example.tasks_dhruv.models

class Task {
    var taskName: String
    var isHighPriority: Boolean

    constructor(taskName: String, isHighPriority: Boolean) {
        this.taskName = taskName
        this.isHighPriority = isHighPriority
    }
}