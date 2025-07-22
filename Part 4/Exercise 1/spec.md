## Exercise 1

### a.) Class Identification and Constructs

After analyzing the code in the `exercise1` folder, I identified the following classes and constructs:

1. **Zipper**

   * This is a **public class**.
   * It contains logic related to file handling and compression.
   * It also defines a **protected abstract static inner class** called `Handler`.

2. **Zipper.Handler**

   * This is a **protected abstract static inner class**.
   * It defines an abstract class meant to be extended for handling different file operations.
   * Used to define common behavior without knowing the specific implementation.

3. **TestZipper**

   * This is a **JUnit test class**.
   * It is a **public class** used for **unit testing** the functionality in the `Zipper` class.

4. **Exercise1**

   * This is a **main class** with a `main()` method that demonstrates the use of `Zipper`.
   * Used to **execute and showcase** the functionality implemented.

---

### b.) Explanation of Constructs and Justification

1. **Zipper – Public Class**

   * Chosen to encapsulate all the logic for file zipping and unzipping.
   * Being `public`, it's accessible from other classes.
   * It uses file I/O operations and defines reusable methods.
   * Not an interface because it provides full method implementations, not just definitions.

2. **Zipper.Handler – Protected Abstract Static Inner Class**

   * **Abstract**: Because it defines a contract for file handlers but leaves specific implementation details to subclasses.
   * **Static**: Allows use without needing an instance of the outer `Zipper` class.
   * **Protected**: Limits its usage to within the package or subclasses.
   * **Why Not Interface?** It can have concrete methods and fields; interfaces would not allow full method implementation in older Java versions.

3. **TestZipper – Public Test Class**

   * A test class following **JUnit standards**.
   * Required to test various scenarios of the `Zipper` functionality.
   * It’s a concrete class with test methods, which wouldn't make sense as an abstract class or interface.

4. **Exercise1 – Public Class with main()**

   * Standard practice for demonstration or entry point classes.
   * Chosen because it's meant to be executed directly.
   * Other constructs (like an interface or abstract class) are not appropriate since it’s executable.

---

### c.) Relation of Temporary Directory Lifespan to Class Design

* The **temporary directory** is created and used within a limited scope (e.g., during execution or testing).
* This relates to **resource management** in the classes.
* Specifically, the `Zipper` class handles files within this directory and must ensure proper **creation, use, and cleanup**.
* This concept reinforces the importance of **lifecycle-aware design**:

  * Classes like `Zipper` are built to handle short-lived resources carefully.
  * The use of **try-with-resources** or **finally blocks** is often applied for such cases.

---

### d.) Meaning of `protected abstract static class Handler`

* **protected**: Limits visibility to the package and subclasses. It’s not intended for external use outside its intended context.
* **abstract**: Indicates that `Handler` is a base class and cannot be instantiated directly. It defines abstract methods that must be implemented by subclasses.
* **static**: Allows the class to be used without having an instance of the outer class (`Zipper`). This is valid because:

  * It does **not depend** on an instance of `Zipper`.
  * Static inner classes are common when the inner class is **logically grouped** but **functionally independent** of the outer class’s instance.

**Appropriateness of `static`**:

* Since `Handler` doesn't need to access non-static members of `Zipper`, making it static is **memory efficient** and avoids unnecessary coupling.

