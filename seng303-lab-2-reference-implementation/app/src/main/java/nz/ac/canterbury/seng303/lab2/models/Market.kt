package nz.ac.canterbury.seng303.lab2.models

class Market (
    val id: Int,
    val name: String,
    val description: String,
    val openTimes: String,
    val location: String,
) : Identifiable {
    override fun getIdentifier(): Int {
        return id
    }

    companion object {

        fun getMarkets(): List<Market> {
            return listOf(
                Market(
                    id = 1,
                    name = "South Christchurch Farmers Market",
                    description = "Premier destination for fresh, especially organic, produce",
                    openTimes = "Every Sunday, 9am to midday",
                    location = "66 Colombo St",
                ),
                Market(
                    id = 2,
                    name = "Methven Arts & Growers Market",
                    description = "Local farmers selling fresh produce.",
                    openTimes = "Every Saturday, 8 AM - 2 PM",
                    location = "34 Methven Chertsey Road, Methven",
                ),
                Market(
                    id = 3,
                    name = "Lincoln Farmers & Craft Market",
                    description = "Local farmers selling fresh produce.",
                    openTimes = "Every Saturday 10am - 1pm",
                    location = "Gerald St, Lincoln, Christchurh",
                ),
                Market(
                    id = 4,
                    name = "Cambridge Farmers Market",
                    description = "Local farmers selling fresh produce.",
                    openTimes = "Saturday 8am - midday",
                    location = "67 Queen St, Leamington, Cambridge",
                )
            )

        }
    }
}