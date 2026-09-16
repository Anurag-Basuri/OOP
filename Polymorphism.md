# Polymorphism in Java, C++, and Python

Polymorphism means **one interface, many implementations**.

For example, different classes can provide their own version of `speak()`:

```text
Animal
├── Dog      -> Woof
├── Cat      -> Meow
└── Cow      -> Moo
```

Code can call `speak()` through a common interface while the actual behavior depends on the object.

---

## 1. Compile-Time and Runtime Polymorphism

### Compile-Time Polymorphism

The compiler decides which function to call before the program runs.

Common examples:

- Function or method overloading
- Operator overloading in C++

### Runtime Polymorphism

The method is selected while the program is running based on the actual object.

The usual example is method overriding:

```text
Animal reference = Dog object
reference.speak() -> Dog's speak()
```

Runtime polymorphism is useful when one piece of code must work with many implementations without checking each concrete type.

---

## 2. Method Overloading

Overloading uses the same method name with different parameter lists. The return type alone cannot distinguish overloads.

### Java Code

```java
class Calculator {
    int add(int a, int b) {
        return a + b;
    }

    double add(double a, double b) {
        return a + b;
    }
}

Calculator calculator = new Calculator();
calculator.add(2, 3);       // int version
calculator.add(2.5, 3.5);  // double version
```

The compiler selects the method from the argument types, so this is compile-time polymorphism.

### C++ Code

```cpp
#include <iostream>
using namespace std;

class Calculator {
public:
    int add(int a, int b) {
        return a + b;
    }

    double add(double a, double b) {
        return a + b;
    }
};
```

C++ also supports operator overloading, for example defining how `+` works for a user-defined class.

---

## 3. Method Overriding and Runtime Polymorphism

Overriding occurs when a subclass provides a new implementation of an inherited method with the same signature.

### Java Code

```java
class Animal {
    void speak() {
        System.out.println("Animal sound");
    }
}

class Dog extends Animal {
    @Override
    void speak() {
        System.out.println("Woof");
    }
}

Animal animal = new Dog();
animal.speak(); // Woof
```

The reference type is `Animal`, but the object type is `Dog`. Java chooses the overridden instance method at runtime.

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
        cout << "Woof\n";
    }
};

int main() {
    Dog dog;
    Animal& animal = dog;
    animal.speak(); // Woof
}
```

In C++, `virtual` is required in the base class for runtime dispatch through a base pointer or reference. `override` asks the compiler to verify that the derived method really overrides a base method.

Without `virtual`, a call through a base pointer or reference is usually resolved using the base class's method.

### Why Write `@Override` in Java?

`@Override` is an annotation, not the mechanism that performs overriding. Java can override the method without it:

```java
class Dog extends Animal {
    void speak() {
        System.out.println("Woof");
    }
}
```

However, `@Override` is strongly recommended because it asks the compiler to check the method:

```java
class Dog extends Animal {
    @Override
    void speak() {
        System.out.println("Woof");
    }
}
```

Without the annotation, a spelling or parameter mistake silently creates a new method:

```java
class Dog extends Animal {
    void speek() { // Typo: this does not override speak()
        System.out.println("Woof");
    }
}
```

With `@Override`, the compiler reports an error instead of allowing this mistake.

#### Java limitations

- It is optional, so old code may omit it.
- It only checks the declaration; it does not make a method virtual or change runtime behavior.
- The method must actually be overridable. `static`, `private`, and `final` methods cannot be overridden.
- The overriding method cannot reduce visibility. A `public` method cannot be overridden as `protected` or `private`.
- The parameter list must match. Changing a parameter creates overloading, not overriding.

### Why Write `override` in C++?

In C++, `override` is a language keyword. It is also optional, but it provides a compile-time check:

```cpp
using namespace std;

class Dog : public Animal {
public:
    void speak() const override {
        cout << "Woof\n";
    }
};
```

The compiler rejects the code if the base method is not `virtual`, if the parameters differ, or if qualifiers such as `const` do not match.

Without `override`, this mistake can compile as a new function:

```cpp
using namespace std;

