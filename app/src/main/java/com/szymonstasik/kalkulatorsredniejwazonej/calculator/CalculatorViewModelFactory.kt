package com.szymonstasik.kalkulatorsredniejwazonej.calculator

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.szymonstasik.kalkulatorsredniejwazonej.database.WeightedAverageDao
import com.szymonstasik.kalkulatorsredniejwazonej.menu.MenuViewModel

class CalculatorViewModelFactory(private val weightedAverageKey: Long,
                                 private val dataSource: WeightedAverageDao) : ViewModelProvider.Factory {
    @Suppress("unchecked_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CalculatorViewModel::class.java)) {
            return CalculatorViewModel(weightedAverageKey, dataSource) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}