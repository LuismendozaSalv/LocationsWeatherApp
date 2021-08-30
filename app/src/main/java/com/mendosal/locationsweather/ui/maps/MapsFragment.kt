package com.mendosal.locationsweather.ui.maps

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
import androidx.navigation.findNavController
import com.google.android.gms.maps.model.Marker

@AndroidEntryPoint
class MapsFragment : Fragment() {
    private lateinit var mMap: GoogleMap
    private var mapReady = false
    private lateinit var mapsViewModel: MapsViewModel

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
    }

    private fun loadInitialMarkers(cityWeatherList: List<CityWeatherEntity>) {
        if (cityWeatherList.size > 0) {
            mapsViewModel.storeValue(DataStoreKeys.INIT_LOAD_KEY, "Y")
        }
        cityWeatherList.forEach {
            val cityPosition = LatLng(it.coordLat?.toDouble()!!, it.coordLon?.toDouble()!!)
            val id = it.weatherId!!
            mMap.addMarker(
                    MarkerOptions()
                            .position(cityPosition)
                            .title(it.cityName)
                            .snippet(it.weatherDescription)
                            .icon(context?.let { ImageUtils.bitMapFromVector(it,
                                    ImageUtils.getWeatherIcon(id), 60, 60) }))

        }
        val southAmerica = LatLng(-27.8131911, -59.630548)
        mMap.moveCamera(CameraUpdateFactory.newLatLng(southAmerica))
    }

    private fun callOnMarkerClickListener(view: View) {
        mMap.setOnMarkerClickListener(object : GoogleMap.OnMarkerClickListener {
            override fun onMarkerClick(marker: Marker): Boolean {
                Toast.makeText(context, marker.title, Toast.LENGTH_LONG).show()
                val action = MapsFragmentDirections.actionMapsFragmentToWeatherInfoBottomSheet(marker.title)
                view.findNavController().navigate(action)
                return false
            }
        })
    }

}