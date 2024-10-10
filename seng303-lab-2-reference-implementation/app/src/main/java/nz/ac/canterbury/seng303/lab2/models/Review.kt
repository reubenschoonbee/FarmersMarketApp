package nz.ac.canterbury.seng303.lab2.models

data class Review(
    val username: String,
    val comment: String,
    val rating: Int,
    val timestamp: Long = System.currentTimeMillis()
)
