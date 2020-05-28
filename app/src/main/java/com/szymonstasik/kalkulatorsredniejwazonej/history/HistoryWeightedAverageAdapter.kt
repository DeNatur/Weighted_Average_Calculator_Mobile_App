package com.szymonstasik.kalkulatorsredniejwazonej.history

import android.app.Application
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.szymonstasik.kalkulatorsredniejwazonej.calcuatorresult.ResultNotesAdapter
import com.szymonstasik.kalkulatorsredniejwazonej.database.NoteNWeight
import com.szymonstasik.kalkulatorsredniejwazonej.database.WeightedAverage
import com.szymonstasik.kalkulatorsredniejwazonej.databinding.ListItemNoteAndWeightResultBinding
import com.szymonstasik.kalkulatorsredniejwazonej.databinding.ListItemWeightedAverageBinding

class HistoryWeightedAverageAdapter
    : ListAdapter<WeightedAverage, HistoryWeightedAverageAdapter.ViewHolder>
    (HistoryWeightedAverageAdapter.WeightedAverageDiffCallback()){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position) as WeightedAverage
        holder.bind(item)
    }

    class ViewHolder private constructor(private val binding: ListItemWeightedAverageBinding)
        : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: WeightedAverage) {
            binding.weightedAverage = item
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ListItemWeightedAverageBinding.inflate(layoutInflater, parent, false)

                return ViewHolder(binding)
            }
        }
    }

    /**
     * Callback for calculating the diff between two non-null items in a list.
     *
     * Used by ListAdapter to calculate the minumum number of changes between and old list and a new
     * list that's been passed to `submitList`.
     */
    class WeightedAverageDiffCallback : DiffUtil.ItemCallback<WeightedAverage>(){
        override fun areItemsTheSame(oldItem: WeightedAverage, newItem: WeightedAverage): Boolean {
            return  oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: WeightedAverage, newItem: WeightedAverage): Boolean {
            var same: Boolean = true
            for (position in oldItem.notes.indices){
               if (oldItem.notes[position] != newItem.notes[position])
                   same = false
            }
            return same &&
                    oldItem.timeAddedMilli == newItem.timeAddedMilli &&
                    oldItem.name == newItem.name
        }
    }

}