# MoveInSync SDE Interview: Java Fundamentals and OOP

This folder is a focused preparation guide based on publicly available
candidate reports. Interview questions vary by role, hiring cycle, and
candidate experience. The labels below distinguish what a report explicitly
mentions from questions that are useful follow-up preparation.

## What public reports explicitly mention

### Java and OOP questions

The following prompts are explicitly visible in public MoveInSync interview
question reports:

1. Explain the OOP concepts.
2. Explain the difference between C++ and Java.
3. Basic Java questions.
4. Spring Boot annotations.
5. Design patterns.
6. Coding-style assessment.

The reports do not always publish the interviewer's complete follow-up
conversation. Therefore, the detailed questions in the next section are
practice expansions, not claims that every one was asked verbatim.

### Backend and engineering questions

Public backend-engineer reports also mention:

- Design a ticket-booking application for cities, theatres, shows, and seats.
- Allow only one person to book a seat when concurrent requests target it.
- Release a selected but unbooked seat after ten minutes.
- Expose APIs for available, blocked, and booked seats.
- Work with a matrix of size `10^18 x 10^18` and sub-table coordinates.
- Design a reliable million-record upload and database-insert application.
- Avoid data loss and handle temporary database downtime.
- Design an ExecutorService.
- Explain how threads are managed in a web server.
- Explain different garbage-collection algorithms.
- Discuss design patterns and technical challenges from previous work.

The on-campus report describes a DSA round followed by profile-specific
backend/frontend interviews and a deeper third round. This means Java
fundamentals should be prepared together with DSA, concurrency, and design.

## Java fundamentals question bank

### Language and runtime

1. What is the difference between JDK, JRE, and JVM?
2. What happens during compilation and execution of a Java program?
3. Why is Java platform-independent?
4. What are primitive types and wrapper classes?
5. What is autoboxing and unboxing?
6. What is the difference between `==` and `equals()`?
7. Why must `hashCode()` agree with `equals()`?
8. Why is `String` immutable?
9. Compare `String`, `StringBuilder`, and `StringBuffer`.
10. What is the difference between stack and heap memory?
11. What are access modifiers?
12. What do `final`, `finally`, and `finalize` mean?
13. What are static fields, methods, and initialization blocks?
14. What is the difference between method overloading and overriding?
15. What are checked and unchecked exceptions?
16. Compare `throw` and `throws`.
17. How do `try`, `catch`, and `finally` work?
18. What is try-with-resources?
19. What is the Java Collections Framework?
20. Compare `ArrayList` and `LinkedList`.
21. Compare `HashMap`, `LinkedHashMap`, and `TreeMap`.
22. How does a `HashMap` work?
23. Why should mutable keys be avoided in a `HashMap`?
24. Compare `HashSet`, `LinkedHashSet`, and `TreeSet`.
25. What are generics and why are they useful?
26. What is the difference between `Comparable` and `Comparator`?
27. What are streams and lambda expressions?
28. What is the difference between `map`, `filter`, and `reduce`?
29. What is a functional interface?
30. What is the difference between an interface default method and a static method?

### Concurrency and backend fundamentals

31. What is the difference between a process and a thread?
32. What are a race condition and a critical section?
33. Compare `synchronized`, `volatile`, and `AtomicInteger`.
34. What are deadlock, livelock, and starvation?
35. How can deadlocks be prevented?
36. What are `Executor`, `ExecutorService`, and `ScheduledExecutorService`?
37. How does a thread pool work?
38. What is the difference between `execute()` and `submit()`?
39. How should a service shut down an executor?
40. What is a `Future`? When would you use `CompletableFuture`?
41. How would you make a seat booking operation atomic?
42. How would you make a ten-minute seat hold expire safely?
43. How would you retry database work without creating duplicate records?
44. What is idempotency, and why is it important for APIs?
45. What happens when a web server receives more requests than its worker pool
    can process?
