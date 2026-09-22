from abc import ABC, abstractmethod


class Shape(ABC):
    @abstractmethod
    def area(self) -> float:
        ...

    def print_area(self) -> None:
        print(f"Area: {self.area()}")


class Circle(Shape):
    def __init__(self, radius: float) -> None:
        self.radius = radius

    def area(self) -> float:
        return 3.141592653589793 * self.radius**2


def main() -> None:
    shape: Shape = Circle(2)
    shape.print_area()


if __name__ == "__main__":
    main()
