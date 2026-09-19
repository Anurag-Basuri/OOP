/*
 * Runnable examples of inheritance, field hiding, method overriding,
 * constructor order, and access through parent/child references.
 *
 * Compile and run:
 *   g++ -std=c++17 -Wall -Wextra -pedantic Inheritance.cpp -o Inheritance
 *   ./Inheritance
 */

#include <iostream>
#include <string>

class Animal {
public:
    int value = 10;
    inline static const std::string category = "Animal";

    Animal() {
        std::cout << "1. Animal constructor\n";
    }

    virtual ~Animal() = default;

    virtual void eat() const {
        std::cout << "Animal::eat()\n";
    }

    void printFields() const {
        std::cout << "Animal::value = " << value << '\n';
        std::cout << "Animal::category = " << category << '\n';
    }
};

class Dog : public Animal {
public:
    // This is a second member. It does not override Animal::value.
    int value = 20;
    inline static const std::string category = "Dog";

    Dog() {
        std::cout << "2. Dog constructor\n";
    }

    void eat() const override {
        std::cout << "Dog::eat()\n";
    }

    void bark() const {
        std::cout << "Dog::bark()\n";
    }

    void printFields() const {
        std::cout << "Dog::value = " << value << '\n';
        std::cout << "Animal::value = " << Animal::value << '\n';
        std::cout << "Dog::category = " << category << '\n';
        std::cout << "Animal::category = " << Animal::category << '\n';
    }

    void callParentEat() const {
        Animal::eat();
        eat();
    }
};

void separator(const char* title) {
    std::cout << "\n--- " << title << " ---\n";
}

int main() {
    separator("Construction order");
    Dog dog;

    separator("Access through a child object");
    std::cout << "dog.value = " << dog.value << '\n';                 // 20
    std::cout << "dog.Animal::value = " << dog.Animal::value << '\n'; // 10
    dog.printFields();                                                 // Dog method

    separator("Access through a parent reference");
    Animal& animal = dog; // Upcasting: safe and implicit
    std::cout << "animal.value = " << animal.value << '\n';            // 10
    std::cout << "animal.category = " << Animal::category << '\n';
    animal.eat();                                                       // Dog::eat()
    animal.printFields();                                               // Animal::printFields()

    separator("Access through a parent pointer");
    Animal* animalPointer = &dog;
    animalPointer->eat();                                               // Dog::eat()
    animalPointer->printFields();                                       // Animal::printFields()
    if (Dog* sameDog = dynamic_cast<Dog*>(animalPointer)) {
        sameDog->bark();
        std::cout << "sameDog->value = " << sameDog->value << '\n';     // 20
    }

    separator("Explicit parent method call");
    dog.callParentEat();

    separator("Important rule");
    std::cout << "Data members are hidden, not dynamically dispatched.\n";
    std::cout << "Virtual methods are overridden and dynamically dispatched.\n";
    std::cout << "Static members are hidden and resolved by the class name.\n";

    return 0;
}
