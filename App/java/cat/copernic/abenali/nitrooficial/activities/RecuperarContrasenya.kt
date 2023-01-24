package cat.copernic.abenali.nitrooficial.activities

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.ImageButton
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import cat.copernic.abenali.nitrooficial.R
import cat.copernic.abenali.nitrooficial.databinding.ActivityRecuperarContrasenyaBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class RecuperarContrasenya : AppCompatActivity() {
//Pantalla de recuperar contraseña

    private lateinit var binding: ActivityRecuperarContrasenyaBinding
    private lateinit var firebaseAuth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRecuperarContrasenyaBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val volver = findViewById<ImageButton>(R.id.VolverRecuperar)

        binding.botonConfirmarRecuperar.setOnClickListener {
            Log.d("Recuperar", "va a recuperar el correo")

            val correo = binding.etEmail.text.toString().trim()
            enviaGmail(correo)
        }

        volver.setOnClickListener {
            val intent = Intent(this, InicioUsuario::class.java)
            startActivity(intent)

        }

        firebaseAuth = Firebase.auth

    }
//funcion que te envia un correo al email que se introduce en el cuadro de texto y te permite recuperar tu contraseña
    private fun enviaGmail(correo: String) {
        Log.d("Recuperar", "z$correo z")
        firebaseAuth.sendPasswordResetEmail(correo)
            .addOnCompleteListener() { task ->
                if (task.isSuccessful) {
                    Toast.makeText(baseContext,"Correo enviado!",Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(
                        baseContext, "Mal",
                        Toast.LENGTH_SHORT
                    ).show()
                }

            }
    }

}