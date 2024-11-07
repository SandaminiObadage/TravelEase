package com.example.traveleasemad

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.MapView
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import android.location.Geocoder
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.IOException
import java.util.Locale
import com.google.android.gms.maps.model.LatLngBounds

class HotelsActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var searchView: SearchView
    private lateinit var mapView: MapView
    private lateinit var googleMap: GoogleMap
    private lateinit var hotelsRecyclerView: RecyclerView
    private lateinit var dbHelper: DatabaseHelper.DBhelper
    private lateinit var hotels: List<HotelDashboardActivity.Hotel>

    private lateinit var backButton: Button
    private lateinit var nextButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_hotels)

        dbHelper = DatabaseHelper.DBhelper()
        hotels = dbHelper.getHotels()

        initializeUI(savedInstanceState)
    }

    private fun initializeUI(savedInstanceState: Bundle?) {
        searchView = findViewById(R.id.searchView)
        mapView = findViewById(R.id.mapView)
        hotelsRecyclerView = findViewById(R.id.hotelsRecyclerView)
        backButton = findViewById(R.id.back)
        nextButton = findViewById(R.id.next)

        mapView.onCreate(savedInstanceState)
        mapView.getMapAsync(this)

        setupSearchView()
        setupButtons()


        hotelsRecyclerView.layoutManager = LinearLayoutManager(this)
        hotelsRecyclerView.adapter = HotelsAdapter(hotels) { selectedHotel ->
            navigateToHotelDetails(selectedHotel)
        }
    }

    private fun navigateToHotelDetails(hotel: HotelDashboardActivity.Hotel) {
        val intent = Intent(this, HotelDetailsActivity::class.java)

        intent.putExtra("HOTEL_ID", hotel.id)
        intent.putExtra("HOTEL_NAME", hotel.name)
        intent.putExtra("HOTEL_IMAGE_URL", hotel.getImageUrlString())
        startActivity(intent)
    }

    private fun setupSearchView() {
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                mapView.visibility = View.VISIBLE
                filterHotels(query)
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                filterHotels(newText)
                return true
            }
        })
    }

    private fun setupButtons() {
        backButton.setOnClickListener {
            onBackPressed()
        }

        nextButton.setOnClickListener {
            startActivity(Intent(this, HotelDetailsActivity::class.java))
        }
    }

    private fun filterHotels(query: String?) {
        if (!query.isNullOrEmpty()) {
            CoroutineScope(Dispatchers.Main).launch {
                val addresses = getLocationFromQuery(query)  //location set to to la
                if (addresses != null && addresses.isNotEmpty()) {
                    val address = addresses[0]
                    val filteredHotels = getHotelsNearLocation(address.latitude, address.longitude)


                    updateMapWithHotels(filteredHotels)


                    updateRecyclerView(filteredHotels)
                } else {
                    Toast.makeText(this@HotelsActivity, "Location not found", Toast.LENGTH_SHORT).show()
                }
            }
        } else {
            googleMap.clear()
            updateRecyclerView(hotels)
        }
    }

    private suspend fun getLocationFromQuery(query: String): List<android.location.Address>? {
        return withContext(Dispatchers.IO) {
            val geocoder = Geocoder(this@HotelsActivity, Locale.getDefault())
            try {
                geocoder.getFromLocationName(query, 1)
            } catch (e: IOException) {
                e.printStackTrace()
                null
            }
        }
    }

    private suspend fun getHotelsNearLocation(latitude: Double, longitude: Double): List<HotelDashboardActivity.Hotel> {
        return withContext(Dispatchers.IO) {
            val dbHelper = DatabaseHelper(this@HotelsActivity)
            dbHelper.getHotelsNearLocation(latitude, longitude)
        }
    }

    private fun updateRecyclerView(filteredHotels: List<HotelDashboardActivity.Hotel>) {

        hotelsRecyclerView.adapter = HotelsAdapter(filteredHotels) { selectedHotel ->
            navigateToHotelDetails(selectedHotel)
        }
        hotelsRecyclerView.adapter?.notifyDataSetChanged() // Notify adapter about data changes
    }

    private fun updateMapWithHotels(hotels: List<HotelDashboardActivity.Hotel>) {
        googleMap.clear()

        if (hotels.isNotEmpty()) {
            val boundsBuilder = LatLngBounds.Builder()
            hotels.forEach { hotel ->
                val hotelLocation = LatLng(hotel.latitude, hotel.longitude)
                googleMap.addMarker(
                    MarkerOptions().position(hotelLocation).title(hotel.name)
                )
                boundsBuilder.include(hotelLocation)
            }

            val bounds = boundsBuilder.build()
            val padding = 100
            googleMap.animateCamera(CameraUpdateFactory.newLatLngBounds(bounds, padding))
        } else {
            Toast.makeText(this, "No hotels found in this location", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onMapReady(gMap: GoogleMap) {
        googleMap = gMap
        googleMap.uiSettings.isZoomControlsEnabled = true
    }

    override fun onResume() {
        super.onResume()
        mapView.onResume()
    }

    override fun onPause() {
        super.onPause()
        mapView.onPause()
    }

    override fun onDestroy() {
        super.onDestroy()
        mapView.onDestroy()
    }
}
