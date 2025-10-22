package com.ps.java8.s09;

import java.util.AbstractMap;
import java.util.Map;

public final class Pairing {
    public static <A, B> Map.Entry<A, B> to(A a, B b) {
        return new AbstractMap.SimpleEntry<>(a, b);
    }

    public static void main(String[] args) {
        var entry = Pairing.to("EUR", 100);
        System.out.println(entry.getKey() + " -> " + entry.getValue());
    }
}
