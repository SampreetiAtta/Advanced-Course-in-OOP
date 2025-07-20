## a.) What form of inheritance is involved in the following case?

#### Answer:
The form of inheritance used in this case is **single inheritance**.

---

#### Explanation:

In Java, **inheritance** allows a class (called the *subclass* or *child class*) to inherit the properties and methods of another class (called the *superclass* or *parent class*). This mechanism promotes code reuse and establishes a hierarchical relationship between classes.

In the given example:

```java
abstract class CommandLineApp {
    String ask(String prompt) {
        System.out.print(prompt + ": ");
        return new java.util.Scanner(System.in).next();
    }
}

class LoginScreen extends CommandLineApp {
    void lock() {
        while(true) {
            var id = ask("Username");
            var pw = ask("Passoword");

            if (id.equals("root") && pw.equals("root"))
                return;

            try {
                Thread.sleep(1000);
            }
            catch(Exception e) {}
        }
    }
}
````

* `LoginScreen` **extends** the abstract class `CommandLineApp`.
* This means `LoginScreen` **inherits** the `ask(String prompt)` method from `CommandLineApp`.
* Since it inherits from only **one** superclass (`CommandLineApp`), the inheritance is **single inheritance**.
* The `ask()` method from the superclass is used directly in the `LoginScreen` class without being overridden, which demonstrates **code reuse** via inheritance.

---

#### We know from Inheritance and Polymorphism

* **Single Inheritance**: A subclass inherits from one superclass. This is the most common form of inheritance in Java.
* **Polymorphism**: The ability of an object to take many forms. It allows one interface to be used for a general class of actions. In this case, polymorphism isn’t explicitly used (e.g., no method overriding or interface usage), but the structure supports it. If `ask()` were overridden in a subclass, the JVM would use **dynamic dispatch** to determine which version to call at runtime.

---

#### Summary

The code demonstrates **single inheritance**, where `LoginScreen` inherits from the abstract class `CommandLineApp`. This allows `LoginScreen` to reuse the `ask()` method and maintain clean, modular code. Although polymorphism is not directly applied here, the design is compatible with it.

---
---
## b.) Comment on and evaluate the following code...

#### Does it compile and work?

1. **`NaturalResource` interface**
   - Declares two methods:
     - `float amountLeft()`
     - `void spend(float amount)`
   - Has JML-like contract comments (`@.pre`, `@.post`), but these are not enforced at compile time in Java.

2. **`Coal` class**
   - Implements `NaturalResource` correctly.
   - Tracks resource with `private float left`.
   - `spend(amount)` simply decrements `left`- works as expected.

3. **`Hydroelectric` class**
   - Declares `amountLeft()` publicly.
   - But its `spend(float amount)` is **private**, not `public` so it does **not** override the interface method.
   - **Result**: `Hydroelectric` fails to implement the interface correctly and will not compile.

#### Why it doesn’t work

- In Java, implementing an interface means providing a **public** definition for every method declared in the interface.
- Here:
  ```java
  private void spend(float amount) throws Exception { … }
  ```

* Marked `private` is not visible as the interface’s `public` method.
* The signature also includes `throws Exception`, which doesn’t match the interface's `void spend(float amount)` (no declared throws).
* Therefore, the class is missing a valid `public void spend(float)` implementation and fails compilation.
---

### Theoretical Background

#### Inheritance

* In Java, interfaces define a contract that implementing classes must follow.
* The interface here allows different types of natural resources to be handled through **a common type**, enabling substitution and reuse.

#### Polymorphism

* With polymorphism, we can write code that operates on `NaturalResource` without knowing the specific subclass (`Coal` or `Hydroelectric`).

* Example:

  ```java
  NaturalResource r = new Coal(100);
  r.spend(10);
  System.out.println(r.amountLeft());
  ```

* However, because `Hydroelectric` doesn't properly implement the interface, polymorphism **breaks**:

  ```java
  NaturalResource r = new Hydroelectric();
  ```

---

### Benefits

* **Good separation of concerns**: Different resources encapsulate their own logic.
* **Interface design allows for extensibility**: More resource types can be added.
* **Polymorphism enables code reuse**: A single function could process any resource implementing the interface.

---

### Drawbacks

* **Incorrect access modifier in `Hydroelectric`** violates interface contract.
* **Throws exception in `spend()`** might not be ideal; design could instead use a no-op or a custom implementation that aligns with the interface.
* **Pre/post conditions are commented but not enforced**: These would need to be implemented with runtime checks or assertions.

---

### Improvements

To correct the issue in `Hydroelectric`, we should either:

1. **Implement the method publicly** and decide on behavior:

   ```java
   @Override
   public void spend(float amount) {
       // Do nothing or log info
   }
   ```
2. **Throw a runtime exception with proper access**:

   ```java
   @Override
   public void spend(float amount) {
       throw new UnsupportedOperationException("Hydroelectric resource is limitless!");
   }
   ```

---

### Conclusion

The idea of modeling natural resources with a shared interface is sound and supports good object-oriented principles like **inheritance** and **polymorphism**. However, the current implementation fails due to improper method visibility in one class. By correcting this, the design can fully utilize the benefits of polymorphism and interface abstraction.

---
---
## c.) Code Review: `RandomGenerator` and `RandomIntegerGenerator`

---

#### Does the Code Work?

**Yes**, the code **compiles and runs** correctly in modern Java versions (Java 5 and above). This is because Java allows **covariant return types**, meaning a subclass can override a method and return a more specific type than its superclass.

---

#### Why It Works

In the given code:

```java
class RandomGenerator {
    Object generate() {
        var random = new java.util.Random();
        return switch (random.nextInt(4)) {
            case 1 -> 1;
            case 2 -> 2;
            case 3 -> "three";
            default -> new Object();
        };
    }
}
````