46. What is the difference between optimistic and pessimistic locking?
47. What are common causes of high Java memory usage?
48. Compare young-generation and old-generation garbage collection.
49. What are stop-the-world pauses?
50. How would you investigate a memory leak or long GC pause?

## OOP question bank

1. What is an object and what is a class?
2. Explain encapsulation, inheritance, abstraction, and polymorphism.
3. Give a practical example of each OOP principle.
4. What is the difference between an `is-a` and a `has-a` relationship?
5. When should composition be preferred over inheritance?
6. What is the difference between association, aggregation, and composition?
7. What is upcasting and downcasting?
8. If the reference type is a parent and the object type is a child, which
   overridden method executes?
9. Why are fields not polymorphic in Java?
10. What does `super` access in a multilevel hierarchy?
11. Can a subclass directly access a private parent field?
12. Compare an abstract class and an interface.
13. Can Java support multiple inheritance?
14. What is an immutable class? How would you design one?
15. What is a constructor? Is it inherited or overridden?
16. What are the SOLID principles?
17. Explain dependency inversion with a small example.
18. Explain the Factory, Strategy, Observer, and Singleton patterns.
19. What problems can a Singleton introduce in testing and concurrency?
20. Refactor a procedural ticket-booking design into cohesive classes.

## Short answer patterns

### Parent reference and child object

```java
Animal animal = new Dog();
animal.eat();             // Dog.eat() if Dog overrides eat()
```

The compiler uses the reference type to decide which members are available.
Overridden instance methods use the runtime object type. Fields are selected
using the reference type and are hidden rather than overridden.

### Abstract class versus interface

Use an abstract class when related types need shared state, constructors, or
implemented behavior. Use an interface for a capability or contract that
unrelated classes may implement. A class can extend one class but implement
multiple interfaces.

### HashMap contract

`HashMap` uses `hashCode()` to find a bucket and `equals()` to distinguish
keys within that bucket. If two keys are equal, they must have the same hash
code. Keys should not change in a way that affects equality while stored.

### Safe seat booking

Validate the seat and show, then perform the state transition from
`AVAILABLE` to `HELD` atomically. Use a database constraint/transaction or a
carefully scoped lock. Store an expiry time and make the release operation
idempotent. Do not rely only on an in-memory lock when multiple service
instances can receive requests.

## Suggested study order

1. OOP principles, overriding, interfaces, abstract classes, and composition.
2. `equals`/`hashCode`, strings, collections, exceptions, and generics.
3. Threads, synchronization, executor services, and atomic state changes.
4. Garbage collection and JVM memory.
5. Design patterns, SOLID, and the ticket-booking design.
6. DSA practice and explanation of previous projects.

## Java fundamentals: interview-ready answers

These answers are intentionally phrased as answers you could give in a
technical interview. Start with the first sentence, then expand with the
example or follow-up detail if the interviewer asks.

### 1. JDK, JRE, and JVM

The **JVM** runs Java bytecode. The **JRE** contains the JVM plus libraries
needed to run Java applications. The **JDK** contains the JRE plus development
tools such as `javac`, `javadoc`, and `jar`.

```text
JDK = JRE + development tools
JRE = JVM + runtime libraries
JVM = bytecode execution environment
```

### 2. Compilation and execution

`javac Hello.java` compiles source code into platform-neutral bytecode in
`Hello.class`. The JVM loads, verifies, and executes that bytecode. It
interprets code and may use a JIT compiler to compile frequently executed
bytecode into native machine code.

### 3. Java platform independence

Java source is compiled to bytecode rather than directly to one operating
system's machine code. A compatible JVM on Windows, Linux, or macOS can run
the same bytecode. The JVM itself is platform-specific; the bytecode is
portable.

### 4. Primitive types and wrapper classes

Primitive types hold simple values directly: `int`, `long`, `double`, `float`,
`char`, `boolean`, `byte`, and `short`. Wrapper classes represent those values
as objects, such as `Integer`, `Long`, and `Double`.

```java
int count = 10;
Integer boxedCount = 10;
```

