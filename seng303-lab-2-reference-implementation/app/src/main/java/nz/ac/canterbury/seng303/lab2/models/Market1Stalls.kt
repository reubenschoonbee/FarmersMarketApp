package nz.ac.canterbury.seng303.lab2.models

import nz.ac.canterbury.seng303.lab2.R

class Market1Stalls(
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
        fun getStalls(marketId: Int): List<Market1Stalls> {
            return listOf(
                    Market1Stalls(
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
                    Market1Stalls(
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
                    Market1Stalls(
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
                    Market1Stalls(
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
                    Market1Stalls(
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
                    ),
                    Market1Stalls(
                        6,
                        "Ocean's Bounty",
                        "Seafood",
                        listOf(
                            Product(51, "Oysters", "Fresh oysters on the half shell", 15.0),
                            Product(52, "Clams", "Steamed clams with garlic butter", 10.0),
                            Product(53, "Calamari", "Crispy fried calamari", 12.0),
                            Product(54, "Seaweed", "Fresh seaweed salad", 7.0),
                            Product(55, "Fish Tacos", "Tacos filled with grilled fish", 8.0),
                            Product(56, "Crab Cakes", "Crispy crab cakes", 10.0),
                            Product(
                                57,
                                "Shrimp Cocktail",
                                "Chilled shrimp with cocktail sauce",
                                12.0
                            ),
                            Product(58, "Seafood Paella", "Mixed seafood rice dish", 14.0),
                            Product(59, "Fish & Chips", "Classic fish and chips", 11.0),
                            Product(60, "Clam Chowder", "Creamy clam chowder", 6.0)
                        ),
                        R.drawable.stall_oceans_bounty
                    ),
                    Market1Stalls(
                        7,
                        "Tea Time",
                        "Tea",
                        listOf(
                            Product(61, "Green Tea", "Refreshing green tea", 2.0),
                            Product(62, "Herbal Tea", "Calming herbal blends", 2.5),
                            Product(63, "Black Tea", "Rich black tea", 2.0),
                            Product(64, "Chai Tea", "Spiced chai tea", 3.0),
                            Product(65, "Iced Tea", "Chilled tea with lemon", 2.5),
                            Product(66, "Matcha Latte", "Smooth matcha latte", 4.0),
                            Product(67, "Tea Accessories", "Tea pots and cups", 5.0),
                            Product(68, "Loose Leaf Tea", "Premium loose leaf tea", 4.5),
                            Product(69, "Flavored Tea", "Variety of flavored teas", 3.5),
                            Product(70, "Tea Infuser", "Infuser for loose leaf tea", 2.0)
                        ),
                        R.drawable.stall_tea_time
                    ),
                    Market1Stalls(
                        8,
                        "Farm Fresh Eggs",
                        "Eggs",
                        listOf(
                            Product(71, "Free-range Eggs", "Fresh free-range eggs", 3.0),
                            Product(72, "Egg-based Products", "Various egg-based products", 4.0),
                            Product(73, "Egg Salad", "Delicious egg salad", 5.0),
                            Product(74, "Quiche", "Savory egg quiche", 6.0),
                            Product(75, "Deviled Eggs", "Classic deviled eggs", 4.5),
                            Product(76, "Egg Muffins", "Egg muffins with veggies", 5.0),
                            Product(77, "Pickled Eggs", "Pickled eggs for snacking", 3.5),
                            Product(78, "Omelet Mix", "Mix for homemade omelets", 2.5),
                            Product(79, "Egg Noodles", "Fresh egg noodles", 4.0),
                            Product(80, "Egg Drop Soup", "Egg drop soup", 3.0)
                        ),
                        R.drawable.stall_farm_fresh_eggs
                    ),
                    Market1Stalls(
                        9,
                        "Olive Orchard",
                        "Olives",
                        listOf(
                            Product(81, "Extra Virgin Olive Oil", "High-quality olive oil", 10.0),
                            Product(
                                82,
                                "Stuffed Olives",
                                "Delicious olives stuffed with herbs",
                                5.0
                            ),
                            Product(83, "Olive Tapenade", "Savory olive spread", 6.0),
                            Product(84, "Marinated Olives", "Flavorful marinated olives", 4.0),
                            Product(85, "Olive Bruschetta", "Bruschetta with olives", 5.0),
                            Product(86, "Olive Oil Infused", "Olive oil infused with herbs", 8.0),
                            Product(87, "Olive Pesto", "Pesto made with olives", 6.0),
                            Product(88, "Green Olives", "Crisp green olives", 4.0),
                            Product(89, "Black Olives", "Savory black olives", 4.0),
                            Product(90, "Olive-based Snacks", "Snacks made with olives", 5.0)
                        ),
                        R.drawable.stall_olive_orchard
                    ),
                    Market1Stalls(
                        10,
                        "Sweet Treats",
                        "Bakery",
                        listOf(
                            Product(91, "Croissants", "Flaky butter croissants", 3.0),
                            Product(92, "Cookies", "Homemade cookies", 2.0),
                            Product(93, "Brownies", "Rich chocolate brownies", 2.5),
                            Product(94, "Cupcakes", "Decorated cupcakes", 3.5),
                            Product(95, "Cheesecake", "Creamy cheesecake", 4.0),
                            Product(96, "Danish Pastries", "Flaky danish pastries", 3.5),
                            Product(97, "Tarts", "Fruit tarts", 4.5),
                            Product(98, "Pies", "Assorted fruit pies", 5.0),
                            Product(99, "Meringues", "Light meringues", 2.5),
                            Product(100, "Macarons", "Colorful French macarons", 3.5)
                        ),
                        R.drawable.stall_sweet_treats
                    ),
                    Market1Stalls(
                        11,
                        "Zvolskiy Delights",
                        "Bakery",
                        listOf(
                            Product(101, "Baked Goods", "Local specialties and baked goods", 4.0),
                            Product(102, "Sourdough Bread", "Artisan sourdough bread", 4.5),
                            Product(103, "Pita Bread", "Freshly baked pita bread", 3.0),
                            Product(104, "Flatbread", "Soft flatbread", 2.5),
                            Product(105, "Breadsticks", "Crispy breadsticks", 3.0),
                            Product(106, "Rolls", "Fresh dinner rolls", 2.0),
                            Product(107, "Focaccia", "Herb-infused focaccia", 4.0),
                            Product(108, "Bagels", "Freshly baked bagels", 3.0),
                            Product(109, "Brioche", "Soft brioche bread", 3.5),
                            Product(110, "Ciabatta", "Italian ciabatta bread", 4.0)
                        ),
                        R.drawable.stall_zvolskiy_delights
                    ),
                    Market1Stalls(
                        12,
                        "Donad's Bakery",
                        "Bakery",
                        listOf(
                            Product(111, "Bread", "Freshly baked bread", 2.5),
                            Product(112, "Pastries", "Delicious pastries", 3.0),
                            Product(113, "Tarts", "Sweet and savory tarts", 4.5),
                            Product(114, "Cookies", "Freshly baked cookies", 2.0),
                            Product(115, "Muffins", "Fluffy muffins", 3.0),
                            Product(116, "Cinnamon Rolls", "Warm cinnamon rolls", 4.0),
                            Product(117, "Danish", "Fruit danish pastries", 3.5),
                            Product(118, "Cakes", "Homemade cakes", 5.0),
                            Product(119, "Cupcakes", "Decorated cupcakes", 4.0),
                            Product(120, "Biscotti", "Crunchy biscotti", 2.5)
                        ),
                        R.drawable.stall_donads_bakery
                    ),
                    Market1Stalls(
                        13,
                        "Artisan Breads",
                        "Breads",
                        listOf(
                            Product(121, "Sourdough", "Artisan sourdough bread", 4.0),
                            Product(122, "Baguettes", "Fresh baguettes", 2.5),
                            Product(123, "Whole Wheat Bread", "Nutritious whole wheat bread", 3.0),
                            Product(124, "Multigrain Bread", "Healthy multigrain bread", 3.5),
                            Product(125, "Rye Bread", "Traditional rye bread", 3.0),
                            Product(126, "Flatbread", "Soft flatbread", 2.5),
                            Product(127, "Pita Bread", "Freshly baked pita bread", 3.0),
                            Product(128, "Focaccia", "Herb-infused focaccia", 4.0),
                            Product(129, "Dinner Rolls", "Soft dinner rolls", 2.0),
                            Product(130, "Breadsticks", "Crispy breadsticks", 2.5)
                        ),
                        R.drawable.stall_artisan_bakery
                    ),
                    Market1Stalls(
                        14,
                        "Veggie Haven",
                        "Vegetables",
                        listOf(
                            Product(131, "Organic Vegetables", "Fresh organic vegetables", 5.0),
                            Product(132, "Salad Mix", "Fresh salad greens", 4.0),
                            Product(133, "Herbs", "Fresh herbs for cooking", 2.0),
                            Product(134, "Root Vegetables", "Seasonal root vegetables", 3.0),
                            Product(135, "Peppers", "Assorted peppers", 2.5),
                            Product(136, "Tomatoes", "Fresh tomatoes", 2.0),
                            Product(137, "Cucumbers", "Crisp cucumbers", 1.5),
                            Product(138, "Zucchini", "Fresh zucchini", 2.0),
                            Product(139, "Carrots", "Sweet carrots", 2.0),
                            Product(140, "Eggplant", "Fresh eggplant", 3.0)
                        ),
                        R.drawable.stall_vegie_haven
                    ),
                    Market1Stalls(
                        15,
                        "Berry Bliss",
                        "Berries",
                        listOf(
                            Product(141, "Strawberries", "Fresh strawberries", 3.5),
                            Product(142, "Blueberries", "Fresh blueberries", 4.0),
                            Product(143, "Raspberries", "Sweet raspberries", 5.0),
                            Product(144, "Blackberries", "Juicy blackberries", 4.5),
                            Product(145, "Gooseberries", "Tart gooseberries", 4.0),
                            Product(146, "Mixed Berries", "Assorted fresh berries", 5.0),
                            Product(147, "Berry Jam", "Homemade berry jam", 6.0),
                            Product(148, "Berry Smoothies", "Delicious berry smoothies", 4.5),
                            Product(149, "Dried Berries", "Dried mixed berries", 5.0),
                            Product(150, "Berry Desserts", "Assorted berry desserts", 6.0)
                        ),
                        R.drawable.stall_berry_bliss
                    ),
                    Market1Stalls(
                        16,
                        "Fruity Fiesta",
                        "Fruits",
                        listOf(
                            Product(151, "Apples", "Crisp apples", 2.0),
                            Product(152, "Oranges", "Juicy oranges", 2.5),
                            Product(153, "Bananas", "Fresh bananas", 1.5),
                            Product(154, "Pineapples", "Sweet pineapples", 3.0),
                            Product(155, "Mangoes", "Ripe mangoes", 3.5),
                            Product(156, "Grapes", "Fresh grapes", 4.0),
                            Product(157, "Peaches", "Juicy peaches", 3.0),
                            Product(158, "Cherries", "Fresh cherries", 4.5),
                            Product(159, "Kiwi", "Exotic kiwi fruit", 2.5),
                            Product(160, "Watermelon", "Refreshing watermelon", 3.5)
                        ),
                        R.drawable.stall_fruity_fiesta
                    ),
                    Market1Stalls(
                        17,
                        "Veggie Patch",
                        "Vegetables",
                        listOf(
                            Product(131, "Organic Carrots", "Fresh organic carrots", 1.5),
                            Product(132, "Tomatoes", "Juicy tomatoes", 2.0),
                            Product(133, "Cucumbers", "Crisp cucumbers", 1.0),
                            Product(134, "Zucchini", "Fresh zucchini", 2.5),
                            Product(135, "Bell Peppers", "Colorful bell peppers", 1.5),
                            Product(136, "Lettuce", "Fresh lettuce for salads", 1.0),
                            Product(137, "Spinach", "Organic spinach", 2.0),
                            Product(138, "Herbs", "Fresh herbs for cooking", 1.0),
                            Product(139, "Eggplant", "Fresh eggplant", 2.0),
                            Product(140, "Pumpkins", "Seasonal pumpkins", 3.0)
                        ),
                        R.drawable.stall_vegie_patch
                    ),
                    Market1Stalls(
                        17,
                        "Cheese Corner",
                        "Cheese",
                        listOf(
                            Product(101, "Cheddar", "Sharp cheddar cheese", 5.0),
                            Product(102, "Brie", "Creamy brie cheese", 7.0),
                            Product(103, "Gouda", "Aged gouda cheese", 6.0),
                            Product(104, "Feta", "Crumbly feta cheese", 4.0),
                            Product(105, "Mozzarella", "Fresh mozzarella cheese", 5.5),
                            Product(106, "Blue Cheese", "Tangy blue cheese", 8.0),
                            Product(107, "Parmesan", "Grated parmesan cheese", 6.5),
                            Product(108, "Goat Cheese", "Soft goat cheese", 6.0),
                            Product(109, "Ricotta", "Creamy ricotta cheese", 4.5),
                            Product(110, "Cream Cheese", "Smooth cream cheese", 3.0)
                        ),
                        R.drawable.stall_cheese_corner
                    ),
                    Market1Stalls(
                        18,
                        "Burger Bliss",
                        "Burgers",
                        listOf(
                            Product(111, "Beef Burger", "Juicy beef burger", 8.0),
                            Product(112, "Veggie Burger", "Delicious veggie burger", 7.0),
                            Product(113, "Cheeseburger", "Beef burger with cheese", 9.0),
                            Product(114, "Bacon Burger", "Beef burger with bacon", 10.0),
                            Product(115, "Chicken Burger", "Grilled chicken burger", 9.5),
                            Product(116, "BBQ Burger", "BBQ sauce beef burger", 9.0),
                            Product(117, "Mushroom Burger", "Beef burger with mushrooms", 9.0),
                            Product(118, "Double Burger", "Double beef patty burger", 11.0),
                            Product(119, "Spicy Burger", "Spicy beef burger", 9.0),
                            Product(120, "Fish Burger", "Crispy fish burger", 8.5)
                        ),
                        R.drawable.stall_burger_bliss
                    ),

                    Market1Stalls(
                        20,
                        "Jammin' Good",
                        "Jams",
                        listOf(
                            Product(131, "Strawberry Jam", "Sweet strawberry jam", 3.0),
                            Product(132, "Blueberry Jam", "Delicious blueberry jam", 4.0),
                            Product(133, "Raspberry Jam", "Tart raspberry jam", 3.5),
                            Product(134, "Apricot Jam", "Fruity apricot jam", 2.5),
                            Product(135, "Orange Marmalade", "Zesty orange marmalade", 3.5),
                            Product(136, "Peach Preserves", "Sweet peach preserves", 4.0),
                            Product(137, "Mixed Berry Jam", "Assorted berry jam", 4.0),
                            Product(138, "Honey Jam", "Natural honey jam", 3.5),
                            Product(139, "Fig Jam", "Rich fig jam", 4.5),
                            Product(140, "Ginger Jam", "Spicy ginger jam", 3.5)
                        ),
                        R.drawable.stall_jammin_good
                    )

                )

        }
    }
}
