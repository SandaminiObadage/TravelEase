package com.example.traveleasemad

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.android.material.bottomappbar.BottomAppBar
import com.google.android.material.floatingactionbutton.FloatingActionButton

class HotelDashboardActivity : AppCompatActivity() {
    data class Hotel(
        val id: String,
        val name: String,
        val location: String,
        val latitude: Double,
        val longitude: Double,
        val price: Double,
        val category: String,
        val description: String,
        val imageUrl: String,
        val rating: Float,

        )

    {
        fun getImageUrlString(): String {
            return imageUrl
        }

    }

    // Declare all the views
    private lateinit var profileImageView: ImageView
    private lateinit var welcomeTextView: TextView
    private lateinit var userNameTextView: TextView
    private lateinit var notificationBellImageView: ImageView
    private lateinit var findStayTextView: TextView
    private lateinit var searchEditText: EditText
    private lateinit var settingsImageView: ImageView
    private lateinit var popularRecyclerView: RecyclerView
    private lateinit var hotelsTextView: TextView
    private lateinit var adventuresTextView: TextView
    private lateinit var floatingActionButton: FloatingActionButton
    private lateinit var bottomAppBar: BottomAppBar
    private lateinit var profileButton: ImageView
    private lateinit var savedIcon: ImageView
    private lateinit var userIdTextView: TextView
    private var userId: String? = null


    private val hotelsList = listOf(
        Hotel("1", "Hotel A", "Location A", 7.123, 8.456, 120.0, "Luxury", "A luxury hotel", "https://imageurl.com/hotelA", 4.5f),
        Hotel("2", "Hotel B", "Location B", 7.789, 8.987, 80.0, "Budget", "A budget-friendly hotel", "https://imageurl.com/hotelB", 4.0f),
        Hotel("3", "Hotel C", "Location C", 7.654, 8.321, 150.0, "Resort", "A beautiful resort", "https://imageurl.com/hotelC", 5.0f)
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.hotels_dashboard)


        userId = intent.getStringExtra("USER_ID")

        // Initialize the views
        userIdTextView = findViewById(R.id.userIdTextView)
        profileImageView = findViewById(R.id.imageView3)
        welcomeTextView = findViewById(R.id.textView5)
        userNameTextView = findViewById(R.id.textView6)
        notificationBellImageView = findViewById(R.id.imageView4)
        findStayTextView = findViewById(R.id.textView8)
        searchEditText = findViewById(R.id.editTextText7)
        settingsImageView = findViewById(R.id.imageView5)
        popularRecyclerView = findViewById(R.id.view_pop)
        hotelsTextView = findViewById(R.id.hotels)
        adventuresTextView = findViewById(R.id.adventures)
        floatingActionButton = findViewById(R.id.fab_add)
        bottomAppBar = findViewById(R.id.app_bar)
        profileButton = findViewById(R.id.profile)
        savedIcon = findViewById(R.id.favorite)


        userIdTextView.text = "User ID: $userId"
        userIdTextView.visibility = if (userId != null) View.VISIBLE else View.GONE


        hotelsTextView.setOnClickListener {
            startActivity(Intent(this, HotelsActivity::class.java))
        }

        adventuresTextView.setOnClickListener {
            startActivity(Intent(this, AdventuresActivity::class.java))
        }




        val dbHelper = DatabaseHelper(this)
        val hotelsFromDb = dbHelper.getAllHotels()


        savedIcon.setOnClickListener {
            val intent = Intent(this, BookingSummaryActivity::class.java)
            startActivity(intent)
        }

        findViewById<ImageView>(R.id.home).setOnClickListener {
            val intent = Intent(this, MainDashboard::class.java)
            startActivity(intent)
        }




        settingsImageView.setOnClickListener {

        }
    }




    }








