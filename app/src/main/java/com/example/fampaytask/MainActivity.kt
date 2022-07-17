package com.example.fampaytask

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import com.example.fampaytask.databinding.ActivityMainBinding
import com.example.fampaytask.model.Card
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity(), ClickListener {
    private lateinit var api: FamApi

    private val apiUrl: String = "https://run.mocky.io/v3/"
    private lateinit var hc1Adapter: MainAdapter
    private lateinit var hc3Adapter: MainAdapter
    private lateinit var hc5Adapter: MainAdapter
    private lateinit var hc6Adapter: MainAdapter
    private lateinit var hc9Adapter: MainAdapter

    private val viewModel: MainViewModel by viewModels()

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setUpRecyclerViews()

        val famApi = Retrofit.Builder().addConverterFactory(GsonConverterFactory.create())
            .baseUrl(apiUrl)
            .build()
            .create(FamApi::class.java)

        viewModel.loadData(famApi)

        viewModel.cardGroups.observe(this) { cardGroups ->
            cardGroups.forEach { cardGroup ->
                when (cardGroup.designType) {
                    "HC1" -> {
                        hc1Adapter.submitList(cardGroup.cards)
                    }
                    "HC3" -> {
                        hc3Adapter.submitList(cardGroup.cards)
                    }
                    "HC5" -> {
                        val cards = ArrayList<Card>()
                        cards.addAll(hc5Adapter.currentList)
                        cards.addAll(cardGroup.cards)
                        hc5Adapter.submitList(cards)
                    }
                    "HC6" -> {
                        hc6Adapter.submitList(cardGroup.cards)
                    }
                    "HC9" -> {
                        hc9Adapter.submitList(cardGroup.cards)
                    }
                }
            }
            viewModel.uiState.postValue(UiState.Success)
            Log.d("adapter", "refreshed")
        }

        binding.swiperLayout.setOnRefreshListener {
            viewModel.uiState.postValue(UiState.Loading)
            viewModel.loadData(famApi)
        }

        binding.retryButton.setOnClickListener {
            viewModel.uiState.postValue(UiState.Loading)
            viewModel.loadData(famApi)
        }

        viewModel.uiState.observe(this) { uiState ->
            binding.swiperLayout.isRefreshing = uiState == UiState.Loading
            binding.errorView.isVisible = uiState == UiState.Failure
        }

    }

    private fun setUpRecyclerViews() {
        hc1Adapter = MainAdapter("HC1", this)
        hc3Adapter = MainAdapter("HC3", this)
        hc5Adapter = MainAdapter("HC5", this)
        hc6Adapter = MainAdapter("HC6", this)
        hc9Adapter = MainAdapter("HC9", this)

        binding.apply {
            hc1CardContainer.adapter = hc1Adapter
            hc3CardContainer.adapter = hc3Adapter
            hc5CardContainer.adapter = hc5Adapter
            hc6CardContainer.adapter = hc6Adapter
            hc9CardContainer.adapter = hc9Adapter
        }
    }

    override fun onPress(url: String) {
        this.openUrl(url)
    }

    override fun onLongPress() {

    }

}


fun Activity.openUrl(url: String? = null) {
    try {
        this.startActivity(Intent(this, WebActivity::class.java).putExtra("link", url))
    } catch (e: Exception) { }
}