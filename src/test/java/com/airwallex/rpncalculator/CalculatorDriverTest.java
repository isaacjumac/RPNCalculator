package com.airwallex.rpncalculator;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.Assert.assertEquals;

@RunWith(JUnit4.class)
public class CalculatorDriverTest {
    private CalculatorDriver driver;

    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;

    private static final String[][] VALID_INPUTS = {
            {"  5  2 "},  // preceding or trailing white spaces will be trimmed, multiple white spaces in between are accepted
            {"2     sqrt", "undo", "clear 9 sqrt"}, // multiple white spaces and tabs in between are accepted
            {"5 2 -", "3 -", "clear"}, // some normal calculation case with multiple lines
            {"5 4 3 2", "undo undo *", "5 *", "undo"}, // some normal calculation with multiple lines
            {"123456789012345.123456789012345678 123456789012345.123456789012345678 *"}, // some large number calculation
            {"0.123456789012345 0.123456789012345 *"}, // number with many decimal places
            {"0.0000000012345 0.12345 *"}, // very small number calculation
            {"0.00000000012345 0.5 *", "2 *"} // very small number calculation, displaying 0 in between but it's actually larger than 0 on the stack
    };
    public static final String[] EXPECTED_STRING_FOR_VALID_INPUTS = new String[]{
            "stack: 5 2",
            "stack: 1.4142135623", "stack: 2", "stack: 3",
            "stack: 3", "stack: 0", "stack:",
            "stack: 5 4 3 2", "stack: 20", "stack: 100", "stack: 20 5",
            "stack: 15241578753238699603719905502.5208901094",
            "stack: 0.0152415787",
            "stack: 0.0000000001",
            "stack: 0", "stack: 0.0000000001"
    };
    public static final String VALID_OUTPUT = String.join(System.lineSeparator(), EXPECTED_STRING_FOR_VALID_INPUTS) +
            System.lineSeparator();

    private static final String[][] INVALID_INPUTS = {
            {"1 2 3 * 5 + * * 6 5"}, // insufficient parameter within the same line
            {"5 4", "sqrt * *", "3 2 *"}, // insufficient parameters in case of multi-line inputs & post processing after warning
            {"-", "3 -"}, // insufficient parameter from the beginning
            {"1 undo undo 2"}, // nothing to undo
            {"3 !"}, // unsupported command (factorial)
            {"0.0.3"} // unsupported command (non-number will be treated as command)
    };
    public static final String[] EXPECTED_STRING_FOR_INVALID_INPUTS = new String[]{
            "operator * (position: 15): insufficient parameters", "stack: 11",
            "stack: 5 4", "operator * (position: 8): insufficient parameters", "stack: 10", "stack: 10 6",
            "operator - (position: 1): insufficient parameters", "stack:", "operator - (position: 3): insufficient parameters", "stack: 3",
            "Nothing to undo.", "stack: 2",
            "The operation ! is not supported.", "stack: 3",
            "The operation 0.0.3 is not supported.", "stack:"
    };
    public static final String INVALID_OUTPUT = String.join(System.lineSeparator(), EXPECTED_STRING_FOR_INVALID_INPUTS) +
            System.lineSeparator();

    @Before
    public void setUp() {
        System.setOut(new PrintStream(outContent));
        driver = new CalculatorDriver();
    }

    @Test
    public void testEvaluateValidInputShouldReturnCorrectlyWithoutWarning() {
        for (int i = 0; i < VALID_INPUTS.length; i++) {
            String[] inputs = VALID_INPUTS[i];
            driver.clear();
            for (int j = 0; j < inputs.length; j++) {
                String input = inputs[j];
                driver.evaluate(input);
            }
        }
        assertEquals(VALID_OUTPUT, outContent.toString());
    }

    @Test
    public void testEvaluateInvalidInputShouldResultInWarning() {
        for (int i = 0; i < INVALID_INPUTS.length; i++) {
            String[] inputs = INVALID_INPUTS[i];
            driver.clear();
            for (int j = 0; j < inputs.length; j++) {
                String input = inputs[j];
                driver.evaluate(input);
            }
        }
        assertEquals(INVALID_OUTPUT, outContent.toString());
    }

    @After
    public void restoreStreams() {
        outContent.reset();
        System.setOut(originalOut);
    }
}