package com.airwallex.rpncalculator.command;

import com.airwallex.rpncalculator.CalculatorDriver;
import com.airwallex.rpncalculator.RpnCalculator;
import com.airwallex.rpncalculator.command.calculation.CalculationCommand;
import com.airwallex.rpncalculator.command.calculation.Divide;
import com.airwallex.rpncalculator.command.calculation.Minus;
import com.airwallex.rpncalculator.command.calculation.Multiply;
import com.airwallex.rpncalculator.command.calculation.Plus;
import com.airwallex.rpncalculator.command.calculation.SquareRoot;
import com.airwallex.rpncalculator.command.functional.Clear;
import com.airwallex.rpncalculator.command.functional.FunctionalCommand;
import com.airwallex.rpncalculator.command.functional.Undo;
import com.airwallex.rpncalculator.exception.InsufficientParameterException;

import java.util.Arrays;

/**
 * The factory to create correct command instances for the command string
 *
 * @author Zhu Zhaohua
 */
public class CommandFactory {
    /**
     * Create {@linkplain Command} instances according to the {@code commandString}.
     *
     * @param commandString the command string from user input
     * @param driver        the driver application for the calculator. Needed for {@linkplain FunctionalCommand}
     * @param calculator    the calculator that is used in the driver application. Needed for {@linkplain CalculationCommand}
     * @return the command corresponding to the {@code commandString}, or {@code null} if it is not supported
     * @throws InsufficientParameterException - if the required operand count is larger than the available values in
     *                                        the value stack of the calculator
     */
    public static Command getCommand(String commandString, CalculatorDriver driver, RpnCalculator calculator) throws InsufficientParameterException {
        Operation operation = Operation.ofCommandString(commandString);
        if (operation == null) {
            return null;
        }

        if (calculator.getValueCount() < operation.requiredOperandCount) {
            throw new InsufficientParameterException();
        }

        switch (operation) {
            case PLUS:
                return new Plus(calculator, calculator.popValue());
            case MINUS:
                return new Minus(calculator, calculator.popValue());
            case MULTIPLY:
                return new Multiply(calculator, calculator.popValue());
            case DIVIDE:
                return new Divide(calculator, calculator.popValue());
            case SQRT:
                return new SquareRoot(calculator, calculator.popValue());
            case CLEAR:
                return new Clear(driver);
            case UNDO:
                return new Undo(driver);
            default:
                return null;
        }
    }

    /**
     * Enum for all supported command strings
     */
    enum Operation {
        PLUS("+", 2),
        MINUS("-", 2),
        MULTIPLY("*", 2),
        DIVIDE("/", 2),
        SQRT("sqrt", 1),
        CLEAR("clear", 0),
        UNDO("undo", 0);

        Operation(String commandString, int requiredOperandCount) {
            this.commandString = commandString;
            this.requiredOperandCount = requiredOperandCount;
        }

        private String commandString;
        private int requiredOperandCount;

        public static Operation ofCommandString(String commandString) {
            return Arrays.stream(Operation.values())
                    .filter(o -> o.commandString.equals(commandString))
                    .findFirst()
                    .orElse(null);
        }
    }
}
