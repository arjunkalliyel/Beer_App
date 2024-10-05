package com.example.beerapp.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.beerapp.databinding.ItemBeerListBinding
import com.example.beerapp.model.BeerResponse

class BeerAdapter(private val beerList: List<BeerResponse>) :
    RecyclerView.Adapter<BeerAdapter.ItemViewHolder>() {

    inner class ItemViewHolder(val binding: ItemBeerListBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(beer: BeerResponse) {
            binding.tvName.text = beer.name
            binding.tvPrice.text = beer.price
            binding.tvRate.text = beer.rating?.reviews.toString()
            Glide.with(binding.root.context).load(beer.image).into(binding.imageView)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BeerAdapter.ItemViewHolder {
        return ItemViewHolder(
            ItemBeerListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: BeerAdapter.ItemViewHolder, position: Int) {
        holder.bind(beerList[position])
    }

    override fun getItemCount(): Int {
        return beerList.size
    }
}