Wrappers are needed where an object is required, for example in
`List<Integer>`. They can also be `null`, unlike an `int`.

### 5. Autoboxing and unboxing

**Autoboxing** automatically converts a primitive to its wrapper. **Unboxing**
converts a wrapper back to a primitive.

```java
Integer boxed = 10; // autoboxing
int value = boxed;  // unboxing
```

Unboxing a `null` wrapper throws `NullPointerException`, so it should not be
treated as risk-free conversion.

### 6. `==` versus `equals()`

For primitives, `==` compares values. For object references, `==` compares
whether both references point to the same object. `equals()` is a method for
logical equality, provided the class implements it correctly.

```java
new String("x") == new String("x");      // false
new String("x").equals(new String("x")); // true
```

Use `Objects.equals(a, b)` when either reference may be `null`.

### 7. `hashCode()` and `equals()`

If two objects are equal according to `equals()`, they must return the same
`hashCode()`. Hash-based collections use the hash code to find a bucket and
`equals()` to identify the exact key.

The reverse is not required: two unequal objects may have the same hash code.
Whenever a class overrides `equals()`, it should override `hashCode()` using
the same significant fields.

### 8. Why `String` is immutable

Once a `String` is created, its contents cannot change. Immutability makes
strings safe to share, supports the string pool, makes cached hash codes safe,
and prevents a string used as a map key from changing after insertion. It also
helps with security-sensitive values such as class names and file paths.

```java
String name = "Java";
name.concat(" SE"); // creates a new String; name is still "Java"
```

### 9. `String`, `StringBuilder`, and `StringBuffer`

`String` is immutable and is appropriate for values that do not change.
`StringBuilder` is mutable and usually preferred for repeated concatenation in
single-threaded code. `StringBuffer` is mutable and synchronizes its methods,
so it is generally slower and is used only when that legacy synchronization is
required.

```java
StringBuilder result = new StringBuilder();
for (String part : parts) {
    result.append(part);
}
```

### 10. Stack and heap

The **stack** stores method frames, local variables, and call state for each
thread. The **heap** stores objects and arrays shared by threads and managed by
the garbage collector. A local reference may be on the stack while the object
it refers to is on the heap. Stack exhaustion commonly causes
`StackOverflowError`; excessive heap usage may cause `OutOfMemoryError`.

### 11. Access modifiers

Java has four access levels:

| Modifier | Same class | Same package | Subclass | Other package |
|---|---:|---:|---:|---:|
| `private` | Yes | No | No | No |
| package-private | Yes | Yes | Only through package access | No |
| `protected` | Yes | Yes | Yes, subject to cross-package rules | Limited |
| `public` | Yes | Yes | Yes | Yes |

Use the most restrictive access that supports the design. In particular,
private fields plus validated methods protect invariants.

### 12. `final`, `finally`, and `finalize`

- `final` prevents reassignment of a variable, overriding of a method, or
  inheritance of a class.
- `finally` is a block normally used for cleanup after `try`/`catch`.
- `finalize()` was an unreliable, deprecated mechanism associated with
  garbage collection and should not be used for resource cleanup.

Prefer try-with-resources and explicit resource ownership.

### 13. Static fields, methods, and initialization blocks

`static` members belong to the class rather than to each object. A static field
is shared by all instances. A static method can be called through the class
and cannot directly use instance fields or `this`. A static initialization
block runs when the class is initialized, typically once in that class loader.

```java
class Counter {
    static int created;
    static {
        created = 0;
    }
}
```

Avoid mutable global state unless shared ownership is intentional.

### 14. Overloading versus overriding

**Overloading** uses the same method name with different parameter lists in
the same class or hierarchy; the compiler chooses the overload. **Overriding**
replaces an inherited instance method with the same signature; runtime
dispatch chooses the implementation based on the actual object.

```java
void print(int value) {}
void print(String value) {} // overloading

class Dog extends Animal {
    @Override
    void speak() {}          // overriding
}
```

