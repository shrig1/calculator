package com.calculator.utils;

import com.calculator.TokenType;
import static com.calculator.TokenType.*;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

public class MathOps {

    public static Map<TokenType, Function<Double, Double>> functions = new HashMap<>();

    static {
        functions.put(SIN, Math::sin);
        functions.put(COS, Math::cos);
        functions.put(TAN, Math::tan);
        functions.put(SINH, Math::sinh);
        functions.put(COSH, Math::cosh);
        functions.put(TANH, Math::tanh);
        functions.put(ARCSIN, Math::asin);
        functions.put(ARCCOS, Math::acos);
        functions.put(ARCTAN, Math::atan);
        functions.put(CSC, (arg) -> 1 / Math.sin(arg));
        functions.put(SEC, (arg) -> 1 / Math.cos(arg));
        functions.put(COT, (arg) -> 1 / Math.tan(arg));
        functions.put(CSCH, (arg) -> 1 / Math.sinh(arg));
        functions.put(SECH, (arg) -> 1 / Math.cosh(arg));
        functions.put(COTH, (arg) -> 1 / Math.tanh(arg));
        functions.put(VER, (arg) -> 1.0 - Math.cos(arg));
        functions.put(VCS, (arg) -> 1.0 + Math.cos(arg));
        functions.put(CVS, (arg) -> 1.0 - Math.sin(arg));
        functions.put(CVC, (arg) -> 1.0 + Math.sin(arg));
        functions.put(SEM, (arg) -> (1.0 - Math.cos(arg)) / 2.0);
        functions.put(HVC, (arg) -> (1.0 + Math.cos(arg)) / 2.0);
        functions.put(HCV, (arg) -> (1.0 - Math.sin(arg)) / 2.0);
        functions.put(HCC, (arg) -> (1.0 + Math.sin(arg)) / 2.0);
        functions.put(EXS, (arg) -> (1.0 / Math.cos(arg)) - 1.0);
        functions.put(EXC, (arg) -> (1.0 / Math.sin(arg)) - 1.0);
        functions.put(CRD, (arg) -> 2.0 * Math.sin(arg / 2.0));
    }


    /*
    If it gets a whole number then it will evaluate it normally and return the value, but if it gets a decimal number
    then it will evaluate the gamma function(sterling's approximation) and return the value
     */
    public static double factorial(String n) {
        if (n.endsWith(".0")) {
            int num = Integer.parseInt(n.substring(0, n.length() - 2));
            double sum = 1;
            for (int i = num; i > 0; i--) {
                sum *= i;
            }
            return sum;
        } else {
            double num = Double.parseDouble(n);
            return Math.sqrt(2 * Math.PI * num) * Math.pow(num / Math.E, num);
        }
    }
}
