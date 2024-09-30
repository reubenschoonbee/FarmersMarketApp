package nz.ac.canterbury.seng303.lab2.models

import nz.ac.canterbury.seng303.lab2.R

class Market2Stalls(
    val id: Int,
    val name: String,
    val category: String,
    val products: List<Product>,
    val imageResId: Int,
) : Identifiable {

    override fun getIdentifier(): Int {
        return id
    }

    companion object {
        fun getStalls(marketId: Int): List<Market2Stalls> {
            return listOf(
                Market2Stalls(
                    1,
                    "Sunny Seeds",
                    "Flowers",
                    listOf(
                        Product(1, "Roses", "Fresh red roses", 2.5),
                        Product(2, "Tulips", "Colorful tulips", 1.5),
                        Product(3, "Daisies", "Bright yellow daisies", 2.0),
                        Product(4, "Lilies", "Fragrant white lilies", 3.0),
                        Product(5, "Sunflowers", "Tall and cheerful sunflowers", 4.0),
                        Product(6, "Chrysanthemums", "Vibrant chrysanthemums", 3.5),
                        Product(7, "Orchids", "Exotic orchids", 5.0),
                        Product(8, "Gerberas", "Colorful gerbera flowers", 2.8),
                        Product(9, "Carnations", "Classic carnations", 2.2),
                        Product(10, "Iris", "Beautiful blue irises", 3.5)
                    ),
                    R.drawable.stall_sunny_seeds
                ),
                Market2Stalls(
                    2,
                    "Sunrise Breakfast",
                    "Breakfast",
                    listOf(
                        Product(11, "Pancakes", "Fluffy pancakes with syrup", 5.0),
                        Product(12, "Breakfast Burrito", "Eggs, cheese, and salsa", 4.5),
                        Product(13, "Omelet", "Three-egg omelet with veggies", 6.0),
                        Product(14, "French Toast", "Thick slices of French toast", 5.5),
                        Product(15, "Breakfast Sandwich", "Egg and bacon sandwich", 4.0),
                        Product(16, "Smoothie Bowl", "Healthy smoothie bowl", 6.5),
                        Product(17, "Granola Parfait", "Yogurt with granola and fruit", 4.0),
                        Product(18, "Avocado Toast", "Smashed avocado on toast", 5.0),
                        Product(19, "Muffins", "Freshly baked muffins", 2.5),
                        Product(20, "Breakfast Wrap", "Eggs and sausage wrap", 4.5)
                    ),
                    R.drawable.stall_sunrise_breakfast
                ),
                Market2Stalls(
                    3,
                    "Java Junction",
                    "Coffee",
                    listOf(
                        Product(21, "Espresso", "Rich and bold espresso", 3.0),
                        Product(22, "Latte", "Creamy latte with steamed milk", 4.0),
                        Product(23, "Cold Brew", "Chilled coffee brewed for 12 hours", 3.5),
                        Product(24, "Cappuccino", "Espresso with frothed milk", 4.5),
                        Product(25, "Mocha", "Chocolate-flavored coffee", 4.0),
                        Product(26, "Flat White", "Rich coffee with steamed milk", 4.5),
                        Product(27, "Americano", "Espresso with hot water", 3.0),
                        Product(28, "Iced Coffee", "Chilled coffee served over ice", 3.5),
                        Product(29, "Caramel Macchiato", "Espresso with caramel and milk", 4.5),
                        Product(30, "Pastries", "Variety of fresh pastries", 2.5)
                    ),
                    R.drawable.stall_java_junction
                ),
                Market2Stalls(
                    4,
                    "Sausage Sizzle",
                    "Sausages",
                    listOf(
                        Product(
                            31,
                            "Grilled Sausage",
                            "Delicious grilled sausage on a bun",
                            5.5
                        ),
                        Product(32, "Condiments", "Various sauces and toppings", 0.5),
                        Product(33, "Veggie Sausage", "Grilled veggie sausage", 5.0),
                        Product(34, "Cheese Kransky", "Cheese-filled sausage", 6.0),
                        Product(35, "Hot Dog", "Classic hot dog with toppings", 4.0),
                        Product(36, "Sausage Roll", "Savory sausage pastry", 3.5),
                        Product(37, "Chili Dog", "Hot dog with chili topping", 6.5),
                        Product(38, "Bacon-Wrapped Sausage", "Sausage wrapped in bacon", 7.0),
                        Product(39, "Spicy Sausage", "Spicy grilled sausage", 5.0),
                        Product(40, "Onion Rings", "Crispy onion rings", 3.0)
                    ),
                    R.drawable.stall_sausage_sizzle
                ),
                Market2Stalls(
                    5,
                    "Fresh Catch",
                    "Seafood",
                    listOf(
                        Product(41, "Salmon", "Freshly caught salmon", 12.0),
                        Product(42, "Shrimp", "Juicy shrimp", 10.0),
                        Product(43, "Cod", "Fresh cod fillets", 11.0),
                        Product(44, "Tuna", "Fresh tuna steaks", 15.0),
                        Product(45, "Crab", "Sweet crab meat", 14.0),
                        Product(46, "Mackerel", "Rich mackerel fillets", 10.0),
                        Product(47, "Trout", "Delicious trout fillets", 12.0),
                        Product(48, "Haddock", "Mild haddock fillets", 11.0),
                        Product(49, "Sardines", "Grilled sardines", 9.0),
                        Product(50, "Catfish", "Fried catfish", 8.0)
                    ),
                    R.drawable.stall_fresh_catch
                )

            )

        }
    }
}
