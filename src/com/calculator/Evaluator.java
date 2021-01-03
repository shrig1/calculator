package com.calculator;

import com.calculator.utils.MathOps;

import static com.calculator.utils.CheckForCalculationErrors.*;

public class Evaluator implements Expression.Visitor<Double> {

    public void solve(Expression expr) {
        try{
            System.out.println(evaluate(expr));
        } catch(Error e) {
            System.err.println(e.getMessage());
        }
    }

    public double evaluate(Expression expr) {
        return expr.accept(this);
    }

    @Override
    public Double visitLiteralNode(Expression.Literal expr) { return Double.parseDouble(expr.getValue()); }

    @Override
    public Double visitFunctionNode(Expression.Function expr) {
        double arg = evaluate(expr.getArgument());

        return switch (expr.getFunction().getType()) {
            case SIN -> Math.sin(arg);
            case SINH -> Math.sinh(arg);
            case COS -> Math.cos(arg);
            case COSH -> Math.cosh(arg);
            case TAN -> Math.tan(arg);
            case TANH -> Math.tanh(arg);
            case ARCSIN -> Math.asin(arg);
            case ARCCOS -> Math.acos(arg);
            case ARCTAN -> Math.atan(arg);
            case CSC -> 1 / Math.sin(arg);
            case CSCH -> 1 / Math.sinh(arg);
            case SEC -> 1 / Math.cos(arg);
            case SECH -> 1 / Math.cosh(arg);
            case COT -> 1 / Math.tan(arg);
            case COTH -> 1 / Math.tanh(arg);
//            case ARCCSC -> ;
//            case ARCSEC -> ;
//            case ARCCOT -> ;
            default -> throw new Error("Failure in a Function Node, chances are it's because you used the inverses of csc, sec, and cot and they aren't implemented yet");
        };

    }

    @Override
    public Double visitUnaryNode(Expression.Unary expr) {
        double sideExpr = evaluate(expr.getSideExpr());

        return switch (expr.getOperator().getType()) {
            case MINUS -> -1.0 * sideExpr;
            case FACTORIAL -> MathOps.factorial(String.valueOf(sideExpr));
            default -> throw new Error("Failure in a Unary Node");
        };


    }


    @Override
    public Double visitGroupingNode(Expression.Grouping expr) {
        if(expr.getType().equals("grouping")) {
            return evaluate(expr.getExpression());
        }
        else if(expr.getType().equals("abs")) {
            return Math.abs(evaluate(expr.getExpression()));
        }

        //Unreachable
        throw new Error("Failure in a Grouping Node");
    }


    @Override
    public Double visitBinaryNode(Expression.Binary expr) {
        double left = evaluate(expr.getLeft());
        double right = evaluate(expr.getRight());

        switch (expr.getOperator().getType()) {
            case MINUS:
                return left - right;
            case PLUS:
                return left + right;
            case SLASH:
                checkArithmeticErrors(expr.getOperator(), left, right);
                return left / right;
            case STAR:
                return left * right;
            case EXP:
                return Math.pow(left, right);
            case MODULO:
                return left % right;
        }
        // Unreachable.
        throw new Error("Failure in a Binary Node");
    }
}