package com.example.traveleasemad

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log


private const val DATABASE_NAME = "hotelsearching.db"
private const val DATABASE_VERSION = 2


class DatabaseHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {


    override fun onCreate(db: SQLiteDatabase) {
        Log.d("DatabaseHelper", "Creating database tables")
        try {
            db.execSQL("PRAGMA foreign_keys=ON;")


            val hotelsTableQuery = """
                CREATE TABLE hotels (
                    id INTEGER PRIMARY KEY AUTOINCREMENT,
                    name TEXT,
                    location VARCHAR,
                    latitude REAL,
                    longitude REAL,
                    price REAL,
                    category VARCHAR,
                    description TEXT,
                    image_Url TEXT,
                    rating REAL
                )
            """
            db.execSQL(hotelsTableQuery)

            val bookingTableQuery = """
                CREATE TABLE booking (
                    id INTEGER PRIMARY KEY AUTOINCREMENT,
                    user_id TEXT NOT NULL,
                    hotel_id INTEGER,
                    check_in DATE NOT NULL,
                    check_out DATE NOT NULL,
                    no_of_beds INTEGER NOT NULL,
                    no_of_adults INTEGER NOT NULL,
                    no_of_children INTEGER NOT NULL,
                    FOREIGN KEY (hotel_id) REFERENCES hotels(id)
                )
            """
            db.execSQL(bookingTableQuery)

            val bookingSummaryTableQuery = """
                CREATE TABLE bookingSummary (
                    id INTEGER PRIMARY KEY AUTOINCREMENT,
                    status TEXT NOT NULL,
                    booking_id INTEGER,
                    FOREIGN KEY (booking_id) REFERENCES booking(id)
                )
            """
            db.execSQL(bookingSummaryTableQuery)

            val adventuresTableQuery = """
                CREATE TABLE adventures (
                    id INTEGER PRIMARY KEY AUTOINCREMENT,
                    name TEXT,
                    location VARCHAR,
                    latitude REAL,
                    longitude REAL,
                    price REAL,
                    category VARCHAR,
                    description TEXT,
                    image_Url TEXT,
                    rating REAL
                )
            """
            db.execSQL(adventuresTableQuery)

            val reviewsTableQuery = """
                CREATE TABLE reviews (
                    id INTEGER PRIMARY KEY AUTOINCREMENT,
                    user_id TEXT NOT NULL,
                    hotel_id INTEGER,
                    adventure_id INTEGER,
                    rating FLOAT,
                    comment TEXT,
                    image_url TEXT,
                    FOREIGN KEY (hotel_id) REFERENCES hotels(id),
                    FOREIGN KEY (adventure_id) REFERENCES adventures(id)
                )
            """
            db.execSQL(reviewsTableQuery)

//            insertSampleData()
        } catch (e: Exception) {
            Log.e("DatabaseHelper", "Error creating tables", e)
        }
    }


    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        try {
            db.execSQL("DROP TABLE IF EXISTS hotels")
            db.execSQL("DROP TABLE IF EXISTS booking")
            db.execSQL("DROP TABLE IF EXISTS bookingSummary")
            db.execSQL("DROP TABLE IF EXISTS adventures")
            db.execSQL("DROP TABLE IF EXISTS reviews")
            onCreate(db) // Create the tables again
        } catch (e: Exception) {
            Log.e("DatabaseHelper", "Error upgrading database", e)
        }
    }

