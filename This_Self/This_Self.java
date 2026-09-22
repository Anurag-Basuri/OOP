class Counter {
    private int value;

    Counter(int value) {
        this.value = value;
    }

    Counter increment() {
        this.value++;
        return this;
    }

    void print() {
        System.out.println("Value: " + this.value);
    }
}

public class This_Self {
    public static void main(String[] args) {
        new Counter(0).increment().increment().print();
    }
}
