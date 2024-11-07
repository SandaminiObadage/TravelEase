package com.example.traveleasemad

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.traveleasemad.databinding.ActivityMainDashboardBinding

class MainDashboard : AppCompatActivity() {

    private lateinit var binding: ActivityMainDashboardBinding
    private var userId: String? = null
    private lateinit var DBhelper: DatabaseHelper


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainDashboardBinding.inflate(layoutInflater)
        setContentView(binding.root)


        DBhelper = DatabaseHelper(this)


        val db = DBhelper.writableDatabase


        userId = intent.getStringExtra("USER_ID")


        if (userId != null) {
            binding.userIdTextView.text = "User ID: $userId"
            binding.userIdTextView.visibility = View.VISIBLE
        } else {
            binding.userIdTextView.text = "User ID not found"
            binding.userIdTextView.visibility = View.GONE
        }



        binding.iconHotels.setOnClickListener {
            val intent = Intent(this, HotelWall::class.java)
            intent.putExtra("USER_ID", userId)
            startActivity(intent)
        }

        binding.iconSignIn.setOnClickListener {
            val intent = Intent(this, SignInActivity::class.java)
            startActivity(intent)
        }

        binding.iconSignUp.setOnClickListener {
            val intent = Intent(this, SignUpActivity::class.java)
            startActivity(intent)
        }

        binding.iconAddHotels.setOnClickListener {
            val intent = Intent(this, AddingHotels::class.java)
            startActivity(intent)
        }

        binding.iconReviews.setOnClickListener {
            val intent = Intent(this, ReviewActivity::class.java)
            startActivity(intent)
        }

    }

}