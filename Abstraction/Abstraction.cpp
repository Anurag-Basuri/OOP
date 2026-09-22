#include <iostream>
#include <memory>

class Shape {
public:
    virtual ~Shape() = default;
    virtual double area() const = 0;

    void printArea() const {
        std::cout << "Area: " << area() << '\n';
    }
};

class Circle final : public Shape {
public:
    explicit Circle(double radius) : radius_(radius) {}

    double area() const override {
        return 3.141592653589793 * radius_ * radius_;
    }

private:
    double radius_;
};

int main() {
    std::unique_ptr<Shape> shape = std::make_unique<Circle>(2);
    shape->printArea();
}
