package com.calculator;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.util.ArrayList;

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
//            System.out.println(line);
            Lexer lexer = new Lexer(line);
            ArrayList<Token> tokens = lexer.scanTokens();

//            System.out.println(tokens);
            Parser parser = new Parser(tokens);
            Expr expression = parser.parse();

            System.out.println(new AstPrinter().print(expression));
        }
    }
}
