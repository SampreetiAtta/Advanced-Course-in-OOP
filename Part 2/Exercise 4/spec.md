# Föli Public Transportation Payment Card System Specification

## Overview

This models the operation of Turku's public transportation payment card system in Java. The system allows loading money onto a card, checking the balance, and purchasing tickets with time-based validity. The model focuses on object-oriented design principles, such as encapsulation, class responsibility, and data abstraction.

---

## Classes and Responsibilities

### 1. `Card`

**Responsibilities:**
- Maintain the balance of the card
- Store and manage the current active ticket
- Handle loading of money
- Handle ticket purchases

**Member Variables:**
- `private double balance` — stores balance including cents
- `private Ticket currentTicket` — the current valid ticket (null if none)

**Methods:**
- `public void load(double amount)`  
  Adds the given amount to the card balance. Ignores non-positive values.

- `public double getBalance()`  
  Returns the current balance of the card.

- `public boolean purchaseTicket(TicketType type)`  
  Checks if the current ticket is valid. If not, checks balance, deducts price, and issues a new ticket.

- `public boolean hasValidTicket()`  
  Returns true if a ticket exists and is still valid.

- `public Ticket getCurrentTicket()`  
  Returns the current ticket (can be null).

**Class Invariants:**
- `balance >= 0`
- Only one valid ticket can exist at a time

---

### 2. `Ticket`

**Responsibilities:**
- Represent a time-based ticket (single, day, or monthly)
- Track purchase and expiration time

**Member Variables:**
- `private long purchaseTimeMillis` — when the ticket was bought
- `private long expirationTimeMillis` — when the ticket expires
- `private TicketType type` — the type of ticket

**Methods:**
- `public boolean isValid()`  
  Compares current time with expiration time to determine validity.

- `public TicketType getType()`  
  Returns the ticket type.

- `public long getExpirationTime()`  
  Returns expiration timestamp.

**Class Invariants:**
- `expirationTimeMillis > purchaseTimeMillis`
- Ticket is immutable after creation

---

### 3. `TicketType` (Enum)

**Responsibilities:**
- Define constants for ticket pricing and duration

**Enum Constants:**
- `SINGLE(3.0, 1000*60*60*2)`  
- `DAY(8.0, 1000*60*60*24)`  
- `MONTHLY(55.0, 1000*60*60*24*30)`

**Fields:**
- `private final double price`
- `private final long durationMillis`

**Methods:**
- `public double getPrice()`  
- `public long getDurationMillis()`  

---

## Communication and Data Flow

- A `Card` object receives input (e.g., load money or buy ticket).
- When purchasing a ticket, the card checks if a valid ticket exists using `hasValidTicket()`.
- If not valid, and the balance is sufficient, a `Ticket` is created using the current time and `TicketType.getDurationMillis()`.
- The cost is deducted from the card’s balance.
- Validity checks use `System.currentTimeMillis()` to ensure real-time comparison.
- Ticket validity does not extend with purchases; only one active ticket is allowed.

---

## Justifications and Design Principles

- **Encapsulation**: Fields are private and accessed only through public methods.
- **Single Responsibility**: Each class handles its own logic (Card for balance/tickets, Ticket for timing, TicketType for static data).
- **Enum Usage**: `TicketType` avoids hardcoded values, making the system easy to update.
- **Class Invariants**: Maintain correctness of ticket timing and balance.
- **Modularity**: The system is divided into logical parts, allowing for potential extension (e.g., adding weekly tickets or discounts).
- **Real-time Validity**: Ticket expiration is based on real-time clock, using `System.currentTimeMillis()`.

---

## Notes

- Assumes system clocks are synchronized using NTP.
- System does not support physical terminal interactions.
- Designed purely for simulation or backend logic.

