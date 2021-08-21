package com.matxowy.dogfacts.data.network

import androidx.lifecycle.LiveData
import com.matxowy.dogfacts.data.db.entity.DogFactItem

interface DogFactsNetworkDataSource {
    val downloadedDogFacts: LiveData<List<DogFactItem>>

    suspend fun fetchFacts(number: Int)
}