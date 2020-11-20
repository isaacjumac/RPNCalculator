package com.airwallex.rpncalculator.command.functional;

import com.airwallex.rpncalculator.CalculatorDriver;

public class Undo extends FunctionalCommand {
    public Undo(CalculatorDriver calculatorDriver) {
        super(calculatorDriver);
    }

    @Override
    public void execute() {
        calculatorDriver.undo();
    }

    @Override
    public void unexecute() {
        // Do nothing for unexecute
    }
}
