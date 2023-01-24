package cat.copernic.abenali.nitrooficial.adapters
import android.content.ContentValues.TAG
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import cat.copernic.abenali.nitrooficial.R
import cat.copernic.abenali.nitrooficial.databinding.ItemsMedicamentsBinding
import cat.copernic.abenali.nitrooficial.fragments.Medicament.Companion.idsmed
import cat.copernic.abenali.nitrooficial.models.Medicaments
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore


class MedicamnetsAdapter: RecyclerView.Adapter<MedicamnetsAdapter.ViewHolder>() {
    //Adaptador de Medicaments
    var MedicamentsLista: MutableList<Medicaments> = ArrayList()
    //lateinit var context: Context


    //constructor de la classe on es passa la font de dades i el context sobre el que es mostrarà
    fun MedicamentsAdapter(MedicamentsLista:MutableList<Medicaments>/*, contxt: Context*/){
        this.MedicamentsLista = MedicamentsLista
        //this.context = contxt
    }

    //és l'encarregat de retornar el ViewHolder ja configurat
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return ViewHolder(
            ItemsMedicamentsBinding.inflate(
                layoutInflater, parent, false
            )
        )


    }

    //Aquest mètode s'encarrega de passar els objectes, un a un al ViewHolder personalitzat
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        with(holder){
            with(MedicamentsLista[position]){
                binding.txtMedicamento.text = this.nombre
                //binding.imgMedicament.load(this.imageMedicament)
                binding.txtTipus.text = this.tipus
                binding.txtFrequencia.text = this.frequencia
                binding.txtQuantitat.text = this.quantitat
                binding.ibEditar.setOnClickListener {
                    Navigation.findNavController(it).navigate(R.id.action_medicament_to_creacioMedicaments)
                }
                binding.ibBorrar.tag = position;

                binding.ibBorrar.setOnClickListener {
                    val pos = it.tag as Int
                    Log.d(TAG, "position:$pos}")
                    Log.d(TAG, "valor en posicion:${idsmed[pos]}")
                    val db = FirebaseFirestore.getInstance()
                    db.collection("medicamentsUsuaris").document(FirebaseAuth.getInstance().currentUser!!.uid).collection("Creacio_medicaments").
                    document(idsmed.get(pos))

                        .delete()
                        .addOnSuccessListener { Log.d(TAG, "DocumentSnapshot successfully deleted!")
                            MedicamentsLista.removeAt(pos)
                            idsmed.removeAt(pos)
                            notifyItemRemoved(pos)
                            notifyDataSetChanged()
                        }
                        .addOnFailureListener { e -> Log.w(TAG, "Error deleting document", e) }

                }
            }
        }
        val item = MedicamentsLista[position]
        holder.bind(item)

        //estamblim un listener
        holder.itemView.setOnClickListener {
            //Toast.makeText(context,MedicamentsLista.get(position).nombre,Toast.LENGTH_LONG).show()
        }
    }



    override fun getItemCount(): Int {
        return MedicamentsLista.size
    }


    class ViewHolder(val binding: ItemsMedicamentsBinding): RecyclerView.ViewHolder(binding.root) {

        fun bind(Medicaments: Medicaments) {

        }

    }



}