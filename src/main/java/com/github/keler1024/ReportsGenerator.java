package com.github.keler1024;

import java.io.*;

public class ReportsGenerator {
    public static void main(String[] args) {
        if (args.length > 1) {
            System.out.println(args.length + " arguments were provided. Program supports 0 or 1 argument.");
            return;
        }
        BufferedReader inputReader;
        if (args.length == 1) {
            try {
                inputReader = new BufferedReader(new FileReader(args[0]));
            } catch (FileNotFoundException e) {
                System.out.println("Provided file not found.");
                return;
            }
        } else {
            InputStream resourceInputStream = ReportsGenerator.class.getResourceAsStream("/cdr.txt");
            if (resourceInputStream == null) {
                System.out.println("Example file not found");
                return;
            }
            inputReader = new BufferedReader(new InputStreamReader(resourceInputStream));
        }


        try(inputReader) {
            System.out.println(inputReader.readLine());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
