package com.esthiti.horoscopeandroid.actvities

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuInflater
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.appcompat.widget.SearchView.OnQueryTextListener
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.esthiti.horoscopeandroid.HoroscopeAdapter
import com.esthiti.horoscopeandroid.R
import com.esthiti.horoscopeandroid.data.HoroscopeProvider

class MainActivity : AppCompatActivity() {

    // Listado con los items del horóscopo
    var horoscopeList = HoroscopeProvider.getAllHoroscopeList()

    lateinit var recyclerView : RecyclerView
    lateinit var adapter : HoroscopeAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        recyclerView = findViewById(R.id.recyclerView)

    }

    /**
     *
     */
    override fun onResume() {
        super.onResume()

        adapter = HoroscopeAdapter(horoscopeList, { position ->
            val horoscope = horoscopeList[position]
            Log.i("CLICK", "Click sobre un item del horóscopo $horoscope")

            val intent = Intent(this, DetailActivity::class.java)
            intent.putExtra("HOROSCOPE_ID", horoscope.id)
            startActivity(intent)
        })

        recyclerView.adapter = adapter

        // LinearLayoutManager, GridLayoutManager, StaggeredGridLayoutManager

        //recyclerView.layoutManager = GridLayoutManager(this, 2)
        //recyclerView.layoutManager = StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL)
        // recyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        recyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)

    }

    /**
     * Mostrar el menú en la app Horoscope
     */
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // val inflater: MenuInflater = menuInflater
        // inflater.inflate(R.menu.activity_main_menu, menu)
        menuInflater.inflate(R.menu.activity_main_menu, menu)

        val menuItem = menu.findItem(R.id.menu_search)
        val searchView = menuItem.actionView as SearchView

        searchView.setOnQueryTextListener(object : OnQueryTextListener{
            /**
             * Called when the user submits the query. This could be due to a key press on the
             * keyboard or due to pressing a submit button.
             * The listener can override the standard behavior by returning true
             * to indicate that it has handled the submit request. Otherwise return false to
             * let the SearchView handle the submission by launching any associated intent.
             *
             * @param query the query text that is to be submitted
             *
             * @return true if the query has been handled by the listener, false to let the
             * SearchView perform the default action.
             */
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            /**
             * Called when the query text is changed by the user.
             *
             * @param newText the new content of the query text field.
             *
             * @return false if the SearchView should perform the default action of showing any
             * suggestions if available, true if the action was handled by the listener.
             */
            override fun onQueryTextChange(newText: String): Boolean {

                horoscopeList = HoroscopeProvider.getAllHoroscopeList().filter { horoscope ->
                    getString(horoscope.name).contains(newText, true)
                }

                adapter.updateItems(horoscopeList)

                return true
            }

        })


        // Activity_main, HoroscopeAdapter, Main_activity

        return true
    }
}