package com.airwallex.rpncalculator.enums;

import java.util.Arrays;

public enum Operation {
    PLUS("+",  2),
    MINUS("-", 2),
    MULTIPLY("*", 2),
    DIVIDE("/", 2),
    SQRT("sqrt", 1);

    Operation(String commandString, int operandCount) {
        this.commandString = commandString;
        this.operandCount = operandCount;
    }

    private String commandString;
    private int operandCount;

    public static Operation operationOfCommandString(String commandString) {
        return Arrays.stream(Operation.values())
                .filter(o -> o.commandString.equals(commandString))
                .findFirst()
                .orElse(null);
    }
}
