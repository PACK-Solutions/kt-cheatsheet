package com.ps.java8.s05;

import java.util.Objects;

public class NullSafetyExample {
    public static String safeUpper(String s) {
        if (s == null) return "N/A";
        return s.toUpperCase();
    }

    public static String requireNonNullUpper(String s) {
        return Objects.requireNonNull(s, "s must not be null").toUpperCase();
    }

    public static void main(String[] args) {
        System.out.println(safeUpper(null)); // N/A
        System.out.println(requireNonNullUpper("hello")); // HELLO
    }
}
