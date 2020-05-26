package com.szymonstasik.kalkulatorsredniejwazonej.calcuatorresult

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
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

        return binding.root
    }


}
