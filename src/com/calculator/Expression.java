package com.calculator;

public abstract class Expression {
    interface Visitor<T> {
        T visitBinaryNode(Binary expr);
        T visitGroupingNode(Grouping expr);
        T visitLiteralNode(Literal expr);
        T visitUnaryNode(Unary expr);
    }
    static class Binary extends Expression {
        final Expression left;
        final Token operator;
        final Expression right;

        public Binary(Expression left, Token operator, Expression right) {
            this.left = left;
            this.operator = operator;
            this.right = right;
        }

        @Override
        <T> T accept(Visitor<T> visitor) {
            return visitor.visitBinaryNode(this);
        }
    }
    static class Grouping extends Expression {
        final Expression expression;
        final String type;

        public Grouping(Expression expression, String type) {
            this.expression = expression;
            this.type = type;
        }

        @Override
        <T> T accept(Visitor<T> visitor) {
            return visitor.visitGroupingNode(this);
        }
    }
    static class Literal extends Expression {
        final Object value;

        public Literal(Object value) {
            this.value = value;
        }

        @Override
        <T> T accept(Visitor<T> visitor) {
            return visitor.visitLiteralNode(this);
        }
    }
    static class Unary extends Expression {
        final Token operator;
        final Expression right;

        public Unary(Token operator, Expression right) {
            this.operator = operator;
            this.right = right;
        }

        @Override
        <T> T accept(Visitor<T> visitor) {
            return visitor.visitUnaryNode(this);
        }
    }

    abstract <T> T accept(Visitor<T> visitor);
}
