package com.calculator.utils;

// I could use RuntimeException instead but I created my own class in case I wanted to make the error system more elaborate
public class Error extends RuntimeException{

    public Error(String message) {
        super(message);
    }


}