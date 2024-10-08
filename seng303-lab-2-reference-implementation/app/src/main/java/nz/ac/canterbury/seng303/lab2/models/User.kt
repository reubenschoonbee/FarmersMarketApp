package nz.ac.canterbury.seng303.lab2.models

data class User(
    val id: Int,
    val username: String,
    val password: String,
) : Identifiable {
    override fun getIdentifier(): Int {
        return id
    }
}