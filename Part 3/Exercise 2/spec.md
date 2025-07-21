### a.) Explanation of Inheritance and Polymorphism in the Given Code

In this example, we are dealing with **checked exceptions** in Java, and concepts of **inheritance** and **polymorphism** are used through class hierarchies and method overriding.

---

#### 1. **Inheritance**

- The base class is an abstract class: `Problem extends Exception`.
- `WeirdProblem` and `TrickyProblem` both **extend** `Problem`, making them part of the same exception hierarchy.

* The exception class hierarchy is as follows:

	* `Problem` is an abstract class that extends `Exception`.
	* Two concrete classes, `WeirdProblem` and `TrickyProblem`, both extend `Problem`.

This means both `WeirdProblem` and `TrickyProblem` are subclasses of `Problem`, and they indirectly inherit from Java’s base `Exception` class.

---

#### 2. **Polymorphism**

- In the context of exceptions, **polymorphism** allows a `Problem` reference to refer to an object of type `WeirdProblem` or `TrickyProblem`.

- In `Experiment2`, the method is declared to throw `Problem`, which means it can throw any subtype of `Problem`, including `WeirdProblem` or `TrickyProblem`.

```java
void perform() throws Problem
````

* At runtime, the actual type of the exception object thrown depends on the condition, not on the static type (`Problem`).
* This is **runtime polymorphism** — the actual object type determines behavior during execution.

---

#### 3. **Different Catch Branches**

```java
try {
    e1.perform();
    e2.perform();
}
catch (WeirdProblem w) {}
catch (TrickyProblem w) {}
catch (Problem w) {}
```

* The catch blocks are arranged from **specific to general**, which is required in Java.

* If `e1.perform()` or `e2.perform()` throws a `WeirdProblem`, the first catch block handles it.

* If a `TrickyProblem` is thrown, the second catch block catches it.

* If somehow another subclass of `Problem` was thrown, and not caught by the above two, it would fall into the third catch block.

* Even though both `Experiment` and `Experiment2` may throw the same exceptions, the difference is in the **declared method signature**:

  * `Experiment` declares that it throws `WeirdProblem` and `TrickyProblem` explicitly.
  * `Experiment2` declares that it throws the superclass `Problem`.

This means:

* The **compiler** treats `Experiment` and `Experiment2` differently in terms of what exceptions must be caught.
* However, at **runtime**, both can throw the same exceptions, and the catch blocks will behave according to the actual object type thrown.

---

#### Summary

* **Inheritance**: `WeirdProblem` and `TrickyProblem` inherit from `Problem`, creating an exception hierarchy.
* **Polymorphism**: `Problem` can refer to any of its subclasses, allowing `Experiment2.perform()` to return different exception types under a single declared type.
* **Catch Blocks**: Use inheritance and polymorphism to catch specific exception types before more general ones, ensuring correct exception handling.


---
---

### b.) Comparison of Reuse Methods: Printer1 vs Printer2

#### **Printer1: Inheritance-based Reuse**

- `Printer1` **extends** `Fancy`, which means it inherits the `decorate()` method directly from the `Fancy` class.
- This is an example of **inheritance** for reuse.
- Since `decorate()` is inherited, `Printer1` is tightly coupled with `Fancy`. If the decoration style in `Fancy` changes, it directly affects `Printer1`.
- Not very flexible — changing the decoration behavior requires modifying the superclass or creating a new subclass.
- **Less modular** and **harder to maintain** when multiple decoration styles are needed.

#### **Printer2: Composition-based Reuse**

- `Printer2` **implements** both `Printer` and `Decorator` interfaces, but instead of inheriting from `Fancy`, it **uses composition**.
- It has a private field `decorator` which is initialized with a `Fancy` object inside the method `generateDecorator()`.
- This is an example of **composition** for reuse — behavior is reused by containing another object rather than inheriting from it.
- Much more flexible: the decorator can be swapped at runtime, and alternative styles can be easily added without changing the structure of `Printer2`.
- **Better encapsulation**, **looser coupling**, and **easier to extend or modify**.

---

### Extending Functionality in Printer3

The task is to implement `Printer3` so that it adds the existing decoration **both before and after the input**, and surrounds the result with `--` at both ends.

#### **Expected output**:
```

\--== main ==--

````

This suggests:
1. Apply the original decoration (e.g., `== main ==`).
2. Add `--` before and after the result.

This is an example of **functional composition**: combining two layers of decoration.

---

### Type of Implementation

- `Printer3` would most appropriately use **composition**, like `Printer2`.
- It would wrap the output of the existing `decorate()` call with the additional `--` characters on each side.

---

### Extension Based on `Decorator` Interface

If the number of `--` characters was not fixed to 2 per side but instead retrieved from a **method defined in the `Decorator` interface**, such as:

```java
int getDecorationLength();
````

Then:

* Each implementation of `Decorator` would define how many `-` characters to use.
* `Printer3` could use this method to generate the correct number of `-` characters dynamically.
* This keeps decoration behavior encapsulated within the `Decorator`.

---

### Extension Based on `Printer` Interface

If the method to determine decoration length was instead part of the `Printer` interface:

* Now the `Printer` implementation must also be concerned with decoration details.
* This would **violate separation of concerns**, since `Printer` would now handle both **printing** and **decoration logic**.
* Less modular and more tightly coupled.

So, it’s **better design** to place the decoration length method in the `Decorator` interface, not in the `Printer` interface.

---

### Summary of Differences

- **Reuse Method**:  
  Printer1 uses inheritance to reuse functionality from the `Fancy` class, while Printer2 uses composition by holding a reference to a `Decorator` object.

- **Flexibility**:  
  Printer1 is less flexible because it is tightly bound to one specific decoration style inherited from its superclass. Printer2 is more flexible, allowing the decoration style to be changed by swapping the `Decorator` instance.

- **Coupling**:  
  Printer1 is tightly coupled to the `Fancy` class, meaning any changes in `Fancy` directly impact Printer1. Printer2 has looser coupling since it interacts with `Decorator` through an interface, promoting better modularity.

- **Maintainability**:  
  Printer1 is harder to maintain and extend due to its reliance on inheritance. Printer2 is easier to maintain and modify because it supports dynamic behavior through object composition.

- **Suitability for Decoration**:  
  Printer2 is better suited for managing and extending decoration logic since it separates concerns and supports multiple decoration strategies more cleanly than Printer1.

