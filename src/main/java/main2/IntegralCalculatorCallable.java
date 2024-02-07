package main2;

import main.IntegralCalculator;

import java.util.concurrent.Callable;
import java.util.function.DoubleUnaryOperator;

public class IntegralCalculatorCallable implements Callable<Double> {

    private final IntegralCalculator calculator;

    public IntegralCalculatorCallable(double a, double b, int n, DoubleUnaryOperator f) {
        calculator = new IntegralCalculator(a, b, n, f);
    }

    @Override
    public Double call() throws Exception {
        return calculator.calculate();
    }
}
