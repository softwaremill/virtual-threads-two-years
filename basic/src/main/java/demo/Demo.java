package demo;

import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentHashMap;

public class Demo {
    public static void main(String[] args) {
        timed(() -> {
            var threads = new Thread[10_000_000];
            var results = ConcurrentHashMap.newKeySet();
            for (int i = 0; i < threads.length; i++) {
                threads[i] = Thread
                        .ofVirtual()
                        .start(() -> results.add(0));
            }

            for (Thread thread : threads) {
                thread.join();
            }

            return null;
        });
    }

    private static <V> V timed(Callable<V> r) {
        long start = System.currentTimeMillis();
        try {
            return r.call();
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            long end = System.currentTimeMillis();
            System.out.println("Execution time: " + (end - start) + " ms");
        }
    }
}
