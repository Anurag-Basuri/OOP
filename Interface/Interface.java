interface Payment {
    void pay(double amount);

    default void receipt() {
        System.out.println("Receipt generated");
    }
}

class CardPayment implements Payment {
    @Override
    public void pay(double amount) {
        System.out.println("Card payment: " + amount);
    }
}

public class Interface {
    public static void main(String[] args) {
        Payment payment = new CardPayment();
        payment.pay(100);
        payment.receipt();
    }
}
