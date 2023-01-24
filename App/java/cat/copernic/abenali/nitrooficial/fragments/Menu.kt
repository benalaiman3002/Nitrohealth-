package cat.copernic.abenali.nitrooficial.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import cat.copernic.abenali.nitrooficial.R
import cat.copernic.abenali.nitrooficial.databinding.FragmentMenuBinding



class Menu : Fragment() {
    //Fragment de menú, la pantalla principal
    private var _binding:FragmentMenuBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = cat.copernic.abenali.nitrooficial.databinding.FragmentMenuBinding.inflate(inflater, container, false)

        return binding.root
    }
    //Cada vez que se le de a una opción del menu te llevará a su pantalla correspondiente
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.botonmapa.setOnClickListener {
            findNavController().navigate(R.id.action_menu_to_mapsFragment)
        }
        binding.botonmedicaments.setOnClickListener {
            findNavController().navigate(R.id.action_menu_to_medicament)
        }
        binding.botonagenda.setOnClickListener {
            findNavController().navigate(R.id.action_menu_to_contactos)
        }
    }

    }




