package com.app.yuyata.maps

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Address
import android.location.Geocoder
import android.location.LocationManager
import android.os.Bundle
import android.os.Looper
import android.provider.Settings
import android.text.TextUtils
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.app.ActivityCompat.requestPermissions
import androidx.core.app.ActivityCompat.shouldShowRequestPermissionRationale
import androidx.core.content.ContextCompat
import com.app.yuyata.R
import com.app.yuyata.dashboard.dosis.DosisFragment
import com.app.yuyata.databinding.FragmentMapsBinding
import com.google.android.gms.common.api.Status
import com.google.android.gms.location.*
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.gms.tasks.OnCompleteListener

import java.util.*

class MapsFragment : Fragment(), OnMapReadyCallback {
    //Variables

    private var _binding: FragmentMapsBinding? = null
    private val binding get() = _binding!!

    private lateinit var client: FusedLocationProviderClient
    private lateinit var map: GoogleMap

    //Button
    lateinit var searchButton: ImageButton

    //Coordenadas
    var latU: Double = 0.0
    var longU: Double = 0.0


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {


        client = LocationServices.getFusedLocationProviderClient(requireActivity())

        _binding = FragmentMapsBinding.inflate(inflater, container, false)


        searchButton = binding.searchAddress
        searchButton.setOnClickListener {
            val addressField = binding.locationSearch
            val address = addressField.text.toString()
            var addressList: List<Address>? = null
            val userMarkerOptions = MarkerOptions()
            if (!TextUtils.isEmpty(address)) {
                val geocoder = Geocoder(requireActivity())
                addressList = geocoder.getFromLocationName(address,6)
                if(addressList!=null){
                    for (i in addressList.indices) {
                        val userAddress = addressList[i]
                        val userlatLng = LatLng(userAddress.latitude, userAddress.longitude)
                        userMarkerOptions.position(userlatLng)
                        userMarkerOptions.title(address)
                        userMarkerOptions.icon(
                            BitmapDescriptorFactory.defaultMarker(
                                BitmapDescriptorFactory.HUE_AZURE
                            )
                        )
                        map.addMarker(userMarkerOptions)
                        map.moveCamera(CameraUpdateFactory.newLatLng(userlatLng))
                        map.animateCamera(CameraUpdateFactory.zoomTo(15f))
                    }
                }else{
                    Toast.makeText(requireActivity(), this.getString(R.string.toast_location_not_found), Toast.LENGTH_SHORT).show()
                }
            }else{
                Toast.makeText(requireActivity(), this.getString(R.string.toast_location_search), Toast.LENGTH_SHORT).show()
            }
        }
        checkPermissions()
        createFragment()
        return binding.root;
    }

    private fun makeCurrentFragment(fragment: Fragment) =
        requireActivity().supportFragmentManager.beginTransaction().apply {
            replace(R.id.fl_wrapper, fragment)
            commit()
        }


    /*-----------------Inicia aquí*/
    companion object {
        const val REQUEST_CODE_LOCATION = 0
    }

    private fun isPermissionsGranted() = ContextCompat.checkSelfPermission(
        requireActivity(),
        Manifest.permission.ACCESS_FINE_LOCATION
    ) == PackageManager.PERMISSION_GRANTED

    @SuppressLint("MissingPermission")
    private fun enableMyLocation() {
        if (!::map.isInitialized) return
        if (isPermissionsGranted()) {
            map.isMyLocationEnabled = true
        } else {
            requestLocationPermission()
        }
    }

    private fun requestLocationPermission() {
        if (shouldShowRequestPermissionRationale(
                requireActivity(),
                Manifest.permission.ACCESS_FINE_LOCATION
            )
        ) {
            Toast.makeText(activity, this.getString(R.string.toast_location_permission_2), Toast.LENGTH_SHORT)
                .show()
        } else {
            ActivityCompat.requestPermissions(
                requireActivity(),
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                REQUEST_CODE_LOCATION
            )
        }
    }

    @SuppressLint("MissingPermission")
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        when (requestCode) {
            REQUEST_CODE_LOCATION -> if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                map.isMyLocationEnabled = true
            } else {
                Toast.makeText(
                    activity,
                    this.getString(R.string.toast_location_permission_2),
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    @SuppressLint("MissingPermission")
    override fun onResume() {
        super.onResume()
        if (!::map.isInitialized) return
        if (!isPermissionsGranted()) {
            map.isMyLocationEnabled = false
            Toast.makeText(
                activity,
                this.getString(R.string.toast_location_permission_2),
                Toast.LENGTH_SHORT
            ).show()
        }
    }


    /*------------------------------------------------------------
            * antes de esto es código añadido*/
    private fun createFragment() {
        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

    }

    override fun onMapReady(googleMap: GoogleMap) {
        map = googleMap
        createMarker()
        enableMyLocation() // añadido
    }

    private fun createMarker() {
        val coordinate = LatLng(latU, longU)
        val marker =
            MarkerOptions().position(coordinate).title(this.getString(R.string.current_location) + latU + "," + longU)
        map.addMarker(marker)
        map.animateCamera(CameraUpdateFactory.newLatLngZoom(coordinate, 18f), 4000, null)
    }

    private fun checkPermissions() {
        if (ContextCompat.checkSelfPermission(
                requireActivity(),
                Manifest.permission.ACCESS_FINE_LOCATION
            )
            == PackageManager.PERMISSION_GRANTED &&
            ContextCompat.checkSelfPermission(
                requireActivity(),
                Manifest.permission.ACCESS_COARSE_LOCATION
            )
            == PackageManager.PERMISSION_GRANTED
        ) {
            getCurrentLocation()
        } else {
            requestPermissions(
                listOf<String>(
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                ).toTypedArray(), 100
            )
        }
    }

    @SuppressLint("MissingPermission")
    private fun getCurrentLocation() {
        var locationManager =
            requireActivity().getSystemService(Context.LOCATION_SERVICE) as LocationManager

        if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) || locationManager.isProviderEnabled(
                LocationManager.NETWORK_PROVIDER
            )
        ) {
            client.lastLocation.addOnCompleteListener { task ->
                val location = task.result
                if (location != null) {
                    latU = location.latitude
                    longU = location.longitude

                } else {
                    val locationRequest = LocationRequest()
                        .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
                        .setInterval(1000)
                        .setFastestInterval(1000)
                        .setNumUpdates(1)
                    val locationCallback: LocationCallback = object : LocationCallback() {
                        override fun onLocationResult(locationResult: LocationResult) {
                            val location1 = locationResult.lastLocation
                            latU = location1.latitude
                            longU = location1.longitude
                            Toast.makeText(
                                activity,
                                "Lat: " + latU + "Long: " + longU,
                                Toast.LENGTH_SHORT
                            )
                                .show()
                        }
                    }
                    client.requestLocationUpdates(
                        locationRequest,
                        locationCallback, Looper.myLooper()
                    )
                }
            }
        } else {
            startActivity(Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK))
        }
    }


}

