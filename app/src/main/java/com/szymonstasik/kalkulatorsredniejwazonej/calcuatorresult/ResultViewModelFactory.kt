package com.szymonstasik.kalkulatorsredniejwazonej.calcuatorresult

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.szymonstasik.kalkulatorsredniejwazonej.calculator.CalculatorViewModel
import com.szymonstasik.kalkulatorsredniejwazonej.database.WeightedAverageDao

class ResultViewModelFactory(private val weightedAverageKey: Long,
    private val dataSource: WeightedAverageDao) : ViewModelProvider.Factory {
    @Suppress("unchecked_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ResultViewModel::class.java)) {
            return ResultViewModel(weightedAverageKey, dataSource) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}