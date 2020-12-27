package com.calculator;

public enum TokenType {
    // Groupings
    LEFT_PAREN, RIGHT_PAREN, ABS_BRACK,
    // Arithmetic Operators
    MINUS, PLUS, SLASH, STAR, MODULO,
    // Equality
    BANG_EQUAL, EQUAL_EQUAL,
    // Comparison operators
    GREATER, GREATER_EQUAL, LESS, LESS_EQUAL,
    // Values
    NUMBER
}
