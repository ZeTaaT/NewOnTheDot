package com.example.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class TrendFragment : Fragment(){

    private var sort = "&lang=en&country=gb&max=10"
    private lateinit var theNews : ArrayList<News>
    private lateinit var newsAdaptor : NewsAdaptor
    private lateinit var imageButton : ImageButton

    override fun onCreateView(inflater : LayoutInflater, container: ViewGroup?,savedInstanceState: Bundle?) =
        inflater.inflate(R.layout.trend_fragment, container, false)!!


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        try {
            newsAdaptor = NewsAdaptor()
            theNews = newsAdaptor.getNews(view, sort)
            newsAdaptor.newsUpdate(theNews)
            val recView = view.findViewById<RecyclerView>(R.id.trendView)
            recView.layoutManager = LinearLayoutManager(recView.context)
            recView.adapter = newsAdaptor
        }
        catch (e : Exception) {

        }
    }

}