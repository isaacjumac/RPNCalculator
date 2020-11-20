package com.airwallex.rpncalculator.command;

import com.airwallex.rpncalculator.CalculatorDriver;
import com.airwallex.rpncalculator.RpnCalculator;
import com.airwallex.rpncalculator.command.Command;
import com.airwallex.rpncalculator.command.CommandFactory;
import com.airwallex.rpncalculator.command.calculation.Divide;
import com.airwallex.rpncalculator.command.calculation.Minus;
import com.airwallex.rpncalculator.command.calculation.Multiply;
import com.airwallex.rpncalculator.command.calculation.Plus;
import com.airwallex.rpncalculator.command.calculation.SquareRoot;
import com.airwallex.rpncalculator.command.functional.Clear;
import com.airwallex.rpncalculator.command.functional.Undo;
import com.airwallex.rpncalculator.exception.InsufficientParameterException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.math.BigDecimal;

import static org.junit.Assert.*;

@RunWith(JUnit4.class)
public class CommandFactoryTest {
    private RpnCalculator calculator;
    private CalculatorDriver calculatorDriver;

    public static final String[] FUNCTIONAL_COMMANDS = {"clear", "undo"};
    public static final Class[] EXPECTED_FUNCTIONAL_COMMANDS = {Clear.class, Undo.class};

    public static final String[] CALCULATION_COMMANDS = {"+", "-", "*", "/", "sqrt"};
    public static final Class[] EXPECTED_CALCULATION_COMMANDS = {Plus.class, Minus.class, Multiply.class, Divide.class, SquareRoot.class};

    public static final String[] BAD_COMMANDS = {"0.3.5", "square", "redo"};

    @Before
    public void setUp(){
        calculator = new RpnCalculator();
        calculatorDriver = new CalculatorDriver();
    }

    @Test
    public void testGetFunctionalCommandShouldReturnCorrectFunctionalCommand() {
        for(int i = 0; i < FUNCTIONAL_COMMANDS.length; i++) {
            Command command = CommandFactory.getCommand(FUNCTIONAL_COMMANDS[i], calculatorDriver, calculator);
            assertEquals(EXPECTED_FUNCTIONAL_COMMANDS[i], command.getClass());
        }
    }

    @Test
    public void testGetCalculationCommandShouldReturnCorrectCalculationCommand() {
        for(int i = 0; i < CALCULATION_COMMANDS.length; i++) {
            // record new values to make sure there are sufficient parameters
            calculator.recordValue(new BigDecimal(i+1));
            calculator.recordValue(new BigDecimal(i+2));

            Command command = CommandFactory.getCommand(CALCULATION_COMMANDS[i], calculatorDriver, calculator);
            assertEquals(EXPECTED_CALCULATION_COMMANDS[i], command.getClass());
        }
    }

    @Test(expected = InsufficientParameterException.class)
    public void testGetCalculationCommandWithInsufficientOperandsShouldOutputWarning() {
        for(int i = 0; i < CALCULATION_COMMANDS.length; i++) {
            Command command = CommandFactory.getCommand(CALCULATION_COMMANDS[i], calculatorDriver, calculator);
        }
    }

    @Test
    public void testGetUnsupportedCommandsShouldExpectNull() {
        for(int i = 0; i < BAD_COMMANDS.length; i++) {
            Command command = CommandFactory.getCommand(BAD_COMMANDS[i], calculatorDriver, calculator);
            assertNull(command);
        }
    }
}