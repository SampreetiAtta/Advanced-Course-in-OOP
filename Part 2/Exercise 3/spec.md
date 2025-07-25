
## Exercise 3 – Mortgage Installment Calculator


### Objective 
To design a Java-based command-line program that calculates a customer's **monthly mortgage installment** based on user-provided inputs: 
- **Principal amount** (loan amount)
- **Loan term** in months (valid range: 0–300)

The program will apply the formula: 

monthly_installment = principal / loan_term + principal / 240

---

### Program Structure Using Routines

Since the program may be extended into a web version, this prototype should follow a **modular and reusable structure**. Routine abstraction helps in separating different concerns such as input handling, validation, computation, and output display.

#### Method Specifications

1. #### `public static double calculateMonthlyInstallment(double principal, int loanTerm)`
 *  **Purpose:** Computes the monthly installment using the given formula. 
* **Input:** 
	- `principal`: loan amount (must be > 0)
	- `loanTerm`: duration in months (must be in [1, 300])
*  **Output:** 
	- Returns the monthly installment as a `double` 
*	**Behavior:**
	- Throws `IllegalArgumentException` if loanTerm is zero or out of range.

2. #### `public static double readPrincipal(Scanner sc)`
   **Purpose:** Reads and validates the principal amount from the user. 
   **Behavior:** Ensures the principal is positive. Prompts again if invalid.

3. #### `public static int readLoanTerm(Scanner sc)`
   **Purpose:** Reads and validates the loan term from the user.
   
   **Behavior:** Ensures the loan term is within the range [1, 300]. Prompts again if invalid.

4. #### `public static void displayResult(double installment)`
   **Purpose:** Nicely formats and prints the result to the console.

5. #### `public static void main(String[] args)`
   **Purpose:** Entry point. Coordinates input, processing, and output.

---

### Special Conditions to Handle
- **Zero loan term** → Invalid: prompt the user again 
- **Negative or zero principal** → Invalid: prompt the user again 
- **Loan term > 300 months** → Invalid: prompt the user again 
- **Valid inputs** → Display result rounded to two decimal places

---

### Optional: Object-Oriented Modeling (if extended)

If we consider applying object-oriented design (especially for future web conversion), the following class-based model would be ideal:

#### Class: `MortgageCalculator`
- **Fields**: `double principal`, `int loanTerm`
- **Constructor**: Validates and initializes the fields
- **Method**: `double computeInstallment()`
- **Static utility methods** for validation and reading user input

This approach improves encapsulation and makes future refactoring (e.g., for web service) easier.

---

### Summary

The program is designed using routine-based abstraction with clear separation of input, validation, computation, and output. It handles edge cases and lays a foundation for further extension toward object-oriented design. While the current formula is simplistic, the modular design allows easy replacement with a more realistic Euribor-based annuity formula in the future.

