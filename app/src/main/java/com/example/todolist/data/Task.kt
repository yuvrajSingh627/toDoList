package com.example.todolist.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "task-table")
data class Task(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0L,

    @ColumnInfo(name = "task-title")
    val title: String = "",

    @ColumnInfo(name = "task-desc")
    val description: String = ""
)

object DummyTask{
    val taskList = listOf(
        Task(title = "DSA", description = "Complete Striver's SDE Sheet"),
        Task(title = "Projects", description = "Complete toDoList and a clone App"),
        Task(title = "Movie Recom", description = "Complete Movie Recom ML project")
    )
}
