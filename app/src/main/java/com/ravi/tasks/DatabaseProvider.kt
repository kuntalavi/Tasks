package com.ravi.tasks

import android.content.Context
import androidx.room.Room
import com.ravi.tasks.data.local.TaskDatabase

object DatabaseProvider {

    @Volatile
    private var INSTANCE: TaskDatabase? = null

    fun getDatabase(context: Context): TaskDatabase {
        return INSTANCE ?: synchronized(this) {
            Room.databaseBuilder(
                context.applicationContext,
                TaskDatabase::class.java,
                "tasks_DB"
            ).build().also { INSTANCE = it }
        }
    }

}