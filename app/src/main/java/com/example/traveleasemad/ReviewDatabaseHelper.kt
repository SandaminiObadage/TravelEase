package com.example.traveleasemad

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log

class ReviewDatabaseHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        private const val DATABASE_NAME = "reviews.db"
        private const val DATABASE_VERSION = 2 // Increment this if you change the schema
        private const val TABLE_NAME = "reviews"
        private const val COLUMN_ID = "id"
        private const val COLUMN_RATING = "rating"
        private const val COLUMN_REVIEW_TEXT = "reviewText"
    }

    override fun onCreate(db: SQLiteDatabase?) {
        val createTable = """
            CREATE TABLE $TABLE_NAME (
                $COLUMN_ID INTEGER PRIMARY KEY AUTOINCREMENT,
                $COLUMN_RATING REAL,
                $COLUMN_REVIEW_TEXT TEXT
            )
        """.trimIndent()
        db?.execSQL(createTable)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")
        onCreate(db)
    }

    // Method to add a review
    fun addReview(rating: Float, reviewText: String): Long {
        val db = this.writableDatabase
        val contentValues = ContentValues().apply {
            put(COLUMN_RATING, rating)
            put(COLUMN_REVIEW_TEXT, reviewText)
        }

        return try {
            val result = db.insert(TABLE_NAME, null, contentValues)
            Log.d("ReviewDatabaseHelper", "Inserted review with result: $result")
            result // Return the result of the insertion
        } catch (e: Exception) {
            Log.e("ReviewDatabaseHelper", "Error adding review: ${e.message}")
            -1 // Return -1 on error
        }
    }

    // Method to retrieve all reviews
    @SuppressLint("Range")
    fun getAllReviews(): List<Review> {
        val reviews = mutableListOf<Review>()
        val db = this.readableDatabase
        val cursor = db.rawQuery("SELECT * FROM $TABLE_NAME", null)

        if (cursor.moveToFirst()) {
            do {
                val rating = cursor.getFloat(cursor.getColumnIndex(COLUMN_RATING))
                val reviewText = cursor.getString(cursor.getColumnIndex(COLUMN_REVIEW_TEXT))
                reviews.add(Review(rating, reviewText))
            } while (cursor.moveToNext())
        }
        cursor.close()
        return reviews
    }
}
