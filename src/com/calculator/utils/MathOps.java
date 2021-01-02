package com.calculator.utils;

public class MathOps {
    /*
    If it gets a whole number then it will evaluate it normally and return the value, but if it gets a decimal number
    then it will evaluate the gamma function(sterling's approximation) and return the value
     */
    public static double factorial(String n) {
        if(n.endsWith(".0")) {
            int num = Integer.parseInt(n.substring(0, n.length() - 2));
            double sum = 1;
            for(int i = num; i > 0; i--){
                sum *= i;
            }
            return sum;
        } else {
            double num = Double.parseDouble(n);
            return Math.sqrt(2 * Math.PI * num) * Math.pow(num/Math.E, num);
        }
    }
}
