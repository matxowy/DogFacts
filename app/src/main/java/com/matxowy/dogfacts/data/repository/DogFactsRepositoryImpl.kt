package com.matxowy.dogfacts.data.repository

import androidx.lifecycle.LiveData
import com.matxowy.dogfacts.data.db.DogFactsDao
import com.matxowy.dogfacts.data.db.entity.DogFactItem
import com.matxowy.dogfacts.data.network.DogFactsNetworkDataSource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.threeten.bp.ZonedDateTime

const val NUMBER_OF_FACTS = 30

class DogFactsRepositoryImpl(
    private val dogFactsDao: DogFactsDao,
    private val dogFactsNetworkDataSource: DogFactsNetworkDataSource
) : DogFactsRepository {

    init {
        dogFactsNetworkDataSource.downloadedDogFacts.observeForever { newFacts ->
            persistFetchedFacts(newFacts)
        }
    }

    override suspend fun getDogFacts(): LiveData<List<DogFactItem>> {
        return withContext(Dispatchers.IO) {
            initDogFactsData()
            return@withContext dogFactsDao.getDogFacts()
        }
    }

    override suspend fun getDogFactById(id: Int): LiveData<DogFactItem> {
        TODO("Not yet implemented")
    }

    private fun persistFetchedFacts(fetchedDogFacts: List<DogFactItem>) {
        GlobalScope.launch(Dispatchers.IO) {
            dogFactsDao.insert(fetchedDogFacts)
        }
    }

    private suspend fun initDogFactsData() {
        /*val lastTimeFetched = dogFactsDao.getLastFetchedDogFactsTime()

        if (lastTimeFetched == null) {
            fetchDogFacts()
            return
        }
        if (isFetchDogFactsNeeded(lastTimeFetched)) {
            fetchDogFacts()
        }*/

        fetchDogFacts()

    }

    private suspend fun fetchDogFacts() {
        dogFactsNetworkDataSource.fetchFacts(NUMBER_OF_FACTS)
    }

    private fun isFetchDogFactsNeeded(lastFetchTime: ZonedDateTime): Boolean {
        val thirtyMinutesAgo = ZonedDateTime.now().minusMinutes(30)
        return lastFetchTime.isBefore(thirtyMinutesAgo)
    }
}