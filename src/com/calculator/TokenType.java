package com.calculator;

public enum TokenType {
    // Groupings
    LEFT_PAREN, RIGHT_PAREN, ABS_BRACK,
    // Arithmetic Operators
    MINUS, PLUS, SLASH, STAR, MODULO, EXP,
    /*   I might add equality and comparison functionality later, but for now I will omit them
    // Equality
    BANG_EQUAL, EQUAL_EQUAL,
    // Comparison operators
    GREATER, GREATER_EQUAL, LESS, LESS_EQUAL,
     */
    // Values
    NUMBER,

    // Misc.
    EOL
}