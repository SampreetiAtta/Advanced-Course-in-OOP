# Data Protection for Book Type in Library System

In the library system, two different user roles need access to book data: library customers (read-only) and library staff (read-write). To support this, the principle of **encapsulation with role-based access** is applied.

We define a `Book` class with private fields such as `title`, `publicationYear`, `publisher`, etc. Access to these fields is controlled through interfaces:

- **Customers** are given access to a `BookView` interface, which only contains getter methods. This ensures they can read but not modify any book data.
- **Staff** are allowed to use the full `Book` class, which includes both getters and setters. This lets them update information, such as correcting errors or marking a book as lost.

Internally, the book database can be stored as a `List<Book>`. When returning results to customers, the books are cast to `BookView` so only read access is provided.

This design enforces the following:
- Class invariants are protected, as only staff can change data.
- Customers cannot accidentally or intentionally modify book data.
- Staff can perform necessary updates securely and cleanly.

Overall, this separation of access based on role maintains data integrity while supporting the system's functional requirements. 