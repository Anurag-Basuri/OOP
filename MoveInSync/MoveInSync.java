import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class MoveInSync {
    interface PaymentMethod {
        void pay(int amount);
    }

    static final class CardPayment implements PaymentMethod {
        @Override
        public void pay(int amount) {
            System.out.println("Paid " + amount + " by card");
        }
    }

    static class Animal {
        void speak() {
            System.out.println("Animal sound");
        }
    }

    static final class Dog extends Animal {
        @Override
        void speak() {
            super.speak();
            System.out.println("Dog barks");
        }
    }

    static final class SeatBooking {
        private boolean available = true;

        synchronized boolean book() {
            if (!available) {
                return false;
            }
            available = false;
            return true;
        }
    }

    public static void main(String[] args) throws InterruptedException {
        Animal animal = new Dog();
        animal.speak();

        PaymentMethod payment = new CardPayment();
        payment.pay(500);

        List<String> cities = List.of("Bengaluru", "Pune");
        cities.stream().map(String::toUpperCase).forEach(System.out::println);

        SeatBooking seat = new SeatBooking();
        ExecutorService executor = Executors.newFixedThreadPool(2);
        for (int i = 1; i <= 2; i++) {
            int request = i;
            executor.submit(() ->
                    System.out.println("Request " + request + ": " + seat.book()));
        }
        executor.shutdown();
        executor.awaitTermination(1, TimeUnit.SECONDS);
    }
}
