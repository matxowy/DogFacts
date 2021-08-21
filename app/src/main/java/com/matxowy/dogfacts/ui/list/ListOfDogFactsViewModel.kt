package com.matxowy.dogfacts.ui.list

import androidx.lifecycle.ViewModel
import com.matxowy.dogfacts.data.repository.DogFactsRepository
import com.matxowy.dogfacts.internal.lazyDeferred

class ListOfDogFactsViewModel(
    private val dogFactsRepository: DogFactsRepository
) : ViewModel() {

    val dogFactsEntries by lazyDeferred {
        dogFactsRepository.getDogFacts()
    }
}