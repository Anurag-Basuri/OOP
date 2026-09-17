import java.util.Scanner;

class Animal {
    Animal() {
        System.out.println("Animal constructor called");
    }

    void eat() {
        System.out.println("Animal is eating");
    }
}

class Dog extends Animal {
    Dog() {
        super(); // Calls Animal's default constructor
        System.out.println("Dog constructor called");
    }

    void bark() {
        System.out.println("Dog is barking");
    }

    void eat() {
        System.out.println("Dog is eating");
    }
}

class Inheritance {
    public static void main(String[] args) {
        Dog dog = new Dog();
        dog.eat(); // Inherited from Animal
        dog.bark(); // Defined in Dog
    }
}