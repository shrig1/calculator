package com.calculator;

class AstPrinter implements Expr.Visitor<String> {
    String print(Expr expr) {
        return expr.accept(this);
    }

    @Override
    public String visitBinaryNode(Expr.Binary expr) {
        return stringify(expr.operator.getLexme().toString(), expr.left, expr.right);
    }

    @Override
    public String visitGroupingNode(Expr.Grouping expr) {
        return stringify("p", expr.expression);
    }

    @Override
    public String visitLiteralNode(Expr.Literal expr) {
        return expr.value.toString();
    }

    @Override
    public String visitUnaryNode(Expr.Unary expr) {
        return stringify(expr.operator.getLexme().toString(), expr.right);
    }


    private String stringify(String name, Expr... exprs) {
        StringBuilder builder = new StringBuilder();

        builder.append("(");
        for (Expr expr : exprs) {
            builder.append(" ");
            builder.append(expr.accept(this));
        }
        builder.append(" ").append(name).append(")");

        return builder.toString();
    }

    public static void main(String[] args) {

    }
}