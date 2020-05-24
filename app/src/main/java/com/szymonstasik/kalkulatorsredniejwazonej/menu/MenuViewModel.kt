package com.szymonstasik.kalkulatorsredniejwazonej.menu

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.szymonstasik.kalkulatorsredniejwazonej.database.WeightedAverage
import com.szymonstasik.kalkulatorsredniejwazonej.database.WeightedAverageDao
import kotlinx.coroutines.*

class MenuViewModel(val database: WeightedAverageDao
        ) : ViewModel() {


    /**
     * viewModelJob allows us to cancel all coroutines started by this ViewModel.
     */
    private var viewModelJob = Job()

    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)

    /**
     * Variable that tells the Fragment to navigate to a specific [CalculatorFragment]
     *
     * This is private because we don't want to expose setting this value to the Fragment.
     */

    private val _navigateToCalculator = MutableLiveData<WeightedAverage>()



    private suspend fun clear() {
        withContext(Dispatchers.IO) {
            database.clear()
        }
    }
    /**
     * If this is non-null, immediately navigate to [CalculatorFragment] and call [doneCalculatorNavigating]
     */
    val navigateToCalculator: LiveData<WeightedAverage>
        get() = _navigateToCalculator

    /**
     * Call this immediately after navigating to [CalculatorFragment]
     *
     * It will clear the navigation request, so if the user rotates their phone it won't navigate
     * twice.
     */
    fun doneCalculatorNavigating() {
        _navigateToCalculator.value = null
    }

    fun onCalculatorClick(){
        uiScope.launch {
            val newWeightedAverage = WeightedAverage(
                notes = ArrayList<Float>(),
                weights = ArrayList<Float>())
            insert(newWeightedAverage)
            _navigateToCalculator.value = newWeightedAverage
        }
    }

    private suspend fun insert(weightedAverage: WeightedAverage) {
        withContext(Dispatchers.IO) {
            database.insert(weightedAverage)
        }
    }
}