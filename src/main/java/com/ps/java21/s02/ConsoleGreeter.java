package com.ps.java21.s02;

public final class ConsoleGreeter implements Greeter {
    @Override
    public String greet(String name) { return "Hello " + name; }
}
