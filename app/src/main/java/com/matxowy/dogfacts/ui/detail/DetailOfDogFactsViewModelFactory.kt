package com.matxowy.dogfacts.ui.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.matxowy.dogfacts.data.repository.DogFactsRepository

class DetailOfDogFactsViewModelFactory(
    private val dogFactsRepository: DogFactsRepository
): ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return DetailOfDogFactViewModel(
            dogFactsRepository
        ) as T
    }
}