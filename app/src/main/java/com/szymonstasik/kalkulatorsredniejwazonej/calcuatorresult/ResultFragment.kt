package com.szymonstasik.kalkulatorsredniejwazonej.calcuatorresult

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.szymonstasik.kalkulatorsredniejwazonej.R
import com.szymonstasik.kalkulatorsredniejwazonej.calculator.CalculatorFragmentArgs
import com.szymonstasik.kalkulatorsredniejwazonej.database.WeightedAverageDatabase
import com.szymonstasik.kalkulatorsredniejwazonej.databinding.FragmentCalculatorBinding
import com.szymonstasik.kalkulatorsredniejwazonej.databinding.FragmentResultBinding


class ResultFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View? {
        val binding: FragmentResultBinding =  DataBindingUtil.inflate(
            inflater, R.layout.fragment_result, container, false)

        val application = requireNotNull(this.activity).application

        val dataSource = WeightedAverageDatabase.getInstance(application).weightedAverageDao

        val arguments = ResultFragmentArgs.fromBundle(requireArguments())

        val viewModelFactory = ResultViewModelFactory(arguments.weightedAverageKey, dataSource)

        val resultViewModel = ViewModelProvider(this, viewModelFactory)[ResultViewModel::class.java]

        val adapter = ResultNotesAdapter()

        binding.notesAndWeightsRecycler.adapter = adapter

        binding.resultViewModel = resultViewModel

        binding.lifecycleOwner = this

        resultViewModel.result.observe(viewLifecycleOwner, Observer {
            binding.resultText.text = getString(R.string.weighted_average, it)
        })

        resultViewModel.weightedAverage.observe(viewLifecycleOwner, Observer {
            if (it != null){
                adapter.submitList(it.notes)
            }
        })

        resultViewModel.navigateToCalculator.observe(viewLifecycleOwner, Observer {
            if (it != null){
                findNavController().navigate(ResultFragmentDirections.actionResultFragmentToCalculatorFragment(it))
                resultViewModel.onDoneNavigatingToCalculator()
            }
        })

        resultViewModel.navigateToHistory.observe(viewLifecycleOwner, Observer {
            if (it){
                findNavController().navigate(ResultFragmentDirections.actionResultFragmentToHistoryFragment())
                resultViewModel.onDoneNavigatingToHistory()
            }
        })

        return binding.root
    }


}
