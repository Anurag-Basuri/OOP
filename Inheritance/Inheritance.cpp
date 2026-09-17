#include <iostream>
using namespace std;

class Animal {
    public: 
    Animal() {
        cout << "Animal constructor called" << endl;
    }

    void eat() {
        cout << "Animal is eating" << endl;
    }
};

class Dog : public Animal {
    public:
    Dog() {
        cout << "Dog constructor called" << endl;
    }

    void bark() {
        cout << "Dog is barking" << endl;
    }

    void eat() {
        cout << "Dog is eating" << endl;
    }
};

int main() {
        Dog dog;
        dog.eat(); // Inherited from Animal
        dog.bark(); // Defined in Dog
        return 0;
}