The `generate()` method returns an `Object`, which could be an `Integer`, a `String`, or a plain `Object`.

Now in the subclass:

```java
class RandomIntegerGenerator extends RandomGenerator {
    @Override
    Integer generate() {
        return new java.util.Random().nextInt(64);
    }
}
```

The method overrides the `generate()` method but narrows the return type to `Integer`. Since `Integer` is a subtype of `Object`, this is valid under Java’s rules for **covariant return types**.

So, from a compilation and runtime perspective, the code is valid.

---

#### Evaluation: Benefits and Drawbacks

**Benefits:**

* The use of **covariant return types** allows the subclass `RandomIntegerGenerator` to return a more specific result type (`Integer`), which makes the subclass method more useful in a context where an integer is expected.
* The base class `RandomGenerator` encapsulates randomized behavior that returns various object types, making it flexible for generic use.
* The subclass version of `generate()` is cleaner and type-safe it always returns an `Integer`, removing the need for casting when used directly.
* The code uses a modern Java feature: the **switch expression**, which simplifies the random selection logic.

**Drawbacks:**

* The `generate()` method in `RandomGenerator` returns inconsistent types (`Integer`, `String`, and `Object`), which can be confusing and unsafe. Any code using it must perform type checks or casts.
* While the subclass overrides the method with a more specific return type, polymorphism is weakened. If we have a `RandomGenerator` reference to a `RandomIntegerGenerator` object, calling `generate()` will still return `Object`, not necessarily an `Integer`, unless explicitly cast.
* The design violates the principle of **substitution** in inheritance. The subclass is supposed to behave like its superclass, but here the return types are not really interchangeable in practice.
* There is potential for **ClassCastException** if client code makes assumptions about what type is returned by `generate()` without proper checks.
* Overall, the structure is not very type-safe or clean for real-world applications.

---

#### Improvements

1. **Use Generics** to enforce type consistency:

   ```java
   class RandomGenerator<T> {
       T generate() { ... }
   }

   class RandomIntegerGenerator extends RandomGenerator<Integer> {
       @Override
       Integer generate() {
           return new java.util.Random().nextInt(64);
       }
   }
   ```

   This makes the return types predictable and type-safe.

2. **Avoid Mixing Return Types** in the base class. If the idea is to return different kinds of values, consider a common interface or wrapper object.

3. **Use Interfaces**:

   ```java
   interface Generator<T> {
       T generate();
   }
   ```

   Then each generator class can implement the interface according to the type of object it generates.

---

#### Background Theory

* **Inheritance** allows a subclass to inherit and optionally override the behavior of a superclass.
* **Polymorphism** means that we can treat objects of different subclasses as if they are objects of the same superclass or interface, enabling flexible and reusable code.
* **Covariant return types** (introduced in Java 5) allow an overridden method to return a more specific type than the one declared in the superclass.

However, misuse of these features, like inconsistent return types in the superclass, can make code harder to maintain and error-prone.

---

#### Summary

The code is valid Java and compiles due to covariant return types. However, while it demonstrates some useful object-oriented features, the design lacks type safety and clarity. A better approach would be to use generics or interfaces to define a more predictable and type-safe structure.

---
---