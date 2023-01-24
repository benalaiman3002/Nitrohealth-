package cat.copernic.abenali.nitrooficial.fragments

import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import cat.copernic.abenali.nitrooficial.R
import com.google.android.gms.location.*
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions

class Localitzacio : Fragment() {
    //Fragment que muestra el mapa
    private var MY_PERMISSIONS_REQUEST_LOCATION = 99
    private var LOCATION_SERVICE = null
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private lateinit var locationManager: LocationManager
    var gpsStatus = false
    @SuppressLint("MissingPermission")
    //El callback que llama al mapa con sus marcadores y ubicación actual
    private val callback = OnMapReadyCallback { googleMap ->
        val mutua = LatLng(41.563694, 2.017104)
        val farmacia1= LatLng(41.568786639193696, 2.0048306640106723)
        val farmacia2= LatLng(41.56898796954726, 1.9958327784593028)
        val farmacia3= LatLng(41.5699927048988, 2.001494394068934)
        val cm1= LatLng(41.567420579016435, 2.006868746091136)
        googleMap.addMarker(MarkerOptions().position(mutua).title("Mutua Terrassa"))
        googleMap.addMarker(MarkerOptions().position(farmacia1).title("Farmacia"))
        googleMap.addMarker(MarkerOptions().position(farmacia2).title("Farmacia"))
        googleMap.addMarker(MarkerOptions().position(farmacia3).title("Farmacia"))
        googleMap.addMarker(MarkerOptions().position(cm1).title("Centre Médic"))


        if (ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_FINE_LOCATION)
            != PackageManager.PERMISSION_GRANTED) {
            // Solicita los permisos de ubicación si no se han concedido
            ActivityCompat.requestPermissions(
                requireActivity(),
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                MY_PERMISSIONS_REQUEST_LOCATION
            )
        }
            if (ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireActivity())
            fusedLocationClient.lastLocation.addOnSuccessListener { location: Location? ->
                if (location != null) {

                    // Obtiene la ubicación y mueve la cámara del mapa a esa ubicación

                    val currentLatLng = LatLng(location.latitude, location.longitude)
                    googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(currentLatLng, 15f))
                    googleMap.addMarker(
                        MarkerOptions().position(currentLatLng).title("Mi ubicación actual")
                    )
                }
            }

        } else {
            // Si los permisos ya se han concedido, obtiene la ubicación actual
            fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireActivity())
            fusedLocationClient.lastLocation.addOnSuccessListener { location : Location? ->
                    if(location != null) {

                // Obtiene la ubicación y mueve la cámara del mapa a esa ubicación

                        val currentLatLng = LatLng(location.latitude, location.longitude)
                        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(currentLatLng, 15f))
                        googleMap.addMarker(MarkerOptions().position(currentLatLng).title("Mi ubicación actual"))
                }
            }
        }

        val locationCallback = object : LocationCallback() {
            override fun onLocationResult(p0: LocationResult) {
                p0 ?: return
                for (location in p0.locations){
                    // do your stuff with location
                }
            }
        }
        val locationRequest = LocationRequest().apply {
            interval = 5000
            fastestInterval = 2000
            priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        }
        fusedLocationClient?.requestLocationUpdates(locationRequest, locationCallback, null)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.fragment_maps, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
        mapFragment?.getMapAsync(callback)
    }


}