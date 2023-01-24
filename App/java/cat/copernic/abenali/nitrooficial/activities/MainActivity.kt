package cat.copernic.abenali.nitrooficial.activities

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupActionBarWithNavController
import cat.copernic.abenali.nitrooficial.R
import cat.copernic.abenali.nitrooficial.databinding.ActivityMainBinding
import com.google.firebase.auth.FirebaseAuth
//Main activity, donde se carga el nav_graph y se ven todas las pantallas.
@Suppress("DEPRECATION")
class MainActivity : AppCompatActivity() {
    private lateinit var navController: NavController
    private var _binding: ActivityMainBinding? = null
    private val binding get() = _binding!!
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val user = FirebaseAuth.getInstance().currentUser
        val navHostController = supportFragmentManager
            .findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController = navHostController.navController

        setupActionBarWithNavController(navController)


    }
    //Funcion que hace visible la barra de opciones
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.menu_desplegable, menu)
        return true

    }
//Funcion que muestra las opciones de la barra de opciones
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.cerrar_sesion -> {
                val intent = Intent(this, InicioUsuario::class.java)
                startActivity(intent)
                finish()
                true
            }
              R.id.config1 -> {
                  val intent = Intent(this, Config::class.java)
                  startActivity(intent)
                   finish()
                   true
               }
               R.id.about -> {
                   val intent = Intent(this, About::class.java)
                   startActivity(intent)
                   finish()
                   true
               }
            else -> super.onOptionsItemSelected(item)
        }

    }


//Función que permite navegar hacia las pantallas anteriores
    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp() || super.onSupportNavigateUp()
    }
}