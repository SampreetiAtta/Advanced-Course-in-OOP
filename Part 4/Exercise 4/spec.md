## Exercise 4

### Overview

In this task, I modified the implementation from **Exercises 1 and 2** to improve the design using **generics** and return values directly from the handler methods. This improves type safety, reusability, and simplifies the use of the `Zipper` pattern. The major changes include:

* Refactoring the `Handler` interface and its implementations to return a result of generic type `X`.
* Using `Void` as the type for cases with no meaningful return value (e.g. Exercise 1).
* Demonstrating how book collections can be sorted more cleanly using the generic structure.

---

### A) Copying Code

I copied the following classes from Exercises 1 and 2 into the `exercise4` folder:

* `Zipper.java`
* `TestZipper.java`
* `TestZipper2.java`

---

### B) Introducing Generics

#### Original Issue

In Exercise 2, to construct a list of books, mutable data structures like `List<Book> books = new ArrayList<>();` were passed around and modified indirectly. This is not ideal in functional or clean OOP design.

#### Solution

I modified the `Handler<T>` interface to be generic with a return type `X`:

```java
public interface Handler<T, X> {
    X handle(T item);
}
```

For Exercise 1-like cases where no result is needed (just printing, etc.), I used:

```java
Handler<T, Void>
```

and returned `null` explicitly.

#### Example of New Handler for Printing

```java
public class PrintHandler<T> implements Handler<T, Void> {
    @Override
    public Void handle(T item) {
        System.out.println(item);
        return null;
    }
}
```

#### Example of New Handler for Book Collection

```java
public class CollectBooksHandler implements Handler<Book, Book> {
    @Override
    public Book handle(Book book) {
        return book;
    }
}
```

---

### C) Book Collection Sorting

I designed an interface that handles a collection of books and sorts them based on criteria. Four example sorters could be created (title, author, year, page count).

```java
public interface BookSorter {
    List<Book> sort(List<Book> books);
}
```

#### Example of Sorting by Title

```java
public class SortByTitle implements BookSorter {
    @Override
    public List<Book> sort(List<Book> books) {
        return books.stream()
            .sorted(Comparator.comparing(Book::getTitle))
            .collect(Collectors.toList());
    }
}
```

Now in `TestZipper2`, instead of mutating an external list, I do:

```java
List<Book> books = zipper.traverse(new CollectBooksHandler());
BookSorter sorter = new SortByTitle();
List<Book> sorted = sorter.sort(books);
sorted.forEach(System.out::println);
```

---

### Summary

* **Generic handlers** were introduced to return values directly.
* **Void** is used where no return is needed (Exercise 1).
* **BookSorter** abstraction makes sorting cleaner and extensible.
* **Better code readability**, type safety, and functional flexibility.

This new structure makes the `Zipper` class more general and aligns better with OOP and functional programming principles. It avoids unnecessary mutability and promotes clean architecture.
