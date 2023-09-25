package com.example.login

import android.content.Context
import android.content.Intent
import android.media.Image
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat.startActivity
import androidx.core.graphics.drawable.toDrawable
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.example.login.NewsAdaptor.NewsView
import com.squareup.picasso.Picasso

class NewsAdaptor() : RecyclerView.Adapter<NewsView>() {


    private val webSite = "https://gnews.io/api/v4/top-headlines?"
    private val apiKey = "&token=ee6737f511688f3398ad086dacd7da6d"
    private var newsLink = ""
    private val elements = ArrayList<News>()
    private lateinit var shareBtn : ImageButton
    private lateinit var context: Context

    class NewsView(elementView: View) : RecyclerView.ViewHolder(elementView) {
        val titleView : TextView = elementView.findViewById(R.id.title)
        val source : TextView = elementView.findViewById(R.id.source)
        val image : ImageView = elementView.findViewById(R.id.image)
        val shareBtn : ImageButton = elementView.findViewById(R.id.share)
        val readBtn : ImageButton = elementView.findViewById(R.id.readLater)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsView {

        val view = LayoutInflater.from(parent.context).inflate(R.layout.news_items, parent, false)
        val newsHolder = NewsView(view)
        context = view.context
        view.setOnClickListener {
            goToWebsite(view.context ,elements[newsHolder.adapterPosition].url)
        }
        return newsHolder
    }

    override fun onBindViewHolder(holder: NewsView, position: Int) {
        val currentNews = elements[position]

        if(currentNews.title != null){
            holder.titleView.text = currentNews.title
        }else{
            holder.titleView.text = "Null"
        }
        if(currentNews.source != null){
            holder.source.text = currentNews.source
        }else{
            holder.source.text = "Null"
        }
        if(currentNews.imageUrl != null){
            Picasso.get().load(currentNews.imageUrl).into(holder.image)
        }else{
            holder.image.setImageDrawable(R.drawable.ic_launcher_background.toDrawable())
        }
        holder.readBtn.setOnClickListener {

        }
        holder.shareBtn.setOnClickListener {
            val sendIntent: Intent = Intent().apply {
                action = Intent.ACTION_SEND
                putExtra(Intent.EXTRA_TEXT, currentNews.url)
                type = "text/plain"
            }
            val shareIntent = Intent.createChooser(sendIntent, null)
            startActivity(context, shareIntent, null)
        }
    }

    override fun getItemCount(): Int {
        return elements.size
    }
    fun addNews(news:News){
        val array = ArrayList<News>()
        array.add(news)
        elements.addAll(array)
    }
    fun newsUpdate(newsData:ArrayList<News>?) {
        elements.clear()
        if (newsData != null) {
            elements.addAll(newsData)
            notifyDataSetChanged()
        }
        else {

        }

    }

    private fun goToWebsite(context: Context, website : String?) {
        val url = Uri.parse(website)
        val webIntent = Intent(Intent.ACTION_VIEW, url)
        startActivity(context, webIntent, null)
    }
    fun getNews(view: View, sort : String) : ArrayList<News>{
//        val assView = findViewById<TextView>(R.id.ass)
        newsLink = webSite + sort + apiKey

        val newsArray = ArrayList<News>()
        val q = Volley.newRequestQueue(view.context)
        val jsnORequest = JsonObjectRequest(
            Request.Method.GET,newsLink,null,
            { response ->
                val news = response.getJSONArray("articles")

                for (i in 0 until news.length()) {

                    val newsObj = news.getJSONObject(i)
                    val loadNews = News(
                        newsObj.getString("title"),
                        newsObj.getJSONObject("source").getString("name"),
                        newsObj.getString("url"),
                        newsObj.getString("image")
                    )
                    newsArray.add(loadNews)
                }

                this.newsUpdate(newsArray)

            },
            {

            }
        )
        q.add(jsnORequest)
        return newsArray
    }
}