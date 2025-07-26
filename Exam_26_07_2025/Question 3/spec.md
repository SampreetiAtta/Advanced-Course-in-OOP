## Question 3: Suitable Java Class Constructions for the Given Situations

### a) Querying the customer database for clients registered in 2024

**Recommended Class Construction:** `Record`

#### Justification:
- The data returned from the database consists of immutable and structured data (Name, Address, Email Address, Purchased Product, Purchase Date).
- Each entry is a fixed data structure with no complex behavior - ideal use case for a `record` introduced in Java 14.
- A `record` automatically generates the constructor, getters, `toString()`, `equals()`, and `hashCode()` methods.

#### Example:
```java
public record Entry(String name, String address, String email, String product, LocalDate purchaseDate) {}
````

#### Excluded Options:

* `Class` with manual getter/setters is unnecessary overhead since behavior isn't required here.
* `Enum` is not suitable as the data varies per customer and does not represent a fixed set of constants.
* `Interface` is not needed as no abstraction or polymorphism is involved.

---

### b) Reusable filter for clients eligible for loyalty rewards

**Recommended Class Construction:** `Interface` (with lambda support / functional interface)

#### Justification:

* The filtering logic is behavioral and should be reusable.
* A functional interface allows passing logic as an argument - clean and flexible design.
* Works well with lambda expressions and `List.stream().filter(...)`.

#### Example:

```java
@FunctionalInterface
public interface EntryFilter {
    boolean filter(Entry entry);
}
```

#### Usage:

```java
List<Entry> eligible = entries.stream()
    .filter(entry -> entry.purchaseDate().isAfter(LocalDate.of(2023, 1, 1)))
    .collect(Collectors.toList());
```

#### Excluded Options:

* `Abstract Class` is overkill for single-method behavior.
* `Enum` is not for functional behavior.
* `Record` is for data, not behavior.

---

### c) Modeling financial transactions with support for different types

**Recommended Class Construction:** `Abstract Class` with concrete subclasses

#### Justification:

* Common features (value, currency) can be defined in an abstract base class `Transaction`.
* Different types like `Deposit`, `Withdrawal`, `Transfer` can extend it with specific behaviors.
* Abstract methods like `getDescription()` can be overridden to display type-specific text.
* Promotes code reuse and type safety.

#### Example:

```java
public abstract class Transaction {
    protected double amount;
    protected String currency;

    public Transaction(double amount, String currency) {
        this.amount = amount;
        this.currency = currency;
    }

    public abstract String getDescription();
}

public class Deposit extends Transaction {
    public Deposit(double amount, String currency) {
        super(amount, currency);
    }

    @Override
    public String getDescription() {
        return "Deposit of " + currency + amount;
    }
}
```

#### Excluded Options:

* `Enum` cannot model variable data like amount.
* `Interface` lacks field storage and default behavior implementation.
* `Record` is immutable and not suitable when behavior (like formatting or polymorphism) needs to vary by subclass.

