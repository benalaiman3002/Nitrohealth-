package cat.copernic.abenali.nitrooficial.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import cat.copernic.abenali.nitrooficial.R
import cat.copernic.abenali.nitrooficial.adapters.ContactosAdapter
import cat.copernic.abenali.nitrooficial.databinding.FragmentContactosBinding
import cat.copernic.abenali.nitrooficial.models.Contacto
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

@Suppress("UNREACHABLE_CODE")
class Contactos : Fragment() {
    //Fragment de la pantalla de contactos
    private var _binding: FragmentContactosBinding? = null
    private val binding get() = _binding!!
    private val miAdapter: ContactosAdapter = ContactosAdapter()
    private val CONTACT_PERMISSION_CODE = 1;
    private val CONTACT_PICK_CODE = 2;
    private lateinit var contactes: MutableList<Contacto>

    companion object {
        public lateinit var idsContact: MutableList<String>


    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentContactosBinding.inflate(inflater, container, false)
        setupRecyclerView()

        idsContact = arrayListOf<String>()
        contactes = arrayListOf(Contacto())

        getContactos()
        setupRecyclerView()
        return binding.root

    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.anyadircontacte.setOnClickListener {
            findNavController().navigate(R.id.action_contactos_to_creacioContactes)
        }
    }


    /*AÑADIR DESPUES CUANDO FUNCIONE TOHDO, SON LOS PERMISOS*/
    /*

    private fun checkContactPermission(): Boolean {
        return onRe(
            requireContext(),
            android.Manifest.permission.READ_CONTACTS
        ) == PackageManager.PERMISSION_GRANTED
    }

    private fun requestPermissionContact() {
        val permission = arrayOf(android.Manifest.permission.READ_CONTACTS)
        requestPermissions(requireActivity() ,permission, CONTACT_PERMISSION_CODE)
    }

    private fun pickContact() {
        val intent = Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI)
        startActivityForResult(intent, CONTACT_PICK_CODE)
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }
*/
    private fun setupRecyclerView(){
        //Especifiquem que els fills del RV seran del mateix tamany i així optimitzem la seva creació
        binding.rvContactos.setHasFixedSize(true)

        //indiquem que el RV es mostrarà en format llista
        binding.rvContactos.layoutManager = LinearLayoutManager(context)

        //generem el adapter
//        miAdapter.ContactosAdapter(getContactos())

        //assignem el adapter al RV
        binding.rvContactos.adapter = miAdapter
    }

    private fun getContacts(): MutableList<Contacto> {
        return this.contactes
    }
//Funcion que recoge todos los contactos de la base de datos
    private fun getContactos(){
        val db = FirebaseFirestore.getInstance()
            idsContact.clear()
            contactes.clear()
            db.collection("Contactes").document(FirebaseAuth.getInstance().currentUser!!.uid)
                .collection("Creacio_contactes").get().addOnSuccessListener {
                    Log.d("contactos", it.isEmpty.toString())

                    for (document in it) {
                        idsContact.add(document.id)
                        val contacto = document.toObject(Contacto::class.java)
                        contactes.add(contacto)
                        Log.d("contactos", document.toObject(Contacto::class.java).toString())
                    }
                    miAdapter.ContactosAdapter(getContacts())
                    miAdapter.notifyDataSetChanged()
                }


            }


}

private fun <E> MutableList<E>.add(element: Contacto) {

}
