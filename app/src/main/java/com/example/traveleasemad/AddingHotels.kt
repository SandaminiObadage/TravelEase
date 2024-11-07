package com.example.traveleasemad

import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import com.example.traveleasemad.databinding.AddingHotelsBinding
import java.io.File
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import android.Manifest

class AddingHotels : AppCompatActivity() {

    private lateinit var dbHelper: DatabaseHelper
    private lateinit var imgHotelPhoto: ImageView
    private lateinit var etImageUrl: EditText
    private var photoUri: Uri? = null
    private lateinit var binding: AddingHotelsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        binding = AddingHotelsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        dbHelper = DatabaseHelper(this)


        imgHotelPhoto = binding.imgHotelPhoto
        etImageUrl = binding.etImageUrl

        binding.btnUploadPhoto.setOnClickListener {

            if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
                openCamera()
            } else {
                ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.CAMERA), REQUEST_CAMERA_PERMISSION)
            }
        }

        binding.btnAdd.setOnClickListener {
            val name = binding.etHotelName.text.toString()
            val location = binding.etLocation.text.toString()
            val latitude = binding.etLatitude.text.toString().toDoubleOrNull() ?: 0.0
            val longitude = binding.etLongitude.text.toString().toDoubleOrNull() ?: 0.0
            val price = binding.etPrice.text.toString().toDoubleOrNull() ?: 0.0
            val category = binding.etCategory.text.toString()
            val description = binding.etDescription.text.toString()
            val imageUrl = etImageUrl.text.toString()
            val rating = binding.etRating.text.toString().toDoubleOrNull() ?: 0.0

            // Call the addHotel() method to add the hotel to the database
            val result = dbHelper.addHotel(name, location, latitude, longitude, price, category, description, imageUrl, rating)

            if (result != -1L) {
                Toast.makeText(this, "Hotel Added Successfully", Toast.LENGTH_SHORT).show()
                clearFields() // Clear the input fields after a successful insertion
            } else {
                Toast.makeText(this, "Error Adding Hotel", Toast.LENGTH_SHORT).show()
            }
        }

        binding.btnDelete.setOnClickListener {
            val name = binding.etHotelName.text.toString()
            // Call a method to delete the hotel from the database
            dbHelper.deleteHotel(name)
            Toast.makeText(this, "Hotel Deleted", Toast.LENGTH_SHORT).show()
            clearFields()
        }

        binding.btnModify.setOnClickListener {
            val name = binding.etHotelName.text.toString()
            val location = binding.etLocation.text.toString()
            val latitude = binding.etLatitude.text.toString().toDoubleOrNull() ?: 0.0
            val longitude = binding.etLongitude.text.toString().toDoubleOrNull() ?: 0.0
            val price = binding.etPrice.text.toString().toDoubleOrNull() ?: 0.0
            val category = binding.etCategory.text.toString()
            val description = binding.etDescription.text.toString()
            val imageUrl = etImageUrl.text.toString()
            val rating = binding.etRating.text.toString().toDoubleOrNull() ?: 0.0


            dbHelper.modifyHotel(name, location, latitude, longitude, price, category, description, imageUrl, rating)
            Toast.makeText(this, "Hotel Modified", Toast.LENGTH_SHORT).show()
            clearFields()
        }

        binding.backbtn.setOnClickListener {
            val intent = Intent(this, MainDashboard::class.java)
            startActivity(intent)
        }
    }

    private fun openCamera() {
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        if (intent.resolveActivity(packageManager) != null) {   //check availability

            val photoFile: File? = createImageFile()
            photoFile?.also {
                photoUri = FileProvider.getUriForFile(this, "${packageName}.fileprovider", it)
                intent.putExtra(MediaStore.EXTRA_OUTPUT, photoUri)
                startActivityForResult(intent, REQUEST_IMAGE_CAPTURE)
            }
        }
    }

    private fun createImageFile(): File {
        val timeStamp: String = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(Date())
        val storageDir: File? = getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        return File.createTempFile("JPEG_${timeStamp}_", ".jpg", storageDir).apply {
            // Save a file
            photoUri = Uri.fromFile(this)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == Activity.RESULT_OK) {

            imgHotelPhoto.setImageURI(photoUri)
            imgHotelPhoto.visibility = View.VISIBLE
            etImageUrl.setText(photoUri.toString())
        }
    }

    private fun clearFields() {

        binding.etHotelName.text.clear()
        binding.etLocation.text.clear()
        binding.etLatitude.text.clear()
        binding.etLongitude.text.clear()
        binding.etPrice.text.clear()
        binding.etCategory.text.clear()
        binding.etDescription.text.clear()
        binding.etRating.text.clear()
        imgHotelPhoto.setImageURI(null)
        imgHotelPhoto.visibility = View.GONE
        etImageUrl.text.clear()
    }

    companion object {
        private const val REQUEST_IMAGE_CAPTURE = 1
        private const val REQUEST_CAMERA_PERMISSION = 100
    }
}
