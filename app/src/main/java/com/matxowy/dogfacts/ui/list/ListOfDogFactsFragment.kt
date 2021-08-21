package com.matxowy.dogfacts.ui.list

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import com.matxowy.dogfacts.R
import com.matxowy.dogfacts.data.network.ConnectivityInterceptorImpl
import com.matxowy.dogfacts.data.network.DogFactsApiService
import com.matxowy.dogfacts.data.network.DogFactsNetworkDataSourceImpl
import com.matxowy.dogfacts.ui.base.ScopedFragment
import kotlinx.android.synthetic.main.list_of_dog_facts_fragment.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.closestKodein
import org.kodein.di.generic.instance

class ListOfDogFactsFragment : ScopedFragment(), KodeinAware {

    override val kodein by closestKodein()
    private val viewModelFactory: ListOfDogFactsViewModelFactory by instance()

    private lateinit var viewModel: ListOfDogFactsViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.list_of_dog_facts_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this, viewModelFactory).get(ListOfDogFactsViewModel::class.java)

        bindUi()
    }

    private fun bindUi() = launch {
        val dogFacts = viewModel.dogFactsEntries.await()
        dogFacts.observe(viewLifecycleOwner, Observer {
            if (it == null) return@Observer

            text.text = it.toString()
        })

    }

}