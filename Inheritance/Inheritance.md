# Inheritance in Java, C++, and Python

Inheritance allows one class to reuse and extend another class.

- **Base class / superclass / parent**: the class being inherited from.
- **Derived class / subclass / child**: the class that inherits.
- **`is-a` relationship**: a `Dog` is an `Animal`, so inheritance may be appropriate.
- **`has-a` relationship**: a `Car` has an `Engine`, so composition is usually better.

Inheritance gives a derived class:

1. Reusable behavior from the base class.
2. New fields and methods.
3. The ability to override inherited methods.
4. Polymorphism: base-type code can work with derived objects.

Inheritance should model a genuine behavioral relationship, not just code reuse. Prefer composition when a type only needs to use another object.

---

## 1. Basic Syntax

### Java Code

```java
class Animal {
    public void speak() {
        System.out.println("Animal sound");
    }
}

class Dog extends Animal {
    @Override
    public void speak() {
        System.out.println("Bark");
    }
}
```

### C++ Code

```cpp
#include <iostream>
using namespace std;

class Animal {
public:
    virtual void speak() const {
        cout << "Animal sound\n";
    }

    virtual ~Animal() = default;
};

class Dog : public Animal {
public:
    void speak() const override {
        cout << "Bark\n";
    }
};
```

In C++, `public` inheritance means that a `Dog` can be used where an `Animal` is expected. The `virtual` function and `override` keyword enable runtime overriding.

### Python Code

```python
class Animal:
    def speak(self) -> None:
        print("Animal sound")

class Dog(Animal):
    def speak(self) -> None:
        print("Bark")

animal: Animal = Dog()
animal.speak()  # Bark: Python uses runtime method lookup
```

Python supports inheritance, but it does not require inheritance for
polymorphism. A class can also be used through duck typing when it provides the
needed methods.

---

## 2. Types of Inheritance

Each type describes the shape of the class relationship. The same type can be implemented differently in Java and C++.

### Single Inheritance

One subclass inherits directly from one superclass.

```text
Vehicle
   |
  Car
```

**Java code**

```java
class Vehicle {
    void start() {
        System.out.println("Vehicle started");
    }
}

class Car extends Vehicle {
    void drive() {
        System.out.println("Car is driving");
    }
}
```

**C++ code**

```cpp
#include <iostream>
using namespace std;

class Vehicle {
public:
    void start() {
        cout << "Vehicle started\n";
    }
};

class Car : public Vehicle {
public:
    void drive() {
        cout << "Car is driving\n";
    }
};
```

**Use:** A straightforward `is-a` relationship, such as `Car` is a `Vehicle`.

**Limitations:** The child becomes coupled to the parent. Do not use inheritance only to reuse a few methods; composition may be safer.

### Multilevel Inheritance

An inheritance chain contains more than one level.

```text
Device
   |
Computer
   |
 Laptop
```

**Java code**

```java
class Device {
    void powerOn() {
        System.out.println("Power on");
    }
}

class Computer extends Device {
    void boot() {
        System.out.println("Operating system booted");
    }
}

class Laptop extends Computer {
    void fold() {
        System.out.println("Laptop folded");
    }
}
```

**C++ code**

```cpp
#include <iostream>
using namespace std;

class Device {
public:
    void powerOn() {
        cout << "Power on\n";
    }
};

class Computer : public Device {
public:
    void boot() {
        cout << "Operating system booted\n";
    }
};

class Laptop : public Computer {
public:
    void fold() {
        cout << "Laptop folded\n";
    }
};
```

**Use:** Progressive specialisation when every level adds a meaningful responsibility.

**Limitations:** Long chains are hard to understand, test, and change. Keep the hierarchy short and avoid inheriting merely because a class happens to be related in the real world.

### Hierarchical Inheritance

Several subclasses inherit from one common superclass.

```text
       Employee
       /      \
  Developer  Designer
```

**Java code**

```java
class Employee {
    void work() {
        System.out.println("Employee is working");
    }
}

class Developer extends Employee {
    void writeCode() {
        System.out.println("Writing code");
    }
}

class Designer extends Employee {
    void createDesign() {
        System.out.println("Creating design");
    }
}
```

**C++ code**

```cpp
#include <iostream>
using namespace std;

class Employee {
public:
    void work() {
        cout << "Employee is working\n";
    }
};

class Developer : public Employee {
public:
    void writeCode() {
        cout << "Writing code\n";
    }
};

class Designer : public Employee {
public:
    void createDesign() {
        cout << "Creating design\n";
    }
};
```

