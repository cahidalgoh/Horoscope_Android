package com.esthiti.horoscopeandroid.actvities

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.esthiti.horoscopeandroid.R
import com.esthiti.horoscopeandroid.data.HoroscopeProvider

class DetailActivity : AppCompatActivity() {

    lateinit var ivIcon : ImageView
    lateinit var tvHoroscopeName : TextView
    lateinit var tvDates : TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_detail)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Recupera los componentes de la vista
        ivIcon = findViewById(R.id.ivIcon)
        tvHoroscopeName = findViewById(R.id.tvHoroscopeName)
        tvDates = findViewById(R.id.tvDates)

        // Se añade los signos !! para forzar a que acepte que siempre va a devolver datos
        val id = intent.getStringExtra("HOROSCOPE_ID")!!

        var horoscope = HoroscopeProvider.getById(id)!!

        ivIcon.setImageResource(horoscope.icon)
        tvHoroscopeName.setText(horoscope.name)
        tvDates.setText(horoscope.dates)

        Toast.makeText(this, getString(horoscope.name), Toast.LENGTH_SHORT).show()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {

        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.activity_detail_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.menu_favorite -> {
                Toast.makeText(this, "Favorite", Toast.LENGTH_SHORT).show()
                return true
            }
            R.id.menu_share -> {
                Toast.makeText(this, "Share", Toast.LENGTH_SHORT).show()
                return true
            }
            else -> {
                return  super.onOptionsItemSelected(item)
            }
        }
    }
}