package com.airwallex.rpncalculator.command.calculation;

import com.airwallex.rpncalculator.RpnCalculator;

import java.math.BigDecimal;

public class Minus extends CalculationCommand {
    public Minus(RpnCalculator calculator, BigDecimal operand) {
        super(calculator, operand);
    }

    @Override
    public void execute() {
        calculator.doMinus(operand);
    }

    @Override
    public void unexecute() {
        new Plus(calculator, operand).execute();
        calculator.recordValue(operand);
    }
}
