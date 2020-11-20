package com.airwallex.rpncalculator.command.functional;

import com.airwallex.rpncalculator.CalculatorDriver;
import com.airwallex.rpncalculator.command.Command;

public abstract class FunctionalCommand extends Command {
    CalculatorDriver calculatorDriver;

    public FunctionalCommand(CalculatorDriver calculatorDriver) {
        this.calculatorDriver = calculatorDriver;
    }
}