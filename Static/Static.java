class Student {
    static int count;

    Student() {
        count++;
    }
}

public class Static {
    static int square(int value) {
        return value * value;
    }

    public static void main(String[] args) {
        new Student();
        new Student();
        System.out.println("Students: " + Student.count);
        System.out.println("Square: " + Static.square(5));
    }
}
