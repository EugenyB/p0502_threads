package main3;

import main.IntegralCalculator;

import java.util.function.DoubleUnaryOperator;

public class IntegralCalculatorThread implements Runnable {

    private final IntegralCalculator calculator;
    private final Main3 main;

    public IntegralCalculatorThread(double a, double b, int n, DoubleUnaryOperator f, Main3 main) {
        calculator = new IntegralCalculator(a, b, n, f);
        this.main = main;
    }

    @Override
    public void run() {
        double v = calculator.calculate();
        main.sendResult(v);
    }
}
