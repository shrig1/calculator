package com.calculator;

import java.util.HashMap;
import java.util.Map;
import static com.calculator.TokenType.*;

public class Environment {
    public Map<String, Double> variables = new HashMap<>();
    private double previous_result;


    public double getPreviousResult() {
        return previous_result;
    }

    public void setPreviousResult(double previous_result) {
        this.previous_result = previous_result;
    }
}
