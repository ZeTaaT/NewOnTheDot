package com.example.login

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentContainer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar

class PreferFragment : Fragment(){

    private var sort = "&lang=en&country=gb&max=10"
    private var topic = "&topic=breaking-news"
    private lateinit var theNews : ArrayList<News>
    private lateinit var newsAdaptor : NewsAdaptor
    private lateinit var laterView : View


    override fun onCreateView(inflater : LayoutInflater, container: ViewGroup?,savedInstanceState: Bundle?) =
        inflater.inflate(R.layout.recom_gragmet, container, false)!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        try {

            laterView = view
            newsAdaptor = NewsAdaptor()
            theNews = newsAdaptor.getNews(view, sort)
            newsAdaptor.newsUpdate(theNews)
            val recView = view.findViewById<RecyclerView>(R.id.laterView)
            recView.layoutManager = LinearLayoutManager(recView.context)
            recView.adapter = newsAdaptor

        }
        catch (e : Exception) {

        }
    }

}