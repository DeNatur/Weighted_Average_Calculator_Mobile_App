package com.szymonstasik.kalkulatorsredniejwazonej.calculator

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.szymonstasik.kalkulatorsredniejwazonej.R
import com.szymonstasik.kalkulatorsredniejwazonej.database.WeightedAverageDatabase
import com.szymonstasik.kalkulatorsredniejwazonej.databinding.FragmentCalculatorBinding
import com.szymonstasik.kalkulatorsredniejwazonej.databinding.FragmentMenuBinding

class CalculatorFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View? {
        val binding: FragmentCalculatorBinding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_calculator, container, false)

        val application = requireNotNull(this.activity).application

        val dataSource = WeightedAverageDatabase.getInstance(application).weightedAverageDao

        val arguments = CalculatorFragmentArgs.fromBundle(requireArguments())

        val viewModelFactory = CalculatorViewModelFactory(arguments.id, dataSource)

        val calculatorViewModel = ViewModelProvider(this, viewModelFactory)[CalculatorViewModel::class.java]

        binding.calculatorViewModel = calculatorViewModel

        binding.lifecycleOwner = this

        val adapter = CalculatorAdapter(CalculatorAdapter.ChangeNoteListener{ position, noteValue ->
                calculatorViewModel.changeValueOfNote(position, noteValue)
            }, CalculatorAdapter.ChangeWeightListener{position, weightValue ->
                calculatorViewModel.changeValueOfWeight(position, weightValue)
            }
        )


        binding.notesAndWeightesRecycler.adapter = adapter



        calculatorViewModel.weightedAverage.observe(viewLifecycleOwner, Observer {
            if (it != null)
                adapter.submitList(it.notes)
        })

        calculatorViewModel.navigateToResult.observe(viewLifecycleOwner, Observer {
            if (it != null) {
                findNavController().navigate(
                    CalculatorFragmentDirections.actionCalculatorFragmentToResultFragment(
                        it))
                calculatorViewModel.doneNavigatingToResult()
            }
        })

        binding.addNewNote.setOnClickListener {
            calculatorViewModel.addNewNote()
            binding.notesAndWeightesRecycler.smoothScrollToPosition(adapter.itemCount )
        }

        binding.deleteNote.setOnClickListener {
            calculatorViewModel.deleteLastNote()
            binding.notesAndWeightesRecycler.smoothScrollToPosition(adapter.itemCount )
        }

        return binding.root
    }

}
