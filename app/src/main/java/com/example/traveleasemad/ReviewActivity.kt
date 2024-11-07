package com.example.traveleasemad

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.RatingBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.annotation.SuppressLint

class ReviewActivity : AppCompatActivity() {

    private lateinit var ratingBar: RatingBar
    private lateinit var editTextReview: EditText
    private lateinit var btnSubmitReview: Button
    private lateinit var recyclerViewPositiveReviews: RecyclerView
    private lateinit var recyclerViewNegativeReviews: RecyclerView
    private lateinit var positiveReviewAdapter: ReviewAdapter
    private lateinit var negativeReviewAdapter: ReviewAdapter
    private lateinit var dbHelper: ReviewDatabaseHelper
    private val positiveReviewsList = mutableListOf<Review>()
    private val negativeReviewsList = mutableListOf<Review>()

    @SuppressLint("NotifyDataSetChanged")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_review)

        // Initialize database helper
        dbHelper = ReviewDatabaseHelper(this)

        ratingBar = findViewById(R.id.ratingBar)
        editTextReview = findViewById(R.id.editTextReview)
        btnSubmitReview = findViewById(R.id.btnSubmitReview)
        recyclerViewPositiveReviews = findViewById(R.id.recyclerViewPositiveReviews)
        recyclerViewNegativeReviews = findViewById(R.id.recyclerViewNegativeReviews)

        positiveReviewAdapter = ReviewAdapter(positiveReviewsList)
        negativeReviewAdapter = ReviewAdapter(negativeReviewsList)

        recyclerViewPositiveReviews.layoutManager = LinearLayoutManager(this)
        recyclerViewNegativeReviews.layoutManager = LinearLayoutManager(this)
        recyclerViewPositiveReviews.adapter = positiveReviewAdapter
        recyclerViewNegativeReviews.adapter = negativeReviewAdapter

        // Load existing reviews from the database
        loadReviews()

        // Set submit button click listener
        btnSubmitReview.setOnClickListener {
            val rating = ratingBar.rating
            val reviewText = editTextReview.text.toString()

            if (reviewText.isEmpty()) {
                Toast.makeText(this, "Please enter a review text", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Add the review to the database
            val success = dbHelper.addReview(rating, reviewText)

            if (success != -1L) {
                Toast.makeText(this, "Review added successfully", Toast.LENGTH_SHORT).show()
                val newReview = Review(rating, reviewText)

                if (rating >= 3.0) {
                    positiveReviewsList.add(newReview)
                    positiveReviewAdapter.notifyDataSetChanged()
                } else {
                    negativeReviewsList.add(newReview)
                    negativeReviewAdapter.notifyDataSetChanged()
                }

                // Clear input fields
                editTextReview.text.clear()
                ratingBar.rating = 0f
            } else {
                Toast.makeText(this, "Failed to add review. Please try again later.", Toast.LENGTH_SHORT).show()
            }
        }
    }

    // Load reviews from the database
    @SuppressLint("NotifyDataSetChanged")
    private fun loadReviews() {
        positiveReviewsList.clear()
        negativeReviewsList.clear()

        // Retrieve all reviews from the database
        val allReviews = dbHelper.getAllReviews()
        for (review in allReviews) {
            if (review.rating >= 3.0) {
                positiveReviewsList.add(review)
            } else {
                negativeReviewsList.add(review)
            }
        }

        positiveReviewAdapter.notifyDataSetChanged()
        negativeReviewAdapter.notifyDataSetChanged()
    }
}