**Use:** Several related types share common behavior but add different features, such as `Shape -> Circle` and `Shape -> Rectangle`.

**Limitations:** The base class should contain genuinely common behavior. If the children share little, the hierarchy becomes artificial. An abstract class or interface is often better when the base should define only a contract.

### Multiple Inheritance

One class inherits directly from two or more base classes. C++ supports this for classes; Java does not allow a class to extend multiple classes.

**C++ code**

```cpp
#include <iostream>
using namespace std;

class Camera {
public:
    void takePhoto() const {
        cout << "Photo taken\n";
    }
};

class Phone {
public:
    void call() const {
        cout << "Calling\n";
    }
};

class SmartPhone : public Camera, public Phone { };
```

**Java equivalent: multiple interfaces**

```java
interface Camera {
    void takePhoto();
}

interface Phone {
    void call();
}

class SmartPhone implements Camera, Phone {
    public void takePhoto() {
        System.out.println("Photo taken");
    }

    public void call() {
        System.out.println("Calling");
    }
}
```

**Use:** Combining independent capabilities, such as a smartphone that can take photos and make calls.

**Limitations:** C++ can have name conflicts and the diamond problem when both bases share a parent. Java avoids class ambiguity by allowing one superclass and multiple interfaces. Prefer small interfaces or composition when possible.

### Hybrid Inheritance

Hybrid inheritance combines multiple patterns, commonly hierarchical and multiple inheritance.

```text
        Device
        /    \
   Scanner  Printer
        \    /
        Copier
```

**C++:** This can be implemented with multiple inheritance. If both branches inherit from `Device`, virtual inheritance may be needed to keep one shared `Device` base.

**Java:** A class cannot extend both branch classes. Use one superclass plus interfaces, or compose the required objects.

**Use:** Rarely, when a type genuinely combines established hierarchies.

**Limitations:** It creates tight coupling, ambiguous members, and complicated constructor rules. In interviews, mention that composition and interfaces are usually clearer alternatives.

---

## 3. Constructor and Destructor Order

Base construction happens before derived construction. Destruction happens in the reverse order.

### Java Code

```java
class Parent {
    Parent() {
        System.out.println("Parent constructor");
    }
}

class Child extends Parent {
    Child() {
        super();
        System.out.println("Child constructor");
    }
}
```

Creating `new Child()` prints:

```text
Parent constructor
Child constructor
```

Java automatically calls the no-argument superclass constructor if `super(...)` is not written. If the superclass has no no-argument constructor, the subclass must call an available constructor explicitly.

### C++ Code

```cpp
#include <iostream>
using namespace std;

class Parent {
public:
    Parent() {
        cout << "Parent constructor\n";
    }

    virtual ~Parent() {
        cout << "Parent destructor\n";
    }
};

class Child : public Parent {
public:
    Child() {
        cout << "Child constructor\n";
    }

    ~Child() override {
        cout << "Child destructor\n";
    }
};
```

The C++ initialization order is:

1. Virtual base classes.
2. Direct base classes.
3. Data members, in declaration order.
4. The constructor body.

The destructor order is the reverse. A polymorphic C++ base class should normally have a virtual destructor.

---

## 4. Preventing Inheritance and Overriding

**Java code:**

```java
final class Utility { }       // Cannot be extended

class Parent {
    public final void work() { } // Cannot be overridden
}
```

**C++ code:**

```cpp
using namespace std;

class Utility final { };

class Parent {
public:
    virtual void work() final { }
};
```

---

## 5. Abstract Classes and Interfaces

Inheritance is often used to implement an abstract contract, but the complete
topic is covered separately:

- [Abstraction](../Abstraction/Abstraction.md): abstract classes and pure
  virtual functions.
- [Interfaces](../Interface/Interface.md): Java interfaces, C++ interface-style
  classes, Python protocols, and duck typing.

The inheritance-specific rule is simple: an abstract base can still be
extended, and a concrete child must implement all required abstract operations.
An interface or abstract base reference can then refer to a child object.

Python's inheritance version is concise:

```python
class Animal:
    def speak(self) -> str:
        return "animal sound"

class Dog(Animal):
    def speak(self) -> str:
        return "woof"

print(Dog().speak())
```

---

## 6. The Diamond Problem

The diamond problem occurs when two classes inherit from the same base class and another class inherits from both:

```text
       A
      / \
     B   C
      \ /
       D
```

### C++ Without Virtual Inheritance

