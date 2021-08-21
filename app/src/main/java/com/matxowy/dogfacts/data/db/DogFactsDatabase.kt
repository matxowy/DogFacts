package com.matxowy.dogfacts.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.matxowy.dogfacts.data.db.entity.DogFactItem
import com.matxowy.dogfacts.internal.Converters

@Database(
    entities = [DogFactItem::class],
    version = 1
)
/*@TypeConverters(Converters::class)*/
abstract class DogFactsDatabase : RoomDatabase() {
    abstract fun dogFactsDao(): DogFactsDao

    companion object {
        @Volatile private var instance: DogFactsDatabase? = null
        private val LOCK = Any()

        operator fun invoke(context: Context) = instance ?: synchronized(LOCK) {
            instance ?: buildDatabase(context).also { instance = it }
        }

        private fun buildDatabase(context: Context) =
            Room.databaseBuilder(context.applicationContext,
                DogFactsDatabase::class.java, "facts.db")
                .build()
    }
}