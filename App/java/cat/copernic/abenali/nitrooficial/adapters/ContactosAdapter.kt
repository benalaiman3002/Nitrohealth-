package cat.copernic.abenali.nitrooficial.adapters

import android.content.ContentValues
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import cat.copernic.abenali.nitrooficial.databinding.ItemsContactosBinding
import cat.copernic.abenali.nitrooficial.fragments.Contactos
import cat.copernic.abenali.nitrooficial.models.Contacto
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore


class ContactosAdapter: RecyclerView.Adapter<ContactosAdapter.ViewHolder>() {
    //Adaptador de Contactos
    var contactoLista: MutableList<Contacto> = ArrayList()
   // lateinit var context: Context

    //constructor de la classe on es passa la font de dades i el context sobre el que es mostrarà

    fun ContactosAdapter(contactoLista:MutableList<Contacto>/*, contxt: Context*/){
        this.contactoLista = contactoLista
        //this.context = contxt
    }

    //és l'encarregat de retornar el ViewHolder ja configurat
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return ViewHolder(
            ItemsContactosBinding.inflate(
                layoutInflater, parent, false
            )
        )
    }

    //Aquest mètode s'encarrega de passar els objectes, un a un al ViewHolder personalitzat
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        with(holder){
            with(contactoLista.get(position)){

                binding.txContacto.text = this.name
                binding.txNumero.text =  this.num

                binding.btnElimCont.tag = position;

                binding.btnElimCont.setOnClickListener {
                    val pos = it.tag as Int
                    Log.d(ContentValues.TAG, "position:$pos}")
                    Log.d(ContentValues.TAG, "valor en posicion:${Contactos.idsContact[pos]}")
                    val db = FirebaseFirestore.getInstance()
                    db.collection("Contactes").document(FirebaseAuth.getInstance().currentUser!!.uid).collection("Creacio_contactes").
                    document(Contactos.idsContact.get(pos))

                        .delete()
                        .addOnSuccessListener { Log.d(ContentValues.TAG, "DocumentSnapshot successfully deleted!")
                            contactoLista.removeAt(pos)
                            Contactos.idsContact.removeAt(pos)
                            notifyItemRemoved(pos)
                            notifyDataSetChanged()
                        }
                        .addOnFailureListener { e -> Log.w(ContentValues.TAG, "Error deleting document", e) }

                }
            }
        }
        val item = contactoLista.get(position)
        holder.bind(item)

        //estamblim un listener
        holder.itemView.setOnClickListener {
           // Toast.makeText(context,contactoLista.get(position).nombre,Toast.LENGTH_LONG).show()
        }

    }


    override fun getItemCount(): Int {
        return contactoLista.size
    }




    class ViewHolder(val binding: ItemsContactosBinding): RecyclerView.ViewHolder(binding.root) {

        fun bind(contacto: Contacto) {

        }

    }


}