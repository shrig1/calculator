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
        BufferedReader reader = new BufferedReader( new InputStreamReader(System.in));
        Environment env = new Environment();
        Lexer lexer;
        Parser parser;
        Evaluator solver;
        while(true) {
            System.out.print("> ");
            String line = reader.readLine();
            if(line.isEmpty()) {
                break;
            }

            try{
//                System.out.println(line);
                lexer = new Lexer(line);
                ArrayList<Token> tokens = lexer.scanTokens();

//                System.out.println(tokens);
                parser = new Parser(tokens, env);
                Expression expression = parser.parse();
//                System.out.println(new AstPrinter().print(expression));
                solver = new Evaluator(env);
                solver.solve(expression);
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }


        }
        reader.close();
    }


}
