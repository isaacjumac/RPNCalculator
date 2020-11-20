package com.airwallex.rpncalculator.command.calculation;

import com.airwallex.rpncalculator.RpnCalculator;

import java.math.BigDecimal;

public class Divide extends CalculationCommand {
    public Divide(RpnCalculator calculator, BigDecimal operand) {
        super(calculator, operand);
    }

    @Override
    public void execute() {
        calculator.doDivision(operand);
    }

    @Override
    public void unexecute() {
        new Multiply(calculator, operand).execute();
        calculator.recordNewValue(operand);
    }
}
