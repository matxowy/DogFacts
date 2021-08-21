package com.matxowy.dogfacts.ui.detail

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.matxowy.dogfacts.R

class DetailOfDogFactFragment : Fragment() {

    companion object {
        fun newInstance() = DetailOfDogFactFragment()
    }

    private lateinit var viewModel: DetailOfDogFactViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.detail_of_dog_fact_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(DetailOfDogFactViewModel::class.java)
        // TODO: Use the ViewModel
    }

}