//    fun insertSampleData() {
//        insertHotel("Hilton Colombo", "Colombo", 6.9271, 79.8612, 250.0, "Luxury", "5-star hotel in Colombo", "https://example.com/hilton.jpg", 4.5)
//        insertHotel("Taj Samudra", "Colombo", 6.9319, 79.8511, 270.0, "Luxury", "Ocean-view luxury hotel", "https://example.com/taj.jpg", 4.8)
//        insertHotel("Shangri-La Colombo", "Colombo", 6.9308, 79.8515, 300.0, "Luxury", "Modern luxury hotel with sea views", "https://example.com/shangrila.jpg", 4.9)
//        insertHotel("Cinnamon Grand", "Colombo", 6.9269, 79.8507, 220.0, "Luxury", "Upscale hotel in the heart of Colombo", "https://example.com/cinnamon.jpg", 4.6)
//
//        insertHotel("Amaya Lake", "Dambulla", 7.8592, 80.6500, 180.0, "Resort", "Lake-view resort", "https://example.com/amaya.jpg", 4.7)
//        insertHotel("Heritance Kandalama", "Dambulla", 7.8632, 80.6740, 240.0, "Luxury", "Eco-friendly hotel overlooking Kandalama Lake", "https://example.com/heritance.jpg", 4.9)
//        insertHotel("Jetwing Lake", "Dambulla", 7.8912, 80.6785, 200.0, "Resort", "Elegant lakeside hotel with modern amenities", "https://example.com/jetwinglake.jpg", 4.7)
//        insertHotel("Sigiriya Village", "Dambulla", 7.9560, 80.7590, 160.0, "Resort", "Serene hotel near Sigiriya Rock", "https://example.com/sigiriya.jpg", 4.5)
//
//        insertHotel("Jetwing Lighthouse", "Galle", 6.0351, 80.2160, 320.0, "Boutique", "Scenic boutique hotel by the sea", "https://example.com/jetwing.jpg", 4.9)
//        insertHotel("The Fortress Resort & Spa", "Galle", 6.0220, 80.2174, 280.0, "Luxury", "Luxurious beachfront resort", "https://example.com/fortress.jpg", 4.8)
//        insertHotel("Amari Galle", "Galle", 6.0535, 80.2115, 260.0, "Resort", "Modern resort with sea views", "https://example.com/amari.jpg", 4.7)
//        insertHotel("Le Grand Galle", "Galle", 6.0417, 80.2145, 290.0, "Luxury", "Stylish beachfront hotel with pool", "https://example.com/legrand.jpg", 4.8)
//
//
//        insertAdventure("Safari Adventure", "Yala", 6.3833, 81.4667, 150.0, "Outdoor", "Wildlife safari with expert guides", "https://example.com/safari.jpg", 4.8)
//        insertAdventure("Snorkeling", "Trincomalee", 8.5680, 81.2330, 50.0, "Water Sports", "Snorkeling in clear blue waters", "https://example.com/snorkeling.jpg", 4.6)
//
//        insertBooking("user1", 1, "2023-10-01", "2023-10-05", 1, 2, 0)
//        insertBooking("user2", 2, "2023-10-10", "2023-10-12", 1, 2, 1)
//
//        insertBookingSummary("Booked", 1)
//
//        insertReview("user1", 1, null, 4.0f, "Great hotel!", "https://example.com/review1.jpg")
//        insertReview("user2", null, 1, 5.0f, "Amazing adventure!", "https://example.com/review2.jpg")
//    }

    private fun insertHotel(name: String, location: String, latitude: Double, longitude: Double, price: Double, category: String, description: String, imageUrl: String, rating: Double): Long {
        val db = writableDatabase
        return try {
            val values = ContentValues().apply {
                put("name", name)
                put("location", location)
                put("latitude", latitude)
                put("longitude", longitude)
                put("price", price)
                put("category", category)
                put("description", description)
                put("image_Url", imageUrl)
                put("rating", rating)
            }
            db.insert("hotels", null, values)
        } catch (e: Exception) {
            Log.e("DatabaseHelper", "Error inserting hotel", e)
            -1
        } finally {
            db.close()
        }
    }



    private fun insertBooking(userId: String, hotelId: Int?, checkIn: String, checkOut: String, noOfBeds: Int, noOfAdults: Int, noOfChildren: Int): Long {
        val db = writableDatabase
        return try {
            val values = ContentValues().apply {
                put("user_id", userId)
                put("hotel_id", hotelId)
                put("check_in", checkIn)
                put("check_out", checkOut)
                put("no_of_beds", noOfBeds)
                put("no_of_adults", noOfAdults)
                put("no_of_children", noOfChildren)
            }
            db.insert("booking", null, values)
        } catch (e: Exception) {
            Log.e("DatabaseHelper", "Error inserting booking", e)
            -1
        } finally {
            db.close()
        }
    }

    private fun insertBookingSummary(status: String, bookingId: Int): Long {
        val db = writableDatabase
        return try {
            val values = ContentValues().apply {
                put("status", status)
                put("booking_id", bookingId)
            }
            db.insert("bookingSummary", null, values)
        } catch (e: Exception) {
            Log.e("DatabaseHelper", "Error inserting booking summary", e)
            -1
        } finally {
            db.close()
        }
    }

    private fun insertAdventure(name: String, location: String, latitude: Double, longitude: Double, price: Double, category: String, description: String, imageUrl: String, rating: Double): Long {
        val db = writableDatabase
        return try {
            val values = ContentValues().apply {
                put("name", name)
                put("location", location)
                put("latitude", latitude)
                put("longitude", longitude)
                put("price", price)
                put("category", category)
                put("description", description)
                put("image_Url", imageUrl)
                put("rating", rating)
            }
            db.insert("adventures", null, values)
        } catch (e: Exception) {
            Log.e("DatabaseHelper", "Error inserting adventure", e)
            -1
        } finally {
            db.close()
        }
    }

    private fun insertReview(userId: String, hotelId: Int?, adventureId: Int?, rating: Float, comment: String, imageUrl: String): Long {
        val db = writableDatabase
        return try {
            val values = ContentValues().apply {
                put("user_id", userId)
                put("hotel_id", hotelId)
                put("adventure_id", adventureId)
                put("rating", rating)
                put("comment", comment)
                put("image_url", imageUrl)
            }
            db.insert("reviews", null, values)
        } catch (e: Exception) {
            Log.e("DatabaseHelper", "Error inserting review", e)
            -1
        } finally {
            db.close()
        }
    }

    fun getAllData(): List<Pair<String, Int>> {
        val db = readableDatabase
        val list = mutableListOf<Pair<String, Int>>()
        val cursor = db.rawQuery("SELECT name, id FROM hotels", null)

        try {
            if (cursor.moveToFirst()) {
                do {
                    val nameIndex = cursor.getColumnIndex("name")
                    val idIndex = cursor.getColumnIndex("id")

                    // Check if the indices are valid before accessing
                    if (nameIndex != -1 && idIndex != -1) {
                        val name = cursor.getString(nameIndex)
                        val id = cursor.getInt(idIndex)
                        list.add(Pair(name, id))
                    } else {
                        Log.e("DatabaseHelper", "Column index not found")
                    }
                } while (cursor.moveToNext())
            }
        } catch (e: Exception) {
            Log.e("DatabaseHelper", "Error retrieving data", e)
        } finally {
            cursor.close()
            db.close()
        }
        return list
    }

    fun getAllHotels():List<HotelDashboardActivity.Hotel> {
        val hotelList = mutableListOf<HotelDashboardActivity.Hotel>()
        val db = readableDatabase
        val cursor = db.rawQuery("SELECT * FROM hotels", null)

        if (cursor.moveToFirst()) {
            do {
                val hotel = HotelDashboardActivity.Hotel(
                    id = cursor.getString(cursor.getColumnIndexOrThrow("id")),
                    name = cursor.getString(cursor.getColumnIndexOrThrow("name")),
                    location = cursor.getString(cursor.getColumnIndexOrThrow("location")),
                    latitude = cursor.getDouble(cursor.getColumnIndexOrThrow("latitude")),
                    longitude = cursor.getDouble(cursor.getColumnIndexOrThrow("longitude")),
                    price = cursor.getDouble(cursor.getColumnIndexOrThrow("price")),
                    category = cursor.getString(cursor.getColumnIndexOrThrow("category")),
                    description = cursor.getString(cursor.getColumnIndexOrThrow("description")),
                    imageUrl = cursor.getString(cursor.getColumnIndexOrThrow("image_Url")),
                    rating = cursor.getFloat(cursor.getColumnIndexOrThrow("rating"))
                )
                hotelList.add(hotel)
            } while (cursor.moveToNext())
        }
        cursor.close()
        return hotelList
    }



    class DBhelper {
        fun getHotels(): List<HotelDashboardActivity.Hotel> {
            return listOf(

            )
        }


    }


    fun getHotelsNearLocation(latitude: Double, longitude: Double, radiusInKm: Double = 10.0): List<HotelDashboardActivity.Hotel> {
        val hotelList = mutableListOf<HotelDashboardActivity.Hotel>()

        // Earth radius in kilometers
        val earthRadius = 6371.0

        // Calculate bounding box
        val latRadius = Math.toDegrees(radiusInKm / earthRadius)
        val lonRadius = Math.toDegrees(radiusInKm / earthRadius / Math.cos(Math.toRadians(latitude)))

        val minLat = latitude - latRadius
        val maxLat = latitude + latRadius
        val minLon = longitude - lonRadius
        val maxLon = longitude + lonRadius

        val db = readableDatabase

        // get hotels
        val query = """
        SELECT * FROM hotels
        WHERE latitude BETWEEN ? AND ?
        AND longitude BETWEEN ? AND ?
    """

        val cursor = db.rawQuery(query, arrayOf(minLat.toString(), maxLat.toString(), minLon.toString(), maxLon.toString()))
//use to execute sql query
        if (cursor.moveToFirst()) {
            do {
                val hotel = HotelDashboardActivity.Hotel(
                    id = cursor.getString(cursor.getColumnIndexOrThrow("id")),
                    name = cursor.getString(cursor.getColumnIndexOrThrow("name")),
                    location = cursor.getString(cursor.getColumnIndexOrThrow("location")),
                    latitude = cursor.getDouble(cursor.getColumnIndexOrThrow("latitude")),
                    longitude = cursor.getDouble(cursor.getColumnIndexOrThrow("longitude")),
                    price = cursor.getDouble(cursor.getColumnIndexOrThrow("price")),
                    category = cursor.getString(cursor.getColumnIndexOrThrow("category")),
                    description = cursor.getString(cursor.getColumnIndexOrThrow("description")),
                    imageUrl = cursor.getString(cursor.getColumnIndexOrThrow("image_Url")),
                    rating = cursor.getFloat(cursor.getColumnIndexOrThrow("rating"))
                )
                hotelList.add(hotel)
            } while (cursor.moveToNext())
        }
        cursor.close()
        db.close()
        return hotelList
    }

    fun addHotel(
        name: String,
        location: String,
        latitude: Double,
        longitude: Double,
        price: Double,
        category: String,
        description: String,
        imageUrl: String,
        rating: Double
    ): Long {
        val db = this.writableDatabase
        val contentValues = ContentValues()

        contentValues.put("name", name)
        contentValues.put("location", location)
        contentValues.put("latitude", latitude)
        contentValues.put("longitude", longitude)
        contentValues.put("price", price)
        contentValues.put("category", category)
        contentValues.put("description", description)
        contentValues.put("image_Url", imageUrl)
        contentValues.put("rating", rating)

        // Inserting the hotel record into the hotels table
        val result = db.insert("hotels", null, contentValues)
        db.close()
        return result // Returns the row ID of the newly inserted row, or -1 if an error occurred
    }

    fun deleteHotel(hotelName: String): Int {
        val db = this.writableDatabase
        // Define 'where' part of query.
        val selection = "name = ?"
        // Specify arguments in placeholder order.
        val selectionArgs = arrayOf(hotelName)

        // Issue SQL statement to delete the hotel
        val deletedRows = db.delete("hotels", selection, selectionArgs)
        db.close()

        return deletedRows
    }

    fun modifyHotel(name: String, location: String, latitude: Double, longitude: Double, price: Double, category: String, description: String, imageUrl: String, rating: Double): Int {
        val db = this.writableDatabase

        // Prepare the new values for the update
        val values = ContentValues().apply {
            put("location", location)
            put("latitude", latitude)
            put("longitude", longitude)
            put("price", price)
            put("category", category)
            put("description", description)
            put("image_Url", imageUrl)
            put("rating", rating)
        }

        // Define the selection criteria (update based on the hotel name)
        val selection = "name = ?"
        val selectionArgs = arrayOf(name)

        // Update the hotel in the database
        val updatedRows = db.update("hotels", values, selection, selectionArgs)
        db.close()

        return updatedRows
    }

    fun insertBooking(
        userId: String,
        hotelId: Int,
        checkIn: String,
        checkOut: String,
        noOfBeds: Int,
        noOfAdults: Int,
        noOfChildren: Int
    ): Long {
        val db = this.writableDatabase
        val contentValues = ContentValues().apply {
            put("user_id", userId)
            put("hotel_id", hotelId)
            put("check_in", checkIn)
            put("check_out", checkOut)
            put("no_of_beds", noOfBeds)
            put("no_of_adults", noOfAdults)
            put("no_of_children", noOfChildren)
        }

        return db.insert("booking", null, contentValues)
    }



}