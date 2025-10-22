package com.ps.java21.s08;

import java.util.Objects;

public class ElvisExample21 {
    public static String displayName(String nickname, String fullName) {
        String name = Objects.requireNonNullElse(nickname, fullName);
        return Objects.requireNonNullElse(name, "Unknown");
    }

    public static String required(String input) {
        return Objects.requireNonNull(input, "input required");
    }
}
