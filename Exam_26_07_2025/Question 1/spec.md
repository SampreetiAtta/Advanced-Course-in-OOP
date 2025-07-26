## Question 1: Specification for Card Data Protection in FireStone Database

### Task Overview

The task is to design a data protection mechanism for the `Card` type used in a fan-maintained FireStone digital card game database. There are **two distinct user roles**:

1. **Player (Read-Only Access)**: Can query and view card information.
2. **Administrative Staff (Read/Write Access)**: Can permanently update card details due to changes or corrections.

All operations occur in-memory within a Java process and are saved to disk at the end of the day.

---

### Principle of Data Protection: **Encapsulation and Access Control**

To serve the two roles while ensuring data integrity and security, I apply **encapsulation** using **access modifiers** (`private`, `public`, `protected`) and **interfaces** to restrict operations based on roles.

---

### Class Structure

I define the core `Card` class as follows:

```java
public class Card {
    private String title;
    private int cost;
    private String type;
    private int damage;

    // Constructor
    public Card(String title, int cost, String type, int damage) {
        this.title = title;
        this.cost = cost;
        this.type = type;
        this.damage = damage;
    }

    // Public getters for player view (read-only access)
    public String getTitle() { return title; }
    public int getCost() { return cost; }
    public String getType() { return type; }
    public int getDamage() { return damage; }

    // Setters available only to administrative logic
    public void setTitle(String title) { this.title = title; }
    public void setCost(int cost) { this.cost = cost; }
    public void setType(String type) { this.type = type; }
    public void setDamage(int damage) { this.damage = damage; }
}
````

---

## Role-Based Interfaces

To control access:

```java
public interface CardView {
    String getTitle();
    int getCost();
    String getType();
    int getDamage();
}

public interface CardAdmin extends CardView {
    void setTitle(String title);
    void setCost(int cost);
    void setType(String type);
    void setDamage(int damage);
}
```

* **Players** will use the `CardView` interface.
* **Admins** will use the `CardAdmin` interface.

This allows fine-grained role-based access while keeping the underlying `Card` object consistent.

---

### Class Invariants

* All fields (`title`, `cost`, `type`, `damage`) must remain valid after any modification.
* The following constraints should be preserved:

  * `title` != null
  * `cost >= 0`
  * `damage >= 0`
  * `type` ∈ { "Spell", "Minion", "Weapon", ... }

Enforce validation in setter methods or via an internal validation method.

---

### Usage Example

```java
List<Card> cardList = new ArrayList<>();

// Player access
CardView view = cardList.get(0);
// view.setCost(10); // Not allowed - compile-time error
System.out.println(view.getTitle());

// Admin access
CardAdmin admin = (CardAdmin) cardList.get(0);
admin.setDamage(5); // Allowed
```

---

### Consequences and Benefits

* Players can safely access card information without risk of altering data.
* Admins have controlled full access to update card data.
* Enforces class invariants through encapsulated setters.
* Prevents accidental or unauthorized data mutation.
* Easy to extend if more roles are introduced later.

---

### Chosen Data Structure

I choose `List<Card>` for flexibility and dynamic management of card records.

```java
List<Card> cards = new ArrayList<>();
```

---

### Conclusion

The **encapsulation principle with interface-based role restriction** provides an efficient and maintainable way to protect card data while serving both read-only and read-write access requirements. It separates concerns, enforces data integrity, and supports future scalability.


