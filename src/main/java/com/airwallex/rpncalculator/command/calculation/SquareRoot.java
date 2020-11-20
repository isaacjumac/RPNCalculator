package com.airwallex.rpncalculator.command.calculation;

import com.airwallex.rpncalculator.RpnCalculator;

import java.math.BigDecimal;

public class SquareRoot extends CalculationCommand {
    public SquareRoot(RpnCalculator calculator, BigDecimal operand) {
        super(calculator, operand);
    }

    @Override
    public void execute() {
        calculator.doSquareRoot(operand);
    }

    @Override
    public void unexecute() {
        calculator.removeOneValue();
        calculator.recordNewValue(operand);
    }
}
