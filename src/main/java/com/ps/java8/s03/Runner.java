package com.ps.java8.s03;

import java.util.function.Function;

public class Runner {
    public static <T, R> R withValue(T value, Function<T, R> block) {
        return block.apply(value);
    }

    public static void main(String[] args) {
        String result = withValue(42, v -> "val=" + v);
        System.out.println(result);
    }
}
