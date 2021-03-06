package com.szymonstasik.kalkulatorsredniejwazonej.menu

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.szymonstasik.kalkulatorsredniejwazonej.database.NoteNWeight
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
     * Called when the ViewModel is dismantled.
     * At this point, we want to cancel all coroutines;
     * otherwise we end up with processes that have nowhere to return to
     * using memory and resources.
     */
    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }

    /**
     * Variable that tells the Fragment to navigate to a specific [CalculatorFragment]
     *
     * This is private because we don't want to expose setting this value to the Fragment.
     */

    private val _navigateToCalculator = MutableLiveData<Long>()



    private suspend fun clear() {
        withContext(Dispatchers.IO) {
            database.clear()
        }
    }
    /**
     * If this is non-null, immediately navigate to [CalculatorFragment] and call [doneCalculatorNavigating]
     */
    val navigateToCalculator: LiveData<Long>
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
            var tmpArray = ArrayList<NoteNWeight>()
            tmpArray.add(NoteNWeight())
            val newWeightedAverage = WeightedAverage(
                notes = tmpArray
            )
            _navigateToCalculator.value =  insert(newWeightedAverage)

            Log.d("weighted average", "key " + _navigateToCalculator.value)
        }
    }

    private suspend fun insert(weightedAverage: WeightedAverage): Long {
        return withContext(Dispatchers.IO) {
            database.insert(weightedAverage)
        }
    }
}