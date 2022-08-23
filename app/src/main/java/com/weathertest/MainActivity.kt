package com.weathertest

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.*
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.view.View
import android.widget.CompoundButton
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.weather.weather.WeatherHelper
import com.weather.weather.callback.TodayWeather
import com.weather.weather.callback.ForecastWeatherCallback
import com.weather.weather.model.CurrentWeather
import com.weather.weather.model.ForecastData
import com.weather.weather.model.ForecastWeather
import com.weathertest.databinding.ActivityMainBinding
import com.weathertest.utils.Permissons
import java.lang.Exception
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : AppCompatActivity(), CompoundButton.OnCheckedChangeListener {

    private lateinit var binding: ActivityMainBinding
    private lateinit var mWeatherHelper: WeatherHelper
    private lateinit var mFusedLocationClient: FusedLocationProviderClient

    private var mLatitude = 0.0
    private var mLongitude = 0.0

    protected var mLastLocation: Location? = null

    private var mTemperatureUnit = "metric"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
        mWeatherHelper = WeatherHelper(this, "ae1c4977a943a50eaa7da25e6258d8b2")

        setListener()
        askForLocationPermission()
    }



    private fun setListener() {
        binding.rdoCelsius.setOnCheckedChangeListener(this)
        binding.rdoFahrenheit.setOnCheckedChangeListener(this)
        binding.rdoCurrent.setOnCheckedChangeListener(this)
        binding.rdoForecast.setOnCheckedChangeListener(this)

        binding.btnGetWeather.setOnClickListener {
            /*if (binding.rdoCurrent.isChecked)
                getCurrentWeather()
            else getForecastWeather()*/
            //if (mLatitude != 0.0 && mLongitude != 0.0)
            getForecastWeather()
        }
    }


    /**
     * ask for location permission...
     */
    private fun askForLocationPermission() {
        if (Build.VERSION.SDK_INT >= 23) {
            if (!Permissons.Check_FINE_LOCATION(this)) {
                requestPermissions(arrayOf(Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION), Permissons.LOCATION)

            } else getLocation()
        }
    }


    private fun isLocationEnabled(): Boolean {
        val locationManager: LocationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) || locationManager.isProviderEnabled(
            LocationManager.NETWORK_PROVIDER
        )
    }


    @SuppressLint("MissingPermission", "SetTextI18n")
    private fun getLocation() {
        if (Permissons.Check_FINE_LOCATION(this)) {
            if (isLocationEnabled()) {
                mFusedLocationClient.lastLocation
                    .addOnCompleteListener(this) { task ->
                        if (task.isSuccessful && task.result != null) {
                            mLastLocation = task.result
                            if (mLastLocation != null) {
                                mLongitude = mLastLocation!!.longitude
                                mLatitude = mLastLocation!!.latitude
                            }
                        } else {
                            Log.w("", "getLastLocation:exception", task.exception)
                        }
                    }
            } else {
                Toast.makeText(this, "Please turn on location", Toast.LENGTH_LONG).show()
                val intent = Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
                startActivity(intent)
            }
        } else {
            askForLocationPermission()
        }
    }


    /**
     * Used to get current weather...
     */
    private fun getCurrentWeather() {
        mWeatherHelper.getCurrentWeatherByLatLong(mLatitude, mLongitude, mTemperatureUnit,
            TodayWeather { currentWeather ->
                try {
                    setDailyWeatherData(currentWeather)
                    binding.recWeather.visibility = View.GONE
                    binding.llWeather.root.visibility = View.VISIBLE

                } catch (e: Exception) {
                    e.printStackTrace()
                }
            })
    }


    /**
     * Used to get forecast weather...
     */
    private fun getForecastWeather() {
        mWeatherHelper.getForecastWeatherByLatLong(mLatitude, mLongitude, mTemperatureUnit, ForecastWeatherCallback { forecastData ->
            try {
                if (binding.rdoCurrent.isChecked)
                    setWeatherAdapter(filterTodaysWeather(forecastData.list!!))
                else setWeatherAdapter(filterForecastWeather(forecastData.list!!))
                binding.recWeather.visibility = View.VISIBLE
                binding.llWeather.root.visibility = View.GONE

            }catch (e: Exception) {
                e.printStackTrace()
            }
        })
    }


    /**
     * group all bookings by services..
     */
    @SuppressLint("SimpleDateFormat")
    private fun filterTodaysWeather(weathers: List<ForecastWeather>) : MutableList<ForecastWeather>{
        val todayWeather: MutableList<ForecastWeather> = mutableListOf()
        val currentDate = SimpleDateFormat("yyyy-MM-dd").format(Date())
        for (i in weathers.indices) {
            if (weathers[i].dtTxt.contains(currentDate))
                todayWeather.add(weathers[i])
        }

        return todayWeather
    }


    /**
     * group all bookings by services..
     */
    @SuppressLint("SimpleDateFormat")
    private fun filterForecastWeather(weathers: List<ForecastWeather>) : MutableList<ForecastWeather>{
        val forecastWeather: MutableList<ForecastWeather> = mutableListOf()
        val currentDate = SimpleDateFormat("yyyy-MM-dd").format(Date())
        for (i in weathers.indices) {
            if (!weathers[i].dtTxt.contains(currentDate))
                forecastWeather.add(weathers[i])
        }

        return forecastWeather
    }


    @SuppressLint("SetTextI18n")
    private fun setDailyWeatherData(currentWeather: CurrentWeather) {
        binding.llWeather.txtWeather.text = currentWeather.weather!![0].main
        binding.llWeather.txtWind.text = currentWeather.wind!!.speed.toString()

        if (binding.rdoCelsius.isChecked)
            binding.llWeather.txtTemp.text = currentWeather.main!!.temp.toString() + "°C"
        else binding.llWeather.txtTemp.text = currentWeather.main!!.temp.toString() + "°F"
    }


    private fun setWeatherAdapter(forecastData: List<ForecastWeather>) {
        binding.recWeather.layoutManager = GridLayoutManager(this, 2)
        binding.recWeather.adapter = ForecastWeatherAdapter(this, forecastData)
    }


    override fun onCheckedChanged(p0: CompoundButton?, p1: Boolean) {
        when(p0!!.id) {
            R.id.rdo_celsius -> {
                mTemperatureUnit = "metric"
            }

            R.id.rdo_fahrenheit -> {
                mTemperatureUnit = "imperial"
            }
        }
    }


    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        when (requestCode) {
            Permissons.LOCATION -> if (grantResults.isNotEmpty()) {
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED && grantResults[1] == PackageManager.PERMISSION_GRANTED) {
                    getLocation()
                }
            } else
                askForLocationPermission()
        }
    }
}