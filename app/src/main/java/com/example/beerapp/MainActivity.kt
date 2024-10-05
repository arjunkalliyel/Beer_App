package com.example.beerapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.beerapp.adapter.BeerAdapter
import com.example.beerapp.api.getBeerData
import com.example.beerapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private var beerAdapter: BeerAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        getData()
    }

    private fun getData() {
        getBeerData().observe(this) { response ->
            beerAdapter = BeerAdapter(response)
            binding.rvBeer.adapter = beerAdapter
        }
    }

}