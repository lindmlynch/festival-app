package org.wit.festival.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import org.wit.festival.databinding.CardFestivalBinding
import org.wit.festival.models.FestivalModel

class FestivalAdapter constructor(private var festivals: List<FestivalModel>) :
    RecyclerView.Adapter<FestivalAdapter.MainHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainHolder {
        val binding = CardFestivalBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)
        return MainHolder(binding)
    }

    override fun onBindViewHolder(holder: MainHolder, position: Int) {
        val festival = festivals[holder.adapterPosition]
        holder.bind(festival)
    }

    override fun getItemCount(): Int = festivals.size

    class MainHolder(private val binding : CardFestivalBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(festival: FestivalModel) {
            binding.festivalTitle.text = festival.title
            binding.description.text = festival.description
        }
    }
}