class Dog : public Animal {
public:
    void speak() { // Missing const: may not override Animal::speak() const
        cout << "Woof\n";
    }
};
```

#### C++ limitations

- `override` does not make a function virtual; the base declaration must use `virtual`.
- It cannot be used for a function that does not override a base virtual function.
- The signature must match exactly, including `const`, reference qualifiers, and compatible return types.
- Runtime dispatch requires a base pointer or reference. Calling through a concrete object does not need virtual dispatch.
- Virtual dispatch has a small runtime and memory cost, usually through a virtual table.

### `@Override` vs `override`

| Point | Java `@Override` | C++ `override` |
| :--- | :--- | :--- |
| Required? | No | No |
| Type | Annotation | Language keyword |
| Main purpose | Detect accidental non-overriding methods | Detect accidental non-overriding methods |
| Makes dispatch virtual? | No; eligible instance methods are virtual by default | No; base method must already be `virtual` |
| Typical error caught | Spelling or parameter mismatch | Signature, `const`, or missing `virtual` mismatch |
| Recommended? | Yes | Yes |

### Why Use Runtime Polymorphism?

It allows one function to work with different implementations:

```java
void makeAnimalSpeak(Animal animal) {
    animal.speak();
}
```

The function does not need separate code for `Dog`, `Cat`, and `Cow`. Each class controls its own behavior.

---

## 4. Python and Duck Typing

Python supports runtime polymorphism without requiring a common base class. It focuses on whether an object provides the required method.

```python
class Dog:
    def speak(self):
        print("Woof")

class Robot:
    def speak(self):
        print("Beep")

def make_sound(obj):
    obj.speak()

make_sound(Dog())    # Woof
make_sound(Robot())  # Beep
```

This is called **duck typing**:

> If an object behaves like the required type, it can be used.

The name comes from the idea: “If it walks like a duck and quacks like a duck, treat it as a duck.”

### Duck Typing vs Java/C++

| Feature | Python | Java/C++ |
| :--- | :--- | :--- |
| Main check | Has the required method at runtime | Usually follows declared types |
| Common base class required | No | Usually for base-reference polymorphism |
| Error timing | Often when the missing method is called | More errors found at compile time |
| Flexibility | High | More explicit and type-safe |

Duck typing is flexible, but a typo or missing method may cause a runtime error.

---

## 5. Important Interview Differences

### Overloading vs Overriding

| Feature | Overloading | Overriding |
| :--- | :--- | :--- |
| Classes required | No | Yes, inheritance is normally involved |
| Parameters | Must be different | Same method signature |
| Decision time | Compile time | Runtime |
| Main purpose | Offer several input forms | Give a subclass-specific behavior |
| Java marker | None | `@Override` |
| C++ marker | None | `override` |

### `virtual` in C++

```cpp
using namespace std;

class Base {
public:
    void show() { cout << "Base\n"; } // Not virtual
};

class Derived : public Base {
public:
    void show() { cout << "Derived\n"; }
};
```

If a `Base&` refers to a `Derived` object, calling `show()` uses the base version because the function is not virtual. Add `virtual` to `Base::show()` to enable runtime dispatch.

### Virtual Destructor

If a class is used polymorphically, give it a virtual destructor:

```cpp
using namespace std;

class Base {
public:
    virtual ~Base() = default;
};
```

This ensures that deleting a derived object through a base pointer runs the derived destructor first.

### Java Methods

Java instance methods are virtual by default, except methods such as `static`, `private`, and `final`, which are not overridden in the normal polymorphic way.

---

## Interview Checklist

Be able to explain:

- The meaning of “one interface, many implementations.”
- Compile-time versus runtime polymorphism.
- Overloading versus overriding.
- Why C++ needs `virtual`.
- The purpose of `override` and `@Override`.
- Why a polymorphic C++ base class needs a virtual destructor.
- How Java runtime polymorphism works through a superclass reference.
- What duck typing means in Python.
- One advantage and one risk of duck typing.
