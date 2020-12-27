package com.calculator;

public class Token {
    private final TokenType type;
    private final Object lexme;

    public Token(TokenType type, Object lexme){
        this.type = type;
        this.lexme = lexme;
    }

    public TokenType getType(){ return type;}
    public Object getLexme(){ return lexme;}

    public String toString() { return type + " " + lexme; }
}
