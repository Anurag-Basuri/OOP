#include <iostream>
#include <memory>

class Payment {
public:
    virtual ~Payment() = default;
    virtual void pay(double amount) = 0;
};

class CardPayment final : public Payment {
public:
    void pay(double amount) override {
        std::cout << "Card payment: " << amount << '\n';
    }
};

int main() {
    std::unique_ptr<Payment> payment = std::make_unique<CardPayment>();
    payment->pay(100);
}
