package com.matxowy.dogfacts.data.repository

import androidx.lifecycle.LiveData
import com.matxowy.dogfacts.data.db.DogFactsDao
import com.matxowy.dogfacts.data.db.entity.DogFactItem
import com.matxowy.dogfacts.data.network.DogFactsNetworkDataSource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.threeten.bp.LocalDate

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
        return withContext(Dispatchers.IO) {
            initDogFactsData()
            dogFactsDao.getDogFactById(id)
        }
    }

    override suspend fun getNewDogFactsEntries(): LiveData<List<DogFactItem>> {
        return withContext(Dispatchers.IO) {
            dogFactsDao.deleteOldEntries()
            fetchDogFacts()
            return@withContext dogFactsDao.getDogFacts()
        }
    }

    private fun persistFetchedFacts(fetchedDogFacts: List<DogFactItem>) {
        GlobalScope.launch(Dispatchers.IO) {
            initDateForEntries(fetchedDogFacts)
            dogFactsDao.insert(fetchedDogFacts)
        }
    }

    private fun initDateForEntries(fetchedDogFacts: List<DogFactItem>) {
        for (item in fetchedDogFacts) {
            item.fetchedTime = LocalDate.now()
        }
    }

    private suspend fun initDogFactsData() {
        val lastTimeFetched = dogFactsDao.getLastFetchedDogFactsTime()

        if (lastTimeFetched == null) {
            fetchDogFacts()
            return
        }
        if (isFetchDogFactsNeeded(lastTimeFetched)) {
            dogFactsDao.deleteOldEntries() // if there is older entries than 1 day we're deleting them
            fetchDogFacts()
        }

    }

    private suspend fun fetchDogFacts() {
        dogFactsNetworkDataSource.fetchFacts(NUMBER_OF_FACTS)
    }

    private fun isFetchDogFactsNeeded(lastFetchTime: LocalDate): Boolean {
        val dayAgo = LocalDate.now().minusDays(1)
        return lastFetchTime.isBefore(dayAgo)
    }
}