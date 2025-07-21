## Object-Oriented Redesign of Exercise 3

### Objective
The original implementation in `exercise3.java` handles three geometric shapes (triangle, quadrilateral, circle) and computes their area and boundary. However, it is structured in a procedural way that becomes increasingly messy and hard to maintain as more features or shapes are added.

To address this, I designed a solution using **object-oriented programming (OOP)** principles, especially **inheritance** and **polymorphism**, to make the code more modular, extensible, and maintainable.

---

### Object-Oriented Design

### 1. **Abstract Base Class: `Shape`**
We create an abstract class `Shape` that declares common operations such as `getArea()` and `getBoundary()`. This allows us to define a contract that all shape classes must follow.

```java
public abstract class Shape {
    public abstract double getArea();
    public abstract double getBoundary();
}
```

### 2. **Concrete Subclasses**

We create a subclass for each shape and override the required methods:

#### `Triangle`

```java
public class Triangle extends Shape {
    private double a, b, c;

    public Triangle(double a, double b, double c) {
        this.a = a; this.b = b; this.c = c;
    }

    @Override
    public double getArea() {
        double s = (a + b + c) / 2;
        return Math.sqrt(s * (s - a) * (s - b) * (s - c));
    }

    @Override
    public double getBoundary() {
        return a + b + c;
    }
}
```

#### `Quadrilateral`

```java
public class Quadrilateral extends Shape {
    private double a, b, c, d;

    public Quadrilateral(double a, double b, double c, double d) {
        this.a = a; this.b = b; this.c = c; this.d = d;
    }

    @Override
    public double getArea() {
        // Approximate using Brahmagupta's formula (assuming cyclic)
        double s = (a + b + c + d) / 2;
        return Math.sqrt((s - a) * (s - b) * (s - c) * (s - d));
    }

    @Override
    public double getBoundary() {
        return a + b + c + d;
    }
}
```

#### `Circle`

```java
public class Circle extends Shape {
    private double radius;

    public Circle(double radius) {
        this.radius = radius;
    }

    @Override
    public double getArea() {
        return Math.PI * radius * radius;
    }

    @Override
    public double getBoundary() {
        return 2 * Math.PI * radius;
    }
}
```

---

#### Benefits of the New Design

* **Extensibility**: Easily add new shapes (e.g., `Pentagon`, `Ellipse`) by simply extending `Shape`.
* **Reusability**: Common logic like interface implementation can be reused in new shapes.
* **Polymorphism**: We can treat all shapes uniformly using their base class.
* **Maintainability**: Each class has a single responsibility and is easier to test or debug.

---

#### Example Usage

```java
import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        ArrayList<Shape> shapes = new ArrayList<>();
        Scanner scanner = new Scanner(System.in);
        
        System.out.println("Enter the number of shapes:");
        int count = Integer.parseInt(scanner.nextLine());

        for (int i = 0; i < count; i++) {
            System.out.println("Enter shape type (triangle, quadrilateral, circle):");
            String type = scanner.nextLine();

            switch (type.toLowerCase()) {
                case "triangle":
                    System.out.println("Enter three sides:");
                    shapes.add(new Triangle(
                        scanner.nextDouble(), scanner.nextDouble(), scanner.nextDouble()));
                    break;
                case "quadrilateral":
                    System.out.println("Enter four sides:");
                    shapes.add(new Quadrilateral(
                        scanner.nextDouble(), scanner.nextDouble(), scanner.nextDouble(), scanner.nextDouble()));
                    break;
                case "circle":
                    System.out.println("Enter radius:");
                    shapes.add(new Circle(scanner.nextDouble()));
                    break;
                default:
                    System.out.println("Invalid shape.");
            }
            scanner.nextLine();
        }

        for (int i = 0; i < shapes.size(); i++) {
            Shape s = shapes.get(i);
            System.out.printf("Shape %d: Area = %.2f, Boundary = %.2f%n", 
                i + 1, s.getArea(), s.getBoundary());
        }
    }
}
```

---

#### Justification

* By applying **inheritance**, we define a generic interface for all shapes.
* Using **polymorphism**, we can perform operations like `getArea()` without knowing the specific type.
* The design supports **extensibility** for future shapes and operations.
* Encapsulating logic in classes increases **code readability and testability**.

---

#### Future Extensions

* Add interface `Measurable` if more types (e.g., 3D shapes) are considered.
* Add operations like `getDiameter()` or `getAngles()` in specific shape classes.
* Integrate GUI support or data persistence.

---

#### Conclusion

The refactored OOP-based solution meets the goals of modularity, extensibility, and type-safety. It is clean, easier to maintain, and allows the program to grow naturally with future requirements.


