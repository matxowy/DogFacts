package com.matxowy.dogfacts.ui.detail

import androidx.lifecycle.ViewModel
import com.matxowy.dogfacts.data.repository.DogFactsRepository
import com.matxowy.dogfacts.internal.lazyDeferred

class DetailOfDogFactViewModel(
    private val itemId: Int,
    private val dogFactsRepository: DogFactsRepository
) : ViewModel() {

    val dogFact by lazyDeferred {
        dogFactsRepository.getDogFactById(itemId)
    }
}