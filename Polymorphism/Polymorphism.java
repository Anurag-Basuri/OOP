/*
 * Polymorphism interview and OA practice in one runnable file.
 *
 * Compile:
 *   javac Polymorphism.java
 * Run:
 *   java Polymorphism
 *
 * Polymorphism means that one common operation can work with different
 * objects, while each object supplies the appropriate implementation.
 */

import java.util.Arrays;
import java.util.List;

public class Polymorphism {
    public static void main(String[] args) {
        overloading();
        overridingAndDynamicDispatch();
        fieldsAreHidden();
        upcastingAndDowncasting();
        abstractClassPolymorphism();
        interfacePolymorphism();
        defaultMethods();
        covariantReturnTypes();
        staticFinalAndPrivateMethods();
        overloadResolutionTrap();
        constructorDispatchWarning();
        commonInterviewRules();
    }

    private static void title(String text) {
        System.out.println("\n=== " + text + " ===");
    }

    // 1. Compile-time polymorphism: the compiler chooses an overload.
    private static void overloading() {
        title("Method overloading");
        Calculator calculator = new Calculator();

        System.out.println(calculator.add(2, 3));             // int + int
        System.out.println(calculator.add(2.5, 3.5));         // double + double
        System.out.println(calculator.add("Java", "OOP"));   // String + String
        System.out.println(calculator.add(1, 2, 3));          // three ints

        // The return type alone cannot create an overload.
        // int calculate(int x) and double calculate(int x) is illegal.
    }

    // 2. Runtime polymorphism: the JVM chooses the override from the object.
    private static void overridingAndDynamicDispatch() {
        title("Overriding and dynamic dispatch");
        Animal[] animals = {new Dog("Bruno"), new Cat("Misty"), new Cow("Daisy")};

        for (Animal animal : animals) {
            animal.speak();       // Dog/Cat/Cow implementation
            animal.move();        // Dog/Cat/Cow implementation
            animal.describe();   // inherited method calling an overridden method
        }

        makeAnimalSpeak(new Dog("Rex"));
        makeAnimalSpeak(new Cat("Luna"));
    }

    private static void makeAnimalSpeak(Animal animal) {
        animal.speak(); // Caller uses the parent type; child behavior is selected.
    }

    // 3. Fields are hidden, not overridden. Methods are dynamically dispatched.
    private static void fieldsAreHidden() {
        title("Field hiding versus method overriding");
        Dog dog = new Dog("Field-test");
        Animal animal = dog;

        System.out.println(dog.kind);       // Dog field: "Dog"
        System.out.println(animal.kind);   // Animal field: "Animal"
        System.out.println(dog.name);       // inherited field

        dog.speak();                       // Dog method
        animal.speak();                    // Dog method
        System.out.println("Animal field: " + dog.parentKind());
    }

    // 4. Upcasting is safe and implicit. Downcasting must be checked.
    private static void upcastingAndDowncasting() {
        title("Upcasting and downcasting");
        Dog dog = new Dog("Cast-test");
        Animal animal = dog; // Upcasting: every Dog is an Animal.
        animal.speak();

        if (animal instanceof Dog) {
            Dog sameDog = (Dog) animal; // Checked downcast.
            sameDog.fetch();
        }

        Animal justAnimal = new Animal();
        if (justAnimal instanceof Dog) {
            ((Dog) justAnimal).fetch();
        } else {
            System.out.println("justAnimal is not a Dog; cast would fail.");
        }

        // Dog wrong = (Dog) new Cat("Not a dog"); // ClassCastException.
        // animal.fetch(); // Compile error: Animal does not declare fetch().
    }

    // 5. Abstract classes provide a common contract and shared implementation.
    private static void abstractClassPolymorphism() {
        title("Abstract class polymorphism");
        Shape[] shapes = {new Circle(2), new Rectangle(3, 4)};

        for (Shape shape : shapes) {
            System.out.println(shape.name() + " area = " + shape.area());
            shape.printArea(); // Concrete method uses the abstract method.
        }

        // Shape shape = new Shape(); // Compile error: abstract class.
    }

    // 6. Interfaces allow unrelated classes to share a polymorphic contract.
    private static void interfacePolymorphism() {
        title("Interface polymorphism");
        List<Payment> payments = Arrays.asList(
                new CardPayment("card-123"),
                new UpiPayment("user@upi"),
                new CashPayment());

        for (Payment payment : payments) {
            payment.pay(100);
            payment.receipt();
        }

        Printable printable = new Invoice();
        printable.print(); // Object can be viewed through its interface.
    }

