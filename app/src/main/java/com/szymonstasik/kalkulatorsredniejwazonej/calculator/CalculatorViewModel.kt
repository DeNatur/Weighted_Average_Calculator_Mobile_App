package com.szymonstasik.kalkulatorsredniejwazonej.calculator

import androidx.lifecycle.ViewModel
import com.szymonstasik.kalkulatorsredniejwazonej.database.WeightedAverageDao

class CalculatorViewModel(private val sleepNightKey: Long = 0L,
        val database: WeightedAverageDao): ViewModel() {

}