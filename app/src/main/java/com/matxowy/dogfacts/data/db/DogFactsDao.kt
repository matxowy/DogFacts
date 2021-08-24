package com.matxowy.dogfacts.data.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.matxowy.dogfacts.data.db.entity.DogFactItem
import org.threeten.bp.LocalDate

@Dao
interface DogFactsDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(dogFactEntries: List<DogFactItem>)

    @Query("SELECT * FROM dog_facts")
    fun getDogFacts(): LiveData<List<DogFactItem>>

    @Query("SELECT * FROM dog_facts WHERE id = :itemId")
    fun getDogFactById(itemId: Int): LiveData<DogFactItem>

    @Query("SELECT fetchedTime FROM dog_facts")
    fun getLastFetchedDogFactsTime(): LocalDate

    @Query("DELETE FROM dog_facts")
    fun deleteOldEntries()
}