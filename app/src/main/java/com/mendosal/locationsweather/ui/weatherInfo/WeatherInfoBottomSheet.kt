package com.mendosal.locationsweather.ui.weatherInfo

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.mendosal.locationsweather.R
import com.mendosal.locationsweather.data.local.entity.CityWeatherEntity
import com.mendosal.locationsweather.databinding.FragmentWeatherInfoBottomSheetBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
open class WeatherInfoBottomSheet : Fragment() {
    private lateinit var weatherInfoViewModel: WeatherInfoViewModel
    private lateinit var weatherInfo: List<CityWeatherEntity>
    val args: WeatherInfoBottomSheetArgs by navArgs()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_weather_info_bottom_sheet, container, false)
        weatherInfoViewModel = ViewModelProvider(this).get(WeatherInfoViewModel::class.java)
        weatherInfoViewModel.cityName = args.cityName
        weatherInfoViewModel.getCityWeatherInfo().observe(viewLifecycleOwner, Observer {
            weatherInfo = it.data!!
            val binding :FragmentWeatherInfoBottomSheetBinding = DataBindingUtil.setContentView(
                requireActivity(), R.layout.fragment_weather_info_bottom_sheet)
            binding.cityWeather = weatherInfo.first()
            binding.extraInfo = weatherInfoViewModel.getExtraInfo(weatherInfo.first())
        })

        return view
    }
}