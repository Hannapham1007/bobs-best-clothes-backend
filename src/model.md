# Domain model for Bob's Best Clothes

1. As a customer, I want to be able to add a product to my basket so that I can purchase it later.

2. As a customer, I want to easily increase the quantity of a product already in my basket so that I can purchase more of it.

3. As a customer, I want to easily to decrease the quantity of a product already in my basket, in case I want to buy fewer items.

4. As a customer, I want to remove a product from my basket if I no longer want to purchase it.

5. As a customer, I want  to be able to filter products by categories, so that I can easily find items that match my preferences.

6. As a customer, I want to be ble to search for products by entering the products name into a search bar.

7. As a customer, I want to view detailed information about a product before deciding to add it to my cart.

8. As a customer, after adding desired products to my basket, I want to be able to checkout to complete my purchase.

9. As a registered customer, I want to be able to access a list of my previous orders to track my purchase history.

| Classes          | Variables               | Methods          |
|------------------|-------------------------|------------------|
| User             | int id                  | getAllUsers()    |
| UserController   | string firstName        | createUser()     |
| UserListResponse | string lastName         | deleteAllUsers() |
| UserRepository   | string phone            |                  |
| UserResponse     | string phone            |                  |
|                  | LocalDateTime createdAt |                  |
|                  | LocalDateTime updatedAt |                  |

| Classes             | Variables              | Methods             |
|---------------------|------------------------|---------------------|
| Product             | int id                 | getAllProducts()    |
| ProductController   | string title           | createProduct()     |
| ProductListResponse | float price            | deleteProductById() |
| ProductRepository   | string imageURL        | updateProductById() |
| ProductResponse     | string description     |                     |
|                     | LocalDateTime createAt |                     |
|                     | LocalDateTime updateAt |                     |

| Classes              | Variables              | Methods              |
|----------------------|------------------------|----------------------|
| Category             | int id                 | getAllCategories()   |
| CategoryController   | String name            | createCategory()     |
| CategoryListResponse | String description     | deleteCategoryById() |
| CategoryRepository   | List<Product> products | updateCategory()     |
| CategoryResponse     |                        |                      |

| Classes           | Variables                | Methods           |
|-------------------|--------------------------|-------------------|
| Order             | int id                   | getAllOrders()    |
| OrderController   | float total              | createOrder()     |
| OrderListResponse | List<CartItem> cartItems | getOrderById()    |
| OrderRepository   |                          | deleteOrderById() |
| OrderResponse     |                          |                   |

| Classes              | Variables       | Methods |
|----------------------|-----------------|---------|
| CartItem             | int id          |         |
| CartItemController   | int quantity    |         |
| CartItemListResponse | Product product |         |
| CartItemRepository   | Order order     |         |
| CartItemResponse     |                 |         |
