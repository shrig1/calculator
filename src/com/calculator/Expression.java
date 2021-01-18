package com.calculator;

import java.util.ArrayList;

public abstract class Expression {
    interface Visitor<T> {
        T visitBinaryNode(Binary expr);
        T visitGroupingNode(Grouping expr);
        T visitUnaryNode(Unary expr);
        T visitFunctionNode(Function expr);
        T visitLiteralNode(Literal expr);
    }
    static class Binary extends Expression {
        private final Expression left;
        private final Token operator;
        private final Expression right;

        public Binary(Expression left, Token operator, Expression right) {
            this.left = left;
            this.operator = operator;
            this.right = right;
        }

        public Expression getLeft() { return this.left; }
        public Token getOperator() { return this.operator; }
        public Expression getRight() { return this.right; }

        @Override
        <T> T accept(Visitor<T> visitor) {
            return visitor.visitBinaryNode(this);
        }
    }
    static class Grouping extends Expression {
        private final Expression expression;
        private final String type;

        public Grouping(Expression expression, String type) {
            this.expression = expression;
            this.type = type;
        }

        public Expression getExpression() { return this.expression; }
        public String getType() { return this.type; }

        @Override
        <T> T accept(Visitor<T> visitor) {
            return visitor.visitGroupingNode(this);
        }
    }
    static class Unary extends Expression {
        final Token operator;
        final Expression sideExpr;

        public Unary(Token operator, Expression sideExpr) {
            this.operator = operator;
            this.sideExpr = sideExpr;
        }

        public Token getOperator() {
            return this.operator;
        }
        public Expression getSideExpr() {
            return this.sideExpr;
        }


        @Override
        <T> T accept(Visitor<T> visitor) {
            return visitor.visitUnaryNode(this);
        }
    }


    static class Function extends Expression {
        final Token function;
        private Expression argument;
        private ArrayList<Expression> arguments;
        boolean argOrArgs; // true = arg, false = args

        public Function(Token function, Expression argument) {
            this.function = function;
            this.argument = argument;
            argOrArgs = true;
        }

        public Function(Token function, ArrayList<Expression> argument) {
            this.function = function;
            this.arguments = argument;
            argOrArgs = false;
        }

        public boolean getState() { return argOrArgs; }
        public Token getFunction() { return this.function;}
        public Expression getArgument() {return this.argument;}
        public ArrayList<Expression> getArguments() {return this.arguments;}

        @Override
        <T> T accept(Visitor<T> visitor) { return visitor.visitFunctionNode(this); }

    }


    static class Literal extends Expression {
        final Object value;

        public Literal(Object value) {
            this.value = value;
        }
        public String getValue() { return this.value.toString();}

        @Override
        <T> T accept(Visitor<T> visitor) {
            return visitor.visitLiteralNode(this);
        }
    }

    abstract <T> T accept(Visitor<T> visitor);
}
