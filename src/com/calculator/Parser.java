package com.calculator;

import java.util.ArrayList;

import static com.calculator.TokenType.*;

/*
 * My wacky version of EBNF syntax
 * :> represents an equals, so it will define a layer with the rules
 * | represents or, which means it can have a layer include one rule group or another
 * () represents a grouping
 * [] represents repetition, meaning that a group can be repeated many times
 * ; means termination of the rules for a layer
 * */

public class Parser {
    private final ArrayList<Token> tokens;
    private int current = 0;

    public Parser(ArrayList<Token> tokens) {
        this.tokens = tokens;
    }

    public Expression parse() {
        return expression();
    }

    // going from smallest layer to highest layer

    /**
     * literal :>  NUMBER | '(' expression ')' ;
     */
    private Expression literal() {
        if(match(NUMBER)) return new Expression.Literal(previous().getLexme());
        if(match(LEFT_PAREN)) {
            Expression expr = expression();
            consume(RIGHT_PAREN, "You need a ')' to follow up with an expression after a '('... smh");
            return new Expression.Grouping(expr);
        }

        throw new RuntimeException("Need an expression :(");
    }

    /**
     * negate :>  '-' negate | literal ;
     */
    private Expression negate() {
        if(match(MINUS)) {
            Token op = previous();
            Expression right = negate();
            return new Expression.Unary(op, right);
        }

        return literal();
    }

    /**
     * power :>  negate [ ( '^' ) negate ] ;
     */
    private Expression power() {
        Expression expr = negate();

        while(match(EXP)) {
            Token op = previous();
            Expression right = power();
            expr = new Expression.Binary(expr, op, right);
        }
        return expr;
    }

    /**
     * multidivimod :>  power [ ( '*' | '/' | '%' ) power ] ;
     */
    private Expression multidivimod() {
        Expression expr = power();

        while(match(STAR, SLASH, MODULO)) {
            Token op = previous();
            Expression right = power();
            expr = new Expression.Binary(expr, op, right);
        }
        return expr;
    }

    /**
     * addsub :>  multidivimod [ ( '+' | '-' ) multidivimod ] ;
     */
    private Expression addsub() {
        Expression expr = multidivimod();

        while(match(PLUS, MINUS)) {
            Token op = previous();
            Expression right = multidivimod();
            expr = new Expression.Binary(expr, op, right);
        }
        return expr;
    }

    /**
     * expression :>  addsub ;
     */
    private Expression expression() {
        return addsub();
    }


    private boolean match(TokenType... types) {
        for (TokenType type : types) {
            if (checkType(type)) {
                advance();
                return true;
            }
        }

        return false;
    }

    private Token consume(TokenType type, String message) {
        if (checkType(type)) return advance();


//        System.err.println(message);
        throw new RuntimeException(message);
    }

    private boolean checkType(TokenType type) {
        if (isAtEnd()) return false;
        return peek().getType() == type;
    }

    private Token advance() {
        if (!isAtEnd()) current++;
        return previous();
    }

    private boolean isAtEnd() {
        return peek().getType() == EOL;
    }

    private Token peek() {
        return tokens.get(current);
    }

    private Token previous() {
        return tokens.get(current - 1);
    }
}
