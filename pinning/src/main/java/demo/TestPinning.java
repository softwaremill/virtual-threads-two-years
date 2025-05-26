package demo;

// -Djdk.tracePinnedThreads=full
public class TestPinning {

    synchronized void criticalSection() throws InterruptedException {
        Thread.sleep(1000);
    }

    void test() throws InterruptedException {
        criticalSection();
    }

    public static void main(String[] args) throws InterruptedException {
        Thread.ofVirtual().start(() -> {
            try {
                new TestPinning().test();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }).join();
    }
}
