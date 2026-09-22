#include <iostream>
#include <string>
#include <utility>

class Animal {
public:
    explicit Animal(std::string name) : name_(std::move(name)) {}

    virtual ~Animal() = default;

    virtual void eat() const {
        std::cout << name_ << " eats\n";
    }

protected:
    std::string name_;
};

class Dog final : public Animal {
public:
    explicit Dog(std::string name) : Animal(std::move(name)) {}

    void eat() const override {
        Animal::eat();
        std::cout << name_ << " chews\n";
    }
};

int main() {
    Dog dog("Bruno");
    dog.eat();
}
