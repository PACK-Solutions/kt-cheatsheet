package com.ps.java8.s08;

public class ElvisExample {
    public static String displayName(String nickname, String fullName) {
        // Équivalent "manuel" de Elvis
        String name = (nickname != null) ? nickname : fullName;
        if (name == null) {
            name = "Unknown";
        }
        return name;
    }

    public static String required(String input) {
        if (input == null) {
            throw new IllegalArgumentException("input required");
        }
        return input;
    }

    public static void main(String[] args) {
        System.out.println(displayName(null, "Alice")); // Alice
        System.out.println(displayName(null, null));    // Unknown
        System.out.println(required("ok"));             // ok
    }
}
