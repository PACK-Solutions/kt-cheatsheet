package com.ps.java8.s11;

import java.util.Optional;

public class LetLike {
    public static void main(String[] args) {
        String maybe = Math.random() > 0.5 ? "kotlin" : null;

        // Alternative "let-like" avec Optional
        String upper = Optional.ofNullable(maybe)
                .map(String::toUpperCase)
                .orElse("N/A");

        System.out.println(upper);
    }
}
