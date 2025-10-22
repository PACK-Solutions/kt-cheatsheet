package com.ps.java21.s07;

sealed interface Result<T> permits Success, Error { }

record Success<T>(T value) implements Result<T> { }
record Error<T>(String message) implements Result<T> { }

final class DemoResult {
    static <T> String render(Result<T> r) {
        return switch (r) {
            case Success<T> s -> "OK: " + s.value();
            case Error<T> e   -> "ERR: " + e.message();
        };
    }

    public static void main(String[] args) {
        System.out.println(render(new Success<>("data")));
        System.out.println(render(new Error<>("boom")));
    }
}