    // 7. Interface default methods can be inherited or overridden.
    private static void defaultMethods() {
        title("Interface default methods");
        SmartPhone phone = new SmartPhone();
        phone.start();
        phone.restart(); // Inherited default method.
        phone.stop();    // Phone's override.

        // If two interfaces provide the same default method, the class must
        // override it and resolve the conflict explicitly.
        MultiFunctionDevice device = new MultiFunctionDevice();
        device.scan();
    }

    // 8. A child override may return a more specific type.
    private static void covariantReturnTypes() {
        title("Covariant return types");
        Animal animal = new Dog("Return-test");
        Animal copy = animal.copy();
        System.out.println(copy.getClass().getSimpleName());

        Dog dog = new Dog("Return-test");
        Dog dogCopy = dog.copy(); // Dog.copy() returns Dog, not only Animal.
        dogCopy.fetch();
    }

    // 9. static, final, and private methods do not participate in overriding.
    private static void staticFinalAndPrivateMethods() {
        title("static, final, and private method rules");
        ParentMethods parent = new ChildMethods();

        parent.instanceMethod(); // Child implementation: overridden.
        ParentMethods.staticMethod(); // Parent implementation: static methods hide.
        ChildMethods.staticMethod();
        parent.callPrivateMethod(); // Parent method calls Parent's private method.

        ChildMethods child = new ChildMethods();
        child.callPrivateMethod();  // Child method calls Child's private method.
        child.finalMethod();        // Inherited; it cannot be overridden.
    }

    // 10. Overload selection uses the reference/argument types at compile time.
    private static void overloadResolutionTrap() {
        title("Overload resolution trap");
        Animal animal = new Dog("Overload-test");
        Dog dog = new Dog("Overload-test");
        AnimalHandler handler = new AnimalHandler();

        handler.handle(animal); // handle(Animal), despite object being a Dog.
        handler.handle(dog);    // handle(Dog), because reference type is Dog.
        handler.handle((Object) dog); // handle(Object).

        // Overloading and overriding can combine:
        // first choose handle(Animal) at compile time, then call any
        // overridden methods inside that selected method at runtime.
    }

    // 11. Never call overridable methods from constructors.
    private static void constructorDispatchWarning() {
        title("Constructor dispatch warning");
        new ChildInitialization();
        // The child fields are not initialized when the parent constructor
        // invokes the overridden method.
    }

    private static void commonInterviewRules() {
        title("Interview rules");
        System.out.println("1. Overloading: same name, different parameters; compile time.");
        System.out.println("2. Overriding: child replaces inherited instance behavior; runtime.");
        System.out.println("3. Parent reference can hold a child object (upcasting).");
        System.out.println("4. Fields and static methods are hidden, not overridden.");
        System.out.println("5. private methods are not inherited and cannot be overridden.");
        System.out.println("6. final methods cannot be overridden.");
        System.out.println("7. Constructors are not polymorphic.");
        System.out.println("8. A parent reference exposes only the parent API.");
        System.out.println("9. Use instanceof before a downcast.");
        System.out.println("10. Prefer interfaces/abstract types at API boundaries.");
    }
}

class Calculator {
    int add(int a, int b) {
        return a + b;
    }

    double add(double a, double b) {
        return a + b;
    }

    String add(String a, String b) {
        return a + b;
    }

    int add(int a, int b, int c) {
        return a + b + c;
    }
}

class Animal {
    String kind = "Animal";
    final String name;

    Animal() {
        this("unnamed");
    }

    Animal(String name) {
        this.name = name;
    }

    void speak() {
        System.out.println(name + ": generic animal sound");
    }

    void move() {
        System.out.println(name + ": animal moves");
    }

    void describe() {
        System.out.println(name + " is a " + kind + " that speaks:");
        speak();
    }

    Animal copy() {
        return new Animal(name + "-copy");
    }
}

class Dog extends Animal {
    String kind = "Dog"; // Hides Animal.kind.

    // What is this function doing? It is a constructor that calls the parent constructor with the name parameter.
    Dog(String name) {
        super(name);
    }

    @Override
    void speak() {
        System.out.println(name + ": woof");
    }

    @Override
    void move() {
        System.out.println(name + ": runs");
    }

    void fetch() {
        System.out.println(name + ": fetches the ball");
    }

    String parentKind() {
        return super.kind;
    }

