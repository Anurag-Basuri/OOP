#include <iostream>
#include <memory>
#include <string>
#include <vector>

class Animal {
public:
    virtual ~Animal() = default;
    virtual void speak() const {
        std::cout << "Animal sound\n";
    }
};

class Dog final : public Animal {
public:
    void speak() const override {
        std::cout << "Woof\n";
    }
};

class Cat final : public Animal {
public:
    void speak() const override {
        std::cout << "Meow\n";
    }
};

int add(int a, int b) {
    return a + b;
}

double add(double a, double b) {
    return a + b;
}

int main() {
    std::vector<std::unique_ptr<Animal>> animals;
    animals.push_back(std::make_unique<Dog>());
    animals.push_back(std::make_unique<Cat>());

    for (const auto& animal : animals) {
        animal->speak();
    }

    std::cout << "Overloading: " << add(2, 3) << ", " << add(2.5, 3.5) << '\n';
}
