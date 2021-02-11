package com.example.tvtraker.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.tvtraker.databinding.ItemSerijeBinding
import com.example.tvtraker.model.TvSerija

class SerijeAdapter(val clicklistener: (TvSerija) -> Unit) : ListAdapter<TvSerija, SerijeAdapter.SerijeViewHolder>(DiffCallback()) {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): SerijeViewHolder {
        val binding = ItemSerijeBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return SerijeViewHolder(binding)
    }

    override fun onBindViewHolder(holder: SerijeViewHolder, position: Int) {
        val currentItem = getItem(position)
        holder.bind(currentItem,clicklistener)
    }

    class SerijeViewHolder(private val binding: ItemSerijeBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(serija: TvSerija, clicklistener: (TvSerija) -> Unit) {
            binding.apply {
                titleTextView.text = serija.naslov
                Glide
                    .with(itemView)
                    .load(serija.poster)
                    .into(serijeImageView)

                itemView.setOnClickListener { clicklistener(serija) }
            }
        }
    }

    class DiffCallback : DiffUtil.ItemCallback<TvSerija>() {
        override fun areItemsTheSame(oldItem: TvSerija, newItem: TvSerija): Boolean =
            oldItem._id == newItem._id

        override fun areContentsTheSame(oldItem: TvSerija, newItem: TvSerija): Boolean =
            oldItem == newItem
    }
}