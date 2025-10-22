package com.ps.java8.s02;

public class ConsoleGreeter implements Greeter {
    @Override
    public String greet(String name) {
        return "Hello " + name;
    }
}
