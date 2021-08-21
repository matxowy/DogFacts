package com.matxowy.dogfacts.data.network

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.matxowy.dogfacts.data.db.entity.DogFactItem
import com.matxowy.dogfacts.internal.NoConnectivityException

class DogFactsNetworkDataSourceImpl(
    private val dogFactsApiService: DogFactsApiService
) : DogFactsNetworkDataSource {

    private val _downloadedFacts = MutableLiveData<List<DogFactItem>>()
    override val downloadedDogFacts: LiveData<List<DogFactItem>>
        get() = _downloadedFacts

    override suspend fun fetchFacts(number: Int) {
        try {
            val fetchedFacts = dogFactsApiService
                .getFactsAsync(number)
                .await()
            _downloadedFacts.postValue(fetchedFacts)
        } catch (e: NoConnectivityException) {
            Log.e("Connectivity", "Brak internetu", e)
        }
    }
}