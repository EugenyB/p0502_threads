package main2;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class Main {
    public static void main(String[] args) {
        new Main().run();
    }

    private void run() {
        double a = 0;
        double b = Math.PI;
        int n = 1_000_000_000;
        int nThreads = 1000;
        double delta = (b-a)/nThreads;
        long start = System.currentTimeMillis();
        try (ExecutorService es = Executors.newWorkStealingPool(100)) {
            List<Future<Double>> futures = new ArrayList<>();
            for (int i = 0; i < nThreads; i++) {
                double ai = a + i * delta;
                double bi = ai + delta;
                IntegralCalculatorCallable calculator = new IntegralCalculatorCallable(ai, bi, n / nThreads, Math::sin);
                Future<Double> future = es.submit(calculator);
                futures.add(future);
            }
            //es.shutdown();
            double total = 0;
            for (Future<Double> future : futures) {
                total += future.get();
            }
            long finish = System.currentTimeMillis();
            System.out.println("result = " + total);
            System.out.println(finish - start);
        } catch (ExecutionException | InterruptedException ignored) {
        }
    }
}
