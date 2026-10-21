/**
 * A simple counter that is <b>not</b> thread-safe.
 * <p>
 * The methods {@link #increment()} and {@link #decrement()} each consist of
 * three separate steps (read, change, write). If two threads call them at the
 * same time, one update can overwrite the other and get lost. This is called
 * thread interference.
 * author: Aanchal, Farha ,Purnima
 */
class Counter {
    /** The current value of the counter. */
    private int c = 0;

    /**
     * Increases the counter by 1.
     * Not thread-safe: the read, add and write steps can be interrupted.
     */
    public void increment() {
        int b = c;
        b = b + 1;
        c = b;
    }

    /**
     * Decreases the counter by 1.
     * Not thread-safe: the read, subtract and write steps can be interrupted.
     */
    public void decrement() {
        int d = c;
        d = d - 1;
        c = d;
    }

    /**
     * Returns the current value of the counter.
     *
     * @return the current value
     */
    public int value() {
        return c;
    }
}

/**
 * The same counter as {@link Counter}, but thread-safe.
 * <p>
 * All methods are {@code synchronized}, so only one thread at a time can be
 * inside a method of the same object. The three steps of each method can
 * therefore no longer be interrupted by another thread, and no update is lost.
 */
class SafeCounter {
    /** The current value of the counter. */
    private int c = 0;

    /** Increases the counter by 1. Only one thread at a time can run this. */
    public synchronized void increment() {
        int b = c;
        b = b + 1;
        c = b;
    }

    /** Decreases the counter by 1. Only one thread at a time can run this. */
    public synchronized void decrement() {
        int d = c;
        d = d - 1;
        c = d;
    }

    /**
     * Returns the current value of the counter.
     *
     * @return the current value
     */
    public synchronized int value() {
        return c;
    }
}

/**
 * Demonstrates thread interference and how {@code synchronized} prevents it.
 * <p>
 * In every round, one thread increments a counter {@link #TIMES} times and a
 * second thread decrements the same counter {@link #TIMES} times. The correct
 * final value is therefore always 0. With the un-synchronized {@link Counter}
 * updates get lost and the result is often not 0. With the
 * {@link SafeCounter} the result is always 0.
 * <p>
 * Compile and run:
 * <pre>
 * javac CounterTest.java
 * java CounterTest
 * </pre>
 */
public class CounterTest {

    /** Number of increments (and of decrements) performed by each thread. */
    static final int TIMES = 1_000_000;

    /**
     * Runs the test five times with the un-synchronized {@link Counter} and
     * five times with the {@link SafeCounter}, and prints the final value of
     * each round. The expected value is always 0.
     *
     * @param args not used
     * @throws InterruptedException if a thread is interrupted while waiting
     */
    public static void main(String[] args) throws InterruptedException {

        System.out.println("Unsynchronized Counter (the result should be 0):");
        for (int round = 1; round <= 5; round++) {
            Counter counter = new Counter();
            run(counter::increment, counter::decrement);
            System.out.println("  round " + round + ": " + counter.value());
        }

        System.out.println();
        System.out.println("Synchronized Counter (the result should be 0):");
        for (int round = 1; round <= 5; round++) {
            SafeCounter counter = new SafeCounter();
            run(counter::increment, counter::decrement);
            System.out.println("  round " + round + ": " + counter.value());
        }
    }

    /**
     * Starts two threads on the same counter and waits until both are
     * finished. The first thread calls {@code up} {@link #TIMES} times, the
     * second thread calls {@code down} {@link #TIMES} times.
     *
     * @param up   the action of the first thread, e.g. {@code counter::increment}
     * @param down the action of the second thread, e.g. {@code counter::decrement}
     * @throws InterruptedException if a thread is interrupted while waiting
     */
    static void run(Runnable up, Runnable down) throws InterruptedException {
        Thread t1 = new Thread(() -> {
            for (int i = 0; i < TIMES; i++) {
                up.run();
            }
        });
        Thread t2 = new Thread(() -> {
            for (int i = 0; i < TIMES; i++) {
                down.run();
            }
        });

        t1.start();
        t2.start();
        t1.join();
        t2.join();
    }
}