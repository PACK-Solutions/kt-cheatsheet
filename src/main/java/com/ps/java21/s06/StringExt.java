package com.ps.java21.s06;

public interface StringExt {
    static String surround(String s, String prefix, String suffix) {
        return prefix + s + suffix;
    }

    final class Demo {
        public static void main(String[] args) {
            System.out.println(StringExt.surround("core", "[", "]"));
        }
    }
}
