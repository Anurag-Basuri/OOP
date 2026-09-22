class Counter:
    def __init__(self, value: int) -> None:
        self.value = value

    def increment(self) -> "Counter":
        self.value += 1
        return self

    def print_value(self) -> None:
        print(f"Value: {self.value}")


def main() -> None:
    Counter(0).increment().increment().print_value()


if __name__ == "__main__":
    main()
