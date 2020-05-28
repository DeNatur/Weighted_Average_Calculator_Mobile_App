package com.szymonstasik.kalkulatorsredniejwazonej.history

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.szymonstasik.kalkulatorsredniejwazonej.R
import com.szymonstasik.kalkulatorsredniejwazonej.calculator.CalculatorFragmentArgs
import com.szymonstasik.kalkulatorsredniejwazonej.calculator.CalculatorViewModel
import com.szymonstasik.kalkulatorsredniejwazonej.calculator.CalculatorViewModelFactory
import com.szymonstasik.kalkulatorsredniejwazonej.database.WeightedAverageDatabase
import com.szymonstasik.kalkulatorsredniejwazonej.databinding.FragmentCalculatorBinding
import com.szymonstasik.kalkulatorsredniejwazonej.databinding.FragmentHistoryBinding


class HistoryFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View? {

        val binding: FragmentHistoryBinding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_history, container, false)

        val application = requireNotNull(this.activity).application

        val dataSource = WeightedAverageDatabase.getInstance(application).weightedAverageDao

        val viewModelFactory = HistoryViewModelFactory(dataSource)

        val historyViewModel = ViewModelProvider(this, viewModelFactory)[HistoryViewModel::class.java]

        val adapter = HistoryWeightedAverageAdapter()

        binding.weightedAverageRecycler.adapter = adapter

        historyViewModel.listOfWeightedAverage.observe(viewLifecycleOwner, Observer {
            if (it != null){
                adapter.submitList(it)
            }
        })

        return binding.root
    }
}
