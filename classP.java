class Laptop {
    private String company = "";
    private float cost = 0;

    public Laptop(int cost, String company) {
        System.out.println("Integer cost is: " + cost);
        System.out.println("Company name is" + company);
    }

    public Laptop(float cost, String company) {
        System.out.println("Floated cost is: " + cost);
        System.out.println("Company is: " + company);
    }
    
    public Laptop(double cost, String company) {
        System.out.println("Doubled cost is: " + cost);
        System.out.println("Company is: " + company);
    }
}

class classP {
    public static void main(String[] args) {
        Laptop demo1 = new Laptop(10, "HP");
        Laptop demo2 = new Laptop(10.0, "Asus");
        Laptop demo3 = new Laptop(10.002, "Lenevo");
    }
}
