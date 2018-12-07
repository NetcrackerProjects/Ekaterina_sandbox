package com.gigssandbox;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.util.List;
import java.util.Properties;
import java.util.Scanner;

public class ConsoleHelper {

    private static final Scanner in = new Scanner(System.in);
    private static final PrintStream out = System.out;
    private Properties properties = new Properties();
    {
        try {
            properties.load(new FileInputStream("src/main/resources/strings.properties"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String readString() {
        return in.nextLine();
    }

    public int readInt() {
        return in.nextInt();
    }

    public void writeStringToConsole(String s) {
        out.println(s);
    }

    public void writeListToConsole(List<?> list) {
        if (list.isEmpty())
            out.println(properties.getProperty("empty_result"));
        else {
            list.forEach(out::println);
        }
    }
}
