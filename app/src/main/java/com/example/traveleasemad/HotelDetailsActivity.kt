package com.example.traveleasemad

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.button.MaterialButton
import com.google.android.material.datepicker.MaterialDatePicker
import android.widget.TextView

class HotelDetailsActivity : AppCompatActivity() {

    private lateinit var dbHelper: DatabaseHelper
    private lateinit var hotelDescription: TextView
    private lateinit var bookNowButton: MaterialButton
    private lateinit var checkInDate: TextInputEditText
    private lateinit var checkOutDate: TextInputEditText
    private lateinit var numberOfBeds: TextInputEditText
    private lateinit var numberOfAdults: TextInputEditText
    private lateinit var numberOfChildren: TextInputEditText

    private lateinit var hotelId: String
    private lateinit var hotelName: String
    private lateinit var hotelImageUrl: String

    private lateinit var back: Button
    private lateinit var hotelImageView: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_hotel_details)


        intent.extras?.let {
            hotelId = it.getString("HOTEL_ID", "")
            hotelName = it.getString("HOTEL_NAME", "")
            hotelImageUrl = it.getString("HOTEL_IMAGE_URL", "")
        }

        back = findViewById(R.id.back)
        back.setOnClickListener {
            // Navigate back
            onBackPressed()
        }


        hotelDescription = findViewById(R.id.hotelDescription)
        bookNowButton = findViewById(R.id.bookNowButton)
        checkInDate = findViewById(R.id.checkInDate)
        checkOutDate = findViewById(R.id.checkOutDate)
        numberOfBeds = findViewById(R.id.numberOfBeds)
        numberOfAdults = findViewById(R.id.numberOfAdults)
        numberOfChildren = findViewById(R.id.numberOfChildren)
        hotelImageView = findViewById(R.id.hotelImageView)

        val hotelIdTextView: TextView = findViewById(R.id.hotelIdTextView)
        val hotelNameTextView: TextView = findViewById(R.id.hotelNameTextView)

        hotelIdTextView.text = "Hotel ID: $hotelId"
        hotelNameTextView.text = "Hotel Name: $hotelName"


        val hotelDetails = intent.getStringExtra("HOTEL_DETAILS")
        hotelDescription.text = hotelDetails


        setupDatePickers()


        bookNowButton.setOnClickListener {

            Toast.makeText(this, "Booking...", Toast.LENGTH_SHORT).show()


            if (checkInDate.text.isNullOrEmpty() || checkOutDate.text.isNullOrEmpty() || numberOfBeds.text.isNullOrEmpty() ||
                numberOfAdults.text.isNullOrEmpty() || numberOfChildren.text.isNullOrEmpty()
            ) {
                Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show()
            } else {

                val dbHelper = DatabaseHelper(this)


                val result = dbHelper.insertBooking(
                    userId = "currentUserId",
                    hotelId = hotelId.toInt(),
                    checkIn = checkInDate.text.toString(),
                    checkOut = checkOutDate.text.toString(),
                    noOfBeds = numberOfBeds.text.toString().toInt(),
                    noOfAdults = numberOfAdults.text.toString().toInt(),
                    noOfChildren = numberOfChildren.text.toString().toInt()
                )

                if (result != -1L) {

                    Toast.makeText(this, "Successfully Booked", Toast.LENGTH_SHORT).show()

                    val intent = Intent(this, BookingSummaryActivity::class.java)
                    intent.putExtra("HOTEL_DESCRIPTION", hotelDescription.text.toString())
                    intent.putExtra("CHECK_IN_DATE", checkInDate.text.toString())
                    intent.putExtra("CHECK_OUT_DATE", checkOutDate.text.toString())
                    intent.putExtra("NUMBER_OF_BEDS", numberOfBeds.text.toString())
                    intent.putExtra("NUMBER_OF_ADULTS", numberOfAdults.text.toString())
                    intent.putExtra("NUMBER_OF_CHILDREN", numberOfChildren.text.toString())
                    startActivity(intent)
                } else {

                    Toast.makeText(this, "Booking Failed", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun setupDatePickers() {
        val checkInDatePicker = MaterialDatePicker.Builder.datePicker().build()
        checkInDate.setOnClickListener {
            checkInDatePicker.show(supportFragmentManager, "check_in_date_picker")
            checkInDatePicker.addOnPositiveButtonClickListener { selection ->
                checkInDate.setText(checkInDatePicker.headerText)
            }
        }

        val checkOutDatePicker = MaterialDatePicker.Builder.datePicker().build()
        checkOutDate.setOnClickListener {
            checkOutDatePicker.show(supportFragmentManager, "check_out_date_picker")
            checkOutDatePicker.addOnPositiveButtonClickListener { selection ->
                checkOutDate.setText(checkOutDatePicker.headerText)
            }
        }
    }
}
