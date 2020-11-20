package com.airwallex.rpncalculator;

import com.airwallex.rpncalculator.exception.InsufficientParameterException;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Iterator;

public class RpnCalculator {
    private Deque<BigDecimal> valueStack;

    // Default output format, with at most 10 decimal places
    private static final String OUTPUT_FORMAT = "#0.##########";

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
        BigDecimal result = popValue().divide(operand);
        valueStack.push(result);
    }

    public void doSquareRoot(BigDecimal operand) {
        BigDecimal result = operand.sqrt(MathContext.DECIMAL64);
        valueStack.push(result);
    }

    public void clear() {
        valueStack = new ArrayDeque<>();
    }

    public void removeOneValue() {
        valueStack.pop();
    }

    public BigDecimal popValue() {
        if (valueStack.isEmpty()) {
            throw new InsufficientParameterException();
        }
        return valueStack.pop();
    }

    public void recordNewValue(BigDecimal operand) {
        valueStack.push(operand);
    }

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

    public int getValueCount() {
        return valueStack.size();
    }
}
