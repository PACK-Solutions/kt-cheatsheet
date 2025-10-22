package com.ps.java21.s11;

import java.util.Optional;

public class LetLike21 {
    public static void main(String[] args) {
        String maybe = Math.random() > 0.5 ? "kotlin" : null;
        String result = Optional.ofNullable(maybe)
                .map(String::strip)
                .filter(s -> !s.isEmpty())
                .map(String::toUpperCase)
                .orElse("N/A");
        System.out.println(result);
    }
}
