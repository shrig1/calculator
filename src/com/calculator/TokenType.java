package com.calculator;

public enum TokenType {
    // Groupings
    LEFT_PAREN, RIGHT_PAREN, ABS_BRACK,
    // Arithmetic Operators
    MINUS, PLUS, SLASH, STAR, MODULO, EXP, FACTORIAL,
    /*   I might add equality and comparison functionality later, but for now I will omit them
    // Equality
    BANG_EQUAL, EQUAL_EQUAL,
    // Comparison operators
    GREATER, GREATER_EQUAL, LESS, LESS_EQUAL,
     */
    // Values
    NUMBER, PI, E, PHI,

    // Misc.
    EOL, COMMA,

    //Functions
    SQRT, ROOT, LOG, LN,
    SIN, SINH, COS, COSH, TAN, TANH, CSC, CSCH, SEC, SECH, COT, COTH, ARCSIN, ARCSINH, ARCCOS, ARCCOSH, ARCTAN, ARCTANH, ARCCSC, ARCCSCH, ARCSEC, ARCSECH, ARCCOT, ARCCOTH,
    VER, VCS, CVS, CVC, SEM, HVC, HCV, HCC, EXS, EXC, CRD
}
