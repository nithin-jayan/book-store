# Book-store

A Service API for an online bookstore where the user can perform the following operations:

	CRUD operations on Books
	Checkout operation for single or multiple books which will return the total payable amount.
	Backend operations are non-blocking and using Spring Webflux
	
Tech Stack used: 

Java 8/11 (functional style programming), reactive in memory DB(H2) like R2DBC, Spring Boot, Spring-webflux, logback with sl4j , Lombok ,jacoco

Considerations:

	Use Spring Webflux Reactive API
	Book object should have the following attributes:
	Name
	Description
	Author
	BookType/Classification 
	Price
	ISBN
	
	Checkout method takes a list of books as parameters plus optional promotion code and return total price after discount (if applicable).
	
	Promotion/Discounts are variant according to book type/classification, ex: TECHNOLOGY books may have 10% discount while SCIENCE books have 0% discount.
	
	Use YAML based configurations for managing and externalising master data definitions such as Types, Classification, Discount configuration by Type....etc 
