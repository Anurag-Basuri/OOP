class Animal:
    def speak(self) -> None:
        print("Animal sound")


class Dog(Animal):
    def speak(self) -> None:
        print("Bark")

    def fetch(self) -> None:
        print("Dog fetches the ball")


def main() -> None:
    animal: Animal = Dog()
    animal.speak()

    dog = Dog()
    dog.fetch()


if __name__ == "__main__":
    main()
