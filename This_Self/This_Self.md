# `this`, `self`, and the Current Object

Instance methods need access to the object on which they operate. Java, C++,
and Python expose that current object differently.

## 1. Java `this`

In Java, `this` refers to the current object.

```java
class Student {
    private String name;

    Student(String name) {
        this.name = name; // field = parameter
    }

    Student rename(String name) {
        this.name = name;
        return this;
    }
}
```

```java
class Counter {
    private int value;

    Counter(int value) {
        this.value = value;
    }

    Counter increment() {
        this.value++;
        return this; // return the current object for chaining
    }

    void print() {
        System.out.println(this.value);
    }
}

new Counter(0).increment().increment().print();
```

Java uses `this(...)` to call another constructor in the same class:

```java
class User {
    private final String name;
    private final int age;

    User() {
        this("Unknown", 0);
    }

    User(String name, int age) {
        this.name = name;
        this.age = age;
    }
}
```

`this(...)` must be the first statement in a constructor. `this` is not
available in a static method because a static method has no particular object.

## 2. C++ `this`

In a non-static C++ member function, `this` is a pointer to the current object.

```cpp
class Counter {
    int value;

public:
    Counter(int value) : value(value) {}

    Counter& increment() {
        this->value++;
        return *this;
    }
};
```

Use `this->member` when needed. `this` is a pointer, so `*this` is the current
object itself. Returning `*this` by reference enables method chaining without
copying the object.

C++ has no `this(...)` constructor delegation syntax from Java, but modern C++
can delegate to another constructor:

```cpp
class User {
    std::string name;
    int age;

public:
    User() : User("Unknown", 0) {}
    User(std::string name, int age) : name(std::move(name)), age(age) {}
};
```

A static member function has no `this` pointer and cannot directly access
non-static members.

## 3. Python `self`

`self` is the conventional name for the instance parameter. Unlike Java
`this`, it is not a reserved keyword; another name works, but `self` should
always be used by convention.

```python
class Counter:
    def __init__(self, value: int):
        self.value = value

    def increment(self) -> "Counter":
        self.value += 1
        return self

counter = Counter(0)
counter.increment().increment()
print(counter.value)
```

The call:

```python
counter.increment()
```

is conceptually equivalent to:

```python
Counter.increment(counter)
```

Python's method binding supplies the instance when an instance method is
accessed. The method definition still explicitly declares its first parameter.

## 4. Comparison

| Language | Current-object reference | Form | Available in static method? |
| :--- | :--- | :--- | :--- |
| Java | `this` | Object reference | No |
| C++ | `this` | Pointer | No |
| Python | `self` by convention | Explicit parameter | No instance is supplied |

## Common interview points

- `this`/`self` resolves a field-parameter name collision.
- Java `this(...)` calls another constructor in the same class.
- C++ `*this` is the current object; return it by reference for chaining.
- Python `self` is a convention, not a reserved keyword.
- Static methods cannot directly use instance state because there is no current
  instance.

## References

- [Oracle: The `this` Keyword](https://docs.oracle.com/javase/tutorial/java/javaOO/thiskey.html)
- [cppreference: `this` pointer](https://en.cppreference.com/w/cpp/language/this)
- [Python tutorial: Class and instance objects](https://docs.python.org/3/tutorial/classes.html)
