package com.airwallex.rpncalculator.command.calculation;

import com.airwallex.rpncalculator.RpnCalculator;

import java.math.BigDecimal;

public class Plus extends CalculationCommand {
    public Plus(RpnCalculator calculator, BigDecimal operand) {
        super(calculator, operand);
    }

    @Override
    public void execute() {
        calculator.doPlus(operand);
    }

    @Override
    public void unexecute() {
        new Minus(calculator, operand).execute();
        calculator.recordNewValue(operand);
    }
}
