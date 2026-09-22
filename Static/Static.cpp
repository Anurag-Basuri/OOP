#include <iostream>

class Student {
public:
    inline static int count = 0;

    Student() {
        ++count;
    }
};

class MathUtils {
public:
    static int square(int value) {
        return value * value;
    }
};

int main() {
    Student first;
    Student second;
    std::cout << "Students: " << Student::count << '\n';
    std::cout << "Square: " << MathUtils::square(5) << '\n';
}
