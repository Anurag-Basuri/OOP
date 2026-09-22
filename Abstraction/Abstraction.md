# Data Abstraction in Java, C++, and Python

## 1. Meaning of Abstraction

**Abstraction** means exposing the operations that a user needs while hiding
implementation details that the user does not need to know.

```text
Car
 └── start()
```

The caller uses:

```text
car.start()
```

The caller does not need to understand fuel injection, spark timing, the ECU,
or transmission control. Those details are implementation details.

### A precise definition

Abstraction defines **what an object promises to do** while hiding **how it
does it**. It reduces the amount of information a caller must understand and
helps keep code loosely coupled.

Abstraction is not the same as encapsulation:

| Concept | Main question | Example |
| :--- | :--- | :--- |
| Abstraction | What should be exposed? | `payment.pay(amount)` |
| Encapsulation | How should state and implementation be protected? | `private balance` with a method to update it |

Abstract classes and interfaces are language mechanisms for expressing
abstraction. Encapsulation is commonly implemented with access modifiers and
controlled methods.

---

## 2. Java Abstract Classes

An abstract class can contain abstract methods, concrete methods, fields,
constructors, and static members. It cannot be instantiated directly.

```java
abstract class Animal {
    private final String name;

    Animal(String name) {
        this.name = name;
    }

    abstract void speak();

    void introduce() {
        System.out.println(name);
        speak();
    }
}

class Dog extends Animal {
    Dog(String name) {
        super(name);
    }

    @Override
    void speak() {
        System.out.println("Woof");
    }
}

Animal animal = new Dog("Bruno");
animal.introduce(); // Bruno, then Woof
// Animal invalid = new Animal("A"); // Compile-time error
```

If a subclass does not implement every inherited abstract method, that
subclass must also be declared `abstract`.

Use an abstract class when related classes should share state or implementation
as well as a common contract.

---

## 3. C++ Abstract Classes

C++ has no `abstract` keyword. A class becomes abstract when it has at least
one pure virtual function.

```cpp
#include <iostream>
#include <memory>

class Animal {
public:
    virtual ~Animal() = default;
    virtual void speak() const = 0; // Pure virtual function

    void introduce() const {
        std::cout << "Animal says: ";
        speak();
    }
};

class Dog final : public Animal {
public:
    void speak() const override {
        std::cout << "Woof\n";
    }
};

int main() {
    std::unique_ptr<Animal> animal = std::make_unique<Dog>();
    animal->introduce(); // Woof
    // Animal invalid; // Cannot instantiate an abstract class
}
```

The `= 0` syntax makes `speak()` pure virtual. A derived class must override
all pure virtual functions before it can be instantiated. A polymorphic base
class should normally have a virtual destructor so deletion through a base
pointer is safe.

---

## 4. Python Abstract Classes

Python's `abc` module provides abstract base classes.

```python
from abc import ABC, abstractmethod

class Animal(ABC):
    @abstractmethod
    def speak(self) -> str:
        ...

    def introduce(self) -> None:
        print(self.speak())

class Dog(Animal):
    def speak(self) -> str:
        return "Woof"

animal: Animal = Dog()
animal.introduce()
# Animal()  # TypeError: abstract method is not implemented
```

Python does not enforce ordinary type annotations at runtime, but `ABC` and
`@abstractmethod` do prevent instantiation of an incomplete concrete subclass.
Python can also support structural interfaces with `typing.Protocol`, which
does not require inheritance:

```python
from typing import Protocol

class Speaker(Protocol):
    def speak(self) -> str:
        ...

def announce(speaker: Speaker) -> None:
    print(speaker.speak())
```

---

## 5. When to Use Abstraction

Use an abstract class when:

- Several related classes share code or state.
- A base class should define a partial implementation.
- Subclasses must provide specific operations.

Use an interface or protocol when:

- The contract represents a capability.
- Unrelated classes should support the same operation.
- Multiple contracts may be combined.
- The implementation should remain independent of the contract.

### Interview summary

- An abstract type describes a contract, not a complete object.
- Java uses `abstract`; C++ uses pure virtual functions; Python uses `abc`.
- A reference or pointer to an abstract type is valid; an object of the
  abstract type is not.
- Abstraction reduces coupling; encapsulation protects implementation details.

## References

- [Oracle: Abstract Methods and Classes](https://docs.oracle.com/javase/tutorial/java/IandI/abstract.html)
- [cppreference: Abstract class](https://en.cppreference.com/w/cpp/language/abstract_class)
- [Python `abc` documentation](https://docs.python.org/3/library/abc.html)
- [Python `typing.Protocol` documentation](https://docs.python.org/3/library/typing.html#typing.Protocol)

## Runnable Code

- [Abstraction.java](./Abstraction.java)
- [Abstraction.cpp](./Abstraction.cpp)
- [Abstraction.py](./Abstraction.py)

Run them from this folder:

```powershell
javac Abstraction.java; java Abstraction
g++ -std=c++17 -Wall -Wextra -pedantic Abstraction.cpp -o Abstraction.exe; .\Abstraction.exe
python Abstraction.py
```
