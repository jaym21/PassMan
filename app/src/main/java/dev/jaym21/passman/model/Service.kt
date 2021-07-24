package dev.jaym21.passman.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "service_table")
data class Service(
        @PrimaryKey(autoGenerate = true)
        val id: Int,
        val name: String,
        val username: String,
        val password: String
)