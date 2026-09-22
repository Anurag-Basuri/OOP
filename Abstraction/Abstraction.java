abstract class Shape {
    abstract double area();

    void printArea() {
        System.out.println("Area: " + area());
    }
}

class Circle extends Shape {
    private final double radius;

    Circle(double radius) {
        this.radius = radius;
    }

    @Override
    double area() {
        return Math.PI * radius * radius;
    }
}

public class Abstraction {
    public static void main(String[] args) {
        Shape shape = new Circle(2);
        shape.printArea();
    }
}
