package com.example.newsassiment.news.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.newsassiment.R
import com.example.newsassiment.news.NewsDetailsActivity
import com.example.newsassiment.news.NewsWebViewActivity

import com.example.newsassiment.news.networking.Article

class ItemAdapter(
    private val context: Context,
    private val dataset: MutableList<Article>
) : RecyclerView.Adapter<ItemAdapter.ItemViewHolder>() {

    class ItemViewHolder(private val view: View) : RecyclerView.ViewHolder(view) {
        val textView: TextView = view.findViewById(R.id.item_title)
        val imageView: ImageView = view.findViewById(R.id.item_image)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val adapterLayout = LayoutInflater.from(parent.context).inflate(R.layout.list_item, parent, false)

        return ItemViewHolder(adapterLayout)
    }

    override fun getItemCount(): Int {
        return dataset.size
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val item = dataset[position]

        holder.itemView.setOnClickListener {

            val intent = Intent(it.context, NewsDetailsActivity::class.java)
            intent.putExtra("name", item.title)
            val temp = item.urlToImage
            if (temp == null) {
                intent.putExtra(
                    "image",
                    "https://www.feednavigator.com/var/wrbm_gb_food_pharma/storage/images/_aliases/news_large/9/2/8/5/235829-6-eng-GB/Feed-Test-SIC-Feed-20142.jpg"
                )

            } else {
                intent.putExtra("image", item.urlToImage)
            }
            intent.putExtra("description", item.description)
            intent.putExtra("source", item.url)

            it.context.startActivity(intent)
        }

        holder.textView.text = item.title

        if (item.urlToImage == null) {
            Glide.with(context)
                .load("https://www.feednavigator.com/var/wrbm_gb_food_pharma/storage/images/_aliases/news_large/9/2/8/5/235829-6-eng-GB/Feed-Test-SIC-Feed-20142.jpg")
                .into(holder.imageView)
        } else {
            Glide.with(context)
                .load(item.urlToImage)
                .into(holder.imageView)
        }
    }

    // Add a method to add a list of articles to the existing dataset
    fun addAll(newDataset: List<Article>) {
        dataset.addAll(newDataset)
        notifyDataSetChanged()
    }

    // Add a method to clear the dataset
    fun clear() {
        dataset.clear()
        notifyDataSetChanged()
    }

}