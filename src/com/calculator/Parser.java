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
    private String error = "";

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
        String error = "";
        if(match(NUMBER)) return new Expression.Literal(previous().getLexme());
        if(match(LEFT_PAREN)) {
            try {
                Expression expr = expression();
                consume(RIGHT_PAREN);
                return new Expression.Grouping(expr, "grouping");
            } catch(RuntimeException e) {
                this.error = "Incorrect use of parenthesis";
//                System.err.println("Incorrect use of parenthesis");
                throw new RuntimeException(String.format("Not a valid expression {%s} :(", this.error));
            }
        }
        if(match(ABS_BRACK)) {
            Expression expr = expression();
            try {
                consume(ABS_BRACK);
                return new Expression.Grouping(expr, "abs");
            } catch(RuntimeException e) {
//                System.err.println("Incorrect use of absolute value");
                this.error = "Incorrect use of absolute value";
                throw new RuntimeException(String.format("Not a valid expression {%s} :(", this.error));
            }
        }
        if(match(RIGHT_PAREN)) {
            this.error = "Incorrect use of parenthesis";
            throw new RuntimeException(String.format("Not a valid expression {%s} :(", this.error));
        }

        throw new RuntimeException(String.format("Not a valid expression {%s} :(", this.error));
    }

    /**
     * negate :>  '-' negate | power ;
     */
    private Expression negate() {
        if(match(MINUS)) {
            Token op = previous();
            Expression right = literal();
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

    private Token consume(TokenType type) {
        if (checkType(type)) return advance();


//        System.err.println(message);
        throw new RuntimeException();
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
