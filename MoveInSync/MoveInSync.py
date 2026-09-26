from __future__ import annotations

from abc import ABC, abstractmethod
from concurrent.futures import ThreadPoolExecutor
from threading import Lock


class PaymentMethod(ABC):
    @abstractmethod
    def pay(self, amount: int) -> None:
        pass


class CardPayment(PaymentMethod):
    def pay(self, amount: int) -> None:
        print(f"Paid {amount} by card")


class Animal:
    def speak(self) -> None:
        print("Animal sound")


class Dog(Animal):
    def speak(self) -> None:
        super().speak()
        print("Dog barks")


class SeatBooking:
    def __init__(self) -> None:
        self._available = True
        self._lock = Lock()

    def book(self) -> bool:
        with self._lock:
            if not self._available:
                return False
            self._available = False
            return True


def main() -> None:
    animal: Animal = Dog()
    animal.speak()

    payment: PaymentMethod = CardPayment()
    payment.pay(500)

    seat = SeatBooking()
    with ThreadPoolExecutor(max_workers=2) as executor:
        results = list(executor.map(lambda _: seat.book(), (1, 2)))
    print(f"Booking results: {results}")


if __name__ == "__main__":
    main()
