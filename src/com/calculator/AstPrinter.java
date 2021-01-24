package com.calculator;

class AstPrinter implements Expression.Visitor<String> {
    String print(Expression expr) {
        return expr.accept(this);
    }

    public String visitAssignNode(Expression.Assign expr) {
        return stringify(expr.getName(), expr.getExpression());
    }

    @Override
    public String visitBinaryNode(Expression.Binary expr) {
        return stringify(expr.getOperator().getLexme().toString(), expr.getLeft(), expr.getRight());
    }

    @Override
    public String visitGroupingNode(Expression.Grouping expr) {
        if(expr.getType().equals("grouping")) {
            return stringify("group", expr.getExpression());
        } else if(expr.getType().equals("abs")) {
            return stringify("abs", expr.getExpression());
        }
        else {
            return stringify("", expr.getExpression());
        }
    }

    @Override
    public String visitLiteralNode(Expression.Literal expr) {
        return expr.value.toString();
    }

    @Override
    public String visitUnaryNode(Expression.Unary expr) {
        return stringify(expr.getOperator().getLexme().toString(), expr.getSideExpr());
    }

    @Override
    public String visitFunctionNode(Expression.Function expr) {
        return stringify(expr.getFunction().getType().toString(), expr.getArgument());
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