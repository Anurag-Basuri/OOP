# `super` and Parent-Class Behavior

`super` is used to reuse or extend behavior from a parent class. The exact
syntax and dispatch rules differ across Java, C++, and Python.

## 1. Java `super`

Java uses `super` to access a parent constructor, field, or method.

```java
class Animal {
    protected String name;

    Animal(String name) {
        this.name = name;
    }

    void eat() {
        System.out.println(name + " eats");
    }
}

class Dog extends Animal {
    Dog(String name) {
        super(name); // Calls Animal's constructor
    }

    @Override
    void eat() {
        super.eat(); // Calls Animal.eat()
        System.out.println(name + " also chews");
    }
}
```

`super(...)` must be the first statement in a constructor. If no constructor
call is written, Java inserts `super()` when the parent has an accessible
no-argument constructor.

Java can access a hidden parent field with `super.field`, but fields are not
polymorphic:

```java
class Parent {
    int value = 10;
}

class Child extends Parent {
    int value = 20;

    void print() {
        System.out.println(value);       // Child field
        System.out.println(super.value); // Parent field
    }
}
```

## 2. C++ Base-Class Access

C++ has no `super` keyword. Qualify the base class explicitly:

```cpp
class Animal {
public:
    explicit Animal(std::string name) : name(std::move(name)) {}

    virtual ~Animal() = default;

    virtual void eat() const {
        std::cout << name << " eats\n";
    }

protected:
    std::string name;
};

class Dog : public Animal {
public:
    explicit Dog(std::string name) : Animal(std::move(name)) {}

    void eat() const override {
        Animal::eat(); // Call the base implementation
        std::cout << name << " also chews\n";
    }
};
```

The base constructor is written in the derived constructor's initializer list.
`Animal::eat()` explicitly selects the base implementation.

If a derived class hides a base member, use `Base::member`:

```cpp
class Parent { public: int value = 10; };
class Child : public Parent {
public:
    int value = 20;
    void print() {
        std::cout << value << '\n';
        std::cout << Parent::value << '\n';
    }
};
```

## 3. Python `super()`

Python's `super()` returns a proxy that performs attribute lookup after the
current class in the method resolution order (MRO).

```python
class Animal:
    def __init__(self, name: str):
        self.name = name

    def eat(self) -> None:
        print(f"{self.name} eats")

class Dog(Animal):
    def __init__(self, name: str):
        super().__init__(name)

    def eat(self) -> None:
        super().eat()
        print(f"{self.name} also chews")
```

In simple single inheritance, this commonly means the immediate parent. In
multiple inheritance, it means the next class in the MRO, not necessarily a
particular textual parent:

```python
class A:
    def run(self):
        print("A")

class B(A):
    def run(self):
        print("B")
        super().run()

class C(A):
    def run(self):
        print("C")
        super().run()

class D(B, C):
    def run(self):
        print("D")
        super().run()

D().run() # D, B, C, A
```

Python cooperative multiple inheritance works when every class calls
`super()` and accepts compatible arguments.

## 4. Comparison

| Purpose | Java | C++ | Python |
| :--- | :--- | :--- | :--- |
| Parent constructor | `super(...)` | `Base(...)` in initializer list | `super().__init__(...)` |
| Parent method | `super.method()` | `Base::method()` | `super().method()` |
| Parent field | `super.field` | `Base::field` | Usually `super().field` or explicit class lookup |
| Multiple inheritance lookup | Interfaces and class hierarchy rules | Base-class qualification | MRO and `super()` |

## Common interview points

- `super` reuses parent behavior; it does not create another object.
- Calling a parent method explicitly bypasses the child override for that call.
- Java constructor `super(...)` must be first.
- C++ base construction order is controlled by the language, not initializer
  list text order.
- Python `super()` follows the MRO and is important for cooperative multiple
  inheritance.

## References

- [Oracle: Using the `super` Keyword](https://docs.oracle.com/javase/tutorial/java/IandI/super.html)
- [cppreference: Derived classes](https://en.cppreference.com/w/cpp/language/derived_class)
- [Python built-in `super`](https://docs.python.org/3/library/functions.html#super)

## Runnable Code

- [Super.java](./Super.java)
- [Super.cpp](./Super.cpp)
- [Super.py](./Super.py)

Run them from this folder:

```powershell
javac Super.java; java Super
g++ -std=c++17 -Wall -Wextra -pedantic Super.cpp -o Super.exe; .\Super.exe
python Super.py
```
