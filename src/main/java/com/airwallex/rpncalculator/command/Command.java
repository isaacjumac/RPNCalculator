package com.airwallex.rpncalculator.command;

/**
 * The abstract operator class for all operators to be used in the RPN Calculator
 * <p>
 * New types of operator/feature can be implemented by extending this class
 *
 * @author Zhu Zhaohua
 */
public abstract class Command {
    public abstract void execute();

    public abstract void unexecute();
}
