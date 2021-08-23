package com.matxowy.dogfacts.ui.list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.matxowy.dogfacts.R
import com.matxowy.dogfacts.data.db.entity.DogFactItem
import com.matxowy.dogfacts.ui.base.ScopedFragment
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.ViewHolder
import kotlinx.android.synthetic.main.list_of_dog_facts_fragment.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
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

    private fun bindUi() = launch(Dispatchers.Main) {
        val dogFacts = viewModel.dogFactsEntries.await()

        dogFacts.observe(viewLifecycleOwner, Observer { dogFactEntries ->
            if (dogFactEntries == null) return@Observer

            group_loading.visibility = View.GONE

            (activity as? AppCompatActivity)?.supportActionBar?.title = "Dog Facts"

            initRecyclerView(dogFactEntries.toDogFactsItems())

        })
    }

    private fun List<DogFactItem>.toDogFactsItems() : List<DogFactsItem> {
        return this.map {
            DogFactsItem(it)
        }
    }

    private fun initRecyclerView(items: List<DogFactsItem>) {
        val groupAdapter = GroupAdapter<ViewHolder>().apply {
            addAll(items)
        }

        recyclerView.apply {
            layoutManager = LinearLayoutManager(this@ListOfDogFactsFragment.context)
            adapter = groupAdapter
        }

        groupAdapter.setOnItemClickListener { item, view ->
            Toast.makeText(this@ListOfDogFactsFragment.context, "Clicked", Toast.LENGTH_SHORT).show()
        }

    }

}