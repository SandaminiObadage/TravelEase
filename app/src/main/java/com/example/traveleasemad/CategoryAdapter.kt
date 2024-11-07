//package com.example.traveleasemad
//
//import android.view.LayoutInflater
//import android.view.View
//import android.view.ViewGroup
//import android.widget.TextView
//import androidx.recyclerview.widget.RecyclerView
//
//class CategoryAdapter(
//    private var categories: List<String>,
//    private val onCategoryClick: (String) -> Unit
//) : RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder>() {
//
//    // ViewHolder class to hold individual item views
//    inner class CategoryViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
//        private val categoryTextView: TextView = itemView.findViewById(R.id.categoryTextView)
//
//        fun bind(category: String) {
//            categoryTextView.text = category
//            itemView.setOnClickListener { onCategoryClick(category) }
//        }
//    }
//
//    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
//        val view = LayoutInflater.from(parent.context).inflate(R.layout.category_item, parent, false)
//        return CategoryViewHolder(view)
//    }
//
//    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
//        holder.bind(categories[position])
//    }
//
//    override fun getItemCount(): Int = categories.size
//
//    fun updateCategories(newCategories: List<String>) {
//        categories = newCategories
//        notifyDataSetChanged()
//    }
//}
