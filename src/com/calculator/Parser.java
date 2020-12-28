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

    public Expr parse() {
        return expression();
    }

    // going from smallest layer to highest layer

    /**
     * literal :>  NUMBER | '(' expression ')' ;
     */
    private Expr literal() {
        if(match(NUMBER)) return new Expr.Literal(previous().getLexme());
        if(match(LEFT_PAREN)) {
            Expr expr = expression();
            consume(RIGHT_PAREN, "You need a ')' to follow up with an expression after a '('... smh");
            return new Expr.Grouping(expr);
        }

        throw new RuntimeException("Need an expression :(");
    }

    /**
     * negate :>  '-' negate | literal ;
     */
    private Expr negate() {
        if(match(MINUS)) {
            Token op = previous();
            Expr right = negate();
            return new Expr.Unary(op, right);
        }

        return literal();
    }

    /**
     * power :>  negate [ ( '^' ) negate ] ;
     */
    private Expr power() {
        Expr expr = negate();

        while(match(EXP)) {
            Token op = previous();
            Expr right = power();
            expr = new Expr.Binary(expr, op, right);
        }
        return expr;
    }

    /**
     * multidivimod :>  power [ ( '*' | '/' | '%' ) power ] ;
     */
    private Expr multidivimod() {
        Expr expr = power();

        while(match(STAR, SLASH, MODULO)) {
            Token op = previous();
            Expr right = power();
            expr = new Expr.Binary(expr, op, right);
        }
        return expr;
    }

    /**
     * addsub :>  multidivimod [ ( '+' | '-' ) multidivimod ] ;
     */
    private Expr addsub() {
        Expr expr = multidivimod();

        while(match(PLUS, MINUS)) {
            Token op = previous();
            Expr right = multidivimod();
            expr = new Expr.Binary(expr, op, right);
        }
        return expr;
    }

    /**
     * expression :>  addsub ;
     */
    private Expr expression() {
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
