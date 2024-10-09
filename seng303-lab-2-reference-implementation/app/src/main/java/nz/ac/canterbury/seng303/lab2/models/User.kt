package nz.ac.canterbury.seng303.lab2.models

data class User(
    val id: Int,
    val username: String,
    val password: String,
) : Identifiable {
    override fun getIdentifier(): Int {
        return id
    }

    companion object {
        fun getUsers(): List<User> {
            return listOf(
                User(
                    id = 1,
                    username = "admin",
                    password = "admin"
                ),
                User(
                    id = 2,
                    username = "username",
                    password = "password"
                )

            )
        }
    }
}