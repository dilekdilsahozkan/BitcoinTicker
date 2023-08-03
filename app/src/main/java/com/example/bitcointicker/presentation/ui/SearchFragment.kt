package com.example.bitcointicker.presentation.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.bitcointicker.R
import com.example.bitcointicker.databinding.FragmentSearchBinding
import com.example.bitcointicker.presentation.adapter.CoinAdapter
import com.example.bitcointicker.presentation.viewmodel.SearchViewModel
import com.example.bitcointicker.presentation.viewmodel.ViewState
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SearchFragment : Fragment() {

    private lateinit var binding: FragmentSearchBinding
    private lateinit var viewModel: SearchViewModel

    private lateinit var db: FirebaseFirestore

    private var favoriteStateMap = mutableMapOf<String, Boolean>()

    private val searchAdapter: CoinAdapter by lazy {
        CoinAdapter(
            onRowClick = {
                findNavController().navigate(
                    R.id.action_fragment_search_to_detailFragment,
                    bundleOf(
                        SearchActivity.BUNDLE_ID to it.id,
                        SearchActivity.BUNDLE_IMAGE to it.image,
                        SearchActivity.BUNDLE_NAME to it.name,
                        SearchActivity.BUNDLE_24H to it.price_change_24h,
                        SearchActivity.BUNDLE_CURRENT_PRICE to it.current_price
                    )
                )
            },
            onFavoriteClick = { coin ->
                val favoritesCollection = db.collection("favorites")

                coin.id?.let { coinId ->
                    val isFavorite = favoriteStateMap[coinId] ?: false

                    if (isFavorite) {
                        favoritesCollection.document(coinId).delete()
                            .addOnSuccessListener {
                                Log.e("Firestore", "Removed from favorites")
                                favoriteStateMap[coinId] = false
                                binding.favoriteIcon.setImageResource(R.drawable.ic_favourite_border)
                            }
                            .addOnFailureListener { e ->
                                Log.e("Firestore", "Error removing favorite coin: ${e.message}")
                            }
                    } else {
                        favoritesCollection.document(coinId).set(
                            mapOf(
                                "id" to coin.id,
                                "name" to coin.name,
                                "image" to coin.image,
                                "price_change_24h" to coin.price_change_24h,
                                "current_price" to coin.current_price,
                                "symbol" to coin.symbol
                            )
                        )
                            .addOnSuccessListener {
                                Log.e("Firestore", "Added to favorites")
                                favoriteStateMap[coinId] = true
                                binding.favoriteIcon.setImageResource(R.drawable.ic_favourite_orange)
                            }
                            .addOnFailureListener { e ->
                                Log.e("Firestore", "Error adding favorite coin: ${e.message}")
                            }
                    }
                }
            })
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentSearchBinding.inflate(inflater, container, false)
        viewModel = ViewModelProvider(this)[SearchViewModel::class.java]

        viewModel.getCoins()

        db = FirebaseFirestore.getInstance()

        addObserver()
        addListener()

        return binding.root
    }

    private fun addListener(){
        binding.favoriteIcon.setOnClickListener {
            findNavController().navigate(R.id.action_fragment_search_to_favoriteFragment)
        }
    }

    private fun addObserver(){
        lifecycleScope.launch {
            viewModel.searchState.collect {
                when (it) {
                    is ViewState.Success -> {
                        searchAdapter.submitList(it.data)
                        binding.recyclerview.adapter = searchAdapter
                    }
                    is ViewState.Error -> {
                        println(it.message)
                    }
                    else -> {}
                }
            }
        }
    }
}