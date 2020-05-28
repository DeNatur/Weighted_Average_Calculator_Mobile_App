package com.szymonstasik.kalkulatorsredniejwazonej.history

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.ViewModel
import com.szymonstasik.kalkulatorsredniejwazonej.database.WeightedAverage
import com.szymonstasik.kalkulatorsredniejwazonej.database.WeightedAverageDao

class HistoryViewModel(val database: WeightedAverageDao): ViewModel() {


    private val _listOfWeightAverages = MediatorLiveData<List<WeightedAverage>>()

    val listOfWeightedAverage: LiveData<List<WeightedAverage>>
        get() {
            return _listOfWeightAverages
        }

    init {
        _listOfWeightAverages.addSource(database.getAllWeightedAverages(), _listOfWeightAverages::setValue)
    }
}