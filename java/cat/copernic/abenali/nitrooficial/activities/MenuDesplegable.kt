package cat.copernic.abenali.nitrooficial.activities

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import androidx.appcompat.app.AppCompatActivity
import cat.copernic.abenali.nitrooficial.R
// Menú desplegable en la parte superior de las pantallas
class MenuDesplegable : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu_desplegable)

    }
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.menu_desplegable, menu)
        return true

    }


      }

