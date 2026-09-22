class Animal {
    Animal(String name) {
        System.out.println("Created " + name);
    }

    void eat() {
        System.out.println("Animal eats");
    }
}

class Dog extends Animal {
    Dog(String name) {
        super(name);
    }

    @Override
    void eat() {
        super.eat();
        System.out.println("Dog chews");
    }
}

public class Super {
    public static void main(String[] args) {
        new Dog("Bruno").eat();
    }
}
