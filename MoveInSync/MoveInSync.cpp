#include <iostream>
#include <mutex>
#include <thread>
#include <vector>

class Animal {
public:
    virtual ~Animal() = default;
    virtual void speak() const {
        std::cout << "Animal sound\n";
    }
};

class Dog final : public Animal {
public:
    void speak() const override {
        Animal::speak();
        std::cout << "Dog barks\n";
    }
};

class SeatBooking {
public:
    bool book() {
        std::lock_guard<std::mutex> lock(mutex_);
        if (!available_) {
            return false;
        }
        available_ = false;
        return true;
    }

private:
    bool available_ = true;
    std::mutex mutex_;
};

int main() {
    Dog dog;
    Animal& animal = dog;
    animal.speak();

    SeatBooking seat;
    std::vector<std::thread> requests;
    for (int request = 1; request <= 2; ++request) {
        requests.emplace_back([&seat, request] {
            std::cout << "Request " << request << ": "
                      << std::boolalpha << seat.book() << '\n';
        });
    }
    for (std::thread& request : requests) {
        request.join();
    }
}
