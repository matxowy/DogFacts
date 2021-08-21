package com.matxowy.dogfacts.data.repository

import androidx.lifecycle.LiveData
import com.matxowy.dogfacts.data.db.entity.DogFactItem

interface DogFactsRepository {
    suspend fun getDogFacts(): LiveData<List<DogFactItem>>

    suspend fun getDogFactById(id: Int): LiveData<DogFactItem>
}