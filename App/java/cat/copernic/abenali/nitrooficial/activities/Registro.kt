package cat.copernic.abenali.nitrooficial.activities

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.ImageButton
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import cat.copernic.abenali.nitrooficial.R
import cat.copernic.abenali.nitrooficial.databinding.ActivityRegistroBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
//Pantalla de registro
class Registro : AppCompatActivity() {
    companion object {
        internal const val TAG = "EmailPassword"
    }
    private lateinit var auth: FirebaseAuth
    private lateinit var binding: ActivityRegistroBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registro)
        //FirebaseApp.initializeApp(this)
        auth = Firebase.auth
        binding = ActivityRegistroBinding.inflate(layoutInflater)
        setContentView(binding.root)
        auth = FirebaseAuth.getInstance()


        val volver = findViewById<ImageButton>(R.id.VolverRegistro)
        volver.setOnClickListener {
            val intent = Intent(this, InicioUsuario::class.java)
            startActivity(intent)
        }


        binding.BotonConfirmarRegistro.setOnClickListener {
            //val email =  this.binding.EmailRegistre.text.toString().trim()
            val email =  this.binding.EmailRegistre.text.toString().trim()
            val password = this.binding.ContrasenyaRegistre.text.toString().trim()
            val passwordConf = binding.RepetirContraRegistre.text.toString().trim()
            val fecha = this.binding.DataRegistre.text.toString().trim()
            val nom = this.binding.NomRegistre.text.toString().trim()
            val cognom = this.binding.CognomRegistre.text.toString().trim()
            val codpostal = this.binding.CodiPostalRegistre.text.toString().trim()
            val cip = this.binding.CipRegistre.text.toString().trim()
            val _check_terms = this.binding.TermePrivacitatRegistre.isChecked
            val _check_conduse = this.binding.CondicionsUsRegistre.isChecked

            val isCheck = checkCampo(email, password, passwordConf, fecha, nom, cognom, codpostal, cip, _check_terms, _check_conduse)



            if(isCheck.equals(true) /*email.isNotEmpty() && password.isNotEmpty()*/) {
                Log.d(TAG, "Content")
                Log.w(TAG,"content2")
                createAccount(email,password)
                /*if (password == passwordConf) {
                    Log.d(TAG, "content")
                }else {
                    Toast.makeText(baseContext, "Contrasenya incorrecta", Toast.LENGTH_SHORT).show()
                }*/
            }else {
                Toast.makeText(baseContext, "No has omplert tots els camps!", Toast.LENGTH_SHORT).show()
            }

        }

    }

    public override fun onStart() {
        super.onStart()
        // Check if user is signed in (non-null) and update UI accordingly.
        val currentUser = auth.currentUser
        if(currentUser != null){
            reload();
        }
    }

    private fun createAccount(email: String, password: String) {
        print(email)
        print(password)


        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    Log.d(TAG, "createUserWithEmail:success")
                    val user = auth.currentUser
                    updateUI(user)
                } else {
                    Log.w(TAG, "createUserWithEmail:failure", task.exception)
                    Toast.makeText(baseContext, "Authentication failed.",
                        Toast.LENGTH_SHORT).show()
                    updateUI(null)
                }
            }

        confirmaUsuario()
    }

    private fun checkCampo(
        email: String,
        password: String,
        passwordConf: String,
        fecha: String,
        nom: String,
        cognom: String,
        codpostal: String,
        cip: String,
        _check_terms: Boolean,
        _check_condus: Boolean
    ): Boolean {
        var isCheck = false
        if(fecha.contains("/")){
            //Log.w(TAG, "ERROR: El campo fecha no contiene /")
            isCheck = true
        } else {
            //Log.w(TAG, "CORRECTO: El campo fecha contiene /")
            return false
        }

        if(email.contains("@gmail.com") || email.contains("@gmail.es") || email.contains("@hotmail.com")){
            isCheck = true
        } else {
            isCheck = false
            return false
        }
        if(password.length > 5 && passwordConf.length > 5 && (password == passwordConf)) isCheck = true
        else return false

        if(nom.length > 1 && cognom.length > 1) isCheck = true
        else return false

        if(codpostal.length > 4 && cip.length == 9) isCheck = true
        else return false

        if(_check_terms && _check_condus) isCheck = true
        else return false

        return isCheck
    }


    private fun confirmaUsuario() {
        var usuario = auth.currentUser
        if(usuario != null){
            Log.w(TAG, "USUARIO INFO: ${usuario.email}")
        }

    }
    private fun reload() {

    }
    private fun updateUI(user: FirebaseUser?) {
        val intent = Intent(this, InicioUsuario::class.java)
        startActivity(intent)
        finish()
    }
}