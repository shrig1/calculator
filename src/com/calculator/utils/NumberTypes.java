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

    static class ComplexNumber {
        private final double real;
        private final double imaginary;

        ComplexNumber(double real, double imaginary) {
            this.real = real;
            this.imaginary = imaginary;
        }

        public double getReal() { return real; }

        public double getImaginary() { return imaginary; }
    }
}
