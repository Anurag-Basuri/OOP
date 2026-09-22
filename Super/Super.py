class Animal:
    def __init__(self, name: str) -> None:
        self.name = name

    def eat(self) -> None:
        print(f"{self.name} eats")


class Dog(Animal):
    def eat(self) -> None:
        super().eat()
        print(f"{self.name} chews")


def main() -> None:
    Dog("Bruno").eat()


if __name__ == "__main__":
    main()
