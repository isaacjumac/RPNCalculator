package com.airwallex.rpncalculator.command.functional;

import com.airwallex.rpncalculator.CalculatorDriver;
import com.airwallex.rpncalculator.RpnCalculator;
import com.airwallex.rpncalculator.command.calculation.CalculationCommand;
import com.airwallex.rpncalculator.command.calculation.Multiply;

import java.math.BigDecimal;

public class Clear extends FunctionalCommand {
    public Clear(CalculatorDriver calculatorDriver) {
        super(calculatorDriver);
    }

    @Override
    public void execute() {
        calculatorDriver.clear();
    }

    @Override
    public void unexecute() {
        // Do nothing for unexecute
    }
}
