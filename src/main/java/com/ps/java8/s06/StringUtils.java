package com.ps.java8.s06;

public final class StringUtils {
    private StringUtils() {}

    public static String surround(String s, String prefix, String suffix) {
        return prefix + s + suffix;
    }

    public static void main(String[] args) {
        System.out.println(StringUtils.surround("core", "[", "]")); // [core]
    }
}
