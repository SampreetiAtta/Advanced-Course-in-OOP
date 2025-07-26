## Question 2

### a.) Inheritance and Polymorphism in the Given Scenario

#### **Inheritance**
In the given Java mock-up:

- `AIException` is an **abstract class** that **extends `Exception`**, making it a checked exception.
- `DataProcessingError` and `ModelTrainingError` are concrete subclasses of `AIException`.

This demonstrates the **inheritance hierarchy**:
```

Exception -> AIException -> (DataProcessingError, ModelTrainingError)

````

- Both `DataProcessingError` and `ModelTrainingError` **inherit** properties from `AIException` and indirectly from `Exception`.

#### **Polymorphism**
Polymorphism here is visible in the way exceptions are **thrown** and **caught**:

- In `AIPipeline`, the `run()` method declares two specific exceptions: `DataProcessingError` and `ModelTrainingError`.
- In `AIPipeline2`, the `run()` method declares only the **parent class** `AIException` in the `throws` clause. This is an example of **polymorphism**, where a superclass (`AIException`) is used to refer to any of its subclasses.

This allows flexibility:
```java
AIPipeline2 pipeline2 = new AIPipeline2();
pipeline2.run(); // May throw any subclass of AIException
````

The exception catching in the `try-catch` block also reflects **runtime polymorphism**:

```java
try {
    pipeline1.run();
    pipeline2.run();
}
catch (DataProcessingError dp) {}
catch (ModelTrainingError mt) {}
catch (AIException ae) {}
```

* The Java runtime uses **dynamic dispatch** to select the most specific catch block that matches the thrown exception.
* If an exception of type `DataProcessingError` is thrown, the corresponding `catch (DataProcessingError dp)` block is executed—even though `DataProcessingError` is also an instance of `AIException`.

This is **polymorphic behavior**, because the catch blocks differentiate behavior based on the actual type of the exception object thrown at runtime, even though they may share a common base type (`AIException`).

#### Summary

* **Inheritance** is used to create a hierarchy of exception types (`AIException` as the base, with specific derived classes).
* **Polymorphism** allows the use of a general type (`AIException`) to handle any derived exception, but also allows more specific handling when needed via ordered `catch` blocks.

---
---
---
## Question 2 (b)

### 1. Why does it work / not work?

The given code **does not work correctly** due to a violation of method overriding rules in Java.

#### Explanation:
- In the `CurrencyConverter` class, the method `convert` has the return type `Object`.
- In the subclass `SpecificCurrencyConverter`, the method `convert` has the return type `Double`.

Although `Double` is a subclass of `Object`, **Java requires the return type to be covariant** when overriding a method. Covariant return types must be **compatible**, but the signature must also **match exactly** in method name, parameter list, and return type.

Thus, **this is not a valid override** and will cause a **compile-time error**.

To fix it, either:

* Change the return type in the base class to `Double`, or
* Use method overloading intentionally (with different parameter types), but that changes the behavior.

---

### 2. Advantages and Disadvantages of This Implementation

#### Advantages (conceptually, if corrected)

* **Use of inheritance**: Allows extension of the base class behavior.
* **Encapsulation of logic**: Conversion rules are encapsulated within the method.
* **Switch expression**: The new Java `switch` expression improves readability and conciseness.
* **Customization via subclassing**: Subclasses can override the method to apply different logic (e.g., always apply 1.2 multiplier in `SpecificCurrencyConverter`).

---

#### Disadvantages and Problems

* **Compile-time error**: Return type mismatch breaks overriding, so the subclass won’t compile.
* **Using `Object` as a return type**: Returning `Object` requires type casting in the calling code, which is unsafe and inconvenient.
* **Magic constants**: The conversion rates are hardcoded and unrealistic for a real application (should use a live API or configuration file).
* **Weak type safety**: Using `Object` reduces type safety and can cause runtime errors.
* **Default case is arbitrary**: Returning `amount * 2` as a default makes no sense in real currency conversion and could mislead the user.

---

### Improvements

* Change the return type to `Double` in both classes for type safety.
* Use a `Map<String, Double>` to store conversion rates instead of a hardcoded switch.
* Throw an exception or return an error value for unknown currencies instead of using a default.
* Use `BigDecimal` instead of `double` for precise financial calculations.

---

### Conclusion

The current code is **conceptually on the right track** (using inheritance and polymorphism), but **has a major type mismatch error** that makes it invalid. It also lacks real-world robustness for currency conversion use cases. Fixing the return type and improving the design would make it more practical and safer.

