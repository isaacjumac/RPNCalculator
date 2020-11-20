package com.airwallex.rpncalculator;

import com.airwallex.rpncalculator.command.Command;
import com.airwallex.rpncalculator.command.CommandFactory;
import com.airwallex.rpncalculator.command.calculation.CalculationCommand;
import com.airwallex.rpncalculator.exception.InsufficientParameterException;

import java.math.BigDecimal;
import java.util.ArrayDeque;
import java.util.Deque;

/**
 * The driver class for the calculator.
 * It serves the role for:
 * <ul>
 * <li>parsing & validating the inputs,</li>
 * <li>initiating the operations/commands for actual calculation, and</li>
 * <li>maintaining the command history for calculation</li>
 * </ul>
 *
 * @author Zhu Zhaohua
 */
public class CalculatorDriver {
    private RpnCalculator calculator;

    /**
     * Record the history of {@linkplain CalculationCommand} to support undo
     */
    private Deque<CalculationCommand> calculationCommandHistory;

    public CalculatorDriver() {
        calculator = new RpnCalculator();
        calculationCommandHistory = new ArrayDeque<>();
    }

    /**
     * The actual method for input line evaluation.
     * In order to tolerate leading/trailing whitespace(s), and to allow multiple whitespaces in between,
     * we manually parse the input to find each non-space elements and process them one at a time.
     * <p>
     * Once encountered {@linkplain InsufficientParameterException} or {@linkplain UnsupportedOperationException},
     * a warning message will be output to standard output. Also, the evaluation of current input line will terminate
     * and any further elements in the same input line will just be ignored.
     * <p>
     * After processing the input line (with warning or not), the value stack of calculator will be output to
     * standard output.
     *
     * @param input
     */
    public void evaluate(String input) {
        int start = 0, end = 0;
        while (end < input.length()) {
            while (start < input.length() && Character.isWhitespace(input.charAt(start))) {
                start++;
            }
            end = start;
            while (end < input.length() && !Character.isWhitespace(input.charAt(end))) {
                end++;
            }
            if (start == input.length()) {
                break;
            }

            String element = input.substring(start, end);
            try {
                processElement(element);
            } catch (InsufficientParameterException ipe) {
                System.out.println(String.format("operator %s (position: %d): insufficient parameters", element, end));
                break;
            } catch (UnsupportedOperationException uoe) {
                System.out.println(uoe.getMessage());
                break;
            }

            start = end;
        }
        System.out.println(calculator.formValueStackString());
    }

    /**
     * Process an element in the input line.
     * <p>
     * Firstly try to cast the element string to {@linkplain BigDecimal}, in case of {@linkplain NumberFormatException},
     * i.e., the element is not a well-formatted number, it will be treated as command/operator.
     *
     * @param element
     */
    private void processElement(String element) {
        try {
            BigDecimal operand = new BigDecimal(element);
            calculator.recordValue(operand);
        } catch (NumberFormatException nfe) {
            Command command = CommandFactory.getCommand(element, this, calculator);
            if (command == null) {
                throw new UnsupportedOperationException(String.format("The operation %s is not supported.", element));
            }
            try {
                command.execute();

                if (command instanceof CalculationCommand) {
                    calculationCommandHistory.push((CalculationCommand) command);
                }
            } catch (ArithmeticException ae) {
                System.out.println(ae.getMessage());
            }
        }
    }

    /**
     * Logic for the {@code clear} command.
     * <p>
     * The value stack of the calculator will be emptied by calling {@linkplain RpnCalculator#clear()}.
     * Also, the {@linkplain #calculationCommandHistory} of the calculator driver will also be reset.
     */
    public void clear() {
        calculator.clear();
        calculationCommandHistory = new ArrayDeque<>();
    }

    /**
     * Undo the previous command.
     * <p>
     * If there is no command to be undone, try to remove the latest value from the value stack of the calculator.
     */
    public void undo() {
        if (calculationCommandHistory.isEmpty()) {
            if (calculator.getValueCount() == 0) {
                System.out.println("Nothing to undo.");
            } else {
                calculator.popValue();
            }
        } else {
            calculationCommandHistory.pop().unexecute();
        }
    }
}
