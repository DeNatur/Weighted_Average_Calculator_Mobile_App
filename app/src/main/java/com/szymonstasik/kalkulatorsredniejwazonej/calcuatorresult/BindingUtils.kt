package com.szymonstasik.kalkulatorsredniejwazonej.calcuatorresult

import androidx.databinding.BindingAdapter
import com.szymonstasik.kalkulatorsredniejwazonej.database.NoteNWeight
import com.szymonstasik.kalkulatorsredniejwazonej.utils.Statics
import com.szymonstasik.kalkulatorsredniejwazonej.utils.StrokeTextView

@BindingAdapter("note")
fun StrokeTextView.setNote(item: NoteNWeight?) {
    item?.let {
        text = Statics.NOTE_NUMBERS[item.note]
    }
}

@BindingAdapter("weight")
fun StrokeTextView.setWeight(item: NoteNWeight?) {
    item?.let {
        text = Statics.NOTE_WEIGHTS[item.weight]
    }
}