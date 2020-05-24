package com.szymonstasik.kalkulatorsredniejwazonej.calculator

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.szymonstasik.kalkulatorsredniejwazonej.R
import com.szymonstasik.kalkulatorsredniejwazonej.database.WeightedAverageDatabase
import com.szymonstasik.kalkulatorsredniejwazonej.databinding.FragmentMenuBinding

class CalculatorFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View? {
        val binding: FragmentMenuBinding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_calculator, container, false)

        val application = requireNotNull(this.activity).application

        val dataSource = WeightedAverageDatabase.getInstance(application).weightedAverageDao

        val arguments = CalculatorFragmentArgs.fromBundle(requireArguments())

        val viewModelFactory = CalculatorViewModelFactory(arguments.id, dataSource)

        val calculatorViewModel = ViewModelProvider(this, viewModelFactory)[CalculatorViewModel::class.java]

        

        return binding.root
    }

}
