package com.example.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar

class LaterFragment : Fragment(){


    private lateinit var theNews : ArrayList<News>
    private lateinit var newsAdaptor : NewsAdaptor
    private lateinit var laterView : View

    override fun onCreateView(inflater : LayoutInflater, container: ViewGroup?,savedInstanceState: Bundle?) =
        inflater.inflate(R.layout.later_fragment, container, false)!!


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        try {
            laterView = view
            newsAdaptor = NewsAdaptor()
            val recView = view.findViewById<RecyclerView>(R.id.laterView)
            recView.layoutManager = LinearLayoutManager(recView.context)
            recView.adapter = newsAdaptor
        }
        catch (e : Exception) {
            var snackbar = Snackbar.make(view, "fucking error", Snackbar.LENGTH_LONG)
            snackbar.show()
        }
    }

    override fun onPause() {
        super.onPause()
        val recView = laterView.findViewById<RecyclerView>(R.id.laterView)
        recView.layoutManager = LinearLayoutManager(recView.context)
        recView.adapter = newsAdaptor
    }


}