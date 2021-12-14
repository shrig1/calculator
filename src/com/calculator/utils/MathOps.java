package com.calculator.utils;

import com.calculator.TokenType;
import static com.calculator.TokenType.*;

import java.lang.reflect.Array;
import java.util.*;
import java.util.function.Function;

public class MathOps {

    public static Map<TokenType, Function<Double, Double>> singleParamFunctions = new HashMap<>();
    public static Map<TokenType, Function<ArrayList<Double>, Double>> multiParamFunctions = new HashMap<>();
    private final static double RECTANGLES = 100000000;
    public static final double PHI = 1.618033988749894;

    static {
        singleParamFunctions.put(SQRT, Math::sqrt);
        singleParamFunctions.put(LN, Math::log);
        singleParamFunctions.put(SIN, Math::sin);
        singleParamFunctions.put(COS, Math::cos);
        singleParamFunctions.put(TAN, Math::tan);
        singleParamFunctions.put(SINH, Math::sinh);
        singleParamFunctions.put(COSH, Math::cosh);
        singleParamFunctions.put(TANH, Math::tanh);
        singleParamFunctions.put(ARCSIN, Math::asin);
        singleParamFunctions.put(ARCCOS, Math::acos);
        singleParamFunctions.put(ARCTAN, Math::atan);
        singleParamFunctions.put(CSC, (arg) -> 1 / Math.sin(arg));
        singleParamFunctions.put(SEC, (arg) -> 1 / Math.cos(arg));
        singleParamFunctions.put(COT, (arg) -> 1 / Math.tan(arg));
        singleParamFunctions.put(CSCH, (arg) -> 1 / Math.sinh(arg));
        singleParamFunctions.put(SECH, (arg) -> 1 / Math.cosh(arg));
        singleParamFunctions.put(COTH, (arg) -> 1 / Math.tanh(arg));
        singleParamFunctions.put(ARCCSC, (arg) -> Math.signum(arg) * (-1.0 * ( Math.atan( Math.sqrt(Math.pow(arg, 2) - 1)) + (Math.PI / 2) )));
        singleParamFunctions.put(ARCSEC, (arg) -> Math.signum(arg) * ( Math.atan( Math.sqrt(Math.pow(arg, 2) - 1)) ));
        singleParamFunctions.put(ARCCOT, (arg) -> (-1.0 * Math.atan(arg)) + (Math.PI / 2));
        singleParamFunctions.put(VER, (arg) -> 1.0 - Math.cos(arg));
        singleParamFunctions.put(VCS, (arg) -> 1.0 + Math.cos(arg));
        singleParamFunctions.put(CVS, (arg) -> 1.0 - Math.sin(arg));
        singleParamFunctions.put(CVC, (arg) -> 1.0 + Math.sin(arg));
        singleParamFunctions.put(SEM, (arg) -> (1.0 - Math.cos(arg)) / 2.0);
        singleParamFunctions.put(HVC, (arg) -> (1.0 + Math.cos(arg)) / 2.0);
        singleParamFunctions.put(HCV, (arg) -> (1.0 - Math.sin(arg)) / 2.0);
        singleParamFunctions.put(HCC, (arg) -> (1.0 + Math.sin(arg)) / 2.0);
        singleParamFunctions.put(EXS, (arg) -> (1.0 / Math.cos(arg)) - 1.0);
        singleParamFunctions.put(EXC, (arg) -> (1.0 / Math.sin(arg)) - 1.0);
        singleParamFunctions.put(CRD, (arg) -> 2.0 * Math.sin(arg / 2.0));

        //These functions only take in the parameters it wants and if you give it more than it takes in it will ignore the extras
        multiParamFunctions.put(ROOT, (args) -> Math.pow(args.get(1), 1 / args.get(0)));
        multiParamFunctions.put(LOG, (args) -> Math.log10(args.get(1)) / Math.log10(args.get(0)));
        multiParamFunctions.put(BINOMIALPDF, (args) -> binomialpdf(args.get(0), args.get(1), args.get(2)));
        multiParamFunctions.put(BINOMIALCDF, (args) -> binomialcdf(args.get(0), args.get(1), args.get(2)));
        multiParamFunctions.put(NORMALPDF, (args) -> normalpdf(args.get(0), args.get(1), args.get(2)));
        multiParamFunctions.put(NORMALCDF, (args) -> normalcdf(args.get(0), args.get(1), args.get(2), args.get(3)));
        multiParamFunctions.put(INVNORM, (args) -> inverseNorm(args.get(0), args.get(1), args.get(2)));
        multiParamFunctions.put(BASE, (args) -> base_conversion(args.get(0), args.get(1), args.get(2)));
        multiParamFunctions.put(SORT, MathOps::sort);
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

    public static double base_conversion(double og_num, double og_base, double new_base) {
        int og_b10 = Integer.parseInt(String.valueOf((int)og_num), (int)og_base);
        return Double.parseDouble(Integer.toString(og_b10, (int)new_base));
    }

    public static double sort(ArrayList<Double> e) {
        Stack<Double> elements = new Stack<>();
        Iterator<Double> ei = e.iterator();
        for (Iterator<Double> it = ei; it.hasNext(); ) {
            elements.push(it.next());
        }
        Stack<Double> tmpStack = new Stack<>();
        while(!e.isEmpty())
        {
            // pop out the first element
            Double tmp = elements.pop();

            // while temporary stack is not empty and
            // top of stack is greater than temp
            while(!tmpStack.isEmpty() && tmpStack.peek()
                    > tmp)
            {
                // pop from temporary stack and
                // push it to the input stack
                elements.push(tmpStack.pop());
            }

            // push temp in temporary of stack
            tmpStack.push(tmp);
        }

        System.out.println(tmpStack);
        return 0;
    }

    private static double binomialpdf(double trials, double x_value, double prob_of_success) {
        double bin_coeff = factorial(String.valueOf(trials)) / (factorial(String.valueOf(x_value)) * factorial(String.valueOf(trials - x_value)));
        return bin_coeff * Math.pow(prob_of_success, x_value) * Math.pow((1 - prob_of_success), (trials - x_value));
    }

    private static double binomialcdf(double trials, double x_value, double prob_of_success) {
        double prob = 0;
        for(int i = (int) x_value; i >= 0; i--) {
            prob += binomialpdf(trials, i, prob_of_success);
        }
        return prob;
    }

    public static double getHeightAtZ(double mean, double std, double z_value) {
        return Math.exp((-1.0 / 2.0) * Math.pow( (z_value - mean) / std, 2) ) / (std * Math.sqrt(2 * Math.PI));
    }

    public static double normalpdf(double mean, double std, double x_value) {
        double area = 0;
        double width = (x_value - 6) / RECTANGLES;
        for(int i = 0; i < RECTANGLES; i++) {
            area += width * getHeightAtZ(mean, std, width * i + x_value);
        }
        return Math.abs(area);
    }

    public static double normalcdf(double mean, double std, double l_bound, double u_bound) {
        double area = 0;
        double width = (u_bound - l_bound) / RECTANGLES;
        for(int i = 0; i < RECTANGLES; i++) {
            area += width * getHeightAtZ(mean, std, width * i + l_bound);
        }
        return Math.abs(area);
    }

    public static double inverseNorm(double mean, double std, double prob) {
        double[] a = {2.50662823884, -18.61500062529, 41.39119773534, -25.44106049637};
        double[] b = {-8.47351093090, 23.08336743743, -21.06224101826, 3.13082909833};

        double y = prob - 0.5;
        double r = y * y;
        double z_score = y * (((a[3] * r + a[2]) * r + a[1]) * r + a[0]) / ((((b[3] * r + b[2]) * r + b[1]) * r + b[0]) * r + 1);
        return z_score * std + mean;
    }


    //    public static double getZAtHeight(double mean, double std, double height) {
//        return Math.sqrt(-2.0 * Math.log(height * (std * Math.sqrt(2.0 * Math.PI)))) * std + mean;
//    }
}
