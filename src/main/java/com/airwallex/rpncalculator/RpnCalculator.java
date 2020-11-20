package com.airwallex.rpncalculator;

import com.airwallex.rpncalculator.exception.InsufficientParameterException;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Iterator;

/**
 * This the calculator that actually performs the calculations.
 * <p>
 * It can be extended to support more functionalities in the future.
 *
 * @author Zhu Zhaohua
 */
public class RpnCalculator {
    private Deque<BigDecimal> valueStack;

    // Default output format, with at most 10 decimal places
    private static final String OUTPUT_FORMAT = "#0.##########";

    // The scale of number, i.e., number of decimal digits. Used to keep precision in calculation for division & sqrt
    public static final int SCALE = 20;

    public RpnCalculator() {
        valueStack = new ArrayDeque<>();
    }

    public void doPlus(BigDecimal operand) {
        BigDecimal result = popValue().add(operand);
        valueStack.push(result);
    }

    public void doMinus(BigDecimal operand) {
        BigDecimal result = popValue().subtract(operand);
        valueStack.push(result);
    }

    public void doMultiply(BigDecimal operand) {
        BigDecimal result = popValue().multiply(operand);
        valueStack.push(result);
    }

    public void doDivision(BigDecimal operand) {
        if (operand.compareTo(BigDecimal.ZERO) == 0) {
            recordValue(operand);
            throw new ArithmeticException("Dividing by 0 is not allowed.");
        }
        int precision = operand.stripTrailingZeros().precision() - Math.max(0, operand.stripTrailingZeros().scale()) + SCALE;
        BigDecimal result = popValue().divide(operand, new MathContext(precision));
        valueStack.push(result);
    }

    public void doSquareRoot(BigDecimal operand) {
        if (operand.compareTo(BigDecimal.ZERO) < 0) {
            recordValue(operand);
            throw new ArithmeticException("Square root of negative number is not valid.");
        }
        int precision = operand.stripTrailingZeros().precision() - Math.max(0, operand.stripTrailingZeros().scale()) + SCALE;
        BigDecimal result = operand.sqrt(new MathContext(precision));
        valueStack.push(result);
    }

    public void recordValue(BigDecimal value) {
        valueStack.push(value);
    }

    public BigDecimal popValue() {
        if (valueStack.isEmpty()) {
            throw new InsufficientParameterException();
        }
        return valueStack.pop();
    }

    public void clear() {
        valueStack = new ArrayDeque<>();
    }

    public int getValueCount() {
        return valueStack.size();
    }

    /**
     * Form a whitespace-separated string of values in the value stack.
     */
    public String formValueStackString() {
        DecimalFormat formatter = new DecimalFormat(OUTPUT_FORMAT);
        formatter.setRoundingMode(RoundingMode.DOWN);

        Iterator<BigDecimal> itr = valueStack.descendingIterator();

        StringBuilder sb = new StringBuilder("stack:");
        while (itr.hasNext()) {
            sb.append(" ").append(formatter.format(itr.next()));
        }

        return sb.toString();
    }
}
