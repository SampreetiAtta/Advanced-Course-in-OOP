
## Question 4 

In this task, I have designed a structured and extensible program using object-oriented programming (OOP) principles. The main objective is to simulate a battle in a text-based RPG by incorporating combatant stats, special abilities, and environmental effects. The program is written in a way that promotes scalability and reusability for future upgrades.

---

### Main Objectives

- Simulate a battle between various combatants (e.g., Warrior, Mage, Archer)
- Take into account attributes: health, attack power, defense, speed
- Apply special abilities and environmental effects (e.g., terrain and time of day)
- Output final health status and winner/draw result
- Support extension (new classes, features, or rules)

---

### Key Classes and Responsibilities

#### 1. **Combatant (Abstract Class)**

**Purpose**:
Represents any general character (combatant) in the game. It provides common attributes and behavior like health, attack, and basic actions such as taking damage.

**Main Methods**:
- `useSpecialAbility(Environment env)`: To be overridden by subclasses to define their unique abilities.
- `isAlive()`: Returns true if health > 0.
- `takeDamage(int damage)`: Reduces health when hit.
- `getHealth(), getName()`: Accessor methods.


```java
public abstract class Combatant {
    protected String name;
    protected int health;
    protected int attack;
    protected int defense;
    protected int speed;

    public Combatant(String name, int health, int attack, int defense, int speed) {
        this.name = name;
        this.health = health;
        this.attack = attack;
        this.defense = defense;
        this.speed = speed;
    }

    public abstract int useSpecialAbility(Environment env);

    public boolean isAlive() {
        return health > 0;
    }

    public void takeDamage(int damage) {
        health -= Math.max(damage, 0);
    }

    public String getName() {
        return name;
    }

    public int getHealth() {
        return health;
    }
}
````

---

#### 2. **Subclasses of Combatant**

**Purpose**:
These classes implement specific behavior and abilities unique to each character type.

Each subclass **overrides `useSpecialAbility(Environment env)`** to define its unique ability.

#### a. **Warrior**

High defense; special ability adds fixed bonus to attack.
```java
public class Warrior extends Combatant {
    public Warrior(String name, int health, int attack, int defense, int speed) {
        super(name, health, attack, defense, speed);
    }

    @Override
    public int useSpecialAbility(Environment env) {
        return attack + 5; // Flat bonus damage
    }
}
```

#### b. **Archer**

Gains bonus in forest terrain and loses power at night.
```java
public class Archer extends Combatant {
    public Archer(String name, int health, int attack, int defense, int speed) {
        super(name, health, attack, defense, speed);
    }

    @Override
    public int useSpecialAbility(Environment env) {
        int modifiedAttack = attack;
        if (env.getTerrain().equals("forest")) {
            modifiedAttack += 4;
        }
        if (env.getTimeOfDay().equals("night")) {
            modifiedAttack -= 2;
        }
        return modifiedAttack;
    }
}
```

#### c. **Mage**

Adds a percentage-based magic burst to attack.
```java
public class Mage extends Combatant {
    public Mage(String name, int health, int attack, int defense, int speed) {
        super(name, health, attack, defense, speed);
    }

    @Override
    public int useSpecialAbility(Environment env) {
        return attack + (int)(0.3 * attack); // 30% magic burst
    }
}
```

---

#### 3. **Environment**

**Purpose**:
Encapsulates environmental conditions that affect the battle such as terrain and time of day.

**Main Methods**:
- `getTerrain()`: Returns the terrain type.
- `getTimeOfDay()`: Returns the current time (e.g., "day", "night").

This class is passed to each combatant to allow dynamic ability effects.

```java
public class Environment {
    private String terrain;
    private String timeOfDay;

    public Environment(String terrain, String timeOfDay) {
        this.terrain = terrain;
        this.timeOfDay = timeOfDay;
    }

    public String getTerrain() {
        return terrain;
    }

    public String getTimeOfDay() {
        return timeOfDay;
    }
}
```

---

#### 4. **Battle**

**Purpose**:
Controls the entire combat process. Determines who attacks first (based on speed), applies damage, and prints the battle outcome.

**Main Methods**:
- `fight(Combatant c1, Combatant c2, Environment env)`: Simulates the fight between two combatants.
- `attackTurn(Combatant attacker, Combatant defender, Environment env)`: Performs one attack action in a turn.
- `printResult(Combatant c1, Combatant c2)`: Displays final health and winner/draw.

```java
public class Battle {
    public static void fight(Combatant c1, Combatant c2, Environment env) {
        Combatant first = (c1.speed >= c2.speed) ? c1 : c2;
        Combatant second = (first == c1) ? c2 : c1;

        while (c1.isAlive() && c2.isAlive()) {
            attackTurn(first, second, env);
            if (second.isAlive()) {
                attackTurn(second, first, env);
            }
        }

        printResult(c1, c2);
    }

    private static void attackTurn(Combatant attacker, Combatant defender, Environment env) {
        int rawDamage = attacker.useSpecialAbility(env) - defender.defense;
        defender.takeDamage(rawDamage);
        System.out.println(attacker.getName() + " hits " + defender.getName() + " for " + Math.max(rawDamage, 0));
    }

    private static void printResult(Combatant c1, Combatant c2) {
        System.out.println("\nBattle Outcome:");
        System.out.println(c1.getName() + " - Health: " + c1.getHealth());
        System.out.println(c2.getName() + " - Health: " + c2.getHealth());

        if (!c1.isAlive() && !c2.isAlive()) {
            System.out.println("The battle ends in a draw.");
        } else if (c1.isAlive()) {
            System.out.println(c1.getName() + " wins!");
        } else {
            System.out.println(c2.getName() + " wins!");
        }
    }
}
```

---

#### 5. **Main Class**

**Purpose**:
Serves as the entry point of the application. Gathers input and starts the battle.

**Main Method**:
- `main(String[] args)`: Creates combatants and environment, and calls the `Battle.fight()` method.

```java
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        // Sample hardcoded example for simplicity
        Combatant player1 = new Warrior("Thor", 100, 20, 10, 8);
        Combatant player2 = new Archer("Legolas", 90, 18, 8, 10);
        Environment env = new Environment("forest", "night");

        Battle.fight(player1, player2, env);
    }
}
```

---

### Special Cases Addressed

* **Special Abilities:** Each class implements its ability through `useSpecialAbility(Environment env)`.
* **Environmental Effects:** Terrain and time of day influence combat (e.g., Archer benefits or penalties).
* **Draws:** If both combatants die in the same round.
* **Speed Tiebreaker:** If speed is equal, default to the first player created.
* **Damage below zero:** Ensured with `Math.max(damage, 0)`.

---

### Future Extensions

* Add more combatant classes (e.g., Healer, Assassin)
* Extend battle logic to **team vs. team**
* Add spells, healing, or evasion mechanics
* Implement GUI or web interface
* Save/load combatants and replays

---

### Summary

This specification defines a flexible and object-oriented Java program to simulate RPG battles. Using abstract classes, polymorphism, and environmental interaction, the design is both modular and extendable for future features such as new character types or combat formats.


