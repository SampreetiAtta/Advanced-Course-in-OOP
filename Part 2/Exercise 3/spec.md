```markdown
# Exercise 3: Mortgage Installment Calculator Specification

## Overview

This program calculates the monthly installment for a mortgage loan based on two inputs from the user:

- Principal (loan amount)
- Loan term in months (allowed range: 1–300)

Formula used:

```

monthly\_installment = principal / loan\_term + principal / 240

```

## Method Specifications

### main(String[] args)
- Entry point of the program.
- Calls input methods to read principal and loan term.
- Calls calculation method.
- Prints the result.

### readPrincipal()
- Reads and returns the principal amount as a double.
- Ensures the value is positive.
- Re-prompts if the input is invalid.

### readLoanTerm()
- Reads and returns the loan term as an int.
- Ensures the value is between 1 and 300.
- Re-prompts if the input is invalid.

### calculateMonthlyInstallment(double principal, int loanTerm)
- Calculates and returns the monthly installment using the formula.
- Returns result as a double.

## Special Cases
- Loan term cannot be 0 (to avoid division by zero).
- Handles invalid inputs (non-numeric, negative, or out of range) with re-prompting.
```

