# Encapsulation & Access Modifiers

This guide explains how Java, C++, and Python keep state with its behavior and
control access to implementation details.

---

## Table of Contents

1. [Core Idea of Encapsulation](#1-core-idea-of-encapsulation)
   - [Encapsulation vs. Data Hiding](#encapsulation-vs-data-hiding)
   - [The Role of Invariants](#the-role-of-invariants)
   - [The "Tell, Don't Ask" Principle & Anemic Domain Models](#the-tell-dont-ask-principle--anemic-domain-models)
2. [Encapsulation in Java](#2-encapsulation-in-java)
3. [Encapsulation in C++](#3-encapsulation-in-c)
4. [Encapsulation in Python](#4-encapsulation-in-python)
5. [C++ `struct` vs `class`](#5-c-struct-vs-class)
6. [Access Modifiers in Java](#6-access-modifiers-in-java)
   - [Key Terms: Class, Subclass, and Package](#key-terms-class-subclass-and-package)
   - [The 4 Access Levels](#the-4-access-levels)
   - [`protected` in a Subclass Outside the Package](#protected-in-a-subclass-outside-the-package)
7. [Access Modifiers in C++](#7-access-modifiers-in-c)
   - [Key Terms: Class, Derived Class, and `friend`](#key-terms-class-derived-class-and-friend)
   - [The 3 Access Levels](#the-3-access-levels)
   - [Inheritance Access: `public`, `protected`, and `private`](#inheritance-access-public-protected-and-private)
8. [Controlled Exceptions and Summary](#8-controlled-exceptions-and-summary)

---

## 1. Core Idea of Encapsulation

**Encapsulation** means keeping data and the methods that use it together, while preventing uncontrolled access to that data.

### Encapsulation vs. Data Hiding

- **Data hiding** means restricting direct access to internal data, usually with `private`.
- **Encapsulation** is the wider idea: the class controls its data and exposes useful operations.

### The Role of Invariants

An **invariant** is a rule that must always remain true for an object.

- A bank account balance must not become negative.
- A date must represent a real date.

If client code can reach inside and do:

```cpp
using namespace std;

account.balance = -100000; // Directly bypassing checks!
```

the invariant is broken. Encapsulation lets the class validate changes before accepting them.

### The "Tell, Don't Ask" Principle & Anemic Domain Models

```java
account.setBalance(account.getBalance() - 500);
```

Prefer telling the object what operation to perform:

```java
account.withdraw(500);
```

---

## 2. Encapsulation in Java

Java checks member access at compile time and also enforces it when the program runs.

### Java Implementation & Invariant Enforcement

```java
package com.banking.domain;

public class BankAccount {
    // 1. Private fields: Complete physical data hiding
    private final String accountNumber;
    private double balance;
    private boolean isFrozen;

    // 2. Constructor enforces initialization invariants
    public BankAccount(String accountNumber, double initialDeposit) {
        if (accountNumber == null || accountNumber.trim().isEmpty()) {
            throw new IllegalArgumentException("Account number cannot be null or blank.");
        }
        if (initialDeposit < 0.0) {
            throw new IllegalArgumentException("Initial deposit cannot be negative. Provided: " + initialDeposit);
        }
        this.accountNumber = accountNumber;
        this.balance = initialDeposit;
        this.isFrozen = false;
    }

    // 3. Mutator methods enforce state transition invariants
    public void deposit(double amount) {
        ensureAccountActive();
        if (amount <= 0.0) {
            throw new IllegalArgumentException("Deposit amount must be strictly positive. Provided: " + amount);
        }
        this.balance += amount;
    }

    public void withdraw(double amount) {
        ensureAccountActive();
        if (amount <= 0.0) {
            throw new IllegalArgumentException("Withdrawal amount must be strictly positive. Provided: " + amount);
        }
        if (amount > this.balance) {
            throw new IllegalStateException(
                String.format("Insufficient funds. Requested: %.2f, Available: %.2f", amount, this.balance)
            );
        }
        this.balance -= amount;
    }

    // 4. Safe Read-Only Access
    public double getBalance() {
        return this.balance;
    }

    public String getAccountNumber() {
        return this.accountNumber;
    }

    public boolean isFrozen() {
        return this.isFrozen;
    }

    // 5. Internal private helper: Encapsulating validation logic
    private void ensureAccountActive() {
        if (this.isFrozen) {
            throw new IllegalStateException("Account " + accountNumber + " is frozen. Transactions prohibited.");
        }
    }
}
```

### Mutable Objects and Defensive Copies

Do not expose a mutable internal object directly. For arrays, collections, or older mutable types such as `Date`, copy the value when storing it and when returning it:

```java
private final Date dateOfBirth;

public UserProfile(Date dateOfBirth) {
    this.dateOfBirth = new Date(dateOfBirth.getTime());
}

public Date getDateOfBirth() {
    return new Date(dateOfBirth.getTime());
}
```

For simple immutable data, Java `record` is a concise option:

```java
public record CurrencyAmount(double amount, String currency) {
    public CurrencyAmount {
        if (amount < 0) throw new IllegalArgumentException();
    }
}
```

---

## 3. Encapsulation in C++

C++ checks access rules at compile time.

### C++ Implementation & Invariant Enforcement

```cpp
#include <iostream>
#include <string>
#include <stdexcept>

using namespace std;

class BankAccount {
private:
    // Attributes hidden behind private boundary
    string accountNumber;
    double balance;
    bool isFrozen;

    // Private helper function
    void ensureActive() const {
        if (isFrozen) {
            throw logic_error("Account is frozen. Transactions prohibited.");
        }
    }

public:
    // Constructor initializes invariants
    BankAccount(string accNum, double initialBalance)
        : accountNumber(move(accNum)), balance(initialBalance), isFrozen(false) {
        if (accountNumber.empty()) {
            throw invalid_argument("Account number cannot be empty.");
        }
        if (balance < 0.0) {
            throw invalid_argument("Initial balance cannot be negative.");
        }
    }

    // Mutating member functions (cannot be const)
    void deposit(double amount) {
        ensureActive();
        if (amount <= 0.0) {
            throw invalid_argument("Deposit amount must be strictly positive.");
        }
        balance += amount;
    }

    void withdraw(double amount) {
        ensureActive();
        if (amount <= 0.0) {
            throw invalid_argument("Withdrawal amount must be strictly positive.");
        }
        if (amount > balance) {
            throw runtime_error("Insufficient funds for withdrawal.");
        }
        balance -= amount;
    }

    // Const member functions: Encapsulation promises read-only access
    double getBalance() const {
        return balance;
    }

    const string& getAccountNumber() const {
        return accountNumber;
    }

    bool getIsFrozen() const {
        return isFrozen;
    }
};
```

---

### `const` Member Functions

Put `const` after a member function when it should not change the object:

```cpp
using namespace std;

double getBalance() const;
```

```cpp
void printAccountSummary(const BankAccount& account) {
    cout << "Balance: " << account.getBalance() << "\n";
}
```

Returning a non-const reference to a private member allows outside code to change it. Return small values by value and larger read-only objects by `const&`.

---

## 4. Encapsulation in Python

Python does not have Java-style `private` enforcement. By convention:

- `name` is public.
- `_name` means internal or protected-by-convention.
- `__name` uses name mangling to reduce accidental access; it is not absolute
  security.

Use methods and properties to validate state transitions:

```python
class BankAccount:
    def __init__(self, initial_balance: float = 0.0) -> None:
        if initial_balance < 0:
            raise ValueError("Initial balance cannot be negative")
        self._balance = initial_balance

    @property
    def balance(self) -> float:
        return self._balance

    def deposit(self, amount: float) -> None:
        if amount <= 0:
            raise ValueError("Deposit must be positive")
        self._balance += amount

    def withdraw(self, amount: float) -> None:
        if amount <= 0 or amount > self._balance:
            raise ValueError("Invalid withdrawal")
        self._balance -= amount

account = BankAccount(100)
account.deposit(50)
account.withdraw(25)
print(account.balance)  # 125
# account._balance is accessible, but changing it directly breaks the convention.
```

Python properties provide a clean public interface while keeping validation in
the class. For stronger boundaries, use a separate module API, immutable
objects, or a language with enforced access control.

---

## 5. C++ `struct` vs `class`

A common misconception among beginner and intermediate programmers is that `struct` in C++ is just like a C struct (only holding data without methods or constructors).

### The Real Differences: Defaults Only

In C++, a `struct` is **identical to a `class` in every capability**. A `struct` can have:

- Constructors, Destructors, Virtual Functions
- Private, Protected, and Public sections
- Inheritance, Templates, Operator Overloads

There are **only two syntactic differences** defined by the ISO C++ standard:

| Feature                            | `class`               | `struct`             |
| :--------------------------------- | :-------------------- | :------------------- |
| **Default Member Access**          | `private`             | `public`             |
| **Default Base Class Inheritance** | `private` inheritance | `public` inheritance |

```cpp
using namespace std;

// 1. Member Access Default:
class ClassExample {
    int x; // PRIVATE by default
};

struct StructExample {
    int x; // PUBLIC by default
};

// 2. Inheritance Default:
class Base {};

class DerivedClass : Base {};  // Inherits Base PRIVATELY by default
struct DerivedStruct : Base {}; // Inherits Base PUBLICLY by default
```

Everything else is identical:

```cpp
using namespace std;

// Valid and legal C++:
struct AdvancedStruct {
private:
    int hiddenVal;

public:
    AdvancedStruct(int v) : hiddenVal(v) {}
    virtual ~AdvancedStruct() = default;

    virtual void compute() {
        cout << "Struct computing: " << hiddenVal << "\n";
    }
};
```

---

### Idiomatic Conventions: When to Use `struct` vs `class`

1. Use `struct` for simple public data such as coordinates or configuration values.
2. Use `class` when the type must protect data or enforce rules.

---

## 6. Access Modifiers in Java

Access modifiers are keywords that control who can use a class member (a field, method, or constructor). They are Java's main tool for protecting internal state.

### Key Terms: Class, Subclass, and Package

- **Class**: A blueprint containing data and methods, such as `Animal`.
- **Subclass**: A class that extends another class. In `class Dog extends Animal`, `Dog` is the subclass and `Animal` is the superclass. A subclass inherits accessible behavior from its superclass.
- **Package**: A named group of related classes. Two classes are in the same package only when they declare the same `package` name. A subpackage is still a different package.

### The 4 Access Levels

| Modifier | Same class | Same package | Subclass in another package | Any other class |
| :--- | :---: | :---: | :---: | :---: |
| `public` | Yes | Yes | Yes | Yes |
| `protected` | Yes | Yes | Yes, with a restriction | No |
| _default_ (no modifier) | Yes | Yes | No | No |
| `private` | Yes | No | No | No |

### How the Modifiers Work

```java
package banking;

public class BankAccount {
    private double balance;       // Only BankAccount
    protected String accountType; // BankAccount and permitted subclasses
    String branchCode;            // Same package only

    public double getBalance() {  // Any class
        return balance;
    }
}
```

- **`private`**: Only the declaring class can use it. Use it for state that must be controlled by the class.
- **Default access**: Only classes in the same package can use it. This is useful for package-internal helpers.
- **`protected`**: The class and its subclasses can use it. Classes in the same package can also use it.
- **`public`**: Any class can use it. Use it for the external API.

The usual design is to keep fields `private` and expose safe `public` methods instead of allowing direct changes.

### `protected` in a Subclass Outside the Package

When a subclass is in another package, it can use a protected member through itself or through an object whose type is that subclass. It cannot use the member through an unrelated superclass reference.

```java
// packageA/Parent.java
package packageA;

public class Parent {
    protected int value = 42;
}
```

```java
// packageB/Child.java
package packageB;

import packageA.Parent;

public class Child extends Parent {
    public void showValue() {
        System.out.println(value);        // Legal: this.value
        Child child = new Child();
        System.out.println(child.value);  // Legal: Child reference

        Parent parent = new Parent();
        // System.out.println(parent.value); // Compile error
    }
}
```

This restriction stops a subclass in another package from inspecting or changing every unrelated `Parent` object.

### Practical Java Guidance

1. Start with `private` for fields and helper methods.
2. Add `public` only for the class's external API.
3. Use default access for details shared inside one package.
4. Use `protected` carefully; a protected method is often safer than a protected field.

---

## 7. Access Modifiers in C++

Access specifiers are labels inside a C++ class that control who can use its members. C++ has three access levels and no Java-style package access.

### Key Terms: Class, Derived Class, and `friend`

- **Class**: A type containing data and functions, such as `Animal`.
- **Derived class**: A class that inherits from another class. In `class Dog : public Animal`, `Dog` is the derived class and `Animal` is the base class. “Derived class” is the C++ term commonly used for Java's “subclass.”
- **`friend`**: A specific class or function that the class explicitly allows to access its `private` and `protected` members.
- **Package**: C++ has no package boundary. Namespaces organize names, but they do not automatically grant member access.

### The 3 Access Levels

| Specifier | Same class | Derived class | Unrelated code |
| :--- | :---: | :---: | :---: |
| `public` | Yes | Yes | Yes |
| `protected` | Yes | Yes | No |
| `private` | Yes | No | No |

`friend` code is an explicit exception: it can access private and protected members when the class grants that permission.

### How the Specifiers Work

```cpp
class BankAccount {
private:
    double balance;          // Only BankAccount and its friends

protected:
    void recordTransaction(); // BankAccount and derived classes

public:
    double getBalance() const; // Any code
};
```

- **`private`**: Use it for state and helpers that must be controlled by the class.
- **`protected`**: Use it when derived classes need a controlled extension point.
- **`public`**: Use it for the operations that form the class's external API.

Unlike Java, the default also depends on the type declaration: members are `private` by default in a `class` and `public` by default in a `struct`.

### Inheritance Access: `public`, `protected`, and `private`

C++ also lets you choose how a base class is inherited:

```cpp
class Derived : public Base {};
class ProtectedDerived : protected Base {};
class PrivateDerived : private Base {};
```

This changes how the base class's public and protected members appear through the derived class. Base-class private members are never directly accessible in the derived class.

| Member in base | `public` inheritance | `protected` inheritance | `private` inheritance |
| :--- | :--- | :--- | :--- |
| `public` | `public` | `protected` | `private` |
| `protected` | `protected` | `protected` | `private` |
| `private` | Inaccessible | Inaccessible | Inaccessible |

#### Public Inheritance: an “is-a” Relationship

Use public inheritance when every derived object can be used as a base object:

```cpp
class Dog : public Animal {
    // Dog is an Animal
};
```

#### Private Inheritance: an “implemented-in-terms-of” Relationship

Private inheritance hides the base class's public interface from outside code while allowing internal reuse:

```cpp
class Engine {
public:
    void startPistons() {}
};

class Car : private Engine {
public:
    void drive() {
        startPistons(); // Legal inside Car
    }
};

Car car;
// car.startPistons(); // Compile error: hidden by private inheritance
```

### Practical C++ Guidance

1. Keep data `private` and expose safe public functions.
2. Use `protected` only for a clear derived-class extension point.
3. Prefer public inheritance for genuine “is-a” relationships.
4. Use private inheritance only when implementation reuse is intentional; composition is often clearer.

---

## 8. Controlled Exceptions and Summary

### C++ `friend` Classes and Functions

`friend` gives one named class or function access to private and protected members. Use it sparingly, usually for a closely related helper or stream operator.

```cpp
class Vector {
private:
    int value;
    friend void print(const Vector&);
};

void print(const Vector& vector) {
    // Can access vector.value because print is a friend.
}
```

Friendship is not automatically inherited or shared with other friends.

### Summary and Best Practices

Encapsulation follows the same basic goal in both languages: keep data protected and allow changes only through controlled operations.

#### Common Principles

| Principle | Recommended practice |
| :--- | :--- |
| **Hide state** | Keep fields and data members `private`. |
| **Expose behavior** | Provide methods for valid operations, such as `deposit()` and `withdraw()`. |
| **Validate changes** | Check values in constructors and methods before changing the object. |
| **Avoid leaking internals** | Do not return mutable internal data directly. |

#### Java

| Topic | Best practice |
| :--- | :--- |
| Read-only data | Use immutable values or defensive copies. |
| Inheritance | Use `extends` and choose `protected` carefully. |
| Simple data objects | Use a `record` when the data should be immutable. |
| Package-level helpers | Use default access for details shared only within a package. |

#### C++

| Topic | Best practice |
| :--- | :--- |
| Read-only functions | Mark functions that do not change the object with `const`. |
| Inheritance | Use public inheritance for a genuine “is-a” relationship. |
| Simple data objects | Use a `struct` for simple public data. |
| Internal implementation | Prefer composition or private implementation details when inheritance is not an “is-a” relationship. |
