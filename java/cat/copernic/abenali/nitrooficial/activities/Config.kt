package cat.copernic.abenali.nitrooficial.activities

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import cat.copernic.abenali.nitrooficial.databinding.ActivityConfigBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class Config : AppCompatActivity() {
    //Pantalla de configuración
    lateinit var auth: FirebaseAuth
    lateinit var binding: ActivityConfigBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        auth = Firebase.auth
        binding = ActivityConfigBinding.inflate(layoutInflater)
        setContentView(binding.root)
        auth = FirebaseAuth.getInstance()
//Botón para retroceder
        binding.volverConfig.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

    }
}