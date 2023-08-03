package com.example.bitcointicker.presentation.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.example.bitcointicker.R
import com.example.bitcointicker.databinding.ActivitySearchBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SearchActivity : AppCompatActivity() {

    companion object{
        const val BUNDLE_ID = "id"
        const val BUNDLE_NAME = "name"
        const val BUNDLE_CURRENT_PRICE = "current_price"
        const val BUNDLE_IMAGE = "image"
        const val BUNDLE_24H = "one_day"
    }

    private lateinit var binding: ActivitySearchBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySearchBinding.inflate(layoutInflater)
        setContentView(binding.root)


    }
}