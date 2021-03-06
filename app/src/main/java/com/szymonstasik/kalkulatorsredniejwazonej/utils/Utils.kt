package com.szymonstasik.kalkulatorsredniejwazonej.utils

import android.app.Activity
import android.view.inputmethod.InputMethodManager


object Utils{
    fun getNoteFromId(position: Int): Float =
        when(position){
            0 -> 0f
            1 -> 1f
            2 -> 1.5f
            3 -> 2f
            4 -> 2.5f
            5 -> 3f
            6 -> 3.5f
            7 -> 4f
            8 -> 4.5f
            9 -> 5f
            10 -> 5.5f
            11 -> 6f
            else -> 0f
        }
    fun hideSoftKeyboard(activity: Activity) {
        val inputMethodManager: InputMethodManager = activity.getSystemService(
            Activity.INPUT_METHOD_SERVICE
        ) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(
            activity.currentFocus!!.windowToken, 0
        )
    }
}