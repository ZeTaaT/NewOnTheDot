package com.example.login

//import com.squareup.picasso.Request
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.SearchView
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2
import com.example.login.tabsAdaptor.TabsAdaptorer
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class SecondActivity : AppCompatActivity() {

    private val webSite = "https://gnews.io/api/v4/top-headlines?"
    private var query = "q=Ukraine"
    private var sort = "&lang=en&country=gb&max=100"
    private val apiKey = "&token=17e709aa162e07eefb762605ef36e676"
    private var newsLink = ""


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_second)

        val sharedpreferences = getSharedPreferences("myPreference", Context.MODE_PRIVATE)
        val editor = sharedpreferences.edit()
        editor.putString("preferences","breaking-news")
        editor.commit()

        val tabLayout = findViewById<TabLayout>(R.id.layoutTabs)
        val scroller = findViewById<ViewPager2>(R.id.pager)


        val tabTitles = resources.getStringArray(R.array.tabTitles)

        scroller.adapter = TabsAdaptorer(this)
        TabLayoutMediator(tabLayout, scroller) {tab, position ->
            when (position) {
                0 -> tab.text = tabTitles[0]
                1 -> tab.text = tabTitles[1]
                2 -> tab.text = tabTitles[2]
            }

        }.attach()

        val extras = intent.extras
        var msg = ""

        msg = if (extras == null) {
            "No extras found!"
        } else {
            extras.getString("user") as String
        }

    }

    override fun onBackPressed() {
        moveTaskToBack(true)
    }
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate((R.menu.toolbar_layout), menu)
        return super.onCreateOptionsMenu(menu)
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val myView = findViewById<View>(androidx.appcompat.R.id.action_bar)
        when (item.itemId){
            R.id.action_logout -> {
                val snackbar = Snackbar.make(myView, getString(R.string.log_xml),
                        Snackbar.LENGTH_LONG)
                snackbar.show()
                finish()
                return true
            }
            R.id.search -> {
                val view = findViewById<SearchView>(R.id.search)
                view.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                    override fun onQueryTextSubmit(category: String?): Boolean {

                        return true
                    }

                    override fun onQueryTextChange(p0: String?): Boolean {

                        return true
                    }

                }
                )
            }
        }
        return super.onOptionsItemSelected(item)
    }

    fun update() {

    }
}