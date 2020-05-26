package com.szymonstasik.kalkulatorsredniejwazonej.calculator

import android.content.Context
import android.widget.ArrayAdapter
import android.widget.NumberPicker
import android.widget.Spinner
import androidx.databinding.BindingAdapter
import com.szymonstasik.kalkulatorsredniejwazonej.database.NoteNWeight
import com.szymonstasik.kalkulatorsredniejwazonej.utils.Statics

@BindingAdapter("note")
fun NumberPicker.setNote(item: NoteNWeight?) {
    item?.let {
        minValue = 0
        maxValue = 10
        displayedValues = Statics.NOTE_NUMBERS
        value = item.note
    }
}



@BindingAdapter("weight")
fun NumberPicker.setWeight(item: NoteNWeight?) {
    item?.let {
        minValue = 1
        maxValue = 10
        value = item.weight
    }
}
