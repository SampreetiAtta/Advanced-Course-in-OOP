## Exercise 3

### a) What language feature is involved in the methods `challenge1` and `challenge2`? How does this feature manifest, and how should it be used?

The key language feature involved in `challenge1` and `challenge2` is **polymorphism**, specifically **dynamic method dispatch** via **interfaces or abstract types**.

- In `challenge1`, the method likely accepts a specific class type (e.g., `Crow`), whereas
- In `challenge2`, the method takes a more **generic** or **abstract** type (e.g., `Bird` or an interface like `Winged` or `Bipedal`).

### How it manifests:
- In Java, when a method takes a superclass or interface type as a parameter, we can pass any subclass or implementing class instance to it.
- This allows the program to treat different objects uniformly while maintaining their individual behaviors.

### Proper usage:
- This feature should be used to write more **flexible, reusable, and maintainable code**.
- By programming to interfaces or abstract classes rather than concrete implementations, we can easily extend our system in the future.

---

### b) Functional differences between `challenge1` and `challenge2`

Although the **output** of `challenge1` and `challenge2` appears to be the same, there is a fundamental **difference in flexibility and generality**:

- `challenge1` is likely restricted to a **specific class**, e.g., `Crow`. It cannot accept other birds like `Sparrow` or a generic `Bird` unless explicitly overloaded.
- `challenge2`, however, uses **abstraction** (like the `Bird` class or an interface), which allows it to accept any object that matches the expected **type hierarchy**.

#### Key difference:
- **challenge1** = **Tightly Coupled** (specific type)
- **challenge2** = **Loosely Coupled and Extensible** (abstract/general type)

So, `challenge2` is **functionally more general**, even if the current output is the same.

---

### c) Demonstrating the advantage of `challenge2`

Here’s a simple extension example: suppose we introduce a new class called `Penguin` that extends `Bird` and implements `Bipedal` but **not `Winged`** (since penguins can't fly).

#### Java-like Implementation

```java
class Penguin extends Bird implements Bipedal {
    @Override
    public void move() {
        System.out.println("Penguin waddles.");
    }
}
````

Now let’s assume:

```java
public static void challenge2(Bird bipedBird) {
    bipedBird.move();
}
```

We can do:

```java
Bird pingu = new Penguin();
challenge2(pingu);  // Output: "Penguin waddles."
```

But we **cannot** use `challenge1` if it was defined as:

```java
public static void challenge1(Crow crow) {
    crow.move();
}
```

Because `Penguin` is **not** a `Crow`.

#### Conclusion:

Using the **more general signature** in `challenge2` allows for **extensibility**—new bird types can be introduced and processed uniformly without modifying existing code. This demonstrates the power of **polymorphism and abstraction** in object-oriented design.