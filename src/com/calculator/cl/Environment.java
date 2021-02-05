package com.calculator.cl;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class Environment {
    public Map<String, Double> variables = new HashMap<>();
    private double previous_result;


    public void importVariablesFromFile(String path, boolean override) throws IOException {
        BufferedReader reader = new BufferedReader( new FileReader(path) );
        StringBuilder data = new StringBuilder();
        String line;
        while((line = reader.readLine()) != null) {
            data.append(line.replaceAll(" ", ""));
        }
        String[] vars = data.toString().split(",");
        for(String var: vars) {
            String name = var.split("=")[0];
            double value = Double.parseDouble(var.split("=")[1]);
            boolean exists = (variables.get(name) != null);
            if(exists) {
                if(override){
                    variables.put(name, value);
                }
            }
            else {
                variables.put(name, value);
            }
        }

    }


    public double getPreviousResult() {
        return previous_result;
    }

    public void setPreviousResult(double previous_result) {
        this.previous_result = previous_result;
    }
}
