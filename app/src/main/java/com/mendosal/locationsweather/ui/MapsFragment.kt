package com.mendosal.locationsweather.ui

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
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptor
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.mendosal.locationsweather.R
import com.mendosal.locationsweather.data.remote.models.WeatherResponse

class MapsFragment : Fragment() {
    private lateinit var mapsViewModel: MapsViewModel
    private lateinit var weatherInfo: WeatherResponse

    private val callback = OnMapReadyCallback { googleMap ->
        /**
         * Manipulates the map once available.
         * This callback is triggered when the map is ready to be used.
         * This is where we can add markers or lines, add listeners or move the camera.
         * In this case, we just add a marker near Sydney, Australia.
         * If Google Play services is not installed on the device, the user will be prompted to
         * install it inside the SupportMapFragment. This method will only be triggered once the
         * user has installed Google Play services and returned to the app.
         */
        val sydney = LatLng(-34.0, 151.0)
        googleMap.addMarker(
                MarkerOptions()
                        .position(sydney)
                        .title("Marker in Sydney")
                        .snippet("Atmosphere")
                        .icon(bitMapFromVector(R.drawable.ic_cloud, 60, 60)))
        googleMap.moveCamera(CameraUpdateFactory.newLatLng(sydney))
    }

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_maps, container, false)
        mapsViewModel = ViewModelProvider(this).get(MapsViewModel::class.java)
        mapsViewModel.getWeatherInfo().observe(viewLifecycleOwner, {
            weatherInfo = it
        })
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
        mapFragment?.getMapAsync(callback)
    }

    private fun bitMapFromVector(vectorResID: Int, width: Int = 0, height: Int = 0): BitmapDescriptor {
        val vectorDrawable = context?.let { ContextCompat.getDrawable(it, vectorResID) }
        var vectorHeight = vectorDrawable!!.intrinsicHeight
        var vectorWidth = vectorDrawable.intrinsicWidth
        if (width != 0 && height != 0) {
            vectorHeight = height
            vectorWidth = width
        }
        vectorDrawable!!.setBounds(0, 0, vectorWidth, vectorHeight)
        val bitmap = Bitmap.createBitmap(vectorWidth, vectorHeight, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(bitmap)
        vectorDrawable.draw(canvas)
        return BitmapDescriptorFactory.fromBitmap(bitmap)
    }
}