`D` receives two copies of `A`, so access to an `A` member becomes ambiguous.

### C++ Solution: Virtual Inheritance

```cpp
#include <iostream>
using namespace std;

class A {
public:
    int value = 10;
};

class B : virtual public A { };
class C : virtual public A { };

class D : public B, public C { };

int main() {
    D object;
    cout << object.value << '\n'; // One shared A subobject
}
```

With virtual inheritance, the most-derived class is responsible for constructing the virtual base:

```cpp
using namespace std;

class A {
public:
    A(int value) { }
};

class B : virtual public A {
public:
    B() : A(1) { } // Ignored when constructing D's virtual A
};

class C : virtual public A {
public:
    C() : A(2) { } // Ignored when constructing D's virtual A
};

class D : public B, public C {
public:
    D() : A(3), B(), C() { } // D constructs A
};
```

### Java

Java avoids the class diamond problem by allowing only single class inheritance. A class can implement multiple interfaces, but conflicting default methods must be resolved by overriding the method.

---

## 7. Important Interview Pitfalls

### Object Slicing in C++

Passing a derived object by value as a base object removes the derived part:

```cpp
#include <iostream>
using namespace std;

class Base {
public:
    virtual void show() const {
        cout << "Base\n";
    }

    virtual ~Base() = default;
};

class Derived : public Base {
public:
    void show() const override {
        cout << "Derived\n";
    }
};

void byValue(Base object) {
    object.show(); // Base
}

void byReference(const Base& object) {
    object.show(); // Derived
}
```

Use a reference or pointer for polymorphic behavior.

### Accessing Base Members

Java uses `super`:

```java
class Child extends Parent {
    @Override
    public void work() {
        super.work();
        System.out.println("Child work");
    }
}
```

C++ uses the base-class name:

```cpp
using namespace std;

class Child : public Parent {
public:
    void work() override {
        Parent::work();
        cout << "Child work\n";
    }
};
```

### Base Class Access in C++

```cpp
using namespace std;

class PublicDerived : public Base { };       // public stays public
class ProtectedDerived : protected Base { }; // public becomes protected
class PrivateDerived : private Base { };     // public becomes private
```

Base-class private members are never directly accessible in the derived class. Public inheritance is the normal choice for an `is-a` relationship.

### Liskov Substitution Principle

If `B` is a subtype of `A`, code expecting `A` should work correctly with `B`.

Before using inheritance, ask:

1. Is the derived object genuinely usable as the base object?
2. Does it preserve the base class's expected behavior?
3. Would composition be simpler and less tightly coupled?

### Fragile Base Class

A change in a base class can unexpectedly affect subclasses. Reduce this risk by:

- Keeping base classes small and stable.
- Avoiding unnecessary `protected` state.
- Exposing behavior through clear methods.
- Favoring composition when inheritance is not required.

---

## 8. Java and C++ Comparison

| Topic | Java | C++ |
| :--- | :--- | :--- |
| Class inheritance syntax | `class B extends A` | `class B : public A` |
| Multiple class inheritance | Not supported | Supported |
| Multiple interfaces/contracts | `implements I1, I2` | Multiple base classes |
| Override marker | `@Override` | `override` |
| Base method call | `super.method()` | `Base::method()` |
| Prevent inheritance | `final class` | `class final` |
| Abstract method | `abstract` method | Pure virtual function `= 0` |
| Object slicing | Does not occur through normal references | Can occur when passing by value |
| Base destructor | Managed by garbage collection | Use a virtual destructor for polymorphic bases |
| Preferred alternative to reuse | Composition | Composition |

## Interview Checklist

Be able to explain:

- The difference between a base class and a derived class.
- Single, multilevel, hierarchical, multiple, and hybrid inheritance.
- Why C++ needs `virtual` and a virtual destructor.
- Why Java supports multiple interfaces but not multiple classes.
- The diamond problem and C++ virtual inheritance.
- Object slicing in C++.
- Abstract classes versus interfaces.
- Why composition is often preferred over inheritance.

## Runnable Code

The folder contains one equivalent example for each language:

- [Inheritance.java](./Inheritance.java)
- [Inheritance.cpp](./Inheritance.cpp)
- [Inheritance.py](./Inheritance.py)

Run them from this folder:

```powershell
javac Inheritance.java; java Inheritance
g++ -std=c++17 -Wall -Wextra -pedantic Inheritance.cpp -o Inheritance.exe; .\Inheritance.exe
python Inheritance.py
```
