package com.sachin_singh_dighan.learn_kotlin_coroutines.ui.retrofit.series

import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.sachin_singh_dighan.learn_kotlin_coroutines.R
import com.sachin_singh_dighan.learn_kotlin_coroutines.data.api.ApiHelperImpl
import com.sachin_singh_dighan.learn_kotlin_coroutines.data.api.RetrofitBuilder
import com.sachin_singh_dighan.learn_kotlin_coroutines.data.local.DatabaseBuilder
import com.sachin_singh_dighan.learn_kotlin_coroutines.data.local.DatabaseHelperImpl
import com.sachin_singh_dighan.learn_kotlin_coroutines.data.model.ApiUser
import com.sachin_singh_dighan.learn_kotlin_coroutines.ui.base.ApiUserAdapter
import com.sachin_singh_dighan.learn_kotlin_coroutines.ui.base.UiState
import com.sachin_singh_dighan.learn_kotlin_coroutines.ui.base.ViewModelFactory

class SeriesNetworkCallsActivity : AppCompatActivity() {

    private lateinit var viewModel: SeriesNetworkCallsViewModel
    private lateinit var adapter: ApiUserAdapter
    private lateinit var recyclerView: RecyclerView
    private lateinit var progressBar: ProgressBar
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_series_network_calls)
        setupUI()
        setupViewModel()
        setupObserver()
    }

    private fun setupUI() {
        recyclerView = findViewById(R.id.recyclerView)
        progressBar = findViewById(R.id.progressBar)
        recyclerView.layoutManager = LinearLayoutManager(this)
        adapter =
            ApiUserAdapter(
                arrayListOf()
            )
        recyclerView.addItemDecoration(
            DividerItemDecoration(
                recyclerView.context,
                (recyclerView.layoutManager as LinearLayoutManager).orientation
            )
        )
        recyclerView.adapter = adapter
    }

    private fun setupViewModel() {
        viewModel = ViewModelProvider(
            this,
            ViewModelFactory(
                ApiHelperImpl(RetrofitBuilder.apiService),
                DatabaseHelperImpl(DatabaseBuilder.getInstance(applicationContext))
            )
        )[SeriesNetworkCallsViewModel::class.java]
    }

    private fun setupObserver() {
        viewModel.getUiState().observe(this) {
            when (it) {
                is UiState.Success -> {
                    progressBar.visibility = View.GONE
                    renderList(it.data)
                    progressBar.visibility = View.VISIBLE

                }

                is UiState.Loading -> {
                    progressBar.visibility = View.VISIBLE
                    progressBar.visibility = View.GONE
                }

                is UiState.Error -> {
                    progressBar.visibility = View.GONE
                    Toast.makeText(this,it.message, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun renderList(users: List<ApiUser>) {
        adapter.addData(users)
        adapter.notifyDataSetChanged()
    }
}