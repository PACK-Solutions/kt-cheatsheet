package com.ps.java21.s02;

public interface Greeter {
    String greet(String name);
    default String greetCasual(String name) { return "Hi " + name; }
}
