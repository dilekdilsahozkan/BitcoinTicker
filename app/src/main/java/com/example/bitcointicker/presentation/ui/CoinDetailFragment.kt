package com.example.bitcointicker.presentation.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.bitcointicker.R
import com.example.bitcointicker.base.loadImage
import com.example.bitcointicker.databinding.FragmentCoinDetailBinding
import com.example.bitcointicker.presentation.viewmodel.CoinDetailViewModel
import com.example.bitcointicker.presentation.viewmodel.ViewState
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class CoinDetailFragment : Fragment() {

    private lateinit var binding: FragmentCoinDetailBinding
    private lateinit var viewModel: CoinDetailViewModel

    private val coinId: String? by lazy {
        arguments?.getString(SearchActivity.BUNDLE_ID)
    }
    private val coinName: String? by lazy {
        arguments?.getString(SearchActivity.BUNDLE_NAME)
    }
    private val coinImage: String? by lazy {
        arguments?.getString(SearchActivity.BUNDLE_IMAGE)
    }
    private val coin24h: Double? by lazy {
        arguments?.getDouble(SearchActivity.BUNDLE_24H, 0.0)
    }
    private val coinCurrentPrice: Double? by lazy {
        arguments?.getDouble(SearchActivity.BUNDLE_CURRENT_PRICE, 0.0)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCoinDetailBinding.inflate(inflater, container, false)
        viewModel = ViewModelProvider(this)[CoinDetailViewModel::class.java]

        coinId?.let { viewModel.getCoins(it) }

        binding.coinName.text = coinName.toString()
        binding.coinImage.loadImage(coinImage)
        binding.currentPrice.text = coinCurrentPrice.toString()
        binding.changePercentage.text = coin24h.toString()

        binding.closeIcon.setOnClickListener {
            navigateToSearchFragment()
        }

        addObserver()

        return binding.root
    }

    private fun navigateToSearchFragment() {
        findNavController().navigateUp()
    }

    private fun addObserver(){
        lifecycleScope.launch {
            viewModel.coinDetailState.collect {
                when (it) {
                    is ViewState.Success -> {
                        binding.coinDetail = it.data
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