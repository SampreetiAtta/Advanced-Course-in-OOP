## Exercise 2

### A) Definition and Implementation of the `Book` Class

Below is the implementation of the `Book` class in Java. It reads a file, stores the first line as the name of the book, counts the number of lines, and provides access to all words for further processing.


```java
import java.io.*;
import java.nio.file.*;
import java.util.*;

public class Book implements Comparable<Book> {
    private final Path filePath;
    private final String name;
    private final int lineCount;
    private final List<String> words;

    public Book(Path filePath) throws IOException {
        this.filePath = filePath;
        List<String> lines = Files.readAllLines(filePath);
        this.name = lines.isEmpty() ? "" : lines.get(0);
        this.lineCount = lines.size();
        this.words = new ArrayList<>();
        for (String line : lines) {
            String[] splitWords = line.toLowerCase().split("\\W+");
            Collections.addAll(this.words, splitWords);
        }
    }

    public String getName() {
        return name;
    }

    public int getLineCount() {
        return lineCount;
    }

    public Set<String> getUniqueWords() {
        return new HashSet<>(words);
    }

    public Path getFilePath() {
        return filePath;
    }

    @Override
    public String toString() {
        return String.format("%s (lines: %d, unique words: %d)", name, lineCount, getUniqueWords().size());
    }

    // Natural order (by name)
    @Override
    public int compareTo(Book other) {
        return this.name.compareToIgnoreCase(other.name);
    }
}
````

---

### B) Natural Order (Alphabetical by Name)

Books are sorted alphabetically by their name (first line in the file). This is implemented using the `Comparable` interface.

```java
Collections.sort(bookList); // Uses compareTo from Book
```

**Example Usage:**

```java
System.out.println("Books in natural order (by name):");
Collections.sort(bookList);
bookList.forEach(System.out::println);
```

---

### C) Secondary Order (Ascending by Line Count)

We use a `Comparator` to sort books based on their line count.

```java
bookList.sort(Comparator.comparingInt(Book::getLineCount));
```

**Example Usage:**

```java
System.out.println("Books ordered by line count:");
bookList.sort(Comparator.comparingInt(Book::getLineCount));
bookList.forEach(System.out::println);
```

---

### D) Third Order (Descending by Number of Unique Words)

We sort using a custom comparator that counts unique words and sorts in descending order.

```java
bookList.sort(Comparator.comparingInt((Book b) -> b.getUniqueWords().size()).reversed());
```

**Example Usage:**

```java
System.out.println("Books ordered by unique word count (descending):");
bookList.sort(Comparator.comparingInt((Book b) -> b.getUniqueWords().size()).reversed());
bookList.forEach(System.out::println);
```

---

### E) Combined Order (By Name, then Line Count)

We combine the comparator from B (natural order by name) with C (line count as secondary order).

```java
bookList.sort(
    Comparator.naturalOrder().thenComparingInt(Book::getLineCount)
);
```

**Example Usage:**

```java
System.out.println("Books ordered by name, then by line count:");
bookList.sort(Comparator.naturalOrder().thenComparingInt(Book::getLineCount));
bookList.forEach(System.out::println);
```

---

### Notes

* I used `List<Book>` instead of arrays to avoid fixed-size issues.
* The file reading uses `Files.readAllLines()` for simplicity.
* Word splitting uses regex to filter out punctuation.
* Sorting relies on Java's standard `Collections.sort()` and `Comparator` utilities.



