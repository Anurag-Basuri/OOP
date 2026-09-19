/*
 * Runnable examples of inheritance, field hiding, method overriding,
 * constructor order, and access through parent/child references.
 *
 * Compile and run:
 *   javac Inheritance.java
 *   java Inheritance
 */

class Animal {
    int value = 10;
    static String category = "Animal";

    Animal() {
        System.out.println("1. Animal constructor");
    }

    void eat() {
        System.out.println("Animal.eat()");
    }

    void printFields() {
        System.out.println("Animal.value = " + value);
        System.out.println("Animal.category = " + category);
    }
}

class Dog extends Animal {
    // This is a second field. It does not override Animal.value.
    int value = 20;
    static String category = "Dog";

    Dog() {
        super(); // Must be the first statement; initializes the Animal part.
        System.out.println("2. Dog constructor");
    }

    @Override
    void eat() {
        System.out.println("Dog.eat()");
    }

    void bark() {
        System.out.println("Dog.bark()");
    }

    void printFields() {
        System.out.println("Dog.value = " + value);
        System.out.println("Animal.value = " + super.value);
        System.out.println("Dog.category = " + category);
        System.out.println("Animal.category = " + Animal.category);
    }

    int parentValue() {
        return super.value;
    }

    void callParentEat() {
        super.eat();
        eat();
    }
}

public class Inheritance {
    static void separator(String title) {
        System.out.println("\n--- " + title + " ---");
    }

    public static void main(String[] args) {
        separator("Construction order");
        Dog dog = new Dog();

        separator("Access through a child reference");
        System.out.println("dog.value = " + dog.value);             // 20
        System.out.println("((Animal) dog).value = "
                + ((Animal) dog).value);                            // 10
        dog.printFields();                                          // Child method

        separator("Access through a parent reference");
        Animal animal = dog; // Upcasting: safe and implicit
        System.out.println("animal.value = " + animal.value);       // 10
        System.out.println("animal.category = " + animal.category); // Animal
        animal.eat();                                               // Dog.eat()
        animal.printFields();                                       // Dog.printFields()

        separator("Downcasting and parent access");
        if (animal instanceof Dog) {
            Dog sameDog = (Dog) animal;
            sameDog.bark();
            System.out.println("sameDog.value = " + sameDog.value); // 20
            System.out.println("super.value is available only inside Dog: "
                    + sameDog.parentValue());
        }

        separator("Explicit parent method call");
        dog.callParentEat();

        separator("Important rule");
        System.out.println("Fields are hidden, not overridden.");
        System.out.println("Instance methods are overridden and dynamically dispatched.");
        System.out.println("Static fields are hidden and resolved from the reference type.");
    }
}
