# Overview
This is a command-line based RPN (Reverse Polish Notation) calculator that is able to evaluate user input of numbers 
and operators/commands and perform corresponding calculation/operation in the RPN manner. For details of how what is RPN
and how it is evaluated, please refer here: .

# How to Use
## Prerequisites
1. A bash-shell-equipped OS, e.g., Ubuntu
2. Java 11 or above
3. Maven 4.0.0 (Install with ```(sudo) apt install maven```)

## Build
To set up this application, please execute ```bin/setup.sh```. It will build the application with Maven and 
automatically run the JUnit tests with Maven Surefire plugin that comes with Maven.

## Run
To run this application in command line mode, please execute ```bin/rpn_calculator.sh```.<br>
Your input will be evaluated line-by-line, until it is completely evaluated, or an error occurs.

### Format of Input
Your input should be whitespace(s)-separated lists of numbers and/or commands. <br>
To make the usage less painful, leading or trailing whitespace(s) will be ignored and no error will occur. Multiple 
whitespace(s) in between numbers/commands are also OK. <br>

#### Examples of Valid Inputs:
- ```1 2 3```: proper one-space-separated line
- ```   1 2   3 ;```: with leading/trailing whitespace(s), or multiple whitespaces in between

#### Examples of Invalid Inputs:  
- ```1 + 2```: not in proper RPN formation
- ```sqrt```: no operable values
- ```1.2.3```: malformed number. Will be then treated as command and then give ```The operation 1.2.3 is not supported.``` 
warning
- ```delete```: unsupported command. Will give ```The operation delete is not supported.``` warning

### Output
After processing of each line of input (either successfully or ended with error), the current value stack of the 
calculator will be printed out, in the form of space-separated numbers, with leading ```stack:```. E.g, ```stack: 1 2 
3```. <br>
As of what warning messages can be output, please refer to each command description.

### Precision
BigDecimal is used to store values in our calculator. <br><br>
For ```+```, ```-```, ```*```, we do not limit the scale/precision, so as to be accurate as possible, depending on the 
input precision.<br>
For ```/```, ```sqrt```, we set the internal scale (i.e., the number of decimal places) to be at least 20 (it can be 21,
depending on whether the result non-decimal part shrinks 1 digit or not). The reason to set a limited scale is to avoid
the ```Non-terminating decimal expansion``` exception in case of irrational number results.<br>
**NOTE: We only display up to 10 decimal places when outputting the value stack.**

# Commands/Functions
## Functional Commands
1. ```clear``` <br>
This command clears everything in the current value stack. It will also clear the command history.

2. ```undo``` <br>
This command undoes the previous operation. **Operation** here refers to either calculation (+, -, *, /, sqrt), or 
input of a number.<br>
Note that multiple ```undo``` commands will undo multiple previous operations. E.g., ```1 2 3 undo undo``` will result 
in ```stack: 1``` as the input of 3 and 2 will be undone by the 2 ```undo```s.<br>
If there is NO more operation to be undone, a message saying ```Nothing to undo.``` will be displayed, any following 
```undo``` command will just be ignored, and the value stack output should be empty at that point.

## Calculation Commands
1. ```+```: addition <br>
The top 2 numbers in the value stack will be added. The result will be pushed into the value stack. E.g., ```1 2 +``` 
==> ```stack: 3``` <br>
Requires at least 2 values in the value stack of the calculator. Otherwise, a warning of ```operator + (position:<pos>):
 insufficient parameters``` will be displayed.<br>

2. ```-```: subtraction <br>
The top 2 numbers in the value stack will be subtracted in RPN manner. The result will be pushed into the value stack. 
E.g., ```1 2 -``` ==> ```stack: -1``` <br>
Requires at least 2 values in the value stack of the calculator. Otherwise, a warning of ```operator - (position:<pos>):
 insufficient parameters``` will be displayed.<br>

3. ```*```: multiplication <br>
The top 2 numbers in the value stack will be added. The result will be pushed into the value stack. E.g., ```2 3 *``` 
==> ```stack: 6``` <br>
Requires at least 2 values in the value stack of the calculator. Otherwise, a warning of ```operator * (position:<pos>):
 insufficient parameters``` will be displayed.<br>

4. ```/```: division <br>
The top 2 numbers in the value stack will be added. The result will be pushed into the value stack. E.g., ```1 2 /``` 
==> ```stack: 0.5``` <br><br>
Requires at least 2 values in the value stack of the calculator. Otherwise, a warning of ```operator / (position:<pos>):
 insufficient parameters``` will be displayed.<br>

5. ```sqrt```: square root <br>
The top 2 numbers in the value stack will be added. The result will be pushed into the value stack. E.g., ```2 sqrt``` 
==> ```stack: 1.4142135623``` <br><br>
If there is no value in the value stack of the calculator, a warning of ```operator sqrt (position:<pos>): insufficient 
parameters``` will be displayed. <br>
If the top of the calculator stack is a negative number, a warning of ```Square root of negative number is not valid.```
 will be displayed. The ```sqrt``` command will just be ignored in this case, and you can continue your operation on the
 stack. E.g., ```1 2 3 -1 sqrt``` ==> ```stack: 1 2 3 -1```<br>

# Key Assumptions


# Future Plan
## Phase 2
1. To support more functional commands such as ```redo``` and ```swap```, for more versatile usage;<br>
2. To support more calculation operations such ```!```(factorial), ```COS```, ```ATAN``` etc., to support more 
complicated arithmetic calculations.

## Phase 3
1. To provide an online version with UI;<br>
2. To provide colorized representation of the value stack elements depending on their position and enable UI 
customization.