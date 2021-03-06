package com.mendosal.locationsweather.ui.maps

import android.Manifest
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.Canvas
import androidx.fragment.app.Fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptor
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.mendosal.locationsweather.R
import com.mendosal.locationsweather.data.local.entity.CityWeatherEntity
import com.mendosal.locationsweather.utils.DataStoreKeys
import com.mendosal.locationsweather.utils.ImageUtils
import dagger.hilt.android.AndroidEntryPoint
import com.google.android.libraries.places.api.model.Place
import com.google.android.libraries.places.widget.AutocompleteSupportFragment
import com.google.android.libraries.places.widget.listener.PlaceSelectionListener
import com.google.android.gms.common.api.Status
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.findNavController
import androidx.navigation.fragment.navArgs
import com.google.android.gms.maps.model.Marker
import com.mendosal.locationsweather.ui.location.LocationViewModel
import com.mendosal.locationsweather.ui.weatherInfo.WeatherInfoBottomSheetArgs

@AndroidEntryPoint
class MapsFragment : Fragment() {
    private lateinit var mMap: GoogleMap
    private var mapReady = false
    private lateinit var mapsViewModel: MapsViewModel
    private val LOCATION_PERMISSION_REQUEST_CODE = 2000
    private lateinit var locationViewModel: LocationViewModel

    private val callback = OnMapReadyCallback { googleMap ->
        this.mMap = googleMap
        mapReady = true
        mapsViewModel.defaultCities.observe(viewLifecycleOwner, {
            val cityWeatherList: List<CityWeatherEntity> = it.data!!
            loadInitialMarkers(cityWeatherList)
        })
        view?.let { callOnMarkerClickListener(it) }
    }

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_maps, container, false)
        mapsViewModel = ViewModelProvider(this).get(MapsViewModel::class.java)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
        mapFragment?.getMapAsync(callback)
        prepRequestLocationUpdates()
    }

    private fun prepRequestLocationUpdates() {
        if (ContextCompat.checkSelfPermission(requireContext(),
                        Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            requestLocationUpdates()
        } else {
            val permissionRequest = arrayOf(Manifest.permission.ACCESS_FINE_LOCATION)
            requestPermissions(permissionRequest, LOCATION_PERMISSION_REQUEST_CODE)
        }
    }

    private fun requestLocationUpdates() {
        locationViewModel = ViewModelProviders.of(this).get(LocationViewModel::class.java)
        locationViewModel.getLocationLiveData().observe(requireActivity(), Observer {
            mapsViewModel.getCityWeatherInfo(it.latitude, it.longitude).observe(requireActivity(), {
                val weatherInfoList = it.data!!
                if (weatherInfoList.size > 0) {
                    addMarker(weatherInfoList.first())
                }
            })
        })
    }

    private fun loadInitialMarkers(cityWeatherList: List<CityWeatherEntity>) {
        if (cityWeatherList.size > 0) {
            mapsViewModel.storeValue(DataStoreKeys.INIT_LOAD_KEY, "Y")
        }
        mMap.clear()
        cityWeatherList.forEach {
            addMarker(it)
        }
        val southAmerica = LatLng(-27.8131911, -59.630548)
        mMap.moveCamera(CameraUpdateFactory.newLatLng(southAmerica))
    }

    fun addMarker(cityWeather: CityWeatherEntity) {
        val cityPosition = LatLng(cityWeather.coordLat?.toDouble()!!, cityWeather.coordLon?.toDouble()!!)
        val id = cityWeather.weatherId!!
        mMap.addMarker(
                MarkerOptions()
                        .position(cityPosition)
                        .title(cityWeather.cityName)
                        .snippet(cityWeather.weatherDescription)
                        .icon(context?.let { ImageUtils.bitMapFromVector(it,
                                ImageUtils.getWeatherIcon(id), 60, 60) }))
    }

    private fun callOnMarkerClickListener(view: View) {
        mMap.setOnMarkerClickListener(object : GoogleMap.OnMarkerClickListener {
            override fun onMarkerClick(marker: Marker): Boolean {
                val action = MapsFragmentDirections
                        .actionMapsFragmentToWeatherInfoBottomSheet(marker.title!!)
                view.findNavController().navigate(action)
                return false
            }
        })
    }

    override fun onRequestPermissionsResult(
            requestCode: Int,
            permissions: Array<out String>,
            grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when(requestCode) {
            LOCATION_PERMISSION_REQUEST_CODE -> {
                if (grantResults.isNotEmpty() && grantResults[0] ==  PackageManager.PERMISSION_GRANTED) {
                    requestLocationUpdates()
                } else {
                    Toast.makeText(context, "Unable to update location without permission", Toast.LENGTH_LONG).show()
                }
            }
        }
    }
}