from abc import ABC, abstractmethod
from typing import Protocol


class Speaker(Protocol):
    def speak(self) -> str:
        ...


class Animal(ABC):
    @abstractmethod
    def speak(self) -> str:
        ...


class Dog(Animal):
    def speak(self) -> str:
        return "Woof"


class Robot:
    def speak(self) -> str:
        return "Beep"


def announce(speaker: Speaker) -> None:
    print(speaker.speak())


def main() -> None:
    announce(Dog())
    announce(Robot())


if __name__ == "__main__":
    main()
