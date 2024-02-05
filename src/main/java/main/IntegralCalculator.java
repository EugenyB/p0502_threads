package main;

import java.util.function.DoubleUnaryOperator;
import java.util.stream.IntStream;

public class IntegralCalculator {

    private final double a;
    private final double b;
    private final int n;
    DoubleUnaryOperator f;

    public IntegralCalculator(double a, double b, int n, DoubleUnaryOperator f) {
        this.a = a;
        this.b = b;
        this.n = n;
        this.f = f;
    }

    public double calculate() {
        double h = (b-a)/n;
        return IntStream.range(0, n).mapToDouble(i -> a + i * h).map(f).map(y->y*h).sum();
    }
}
