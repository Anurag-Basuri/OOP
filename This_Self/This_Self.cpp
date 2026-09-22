#include <iostream>

class Counter {
public:
    explicit Counter(int value) : value_(value) {}

    Counter& increment() {
        ++this->value_;
        return *this;
    }

    void print() const {
        std::cout << "Value: " << this->value_ << '\n';
    }

private:
    int value_;
};

int main() {
    Counter(0).increment().increment().print();
}
