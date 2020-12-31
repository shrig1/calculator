package com.calculator.utils;

public class NumberTypes {
    static class Fraction {
        private final double numerator;
        private final double denomenator;

        Fraction(double numerator, double denomenator) {
            this.numerator = numerator;
            this.denomenator = denomenator;
        }

        public double getNumerator() {
            return numerator;
        }

        public double getDenomenator() {
            return denomenator;
        }
    }
}
