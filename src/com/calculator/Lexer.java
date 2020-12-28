package com.calculator;

import java.util.ArrayList;

import static com.calculator.TokenType.*;

public class Lexer {
    private final String line;
    private final ArrayList<Token> tokens = new ArrayList();
    private int start = 0;
    private int current = 0;

    public Lexer(String line) {
        this.line = line;
    }

    public ArrayList<Token> scanTokens() {
        while (!isAtEnd()) {
            // We are at the beginning of the next lexeme.
            start = current;
            scanToken();
        }
        tokens.add(new Token(EOL, null));
        return tokens;

    }

    private void scanToken() {
        char c = advance();
        switch (c) {
            case '(': addToken(LEFT_PAREN); break;
            case ')': addToken(RIGHT_PAREN); break;
            case '|': addToken(ABS_BRACK); break;
            case '-': addToken(MINUS); break;
            case '+': addToken(PLUS); break;
            case '*': addToken(STAR); break;
            case '/': addToken(SLASH); break;
            case '^': addToken(EXP); break;
            case '%': addToken(MODULO); break;
//            case '!':
//                if(matchNext('=')) {
//                    addToken(BANG_EQUAL);
//                }
//                break;
//            case '=':
//                if(matchNext('=')) {
//                    addToken(EQUAL_EQUAL);
//                }
//                break;
//            case '<': addToken(matchNext('=') ? LESS_EQUAL : LESS); break;
//            case '>': addToken(matchNext('=') ? GREATER_EQUAL : GREATER); break;
            case ' ':
            case '\r':
            case '\t': break;
            default:
                if (isDigit(c)) {
                    number();
                } else {
                    System.err.println(String.format("Error, char %s is not allowed >:(", c));
                }
                break;
        }
    }

    //helper methods
    private boolean isAtEnd() {
        return current >= line.length();
    }

    private char advance() {
        current++;
        return line.charAt(current - 1);
    }

    private char peek(int d) {
        if (current + d >= line.length()) return '\0';
        return line.charAt(current + d);
    }

    private void addToken(TokenType type, Object lexme) {
        tokens.add(new Token(type, lexme));
    }

    private void addToken(TokenType type) {
        String text = line.substring(start, current);
        tokens.add(new Token(type, text));
    }

    private boolean matchNext(char expected) {
        if (isAtEnd()) return false;
        if (line.charAt(current) != expected) return false;

        current++;
        return true;
    }


    private boolean isDigit(char c){
        return c >= '0' && c <= '9';
    }

    private void number() {
        while (isDigit(peek(0))) advance();

        // Look for a fractional part.
        if (peek(0) == '.' && isDigit(peek(1))) {
            // Consume the "."
            advance();

            while (isDigit(peek(0))) advance();
        }

        addToken(NUMBER, Double.parseDouble(line.substring(start, current)));
    }


}
