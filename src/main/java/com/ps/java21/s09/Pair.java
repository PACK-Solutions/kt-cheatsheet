package com.ps.java21.s09;

public record Pair<A, B>(A first, B second) { }

final class DemoPair {
    public static void main(String[] args) {
        var p = new Pair<>("EUR", 100);
        System.out.println(p.first() + " -> " + p.second());
    }
}
