package com.esthiti.horoscopeandroid.actvities

import android.content.ClipData.Item
import android.content.Intent
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
import com.esthiti.horoscopeandroid.data.Horoscope
import com.esthiti.horoscopeandroid.data.HoroscopeProvider
import com.esthiti.horoscopeandroid.utils.SessionManager

class DetailActivity : AppCompatActivity() {

    lateinit var ivIcon : ImageView
    lateinit var tvHoroscopeName : TextView
    lateinit var tvDates : TextView

    // Variables de session para controlar el item favorito
    lateinit var session : SessionManager
    lateinit var horoscope : Horoscope
    lateinit var favoriteMenuItem : MenuItem
    var isFavorite = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_detail)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        session = SessionManager(this)

        // Recupera los componentes de la vista
        ivIcon = findViewById(R.id.ivIcon)
        tvHoroscopeName = findViewById(R.id.tvHoroscopeName)
        tvDates = findViewById(R.id.tvDates)

        // Se añade los signos !! para forzar a que acepte que siempre va a devolver datos, que no será null
        val id = intent.getStringExtra("HOROSCOPE_ID")!!

        horoscope = HoroscopeProvider.getById(id)!!

        // ¿El item actual es favorito?
        isFavorite = session.getFavoriteHoroscope() == horoscope.id

        ivIcon.setImageResource(horoscope.icon)
        tvHoroscopeName.setText(horoscope.name)
        tvDates.setText(horoscope.dates)

        // Mensaje tipo alert al usuario
        Toast.makeText(this, getString(horoscope.name), Toast.LENGTH_SHORT).show()
    }


    override fun onCreateOptionsMenu(menu: Menu): Boolean {

        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.activity_detail_menu, menu)

        favoriteMenuItem = menu.findItem(R.id.menu_favorite)
        setFavoriteIcon()
        return true
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.menu_favorite -> {

                Toast.makeText(this, "Favorite", Toast.LENGTH_SHORT).show()

                if (isFavorite){
                    session.setFavoriteHoroscope("")
                } else{
                    session.setFavoriteHoroscope(horoscope.id)
                }
                isFavorite = !isFavorite
                setFavoriteIcon()

                return true
            }
            R.id.menu_share -> {

                Toast.makeText(this, "Share", Toast.LENGTH_SHORT).show()

                val sendIntent = Intent()
                sendIntent.setAction(Intent.ACTION_SEND)
                sendIntent.putExtra(Intent.EXTRA_TEXT, "This is my text to send")
                sendIntent.setType("text/plain")

                val shareIntent = Intent.createChooser(sendIntent, null)
                startActivity(shareIntent)

                return true
            }
            else -> {
                return  super.onOptionsItemSelected(item)
            }
        }
    }


    fun setFavoriteIcon(){
        if (isFavorite){
            favoriteMenuItem.setIcon(R.drawable.ic_favorite_selected)
        } else{
            favoriteMenuItem.setIcon(R.drawable.ic_favorite)
        }
    }
}