    @Override
    Dog copy() {
        return new Dog(name + "-copy");
    }
}

class Cat extends Animal {
    Cat(String name) {
        super(name);
    }

    @Override
    void speak() {
        System.out.println(name + ": meow");
    }

    @Override
    void move() {
        System.out.println(name + ": walks silently");
    }
}

class Cow extends Animal {
    Cow(String name) {
        super(name);
    }

    @Override
    void speak() {
        System.out.println(name + ": moo");
    }

    @Override
    void move() {
        System.out.println(name + ": walks");
    }
}

abstract class Shape {
    abstract double area();

    abstract String name();

    void printArea() {
        System.out.println("computed area = " + area());
    }
}

class Circle extends Shape {
    private final double radius;

    Circle(double radius) {
        this.radius = radius;
    }

    @Override
    double area() {
        return Math.PI * radius * radius;
    }

    @Override
    String name() {
        return "Circle";
    }
}

class Rectangle extends Shape {
    private final double width;
    private final double height;

    Rectangle(double width, double height) {
        this.width = width;
        this.height = height;
    }

    @Override
    double area() {
        return width * height;
    }

    @Override
    String name() {
        return "Rectangle";
    }
}

interface Payment {
    void pay(int amount);

    default void receipt() {
        System.out.println("Receipt generated");
    }
}

class CardPayment implements Payment {
    private final String cardNumber;

    CardPayment(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    @Override
    public void pay(int amount) {
        System.out.println("Paid " + amount + " by card " + cardNumber);
    }
}

class UpiPayment implements Payment {
    private final String address;

    UpiPayment(String address) {
        this.address = address;
    }

    @Override
    public void pay(int amount) {
        System.out.println("Paid " + amount + " through UPI " + address);
    }
}

class CashPayment implements Payment {
    @Override
    public void pay(int amount) {
        System.out.println("Paid " + amount + " in cash");
    }
}

interface Printable {
    void print();
}

class Invoice implements Printable {
    @Override
    public void print() {
        System.out.println("Invoice printed");
    }
}

interface Startable {
    default void start() {
        System.out.println("Startable.start()");
    }
}

interface Stoppable {
    default void stop() {
        System.out.println("Stoppable.stop()");
    }
}

class SmartPhone implements Startable, Stoppable {
    @Override
    public void stop() {
        System.out.println("SmartPhone.stop()");
    }

    void restart() {
        Startable.super.start();
        stop();
    }
}

interface ScannerDevice {
    default void scan() {
        System.out.println("ScannerDevice.scan()");
    }
}

interface CameraDevice {
    default void scan() {
        System.out.println("CameraDevice.scan()");
    }
}

class MultiFunctionDevice implements ScannerDevice, CameraDevice {
    @Override
    public void scan() {
        ScannerDevice.super.scan();
        CameraDevice.super.scan();
        System.out.println("MultiFunctionDevice.scan()");
    }
}

class ParentMethods {
    void instanceMethod() {
        System.out.println("Parent instance method");
    }

    static void staticMethod() {
        System.out.println("Parent static method");
    }

    private void privateMethod() {
        System.out.println("Parent private method");
    }

    void callPrivateMethod() {
        privateMethod();
    }

    final void finalMethod() {
        System.out.println("Parent final method");
    }
}

class ChildMethods extends ParentMethods {
    @Override
    void instanceMethod() {
        System.out.println("Child instance method");
    }

    static void staticMethod() {
        System.out.println("Child static method");
    }

    private void privateMethod() {
        System.out.println("Child private method");
    }

    @Override
    void callPrivateMethod() {
        privateMethod();
    }

    // void finalMethod() {} // Compile error: cannot override final method.
}

class AnimalHandler {
    void handle(Object value) {
        System.out.println("handle(Object)");
    }

    void handle(Animal value) {
        System.out.println("handle(Animal)");
    }

    void handle(Dog value) {
        System.out.println("handle(Dog)");
    }
}

class ParentInitialization {
    ParentInitialization() {
        System.out.println("Parent constructor");
        printValue(); // Dangerous: invokes an overridable method.
    }

    void printValue() {
        System.out.println("Parent implementation");
    }
}

class ChildInitialization extends ParentInitialization {
    private String value = "initialized in child";

    ChildInitialization() {
        System.out.println("Child constructor: value = " + value);
    }

    @Override
    void printValue() {
        System.out.println("Child implementation: value = " + value);
    }
}
