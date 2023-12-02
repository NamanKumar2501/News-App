package com.example.newsassiment.news

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.newsassiment.R
import com.example.newsassiment.databinding.FragmentNewsBinding
import com.example.newsassiment.news.adapter.ItemAdapter
import com.example.newsassiment.news.networking.NewsApi
import com.example.newsassiment.news.networking.NewsService
import retrofit2.Call
import retrofit2.Response


class NewsFragment : Fragment() {

    lateinit var binding: FragmentNewsBinding
    lateinit var adapter: ItemAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentNewsBinding.inflate(layoutInflater, container, false)

        binding.shimmer.startShimmerAnimation()

        getNews()

        binding.refresh.setOnRefreshListener {
            binding.shimmer.visibility = View.VISIBLE
            binding.recyclerView.visibility = View.GONE
            binding.shimmer.startShimmerAnimation()
            getNews()
            binding.refresh.isRefreshing = false
        }

        return binding.root
    }

    private fun getNews() {
        val news = NewsService.newsInstance.getHeadlines("in", 1)
        news.enqueue(object:retrofit2.Callback<NewsApi> {
            override fun onResponse(call: Call<NewsApi>, response: Response<NewsApi>) {
                Log.d("GBK", "Success!")
                binding.shimmer.startShimmerAnimation()

                binding.shimmer.visibility = View.GONE
                binding.recyclerView.visibility = View.VISIBLE

                val news = response.body()
                if (news != null) {
                    Log.d("GBK", news.toString())
                    adapter = ItemAdapter(requireContext(), news.articles)
                    //val recyclerView = findViewById<RecyclerView>(R.id.recycler_view)
                    binding.recyclerView.adapter = adapter
                    binding.recyclerView.layoutManager = LinearLayoutManager(context)
                }
            }

            override fun onFailure(call: Call<NewsApi>, t: Throwable) {
                Log.d("GBK", "Retrying!")
                getNews()
            }
        })
    }
}