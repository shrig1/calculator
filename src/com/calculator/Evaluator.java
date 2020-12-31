package com.calculator;

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
    public Double visitLiteralNode(Expression.Literal expr) {
        return Double.parseDouble(expr.getValue());
    }

    @Override
    public Double visitUnaryNode(Expression.Unary expr) {
        double right = evaluate(expr.getRight());

        switch(expr.getOperator().getType()) {
            case MINUS:
                return -1.0 * right;
        }

        return 0.0;
    }


    @Override
    public Double visitGroupingNode(Expression.Grouping expr) {
        if(expr.type.equals("grouping")) {
            return evaluate(expr.expression);
        }
        else if(expr.type.equals("abs")) {
            return Math.abs(evaluate(expr.expression));
        }

        //Unreachable
        return -69.0;
    }


    @Override
    public Double visitBinaryNode(Expression.Binary expr) {
        double left = evaluate(expr.left);
        double right = evaluate(expr.right);

        switch (expr.operator.getType()) {
            case MINUS:
                return left - right;
            case PLUS:
                return left + right;
            case SLASH:
                checkArithmeticErrors(expr.operator, left, right);
                return left / right;
            case STAR:
                return left * right;
            case EXP:
                return Math.pow(left, right);
            case MODULO:
                return left % right;
        }
        // Unreachable.
        return -69.0;
    }
}