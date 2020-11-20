package com.airwallex.rpncalculator.command.calculation;

import com.airwallex.rpncalculator.RpnCalculator;

import java.math.BigDecimal;

public class Multiply extends CalculationCommand {
    public Multiply(RpnCalculator calculator, BigDecimal operand) {
        super(calculator, operand);
    }

    @Override
    public void execute() {
        calculator.doMultiply(operand);
    }

    @Override
    public void unexecute() {
        new Divide(calculator, operand).execute();
        calculator.recordValue(operand);
    }
}
