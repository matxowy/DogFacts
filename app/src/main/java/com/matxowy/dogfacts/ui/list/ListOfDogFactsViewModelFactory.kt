package com.matxowy.dogfacts.ui.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.matxowy.dogfacts.data.repository.DogFactsRepository

class ListOfDogFactsViewModelFactory(
    private val dogFactsRepository: DogFactsRepository
) : ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return ListOfDogFactsViewModel(
            dogFactsRepository
        ) as T
    }
}