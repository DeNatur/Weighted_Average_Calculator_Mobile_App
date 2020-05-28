package com.szymonstasik.kalkulatorsredniejwazonej.calcuatorresult

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.szymonstasik.kalkulatorsredniejwazonej.database.NoteNWeight
import com.szymonstasik.kalkulatorsredniejwazonej.database.WeightedAverage
import com.szymonstasik.kalkulatorsredniejwazonej.database.WeightedAverageDao
import com.szymonstasik.kalkulatorsredniejwazonej.utils.Utils
import kotlinx.coroutines.*

class ResultViewModel(private val weightedAverageKey: Long = 0L,
                      private val database: WeightedAverageDao): ViewModel() {

    /**
     * viewModelJob allows us to cancel all coroutines started by this ViewModel.
     */
    private var viewModelJob = Job()
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

    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)

    private val _weightedAverage = MutableLiveData<WeightedAverage>()

    val weightedAverage: LiveData<WeightedAverage>
        get() {
            return _weightedAverage
        }

    private val _result = MutableLiveData<Float>()

    private val _navigateToCalculator = MutableLiveData<Long>()

    val navigateToCalculator: LiveData<Long>
        get() {
            return  _navigateToCalculator
        }

    private val _navigateToHistory = MutableLiveData<Boolean>()

    val navigateToHistory: LiveData<Boolean>
        get() {
            return _navigateToHistory
        }

    fun onChangeClick(){
        _navigateToCalculator.value = weightedAverageKey
    }
    fun onCalculateAgainCLick(){
        uiScope.launch {
            var tmpArray = ArrayList<NoteNWeight>()
            tmpArray.add(NoteNWeight())
            val newWeightedAverage = WeightedAverage(
                notes = tmpArray
            )
            _navigateToCalculator.value =  insert(newWeightedAverage)
        }
    }

    fun onNameNoteNWeightsClick(name: String){
        uiScope.launch {
            _weightedAverage.value?.name = name
            _weightedAverage.value?.let { update(it) }
            _navigateToHistory.value = true
        }
    }

    fun onDoneNavigatingToHistory(){
        _navigateToHistory.value = false
    }

    fun onDoneNavigatingToCalculator(){
        _navigateToCalculator.value = null
    }
    val result: LiveData<Float>
        get() {
            return _result
        }

    init {
        uiScope.launch {
            _weightedAverage.value = getWeightedAverage(weightedAverageKey)
            instantinateResult()
        }
    }

    private fun instantinateResult() {
        val resultNoteList = _weightedAverage.value?.notes
        var allWeights = 0f;
        var allValues = 0f;
        if (resultNoteList != null) {
            for (notesNWeight in resultNoteList){
                var weight: Float = notesNWeight.weight + 1f
                allWeights += weight
                var note: Float = Utils.getNoteFromId(notesNWeight.note)
                allValues +=  note * weight
            }
            _result.value = allValues / allWeights
        }
    }

    private suspend fun getWeightedAverage(key: Long) : WeightedAverage? {
        return withContext(Dispatchers.IO) {
            database.get(key)
        }
    }

    private suspend fun update(weightedAverage: WeightedAverage) {
        withContext(Dispatchers.IO) {
            database.update(weightedAverage)
        }
    }

    private suspend fun insert(weightedAverage: WeightedAverage): Long {
        return withContext(Dispatchers.IO) {
            database.insert(weightedAverage)
        }
    }
}