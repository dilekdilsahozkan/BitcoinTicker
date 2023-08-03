package com.example.bitcointicker.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.bitcointicker.BR
import com.example.bitcointicker.base.loadImage
import com.example.bitcointicker.data.dto.CoinDto
import com.example.bitcointicker.databinding.ItemFavoriteCoinsBinding

class FavoriteAdapter :
    ListAdapter<CoinDto, FavoriteAdapter.ViewHolder>(object :
        DiffUtil.ItemCallback<CoinDto>() {
        override fun areItemsTheSame(
            oldItem: CoinDto,
            newItem: CoinDto
        ): Boolean {
            return true
        }

        override fun areContentsTheSame(
            oldItem: CoinDto,
            newItem: CoinDto
        ): Boolean {
            return oldItem == newItem
        }
    })  {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ViewHolder(
            ItemFavoriteCoinsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class ViewHolder(val binding: ItemFavoriteCoinsBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(coin: CoinDto) {
            binding.coinName.text = coin.name.toString()
            binding.coinImage.loadImage(coin.image)
            binding.coinSymbol.text = coin.symbol.toString()
            binding.currentPrice.text = coin.current_price.toString()
        }
    }
}