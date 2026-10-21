public class Main {

    public static void main(String[] args) throws InterruptedException {

        Thread anna = new Thread(() -> sayHello("Anna"));
        Thread ben = new Thread(() -> sayHello("Ben"));
        Thread clara = new Thread(() -> sayHello("Clara"));

        anna.start();
        ben.start();
        clara.start();

        // wait until everybody is done before we say goodbye
        anna.join();
        ben.join();
        clara.join();

        System.out.println("main: all threads are finished");
    }

    private static void sayHello(String name) {
        for (int i = 1; i <= 5; i++) {
            System.out.println(name + " says hello, round " + i);
            try {
                // a short nap, so the other threads get a chance to talk too
                Thread.sleep((long) (Math.random() * 100));
            } catch (InterruptedException e) {
                return;
            }
        }
    }
}