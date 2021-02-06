package com.calculator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static com.calculator.TokenType.*;

public class Lexer {
    private final String line;
    private final ArrayList<Token> tokens = new ArrayList<>();
    private int start = 0;
    private int current = 0;
    private final Map<String, TokenType> functions;

    public Lexer(String line) {
        this.line = line;
        functions = new HashMap<>();
        functions.put("ans", ANS);
        functions.put("sqrt", SQRT);
        functions.put("root", ROOT);
        functions.put("log", LOG);
        functions.put("ln", LN);
        functions.put("sin", SIN);
        functions.put("sinh", SINH);
        functions.put("cos", COS);
        functions.put("cosh", COSH);
        functions.put("tan", TAN);
        functions.put("tanh", TANH);
        functions.put("csc", CSC);
        functions.put("csch", CSCH);
        functions.put("sec", SEC);
        functions.put("sech", SECH);
        functions.put("cot", COT);
        functions.put("coth", COTH);
        functions.put("arcsin", ARCSIN);
        functions.put("arccos", ARCCOS);
        functions.put("arctan", ARCTAN);
        functions.put("arccsc", ARCCSC);
        functions.put("arcsec", ARCSEC);
        functions.put("arccot", ARCCOT);
        functions.put("ver", VER);
        functions.put("vcs", VCS);
        functions.put("cvs", CVS);
        functions.put("cvc", CVC);
        functions.put("sem", SEM);
        functions.put("hvc", HVC);
        functions.put("hcv", HCV);
        functions.put("hcc", HCC);
        functions.put("exs", EXS);
        functions.put("exc", EXC);
        functions.put("crd", CRD);
        functions.put("normalpdf", NORMALPDF);
        functions.put("normalcdf", NORMALCDF);
        functions.put("binomialpdf", BINOMIALPDF);
        functions.put("binomialcdf", BINOMIALCDF);
        functions.put("invnorm", INVNORM);

        functions.put("import", IMPORT);
        functions.put("pi", PI);
        functions.put("phi", PHI);
        functions.put("e", E);
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
            case '!': addToken(FACTORIAL); break;
            case '=': addToken(EQUAL); break;
            case ',': addToken(COMMA); break;
            case ' ':
            case '\r':
            case '\t': break;
            default:
                if (isDigit(c)) {
                    number();
                } else if(isLetter(c)) {
                    name();
                } else if(c == '"') {
                    arg();
                } else {
                    System.err.printf("Error, char %s is not allowed >:(%n", c);
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

    private void addToken(TokenType type, Object lexeme) { tokens.add(new Token(type, lexeme)); }

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

    private boolean isLetter(char c) { return (c >= 'a' && c <= 'z') || (c >= 'A' && c <= 'Z') || c == '_'; }

    private boolean isAlphaNumeric(char c) { return (c >= 'a' && c <= 'z') || (c >= 'A' && c <= 'Z') || (c >= '0' && c <= '9') || c == '_';}

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

    private void name() {
        while(isAlphaNumeric(peek(0))) advance();

        String text = line.substring(start, current);
        TokenType type = functions.get(text);
//        if(type == null) throw new Error(String.format("Invalid text '%s' smh", text));
        if(type == null) {
            addToken(VARIABLE, text);
        } else {
            addToken(type);
        }

    }

    private void arg() {
        while((peek(0) != '"')) advance();
        advance();

        String arg = line.substring(start + 1, current - 1);
        addToken(ARG, arg);
    }


}
