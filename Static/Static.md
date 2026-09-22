# Static Members in Java, C++, and Python

Static members belong to a type or class rather than to one particular
instance. They are useful for shared state and operations that do not need
object state, but each language defines them differently.

## 1. Java Static Fields

```java
class Student {
    static int count;

    Student() {
        count++;
    }
}

new Student();
new Student();
new Student();
System.out.println(Student.count); // 3
```

There is one `count` shared by all `Student` objects. Prefer
`Student.count` rather than an object expression such as `student.count`.

### Static initialization

```java
class Configuration {
    static final String ENVIRONMENT;

    static {
        ENVIRONMENT = "production";
    }
}
```

A static field is initialized when the class is initialized. Static fields
should not be used for uncontrolled global mutable state in concurrent
programs; synchronization or safer designs may be required.

## 2. Java Static Methods

```java
class MathUtils {
    static int square(int x) {
        return x * x;
    }
}

System.out.println(MathUtils.square(5));
```

A static method has no `this` object. It cannot directly access instance
fields or call instance methods:

```java
class Example {
    int value;

    static void invalid() {
        // System.out.println(value); // Compile-time error
    }
}
```

Static methods are hidden, not overridden:

```java
class Parent {
    static void show() { System.out.println("Parent"); }
}

class Child extends Parent {
    static void show() { System.out.println("Child"); }
}

Parent reference = new Child();
reference.show(); // Parent
Child.show();     // Child
```

Use the class name when calling a static method.

## 3. C++ Static Data Members

```cpp
class Student {
public:
    inline static int count = 0;

    Student() {
        ++count;
    }
};

Student a;
Student b;
std::cout << Student::count << '\n'; // 2
```

Before inline variables were introduced, a static data member was commonly
declared in the class and defined once in a source file:

```cpp
class Student {
public:
    static int count;
};

int Student::count = 0;
```

`inline static` (C++17) permits the definition inside the class while
maintaining one program-wide entity.

## 4. C++ Static Member Functions

```cpp
class MathUtils {
public:
    static int square(int x) {
        return x * x;
    }
};

std::cout << MathUtils::square(5) << '\n';
```

A C++ static member function has no `this` pointer. It cannot directly access
non-static data members and cannot be `virtual`.

## 5. Python Class Attributes

Python commonly uses class attributes for data shared by instances:

```python
class Student:
    university = "ABC University"
    count = 0

    def __init__(self, name: str):
        self.name = name
        Student.count += 1

a = Student("A")
b = Student("B")
print(Student.count)       # 2
print(a.university)        # Found on the class
```

An assignment through an instance usually creates or changes an instance
attribute, which can shadow the class attribute:

```python
a.university = "Different University"
print(a.university)       # Different University
print(b.university)       # ABC University
print(Student.university) # ABC University
```

Use `ClassVar` when type-checking code to document that an attribute is meant
to be class-level:

```python
from typing import ClassVar

class Student:
    count: ClassVar[int] = 0
```

## 6. Python `@staticmethod` and `@classmethod`

```python
class MathUtils:
    @staticmethod
    def square(x: int) -> int:
        return x * x

    @classmethod
    def from_text(cls, text: str) -> "MathUtils":
        print(f"Constructing {cls.__name__}")
        return cls()

print(MathUtils.square(5))
MathUtils.from_text("value")
```

- `@staticmethod` receives neither `self` nor `cls`; it is a function stored
  in the class namespace.
- `@classmethod` receives the class as `cls` and can create instances of the
  actual subclass, making it useful for alternate constructors.
- An ordinary Python function defined in a class receives the instance as
  `self` when accessed through an instance.

Python's `@staticmethod` is conceptually similar to a Java/C++ static method,
but Python's class attributes and descriptor behavior are more dynamic.

## 7. Comparison

| Feature | Java | C++ | Python |
| :--- | :--- | :--- | :--- |
| Shared data | `static` field | `static` data member | Class attribute |
| Type-level function | `static` method | `static` member function | `@staticmethod` |
| Receives current object | No | No `this` | No `self` |
| Receives class | Not as an implicit parameter | Not as an implicit parameter | `@classmethod` receives `cls` |
| Static method overriding | No; methods are hidden | Static functions cannot be virtual | Attribute lookup can be customized dynamically |
| Typical call | `Type.method()` | `Type::method()` | `Type.method()` |

## Common interview traps

1. Static methods cannot directly access instance members.
2. Java and C++ static methods do not participate in runtime polymorphism.
3. Java static fields are shared; assigning through an object does not create a
   new per-object field.
4. C++ static data members need a definition unless declared as an appropriate
   inline variable.
5. Python class attributes can be shadowed by instance attributes.
6. Python `@classmethod` is not the same as `@staticmethod`.

## References

- [Oracle: Understanding Class Members](https://docs.oracle.com/javase/tutorial/java/javaOO/classvars.html)
- [cppreference: Static members](https://en.cppreference.com/w/cpp/language/static)
- [cppreference: `this` pointer](https://en.cppreference.com/w/cpp/language/this)
- [Python built-in `staticmethod`](https://docs.python.org/3/library/functions.html#staticmethod)
- [Python tutorial: Class and instance variables](https://docs.python.org/3/tutorial/classes.html#class-and-instance-variables)
