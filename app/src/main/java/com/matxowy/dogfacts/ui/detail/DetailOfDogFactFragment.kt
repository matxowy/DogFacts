package com.matxowy.dogfacts.ui.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.matxowy.dogfacts.R
import com.matxowy.dogfacts.internal.ItemNotFoundException
import com.matxowy.dogfacts.ui.base.ScopedFragment
import kotlinx.android.synthetic.main.detail_of_dog_fact_fragment.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.closestKodein
import org.kodein.di.generic.factory
import org.threeten.bp.LocalDate

class DetailOfDogFactFragment : ScopedFragment(), KodeinAware {

    override val kodein by closestKodein()

    private val viewModelFactoryInstanceFactory :
            ((Int) -> DetailOfDogFactsViewModelFactory) by factory()

    private lateinit var viewModel: DetailOfDogFactViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.detail_of_dog_fact_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val safeArgs = arguments?.let { DetailOfDogFactFragmentArgs.fromBundle(it) }
        val itemId = safeArgs?.itemId ?: throw ItemNotFoundException()

        viewModel = ViewModelProvider(this, viewModelFactoryInstanceFactory(itemId))
            .get(DetailOfDogFactViewModel::class.java)

        bindUi()
    }

    private fun bindUi() = launch(Dispatchers.Main) {
        val dogFact = viewModel.dogFact.await()

        dogFact.observe(viewLifecycleOwner, Observer { dogFactEntry ->
            if (dogFactEntry == null) return@Observer

            updateTitle("Dog Fact")
            dogFactEntry.id?.let { updateFactNumber(it) }
            updateFetchDate(dogFactEntry.fetchedTime)
            updateFact(dogFactEntry.fact)
        })
    }

    private fun updateTitle(title: String) {
        (activity as? AppCompatActivity)?.supportActionBar?.title = title
    }

    private fun updateFactNumber(factId: Int) {
        tv_fact_id.text = "Fact #$factId"
    }

    private fun updateFetchDate(date: LocalDate) {
        tv_fetch_date.text = "Update date of fact: $date"
    }

    private fun updateFact(fact: String) {
        tv_fact.text = fact
    }

}