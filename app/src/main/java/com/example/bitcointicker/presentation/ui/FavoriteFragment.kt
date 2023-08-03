package com.example.bitcointicker.presentation.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.bitcointicker.data.dto.CoinDto
import com.example.bitcointicker.databinding.FragmentFavoriteBinding
import com.example.bitcointicker.presentation.adapter.FavoriteAdapter
import com.google.firebase.firestore.FirebaseFirestore
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FavoriteFragment : Fragment(){

    private lateinit var binding: FragmentFavoriteBinding
    private lateinit var favoriteAdapter: FavoriteAdapter
    private lateinit var db: FirebaseFirestore

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentFavoriteBinding.inflate(inflater, container, false)

        db = FirebaseFirestore.getInstance()
        val favoritesCollection = db.collection("favorites")

        favoriteAdapter = FavoriteAdapter()
        binding.recyclerview.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerview.adapter = favoriteAdapter

        binding.closeIcon.setOnClickListener {
            navigateToSearchFragment()
        }

        favoritesCollection.get()
            .addOnSuccessListener { querySnapshot ->
                val favoriteCoins = mutableListOf<CoinDto>()

                for (document in querySnapshot.documents) {
                    val coinData = document.toObject(CoinDto::class.java)
                    if (coinData != null) {
                        favoriteCoins.add(coinData)
                    }
                    val documentData = document.data
                    Log.d("Firestore", "Document data: $documentData")
                }
                favoriteAdapter.submitList(favoriteCoins)
            }
            .addOnFailureListener { e ->
                Log.e("Firestore", "Error fetching favorite coins: ${e.message}")
            }

        return binding.root
    }
    private fun navigateToSearchFragment() {
        findNavController().navigateUp()
    }
}