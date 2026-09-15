using namespace std;

// 1. Member Access Default:
class ClassExample {
    int x; // PRIVATE by default
};

struct StructExample {
    int x; // PUBLIC by default
};

// 2. Inheritance Default:
class Base {};

class DerivedClass : Base {};  // Inherits Base PRIVATELY by default
struct DerivedStruct : Base {}; // Inherits Base PUBLICLY by default

/**Everything else is same for both struct and class. */
int main() {
    ClassExample classObj;
    StructExample structObj;

    // Accessing member variables
    // classObj.x = 10; // Error: 'x' is private in 'ClassExample'
    structObj.x = 10; // OK: 'x' is public in 'StructExample'

    DerivedClass derivedClassObj;
    DerivedStruct derivedStructObj;

    // Inheritance access
    // Base* basePtr1 = &derivedClassObj; // Error: 'Base' is inaccessible due to private inheritance
    Base* basePtr2 = &derivedStructObj; // OK: 'Base' is accessible due to public inheritance

    return 0;
}