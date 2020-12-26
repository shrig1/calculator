package com.calculator;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.BufferedReader;

public class Calculator {
    public static void main(String[] args) throws IOException {
        runREPL();                                                  //for now it will be an interactive console, might just be a console forever. Who knows ¯\_(ツ)_/¯
    }

    private static void runREPL() throws IOException {
        while(true) {
            BufferedReader reader = new BufferedReader( new InputStreamReader(System.in));
            System.out.print("> ");
            String line = reader.readLine();
            if(line.isEmpty()) {
                break;
            }
            System.out.println(line);
        }
    }
}
