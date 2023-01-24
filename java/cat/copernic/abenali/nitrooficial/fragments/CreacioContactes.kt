package cat.copernic.abenali.nitrooficial.fragments

import android.content.ContentValues
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import cat.copernic.abenali.nitrooficial.R
import cat.copernic.abenali.nitrooficial.adapters.ContactosAdapter
import cat.copernic.abenali.nitrooficial.databinding.FragmentCreacioContactesBinding
import cat.copernic.abenali.nitrooficial.models.Contacto
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore


class CreacioContactes : Fragment() {
    //Fragment de Creación de contactos
    private var _binding: FragmentCreacioContactesBinding? = null
    private val binding get() = _binding!!
    private val miAdapter: ContactosAdapter = ContactosAdapter()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentCreacioContactesBinding.inflate(inflater, container, false)

        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

//Recoge todos los valores de los textos y los añade a la base de datos
        binding.btnAceptarContacte.setOnClickListener {
            val db = FirebaseFirestore.getInstance()
            Log.d(ContentValues.TAG, "Se va a añadir aaaa")
            val creaContacte = Contacto(
                binding.ctNomContacte.text.toString(),
                binding.ctTelefonContacte.text.toString()
            )
            db.collection("Contactes").document(FirebaseAuth.getInstance().currentUser!!.uid)
                .collection("Creacio_contactes").document()
                .set(creaContacte)

                .addOnSuccessListener { documento ->
                    Log.d(ContentValues.TAG, "Se ha añadido correctamente")
                }
                .addOnFailureListener { error ->
                    Log.w(ContentValues.TAG, "Error", error)
                }

            findNavController().navigate(R.id.action_creacioContactes_to_contactos)
        }
        binding.btnCancelarContacte.setOnClickListener {
            findNavController().navigate(R.id.action_creacioContactes_to_contactos)
        }
    }
    }
