package nz.ac.canterbury.seng303.lab2.models

class Product(
    val id: Int,
    val name: String,
    val description: String,
    val price: Double
) : Identifiable {

    override fun getIdentifier(): Int {
        return id
    }
}