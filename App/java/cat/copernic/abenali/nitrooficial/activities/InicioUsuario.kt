package cat.copernic.abenali.nitrooficial.activities

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import cat.copernic.abenali.nitrooficial.databinding.ActivityInicioUsuarioBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class InicioUsuario : AppCompatActivity() {
//Pantalla de Inicio de Usuario
    private lateinit var auth: FirebaseAuth
    private lateinit var binding: ActivityInicioUsuarioBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        auth = Firebase.auth
        binding = ActivityInicioUsuarioBinding.inflate(layoutInflater)
        setContentView(binding.root)
        auth = FirebaseAuth.getInstance()

//Botón que te manda a la pantalla de Registro
        binding.RegistratLink.setOnClickListener {
            val intent = Intent(this, Registro::class.java)
            startActivity(intent)
        }

//Botón que te manda al restablecer contraseña
        binding.linkRestablir.setOnClickListener {
            val intent = Intent(this, RecuperarContrasenya::class.java)
            startActivity(intent)
        }
//Botón que confirma si los campos de texto están vacíos o no e inicia sesión llamando a la función "signIn"
        binding.botonConfirmarLogin.setOnClickListener {
            var email = this.binding.emailInicio.text.toString().trim()
            var password = this.binding.ContraInicio.text.toString().trim()
            if (email.isNotEmpty() && password.isNotEmpty()) {
                signIn(email, password)
            } else {
                Toast.makeText(baseContext, "No has omplert tots els camps!", Toast.LENGTH_SHORT)
                    .show()
            }

        }
    }

//Función que comprueba que los datos de los cuadros de texto y en la base de datos coinciden y te permite iniciar sesión
    private fun signIn(email: String, password: String) {
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    Log.d(Registro.TAG, "signInUserWithEmail:success")
                    val user = auth.currentUser
                    updateUI(user)
                } else {
                    Log.w(Registro.TAG, "signInUserWithEmail:failure", task.exception)
                    Toast.makeText(
                        baseContext, "Authentication failed.",
                        Toast.LENGTH_SHORT
                    )
                }
            }

        confirmaUsuario()
    }
    //Función que cambia de pantalla al MainActivity
    private fun updateUI(user: FirebaseUser?) {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }
//Función que cconfirma si el usuario está en la base de datos o no
    private fun confirmaUsuario() {
        var usuario = auth.currentUser
        if(usuario != null){
            Log.w(Registro.TAG, "USUARIO INFO: ${usuario.email}")
        }

    }

}