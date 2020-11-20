package com.airwallex.rpncalculator.command.calculation;

import com.airwallex.rpncalculator.RpnCalculator;
import com.airwallex.rpncalculator.command.Command;

import java.math.BigDecimal;

/**
 * The abstract class for calculation operator commands.
 * <p>
 * New types of calculation operators can be implemented by extending this class
 *
 * @author Zhu Zhaohua
 */
public abstract class CalculationCommand extends Command {
    BigDecimal operand;
    RpnCalculator calculator;

    public CalculationCommand(RpnCalculator calculator, BigDecimal operand) {
        this.operand = operand;
        this.calculator = calculator;
    }
}
