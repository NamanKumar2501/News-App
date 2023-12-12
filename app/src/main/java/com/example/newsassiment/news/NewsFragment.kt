package com.example.newsassiment.news

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.newsassiment.databinding.FragmentNewsBinding
import com.example.newsassiment.news.adapter.ItemAdapter
import com.example.newsassiment.news.networking.NewsApi
import com.example.newsassiment.news.networking.NewsService
import retrofit2.Call
import retrofit2.Response


class NewsFragment : Fragment() {

    private lateinit var binding: FragmentNewsBinding
    private lateinit var adapter: ItemAdapter
    private var currentPage = 1
    private var isLoading = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentNewsBinding.inflate(layoutInflater, container, false)


        binding.shimmer.startShimmerAnimation()

        setupRecyclerView()
        getNews(currentPage)

        binding.refresh.setOnRefreshListener {
            binding.shimmer.visibility = View.VISIBLE
            binding.recyclerView.visibility = View.GONE
            binding.shimmer.startShimmerAnimation()
            currentPage = 1
            getNews(currentPage)
            binding.refresh.isRefreshing = false
        }

        return binding.root
    }

    private fun setupRecyclerView() {
        adapter = ItemAdapter(requireContext(), mutableListOf())
        binding.recyclerView.adapter = adapter
        binding.recyclerView.layoutManager = LinearLayoutManager(context)

        // Setup RecyclerView scroll listener for pagination
        binding.recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)

                val layoutManager = recyclerView.layoutManager as LinearLayoutManager
                val visibleItemCount = layoutManager.childCount
                val totalItemCount = layoutManager.itemCount
                val firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition()

                // Load more items if we have reached the end of the list and not currently loading
                if (!isLoading && (visibleItemCount + firstVisibleItemPosition) >= totalItemCount
                    && firstVisibleItemPosition >= 0) {
                    loadMoreItems()
                }
            }
        })
    }

    private fun getNews(page: Int) {
        val news = NewsService.newsInstance.getHeadlines("in", page)
        news.enqueue(object : retrofit2.Callback<NewsApi> {
            override fun onResponse(call: Call<NewsApi>, response: Response<NewsApi>) {
                Log.d("GBK", "Success!")
                binding.shimmer.startShimmerAnimation()

                binding.shimmer.visibility = View.GONE
                binding.recyclerView.visibility = View.VISIBLE

                val news = response.body()
                if (news != null) {
                    Log.d("GBK", news.toString())
                    if (currentPage == 1) {
                        adapter.addAll(news.articles)
                    } else {
                        adapter.addAll(news.articles)
                    }
                }
                isLoading = false
            }

            override fun onFailure(call: Call<NewsApi>, t: Throwable) {
                Log.d("GBK", "Retrying!")
                isLoading = false
                // Handle failure as needed
            }
        })
    }

    private fun loadMoreItems() {
        isLoading = true
        currentPage++
        getNews(currentPage)
    }

}