package com.calculator;

import com.calculator.utils.Error;

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
                throw new Error(String.format("Not a valid expression {%s} :(", this.error));
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
                throw new Error(String.format("Not a valid expression {%s} :(", this.error));
            }
        }
        if(match(RIGHT_PAREN)) {
            this.error = "Incorrect use of parenthesis";
            throw new Error(String.format("Not a valid expression {%s} :(", this.error));
        }

        throw new Error(String.format("Not a valid expression {%s} :(", this.error));
    }

    /*
    * function :> func_name '(' expression ')' | literal ;
    * func_name is all the functions valid, I can't be bothered to write all of them down
    */
    private Expression function() {
        if(match(SIN, SINH, COS, COSH, TAN, TANH, CSC, CSCH, SEC, SECH,
                COT, COTH, ARCSIN, ARCSINH, ARCCOS, ARCCOSH, ARCTAN, ARCTANH,
                ARCCSC, ARCCSCH, ARCSEC, ARCSECH, ARCCOT, ARCCOTH, VER,
                VCS, CVS, CVC, SEM, HVC, HCV, HCC, EXS, EXC, CRD)) {
            Token function = previous();
            consume(LEFT_PAREN);
            Expression arg = expression();
            consume(RIGHT_PAREN);
            return new Expression.Function(function, arg);
        }
        return literal();
    }

    /*
    * factorial :>  function '!' | function ;
    */
    private Expression factorial() {
        Expression left = function();
        if(match(FACTORIAL)) {
            Token fac = previous();
            return new Expression.Unary(fac, left);
        }

        return left;
    }

    /**
     * negate :>  '-' negate | factorial ;
     */
    private Expression negate() {
        if(match(MINUS)) {
            Token minus = previous();
            Expression right = factorial();
            return new Expression.Unary(minus, right);
        }

        return factorial();
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
        throw new Error(String.format("Missing Token %s :(", type.toString()));
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
