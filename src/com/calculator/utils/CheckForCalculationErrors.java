package com.calculator.utils;

import com.calculator.Token;

public class CheckForCalculationErrors {
    /* Pretty sure these two methods are not needed but I have them anyways in case I do wacky stuff

    public static void checkNumberOperand(Token operator, Object operand) {
        if (operand instanceof Double) return;
        throw new Error("Operand must be a number.");
    }

    public static void checkNumberOperands(Token operator,
                                     Object left, Object right) {
        if (left instanceof Double && right instanceof Double) return;

        throw new Error("Operands must be numbers.");
    }
    */


    public static void checkArithmeticErrors(Token operator, double left, double right) {
        switch(operator.getType()){
            case SLASH:
                if(right == 0.0) {
                    if(left == 0.0) {
                        throw new Error("Indeterminate form (aka 0/0) was encountered while solving this expression :'(");
                    }
                    throw new Error("Divide by 0 error >:(");
                }
        }

    }
}
