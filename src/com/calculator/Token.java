package com.calculator;

public class Token {
    private final TokenType type;
    private final Object lexeme;
//    public String lexeme;

    public Token(TokenType type, Object lexeme){
        this.type = type;
        this.lexeme = lexeme;
    }

    public TokenType getType(){ return type;}
    public Object getLexme(){ return lexeme;}

    public String toString() { return type + " " + lexeme; }
}
