package dev.jaym21.passman.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import dev.jaym21.passman.model.Service

@Database(entities = [Service::class], exportSchema = false, version = 1)
abstract class ServiceDatabase : RoomDatabase(){

    //abstract fun to get the DAO
    abstract fun getServiceDAO(): ServiceDAO

    companion object{
        //Singleton prevents multiple instances of database opening at the same time.
        @Volatile
        private var INSTANCE: ServiceDatabase? = null

        fun getDatabase(context: Context): ServiceDatabase {
            // if the INSTANCE is not null, then return it,
            // if it is, then create the database
            return INSTANCE ?: synchronized(this) { //synchronized lock is put here so that two threads can not access database at once
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    ServiceDatabase::class.java,
                    "service_database"
                ).build()
                INSTANCE = instance
                return instance
            }
        }
    }
}