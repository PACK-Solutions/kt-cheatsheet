package com.ps.java8.s07;

// Imitation de sealed avec classes statiques imbriquées
abstract class Result<T> {
    private Result() {}
    static final class Success<T> extends Result<T> {
        final T value;
        Success(T value) { this.value = value; }
    }
    static final class Error<T> extends Result<T> {
        final String message;
        Error(String message) { this.message = message; }
    }

    static <T> String render(Result<T> r) {
        if (r instanceof Success) {
            T v = ((Success<T>) r).value;
            return "OK: " + v;
        } else if (r instanceof Error) {
            String m = ((Error<T>) r).message;
            return "ERR: " + m;
        }
        throw new IllegalStateException("Unknown");
    }

    public static void main(String[] args) {
        System.out.println(render(new Success<>("data")));
        System.out.println(render(new Error<>("boom")));
    }
}
