package com.calculator;

public class Token {
    private final TokenType type;
    private final Object lexme;
//    private final Object literal;

    public Token(TokenType type, Object lexme, Object literal){
        this.type = type;
        this.lexme = lexme;
//        this.literal = literal;
    }

    public Token(TokenType type, Object lexme){
        this.type = type;
        this.lexme = lexme;
    }

    public TokenType getType(){ return type;}
    public Object getLexme(){ return lexme;}
//    public Object getLiteral(){ return literal;}

    public String toString() { return type + " " + lexme; }
}
