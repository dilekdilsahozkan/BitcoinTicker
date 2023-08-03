package com.example.bitcointicker.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.bitcointicker.databinding.ItemSearchCoinsBinding
import com.example.bitcointicker.BR
import com.example.bitcointicker.data.dto.CoinDto

class CoinAdapter(
    private val onRowClick: ((result: CoinDto) -> Unit)? = null,
    private val onFavoriteClick: ((result: CoinDto) -> Unit)? = null
) :
    ListAdapter<CoinDto, CoinAdapter.ViewHolder>(object :
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
    }) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ViewHolder(
            ItemSearchCoinsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        holder.binding.setVariable(BR.coin, item)
    }

    inner class ViewHolder(val binding: ItemSearchCoinsBinding) :
        RecyclerView.ViewHolder(binding.root) {

        init {
            binding.coinsLayout.setOnClickListener {
                if (bindingAdapterPosition != RecyclerView.NO_POSITION) {
                    onRowClick?.invoke(getItem(bindingAdapterPosition))
                }
            }

            binding.favourite.setOnClickListener {
                if (bindingAdapterPosition != RecyclerView.NO_POSITION) {
                    onFavoriteClick?.invoke(getItem(bindingAdapterPosition))
                }
            }
        }
    }
}