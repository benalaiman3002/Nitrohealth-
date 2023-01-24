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
import cat.copernic.abenali.nitrooficial.adapters.MedicamnetsAdapter
import cat.copernic.abenali.nitrooficial.databinding.FragmentMedicamentBinding
import cat.copernic.abenali.nitrooficial.models.Medicaments
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore


class Medicament : Fragment() {
//Fragment de medicaments
    private var _binding: FragmentMedicamentBinding? = null
    private val binding get() = _binding!!
    private val miAdapter: MedicamnetsAdapter = MedicamnetsAdapter()
    private lateinit var medicaments: MutableList<Medicaments>


    companion object {
        public lateinit var idsmed: MutableList<String>
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMedicamentBinding.inflate(inflater, container, false)

        medicaments = arrayListOf(Medicaments())
        idsmed = arrayListOf<String>()
        getMedicamentsModel()
        setupRecyclerView()

        return binding.root
    }
//Cuando le das a crear un medicament va a la pantalla de creacio de medicament
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.btnAnyadirMedicament.setOnClickListener {
            findNavController().navigate(R.id.action_medicament_to_creacioMedicaments)
        }


    }

    private fun setupRecyclerView() {
        //Especifiquem que els fills del RV seran del mateix tamany i així optimitzem la seva creació
        binding.rvMedicament.setHasFixedSize(true)


        //indiquem que el RV es mostrarà en format llista
        binding.rvMedicament.layoutManager = LinearLayoutManager(context)

        //generem el adapter
        //miAdapter.MedicamentsAdapter(getMedicaments()) //,context)

        //assignem el adapter al RV
        binding.rvMedicament.adapter = miAdapter
    }

    private fun getMedicaments(): MutableList<Medicaments> {
        return this.medicaments
    }


    private fun getMedicamentsModel() {
        val db = FirebaseFirestore.getInstance()
        idsmed.clear()
        medicaments.clear()
        db.collection("medicamentsUsuaris").document(FirebaseAuth.getInstance().currentUser!!.uid)
            .collection("Creacio_medicaments").get().addOnSuccessListener {
                Log.d("medicamentsData", it.isEmpty.toString())

                for (document in it) {
                    idsmed.add(document.id)
                    val medicament = document.toObject(Medicaments::class.java)
                    medicaments.add(medicament)
                    Log.d("medicamentsData", document.toObject(Medicaments::class.java).toString())
                }
                miAdapter.MedicamentsAdapter(getMedicaments())
                miAdapter.notifyDataSetChanged()

            }
    }

}