Return type alone cannot overload a method.

### 15. Checked and unchecked exceptions

Checked exceptions are checked by the compiler and must be caught or declared,
for example `IOException`. Unchecked exceptions extend `RuntimeException`,
for example `NullPointerException` and `IllegalArgumentException`; the
compiler does not force a catch or declaration.

Use checked exceptions when the caller can reasonably recover and unchecked
exceptions for programming errors or invalid API use. The exact choice should
follow the application's error-handling convention.

### 16. `throw` versus `throws`

`throw` throws one exception object at a particular point:

```java
throw new IllegalArgumentException("amount must be positive");
```

`throws` appears in a method signature to declare possible exceptions:

```java
void read() throws IOException {
}
```

### 17. `try`, `catch`, and `finally`

`try` contains code that may fail, `catch` handles a matching exception, and
`finally` normally runs whether the operation succeeds or fails. Catch
specific exceptions, preserve useful context, and do not silently swallow
failures.

```java
try {
    service.process();
} catch (IOException exception) {
    logger.error("Processing failed", exception);
} finally {
    metrics.recordAttempt();
}
```

For closeable resources, try-with-resources is safer than manual cleanup.

### 18. Try-with-resources

Try-with-resources automatically closes every object implementing
`AutoCloseable`, even when an exception occurs.

```java
try (var input = Files.newBufferedReader(path)) {
    return input.readLine();
}
```

If both the body and `close()` throw, the close exception is retained as a
suppressed exception. This is preferred for files, streams, sockets, and JDBC
resources.

### 19. Java Collections Framework

The Collections Framework provides interfaces and implementations for common
data structures. Important interfaces include `List`, `Set`, `Queue`, and
`Map`; common implementations include `ArrayList`, `HashSet`, `ArrayDeque`,
and `HashMap`.

Choose based on required ordering, uniqueness, lookup complexity, concurrency,
and memory characteristics rather than choosing by habit.

### 20. `ArrayList` versus `LinkedList`

`ArrayList` stores elements in a resizable array, gives fast indexed access,
and is usually the default `List`. Inserting in the middle may move elements.
`LinkedList` stores nodes linked together, has slower indexed access, and has
overhead per node. It is useful only for specific deque or iterator-insertion
patterns; `ArrayDeque` is often better for queue/deque operations.

### 21. `HashMap`, `LinkedHashMap`, and `TreeMap`

- `HashMap`: hash-based lookup, no guaranteed iteration order, average
  constant-time lookup.
- `LinkedHashMap`: hash-based lookup plus predictable insertion order (or
  access order when configured).
- `TreeMap`: sorted keys using natural ordering or a `Comparator`, with
  logarithmic lookup.

The correct choice depends on whether ordering or sorting is part of the
requirement.

### 22. How `HashMap` works

`HashMap` computes a hash for a key, maps it to a bucket, and uses `equals()`
to resolve keys that land in the same bucket. It resizes when its load
threshold is reached. In modern Java implementations, a heavily colliding
bucket can be treeified to improve worst-case lookup behavior.

`HashMap` is not thread-safe. Use a suitable concurrent design, such as
`ConcurrentHashMap`, when multiple threads access shared mutable state.

### 23. Mutable keys in a `HashMap`

If a key changes in a field used by `equals()` or `hashCode()` after insertion,
the map may search the wrong bucket and fail to find the key. The entry still
exists, but normal lookup may not reach it.

Use immutable keys, such as `String` or an immutable value object, and do not
mutate key state while it is stored in the map.

### 24. `HashSet`, `LinkedHashSet`, and `TreeSet`

- `HashSet`: unique elements with no guaranteed iteration order.
- `LinkedHashSet`: unique elements in predictable insertion order.
- `TreeSet`: unique elements kept sorted, using natural ordering or a
  `Comparator`.

All rely on equality/ordering rules. A `TreeSet` considers two values
duplicates when comparison returns zero, even if `equals()` says otherwise,
so the ordering contract must be designed consistently.

