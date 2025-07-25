import java.util.*;

// Book class with title and author
class Book {
    private final String title;
    private final String author;

    public Book(String title, String author) {
        this.title = title;
        this.author = author;
    }

    public String getTitle() { return title; }
    public String getAuthor() { return author; }

    @Override
    public String toString() {
        return "\"" + title + "\" by " + author;
    }
}

// Handler that just returns the book (for collection purposes)
class CollectBooksHandler implements Handler<Book, Book> {
    @Override
    public Book handle(Book book) {
        return book;
    }
}

// Interface for sorting books
interface BookSorter {
    List<Book> sort(List<Book> books);
}

// Sort by title
class SortByTitle implements BookSorter {
    @Override
    public List<Book> sort(List<Book> books) {
        return books.stream()
                .sorted(Comparator.comparing(Book::getTitle))
                .toList();
    }
}

// Sort by author
class SortByAuthor implements BookSorter {
    @Override
    public List<Book> sort(List<Book> books) {
        return books.stream()
                .sorted(Comparator.comparing(Book::getAuthor))
                .toList();
    }
}

public class TestZipper2 {
    public static void main(String[] args) {
        List<Book> books = Arrays.asList(
            new Book("The Great Gatsby", "F. Scott Fitzgerald"),
            new Book("1984", "George Orwell"),
            new Book("Brave New World", "Aldous Huxley"),
            new Book("To Kill a Mockingbird", "Harper Lee")
        );

        Zipper<Book> zipper = new Zipper<>(books);

        // Collect all books using the new handler
        List<Book> collectedBooks = zipper.traverse(new CollectBooksHandler());

        // Choose a sorter (e.g., by title)
        BookSorter sorter = new SortByTitle();
        List<Book> sortedBooks = sorter.sort(collectedBooks);

        // Print sorted books
        System.out.println("Sorted Books:");
        for (Book b : sortedBooks) {
            System.out.println(b);
        }
    }
}

