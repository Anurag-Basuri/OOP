from typing import ClassVar


class Student:
    count: ClassVar[int] = 0

    def __init__(self) -> None:
        Student.count += 1


class MathUtils:
    @staticmethod
    def square(value: int) -> int:
        return value * value


def main() -> None:
    Student()
    Student()
    print(f"Students: {Student.count}")
    print(f"Square: {MathUtils.square(5)}")


if __name__ == "__main__":
    main()
