package com.example.traveleasemad

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView // Import ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class HotelsAdapter(private val hotelList: List<HotelDashboardActivity.Hotel>,
                    private val onHotelClick: (HotelDashboardActivity.Hotel) -> Unit

) :
    RecyclerView.Adapter<HotelsAdapter.HotelViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HotelViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.hotel_item_card, parent, false)
        return HotelViewHolder(view)
    }

    override fun onBindViewHolder(holder: HotelViewHolder, position: Int) {
        holder.bind(hotelList[position], onHotelClick)
        val hotel = hotelList[position]


        holder.itemView.setOnClickListener {
            onHotelClick(hotel)
        }

        Glide.with(holder.itemView.context)
            .load(hotel.getImageUrlString())
            .into(holder.hotelImage)

        holder.hotelName.text = hotel.name
        holder.hotelLocation.text = hotel.location
        // holder.hotelPrice.text = hotel.price.toString()
        holder.hotelDescription.text = hotel.description
        holder.hotelRating.text = "Rating: ${hotel.rating}"


    }


    override fun getItemCount(): Int {
        return hotelList.size
    }

    class HotelViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val hotelImage: ImageView = itemView.findViewById(R.id.etImageUrl)
        val hotelName: TextView = itemView.findViewById(R.id.etHotelName)
        val hotelLocation: TextView = itemView.findViewById(R.id.etLocation)
        // val hotelPrice: TextView = itemView.findViewById(R.id.etPrice)
        val hotelDescription: TextView = itemView.findViewById(R.id.etDescription)
        val hotelRating: TextView = itemView.findViewById(R.id.etRating)

        fun bind(hotel: HotelDashboardActivity.Hotel, onClick: (HotelDashboardActivity.Hotel) -> Unit) {
            hotelName.text = hotel.name
            itemView.setOnClickListener { onClick(hotel) }
        }
    }
}
