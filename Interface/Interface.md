# Interfaces in Java, C++, and Python

An interface is a contract describing operations that an implementation
provides. It lets client code depend on a capability instead of a concrete
class.

```text
Payment
 ├── CardPayment
 ├── UpiPayment
 └── CashPayment
```

## 1. Java Interfaces

```java
interface Payment {
    void pay(double amount);
}

class CreditCardPayment implements Payment {
    @Override
    public void pay(double amount) {
        System.out.println("Paid by credit card: " + amount);
    }
}

Payment payment = new CreditCardPayment();
payment.pay(1000);
```

`Payment` is the declared type, so the caller only depends on `pay`. The
runtime object supplies the implementation.

Java supports multiple interface inheritance:

```java
interface Printable {
    void print();
}

interface Exportable {
    void export();
}

class Report implements Printable, Exportable {
    public void print() { System.out.println("Printed"); }
    public void export() { System.out.println("Exported"); }
}
```

### Java interface members

Modern Java interfaces may contain:

```java
interface Logger {
    String PREFIX = "LOG"; // public static final implicitly

    void write(String message); // public abstract implicitly

    default void info(String message) {
        write("INFO: " + message);
    }

    static Logger console() {
        return message -> System.out.println(message);
    }

    private String normalize(String message) {
        return message.trim();
    }
}
```

- Interface fields are implicitly `public static final`; they are constants.
- Abstract interface methods are implicitly `public`; `default`, `static`, and
  private helper methods have their declared visibility.
- `default` methods have an implementation and may be overridden.
- Interface static methods belong to the interface and are called with
  `Logger.console()`, not through an instance.
- Private interface methods are helpers for methods inside that interface and
  cannot be called by implementing classes.

If two interfaces provide the same default method, the class must resolve the
conflict:

```java
interface A {
    default void show() { System.out.println("A"); }
}

interface B {
    default void show() { System.out.println("B"); }
}

class C implements A, B {
    @Override
    public void show() {
        A.super.show();
        B.super.show();
    }
}
```

## 2. C++ Interface-Style Classes

C++ has no dedicated `interface` keyword. The usual idiom is an abstract class
with public pure virtual functions and a virtual destructor:

```cpp
#include <iostream>

class Payment {
public:
    virtual ~Payment() = default;
    virtual void pay(double amount) = 0;
};

class CreditCardPayment final : public Payment {
public:
    void pay(double amount) override {
        std::cout << "Paid by card: " << amount << '\n';
    }
};

int main() {
    CreditCardPayment concrete;
    Payment& payment = concrete;
    payment.pay(1000);
}
```

Unlike a Java interface, a C++ interface-style class can contain data,
constructors, implemented methods, and access control. A deliberately
interface-like C++ class usually keeps only public pure virtual functions and
a virtual destructor.

## 3. Python Interface Alternatives

Python has no Java-style `interface` keyword. Common choices are duck typing,
abstract base classes, and protocols.

### Duck typing

```python
def pay(payment, amount: float) -> None:
    payment.pay(amount)

class Wallet:
    def pay(self, amount: float) -> None:
        print(f"Wallet paid {amount}")

pay(Wallet(), 100)
```

The class need not inherit from a common base; it only needs the required
operation at runtime.

### Abstract Base Class

```python
from abc import ABC, abstractmethod

class Payment(ABC):
    @abstractmethod
    def pay(self, amount: float) -> None:
        ...
```

### Protocol

```python
from typing import Protocol

class PaymentProtocol(Protocol):
    def pay(self, amount: float) -> None:
        ...

class Wallet:
    def pay(self, amount: float) -> None:
        print(f"Wallet paid {amount}")

def checkout(payment: PaymentProtocol) -> None:
    payment.pay(100)

checkout(Wallet()) # Accepted by a compatible static type checker
```

`Protocol` expresses structural subtyping for static type checkers. A class
does not need to explicitly inherit from the protocol. This is often called
static duck typing.

## 4. Comparison

| Feature | Java | C++ | Python |
| :--- | :--- | :--- | :--- |
| Dedicated `interface` keyword | Yes | No | No |
| Interface-like mechanism | `interface` | Abstract class with pure virtual functions | Protocol, ABC, or duck typing |
| Multiple contracts | Multiple interfaces | Multiple inheritance | Multiple protocols/ABC bases |
| Contract checked | Compiler and JVM rules | Compiler rules | Runtime, ABC, or static type checker depending on approach |
| Default implementation | `default` method | Concrete member function | Concrete method, ABC, or protocol body |
| Interface fields | `public static final` constants | Normal static/data members are possible | Class attributes or protocol members |

## 5. Interface vs Abstract Class

| Prefer an interface when | Prefer an abstract class when |
| :--- | :--- |
| The type represents a capability | Types share implementation |
| Unrelated classes can implement it | Types form one conceptual hierarchy |
| Multiple contracts are useful | Shared state or protected helpers are needed |
| You want a small public contract | You need constructors and common fields |

## References

- [Oracle: Interfaces](https://docs.oracle.com/javase/tutorial/java/IandI/createinterface.html)
- [Oracle: Default Methods](https://docs.oracle.com/javase/tutorial/java/IandI/defaultmethods.html)
- [cppreference: Abstract class](https://en.cppreference.com/w/cpp/language/abstract_class)
- [Python: Protocols and structural subtyping](https://typing.python.org/en/latest/reference/protocols.html)
