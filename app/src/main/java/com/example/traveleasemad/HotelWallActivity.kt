package com.example.traveleasemad

import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.traveleasemad.R

class HotelWall : AppCompatActivity() {

    private var userId: String? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.hotelwall)

        val getStartedTextView: TextView = findViewById(R.id.textGetStarted)
        userId = intent.getStringExtra("USER_ID")


        getStartedTextView.setOnClickListener {

            val intent = Intent(this, HotelDashboardActivity::class.java)
            intent.putExtra("USER_ID", userId)
            startActivity(intent)


            val imageView = findViewById<ImageView>(R.id.imageView)
            val textView = findViewById<TextView>(R.id.textView3)
            val getStartedTextView = findViewById<TextView>(R.id.textGetStarted)
            val arrowImageView = findViewById<ImageView>(R.id.imageView2)


            getStartedTextView.setOnClickListener {

                Toast.makeText(this, "Get Started clicked!", Toast.LENGTH_SHORT).show()


            }

            arrowImageView.setOnClickListener {

                Toast.makeText(this, "Arrow clicked!", Toast.LENGTH_SHORT).show()


            }
        }
    }
}