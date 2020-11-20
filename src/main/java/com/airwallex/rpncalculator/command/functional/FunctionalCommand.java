package com.airwallex.rpncalculator.command.functional;

import com.airwallex.rpncalculator.CalculatorDriver;
import com.airwallex.rpncalculator.command.Command;

/**
 * The abstract class for functional (i.e., not calculation operator) commands
 * <p>
 * New types of functional operations can be implemented by extending this class
 *
 * @author Zhu Zhaohua
 */
public abstract class FunctionalCommand extends Command {
    CalculatorDriver calculatorDriver;

    public FunctionalCommand(CalculatorDriver calculatorDriver) {
        this.calculatorDriver = calculatorDriver;
    }
}