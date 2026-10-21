public class HelloWorld {
    public static void main(String[] args) {
        if (args.length == 0) {
            System.out.println("Hello, world! (no parameter given)");
        } else {
            System.out.println("Hello, world! Parameter: " + args[0]);
        }
    }
}