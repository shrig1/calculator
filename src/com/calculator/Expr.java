package com.calculator;

public abstract class Expr {
    interface Visitor<T> {
        T visitBinaryNode(Binary expr);
        T visitGroupingNode(Grouping expr);
        T visitLiteralNode(Literal expr);
        T visitUnaryNode(Unary expr);
    }
    static class Binary extends Expr {
        final Expr left;
        final Token operator;
        final Expr right;

        public Binary(Expr left, Token operator, Expr right) {
            this.left = left;
            this.operator = operator;
            this.right = right;
        }

        @Override
        <T> T accept(Visitor<T> visitor) {
            return visitor.visitBinaryNode(this);
        }
    }
    static class Grouping extends Expr {
        final Expr expression;

        public Grouping(Expr expression) {
            this.expression = expression;
        }

        @Override
        <T> T accept(Visitor<T> visitor) {
            return visitor.visitGroupingNode(this);
        }
    }
    static class Literal extends Expr {
        final Object value;

        public Literal(Object value) {
            this.value = value;
        }

        @Override
        <T> T accept(Visitor<T> visitor) {
            return visitor.visitLiteralNode(this);
        }
    }
    static class Unary extends Expr {
        final Token operator;
        final Expr right;

        public Unary(Token operator, Expr right) {
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
