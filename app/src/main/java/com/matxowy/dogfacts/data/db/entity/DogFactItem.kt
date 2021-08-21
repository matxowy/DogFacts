package com.matxowy.dogfacts.data.db.entity


import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.matxowy.dogfacts.internal.Converters
import org.threeten.bp.ZonedDateTime

@Entity(tableName = "dog_facts")
data class DogFactItem(
    @PrimaryKey(autoGenerate = true)
    var id: Int? = null,
    val fact: String,
    /*@TypeConverters(Converters::class)
    var fetchedTime: ZonedDateTime?*/
)