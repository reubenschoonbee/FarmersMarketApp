package nz.ac.canterbury.seng303.lab2.models

import nz.ac.canterbury.seng303.lab2.R

class Product(
    val id: Int,
    val name: String,
    val description: String,
    val price: Double,
    val imageResId: Int = R.drawable.no_image_available,
    val quantity: Int = 0,
) : Identifiable {
    override fun getIdentifier(): Int {
        return id
    }
}