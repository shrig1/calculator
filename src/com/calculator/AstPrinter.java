package com.calculator;

class AstPrinter implements Expression.Visitor<String> {
    String print(Expression expr) {
        return expr.accept(this);
    }

    @Override
    public String visitBinaryNode(Expression.Binary expr) {
        return stringify(expr.operator.getLexme().toString(), expr.left, expr.right);
    }

    @Override
    public String visitGroupingNode(Expression.Grouping expr) {
        return stringify("p", expr.expression);
    }

    @Override
    public String visitLiteralNode(Expression.Literal expr) {
        return expr.value.toString();
    }

    @Override
    public String visitUnaryNode(Expression.Unary expr) {
        return stringify(expr.operator.getLexme().toString(), expr.right);
    }


    private String stringify(String name, Expression... exprs) {
        StringBuilder builder = new StringBuilder();

        builder.append("(");
        for (Expression expr : exprs) {
            builder.append(" ");
            builder.append(expr.accept(this));
        }
        builder.append(" ").append(name).append(")");

        return builder.toString();
    }

    public static void main(String[] args) {

    }
}