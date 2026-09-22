from typing import Protocol


class Payment(Protocol):
    def pay(self, amount: float) -> None:
        ...


class CardPayment:
    def pay(self, amount: float) -> None:
        print(f"Card payment: {amount}")


def checkout(payment: Payment) -> None:
    payment.pay(100)


def main() -> None:
    checkout(CardPayment())


if __name__ == "__main__":
    main()
