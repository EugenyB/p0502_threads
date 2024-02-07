package main3;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Main3 {

    private int finished;
    private double total;

    Lock lock;
    Condition condition;

    public static void main(String[] args) {
        new Main3().run();
    }

    private void run() {
        double a = 0;
        double b = Math.PI;
        int n = 1_000_000_000;
        int nThreads = 1000;
        double delta = (b-a)/nThreads;
        total = 0;
        finished = 0;
        lock = new ReentrantLock();
        condition = lock.newCondition();
        long start = System.currentTimeMillis();
        for (int i = 0; i < nThreads; i++) {
            double ai = a + i * delta;
            double bi = ai + delta;
            Thread.ofVirtual().start(new IntegralCalculatorThread(ai, bi, n / nThreads, Math::sin, this));
        }
        lock.lock();
        try {
            while (finished < nThreads) {
                condition.await();;
            }
        } catch (InterruptedException ignored) {
        } finally {
            lock.unlock();
        }
        long finish = System.currentTimeMillis();
        System.out.println("result = " + total);
        System.out.println(finish - start);
    }

    public void sendResult(double value) {
        try {
            lock.lock();
            total += value;
            finished++;
            condition.signal();
        } finally {
            lock.unlock();
        }
    }
}
