package cat.copernic.abenali.nitrooficial.fragments

//import android.os.Bundle
//import androidx.fragment.app.Fragment

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.ContentValues.TAG
import android.content.Context
import android.graphics.Bitmap
import android.graphics.Matrix
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import cat.copernic.abenali.nitrooficial.R
import cat.copernic.abenali.nitrooficial.adapters.MedicamnetsAdapter
import cat.copernic.abenali.nitrooficial.databinding.FragmentCreacioMedicamentsBinding
import cat.copernic.abenali.nitrooficial.models.Medicaments
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore


@Suppress("UNREACHABLE_CODE")
class CreacioMedicaments : Fragment(), AdapterView.OnItemSelectedListener {
    //Fragment de creación de Medicamentos
    var cantidades = arrayOf("5mg/ml", "10mg/ml", "15mg/ml", "20mg/ml", "25mg/ml", "30mg/ml","35mg/ml", "40mg/ml", "45mg/ml", "50mg/ml","55mg/ml", "60mg/ml", "65mg/ml", "70mg/ml","75mg/ml", "80mg/ml", "85mg/ml", "90mg/ml","95mg/ml", "100mg/ml")
    var frecuencia = arrayOf(" 1 hora", " 2 horas", " 3 horas", " 4 horas", " 5 horas", " 6 horas"," 7 horas", " 8 horas", " 9 horas", " 10 horas"," 15 horas", " 20 horas", " 24 horas", " 48 horas")
    private var _binding: FragmentCreacioMedicamentsBinding? = null
    private val binding get() = _binding!!
    private val miAdapter: MedicamnetsAdapter = MedicamnetsAdapter()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentCreacioMedicamentsBinding.inflate(inflater, container, false)
        var acantitades = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, cantidades)
        acantitades.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        var afrecuencia = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, frecuencia)
        afrecuencia.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

//Le da los valores a los spinners

        with(binding.spnQuantitat)
        {
            adapter = acantitades
            setSelection(0, false)
            onItemSelectedListener = this@CreacioMedicaments
            gravity = Gravity.CENTER

        }
        with(binding.spnFrequencia)
        {
            adapter = afrecuencia
            setSelection(0, false)
            onItemSelectedListener = this@CreacioMedicaments
            gravity = Gravity.CENTER

        }
        return binding.root
    }

    fun objetoMedicament() {
        val medicamentoNom = binding.etNomMedicament
        val fotoMedicament = binding.tvFoto
    }
//Crea los medicamentos y los pone en la base de datos recogiendo los valores que se les asigna.
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.btnCancelarmedicement.setOnClickListener {
            findNavController().navigate(R.id.action_creacioMedicaments_to_medicament)
        }
        binding.btnAceptarMedicament.setOnClickListener {
            val db = FirebaseFirestore.getInstance()
            Log.d(TAG, "Se va a añadir aaaa")
            val creamedicament = Medicaments(
                binding.etNomMedicament.text.toString(),
                "",
                "Pastilla",
                "5mg/ml",
                "2h"

            )

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                val name = "NitroHealth"
                val importance = NotificationManager.IMPORTANCE_DEFAULT
                val channel = NotificationChannel("pruebitasNotif", name, importance)
                val notificationManager: NotificationManager =
                    context?.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
                notificationManager.createNotificationChannel(channel)
            }

            var builder = getContext()?.let {
                NotificationCompat.Builder(it, "pruebitasNotif")
                    .setSmallIcon(R.drawable.logoinv)
                    .setContentTitle("NitroHealth")
                    .setContentText(it.resources.getString(R.string.notificacionMedicament))
                    .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                    .setCategory(NotificationCompat.CATEGORY_ALARM)
            }?.build()


            val notifManager = context?.let { it1 -> NotificationManagerCompat.from(it1) }
            // notificationId is a unique int for each notification that you must define
            if (builder != null) {
                notifManager?.notify(1, builder)
            }


            db.collection("medicamentsUsuaris").document(FirebaseAuth.getInstance().currentUser!!.uid)
                .collection("Creacio_medicaments").document()
                .set(creamedicament)
                .addOnSuccessListener { documento ->
                    Log.d(TAG, "Se ha añadido correctamente")
                    findNavController().navigate(R.id.action_creacioMedicaments_to_medicament)

                }
                .addOnFailureListener { error ->
                    Log.w(TAG, "Error", error)
                }
        }

        }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        showToast(message = "Has escogido una opción.")

        }

    private fun showToast(context: Context = requireContext(), message: String, duration: Int = Toast.LENGTH_LONG) {
        Toast.makeText(context, message, duration).show()
    }

    override fun onNothingSelected(parent: AdapterView<*>?) {
        Toast.makeText(requireContext(), "Nothing selected", Toast.LENGTH_SHORT).show()
    }

}



    private fun scaleImage(mBitmap: Bitmap, newWidth: Float, newHeigth: Float): Bitmap {
        //Redimensionem
        val width = mBitmap.width
        val height = mBitmap.height
        var scaleWidth = newWidth / width
        var scaleHeight = newHeigth / height

        //ens quedem amb l'escalat més petit per que no es deformi la imatge
        if (scaleWidth > scaleHeight) scaleWidth = scaleHeight
        else scaleHeight = scaleWidth

        // create a matrix for the manipulation
        val matrix = Matrix()
        // resize the bit map
        matrix.postScale(scaleWidth, scaleHeight)
        // recreate the new Bitmap
        return Bitmap.createBitmap(mBitmap, 0, 0, width, height, matrix, false)
    }