### 25. Generics

Generics provide compile-time type safety and reduce casts:

```java
List<String> cities = new ArrayList<>();
cities.add("Pune");
String city = cities.get(0);
```

Java implements generics mainly through type erasure, so most generic type
arguments are not available at runtime. Use bounded wildcards when designing
flexible APIs, for example `List<? extends Number>` for producers.

### 26. `Comparable` versus `Comparator`

`Comparable<T>` defines a type's natural ordering inside the type through
`compareTo`. `Comparator<T>` defines an external ordering and allows multiple
sort rules without modifying the class.

```java
users.sort(Comparator.comparing(User::name));
```

Comparators should be consistent and should not overflow by subtracting
integers to compare values.

### 27. Streams and lambda expressions

A lambda is a concise function value used where a functional interface is
expected:

```java
names.forEach(name -> System.out.println(name));
```

A stream is a pipeline for declarative processing of a data source. Streams
are not collections, are usually lazy until a terminal operation, and should
not be used when a normal loop makes stateful or exception-heavy logic clearer.

### 28. `map`, `filter`, and `reduce`

- `map` transforms every element.
- `filter` keeps elements matching a predicate.
- `reduce` combines elements into one result.

```java
int total = amounts.stream()
        .filter(amount -> amount > 0)
        .mapToInt(Integer::intValue)
        .sum(); // reduction
```

Keep stream operations side-effect free where possible, especially if
parallel execution may be introduced later.

### 29. Functional interface

A functional interface has exactly one abstract method. It can be implemented
with a lambda or method reference. It may still contain default and static
methods.

```java
@FunctionalInterface
interface Validator {
    boolean valid(String value);
}

Validator nonBlank = value -> !value.isBlank();
```

Common examples are `Predicate<T>`, `Function<T, R>`, `Consumer<T>`, and
`Supplier<T>`.

### 30. Interface default method versus static method

A default method is inherited by implementing classes and can be overridden.
A static interface method belongs to the interface itself and is called using
the interface name; it is not inherited as an instance method.

```java
interface Payment {
    default boolean valid(int amount) {
        return amount > 0;
    }

    static Payment empty() {
        return () -> {};
    }

    void pay();
}
```

Call `payment.valid(100)` for the default method and `Payment.empty()` for the
static method. A class does not call an interface static method through an
instance.

## Sources

- [MoveInSync Interview Experience (On-Campus)](https://www.geeksforgeeks.org/interview-experiences/moveinsync-interview-experience-on-campus/)
- [MoveInSync Interview Experience for BackEnd Engineer Role](https://www.geeksforgeeks.org/interview-experiences/moveinsync-interview-experience-for-backend-engineer-role/)
- [MoveInSync: Basic Java/OOPS, Spring Boot annotations, array reversal, design patterns](https://www.glassdoor.co.in/Interview/Basic-java-OOPS-Spring-Boot-Annotations-Reverse-array-by-n-positions-Design-patterns-Coding-Style-assessment-QTN_8758485.htm)
- [MoveInSync: OOP concepts and C++ versus Java](https://www.glassdoor.co.in/Interview/Explain-oops-concept-Difference-between-c-and-java-QTN_7167227.htm)
- [MoveInSync: basic Java and design questions](https://www.glassdoor.co.in/Interview/Basic-Java-question-and-some-design-questions-QTN_2988479.htm)

The Glassdoor pages may require login or have limited public visibility. The
question wording above preserves only what is publicly visible in the report
titles/snippets; it does not invent hidden details.

## Runnable examples

- [MoveInSync.java](./MoveInSync.java)
- [MoveInSync.cpp](./MoveInSync.cpp)
- [MoveInSync.py](./MoveInSync.py)

Run from this folder:

```powershell
javac MoveInSync.java; java MoveInSync
g++ -std=c++17 -Wall -Wextra -pedantic MoveInSync.cpp -o MoveInSync.exe; .\MoveInSync.exe
python MoveInSync.py
```
