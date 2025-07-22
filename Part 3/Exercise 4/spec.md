## Exercise 4 – Java Class Construction Justification

Here is my reflection and justification on suitable Java class constructions for the given tasks.

---

## a.) Representing Temperatures

**Suitable construction: `class`**

**Justification:**

A `class` is appropriate here because we need value semantics (similar to objects like `BigInteger`), internal logic for unit conversion, and custom printing. The temperature also has behavior that differs significantly from a basic `double`, and there will likely be methods such as `.toCelsius()`, `.add(Temperature t)`, etc., to handle conversions and operations.

**Why not others:** 
- `record` is not ideal because we need behavior-rich objects, and records are meant for simple data carriers.
- `enum` is too limited and cannot hold varying values or complex logic.
- `primitive` types (like `double`) can't represent the temperature scale or contain methods.

---

## b.) Representing Diseases

**Suitable construction: `class`**

**Justification:** 

Diseases have mutable data (new diseases can be added), and they hold references to symptoms and Human objects. This kind of complexity and possible future extensibility is best handled with a standard class.

**Why not others:** 
- `record` is more suited for immutable data and wouldn't support dynamic updates or additions well.
- `enum` is unsuitable because diseases aren’t a closed set—they can increase over time.

---

## c.) Representing database query results (Rows with student data)

**Suitable construction: `record`**

**Justification:** 

Each database row has a fixed structure: (Name, Postal Address, Email Address, Degree Program, Number of Credits). These values are immutable once read from the database. A `record` is concise, automatically provides `toString()`, `equals()`, and is perfect for this use case.

**Why not others:** 
- A `class` would require boilerplate code.
- `enum` and primitive types are completely unsuitable here.

---

## d.) Reusable credit filter

**Suitable construction: `functional interface` (e.g., `Predicate<Row>`) or `method reference`**

**Justification:** 

Since the logic is reusable and fits the style of functional programming (Java 8+), a functional interface like `Predicate<Row>` is the best choice. We can define the filter as a lambda or method reference for readability and reusability.

```java
Predicate<Row> isAlmostGraduated = row -> row.credits() >= 280;
````

**Why not others:**

* A `class` just for a filter would be overkill and less readable.
* `record` or `enum` have no relevance in functional behavior encapsulation.

---

## e.) Differences in `Point` definitions

### 1. `class Point` with `final int[] values`

```java
class Point {
    final int[] values = new int[2];
    
    Point(int x, int y) {
        values[0] = x;
        values[1] = y;
    }
}
```

* Mutable: values inside the array can be changed (`point.values[0] = newX;`)
* Reference sharing: external code can modify internal state if it gets access to the array.
* Not safe for value semantics.
* Shared references can lead to bugs.

---

### 2. `record Point(int x, int y)`

* Immutable: `x` and `y` are final.
* Safe value semantics: changes require creation of a new `Point`.
* Good for thread safety and easy to reason about.
* Recommended when the data should not be modified.

---

### 3. `record Point(Number x, Number y)`

```java
record Point(Number x, Number y) {
    Point(int x, int y) {
        this(new Number(x), new Number(y));
    }
}
```

* The `record` is immutable, but the `Number` class is mutable (`value` field is not final).
* Allows indirect mutability: `point.x().value = newValue;` will change the state.
* Dangerous: appears immutable but isn’t.
* Breaks the principle of value immutability.

---

## Summary

- **a. Temperature:** Use a `class` because it needs custom behavior, unit handling, and operations. It's more than just a data container.

- **b. Disease:** Use a `class` as diseases can grow in number and have references to other objects (like `Human` and symptoms).

- **c. Student Row:** Use a `record` since each row is a fixed set of data, ideal for immutable and structured data containers.

- **d. Credit Filter:** Use a `Predicate<Row>` or a lambda expression to define reusable filtering logic in a functional style.

- **e. Point Implementations:**
  - `class Point` with `int[]`: Mutable, can be unintentionally changed from outside, not safe for shared use.
  - `record Point(int x, int y)`: Fully immutable and safe, best for value objects.
  - `record Point(Number x, Number y)`: Appears immutable, but allows mutation through the `Number` object's fields, which can lead to confusing bugs.



