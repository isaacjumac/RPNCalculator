package com.airwallex.rpncalculator;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.math.BigDecimal;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Some test cases to make sure each function works as expected
 */
@RunWith(JUnit4.class)
public class RpnCalculatorTest {
    private RpnCalculator calculator;

    private static final BigDecimal TOLERANCE = new BigDecimal("0.000000000000001"); // 10^-15

    @Before
    public void setUp() {
        calculator = new RpnCalculator();
    }

    @Test
    public void doPlus() {
        BigDecimal[] inputs = {BigDecimal.valueOf(1), BigDecimal.valueOf(2), BigDecimal.valueOf(Math.PI)};
        for (BigDecimal v : inputs) {
            calculator.recordValue(v);
        }

        while (calculator.getValueCount() > 1) {
            calculator.doPlus(calculator.popValue());
        }

        String expected = "stack: 6.1415926535";
        assertEquals(expected, calculator.formValueStackString());
    }

    @Test
    public void doMinus() {
        BigDecimal[] inputs = {BigDecimal.valueOf(1), BigDecimal.valueOf(2), BigDecimal.valueOf(Math.PI)};
        for (int i = 0; i < inputs.length; i++) {
            BigDecimal v = inputs[i];
            calculator.recordValue(v);
        }

        while (calculator.getValueCount() > 1) {
            calculator.doMinus(calculator.popValue());
        }

        String expected = "stack: 2.1415926535";
        assertEquals(expected, calculator.formValueStackString());
    }

    @Test
    public void doMultiply() {
        BigDecimal[] inputs = {
                BigDecimal.valueOf(2),
                new BigDecimal("3.14159265358979323846") // Math.PI
        };
        // value calculated by a big number calculator with 30 decimal digits precision
        BigDecimal[] expected = {
                new BigDecimal("3878509413.969702905338797207102748902468"),
                new BigDecimal("7757018827.93940522188747625652648598917")
        };

        for (int i = 0; i < inputs.length; i++) {
            BigDecimal v = inputs[i];
            calculator.recordValue(v);
        }
        calculator.recordValue(new BigDecimal("1234567890.123456789012345"));

        BigDecimal value = calculator.popValue();
        for (int i = 0; i < expected.length; i++) {
            calculator.doMultiply(value);
            value = calculator.popValue();
        }
    }

    @Test
    public void doDivision() {
        BigDecimal[] inputs = {BigDecimal.valueOf(10), new BigDecimal("3.14159265358979323846")};
        for (int i = 0; i < inputs.length; i++) {
            BigDecimal v = inputs[i];
            calculator.recordValue(v);
        }

        BigDecimal value;
        do {
            value = calculator.popValue();
            calculator.doDivision(value);
        } while (calculator.getValueCount() > 1);

        // value calculated by a big number calculator with 30 decimal digits precision
        BigDecimal expected = new BigDecimal("3.183098861837906715380353574677");
        assertTrue(expected.subtract(calculator.popValue()).abs().compareTo(TOLERANCE) < 0);
    }

    @Test(expected = ArithmeticException.class)
    public void testDivisionByZero() {
        BigDecimal[] inputs = {BigDecimal.valueOf(2), BigDecimal.ZERO};
        for (int i = 0; i < inputs.length; i++) {
            BigDecimal v = inputs[i];
            calculator.recordValue(v);
        }

        BigDecimal value;
        do {
            value = calculator.popValue();
            calculator.doDivision(value);
        } while (calculator.getValueCount() > 1);
    }

    @Test
    public void doSquareRoot() {
        BigDecimal[] inputs = {
                BigDecimal.ZERO, BigDecimal.ONE,
                BigDecimal.valueOf(10),
                new BigDecimal("3.14159265358979323846")};
        BigDecimal[] expected = {
                BigDecimal.ZERO,
                BigDecimal.ONE,
                new BigDecimal("3.162277660168379331998893544432"),
                new BigDecimal("1.772453850905516027297421798685")};

        for (int i = 0; i < inputs.length; i++) {
            BigDecimal v = inputs[i];
            calculator.recordValue(v);
            calculator.doSquareRoot(calculator.popValue());
            assertTrue(expected[i].subtract(calculator.popValue()).abs().compareTo(TOLERANCE) < 0);
        }
    }

    @Test(expected = ArithmeticException.class)
    public void squareRootOfNegativeShouldResultInArithmeticException() {
        calculator.recordValue(BigDecimal.valueOf(-1));
        calculator.doSquareRoot(calculator.popValue());
    }

    @Test
    public void testRecordPopClearAndCount() {
        for(int i = 0; i < 10; i++) {
            calculator.recordValue(BigDecimal.valueOf(Math.random() * 100));
        }
        assertEquals(10, calculator.getValueCount());

        for(int i = 0; i < 5; i++) {
            calculator.popValue();
        }
        assertEquals(5, calculator.getValueCount());

        calculator.clear();
        assertEquals(0, calculator.getValueCount());
    }
}