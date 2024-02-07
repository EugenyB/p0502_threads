package main;


import java.util.function.DoubleUnaryOperator;

public class Main {

    private int finished;
    private double total;

    public static void main(String[] args) {
        new Main().run();
    }

    private void run() {
        double a = 0;
        double b = Math.PI;
        int n = 1000_000_000;
        int nThreads = 1000;
        DoubleUnaryOperator f = Math::sin;

        //IntegralCalculator calculator = new IntegralCalculator(a, b, n, f);
        finished = 0;
        total = 0;
        long start = System.currentTimeMillis();
        //double result = calculator.calculate();

        double delta = (b - a) / nThreads;
        for (int i = 0; i < nThreads; i++) {
            double ai = a + i * delta;
            double bi = ai + delta;
            Thread.startVirtualThread(new IntegralCalculatorThread(ai, bi, n/nThreads, f, this));
        }

        try {
            synchronized (this) {
                while (finished < nThreads) {
                    wait();
                }
            }
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        long finish = System.currentTimeMillis();
        System.out.println("result = " + total);
        System.out.println(finish - start);
    }

    public synchronized void sendResult(double v) {
        total += v;
        finished++;
        notify();
    }
}
