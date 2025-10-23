package com.ps.java8.s13;

/**
 * Équivalents Java 8 aux usages de Kotlin `when`.
 * On utilise des if/else, instanceof et un switch classique.
 */
public class WhenLike {

    interface Shape {}
    static final class Circle implements Shape { final double radius; Circle(double r){ this.radius=r; } }
    static final class Rectangle implements Shape { final double width, height; Rectangle(double w, double h){ this.width=w; this.height=h; } }
    static final class Unknown implements Shape { final String label; Unknown(String l){ this.label=l; } }

    static double area(Shape s) {
        if (s instanceof Circle) {
            Circle c = (Circle) s;
            return Math.PI * c.radius * c.radius;
        } else if (s instanceof Rectangle) {
            Rectangle r = (Rectangle) s;
            return r.width * r.height;
        } else if (s instanceof Unknown) {
            return Double.NaN;
        } else {
            throw new IllegalArgumentException("shape inconnu: " + s);
        }
    }

    static String classifyScore(int score) {
        if (score >= 90 && score <= 100) return "A";
        if (score >= 75 && score <= 89) return "B";
        if (score >= 60 && score <= 74) return "C";
        if (score >= 0 && score <= 59) return "D";
        return "Invalide";
    }

    static String fizzBuzz(int n) {
        if (n % 15 == 0) return "FizzBuzz";
        if (n % 3 == 0) return "Fizz";
        if (n % 5 == 0) return "Buzz";
        return Integer.toString(n);
    }

    static String describe(Object x) {
        if (x == null) return "null";
        if (x instanceof String) return "String(len=" + ((String)x).length() + ")";
        if (x instanceof Number) return "Number(" + x + ")";
        return "Autre(" + x.getClass().getSimpleName() + ")";
    }

    public static void main(String[] args) {
        Shape[] shapes = { new Circle(2.0), new Rectangle(3.0, 4.0), new Unknown("?") };
        for (Shape s : shapes) {
            System.out.println("area(" + s + ") = " + area(s));
        }

        System.out.println(java.util.Arrays.toString(new int[]{95, 80, 67, 10, -1})
                .replaceAll("[\\[\\] ]", "")); // juste pour montrer la liste d'entrée
        System.out.println(java.util.Arrays.asList(95, 80, 67, 10, -1).stream()
                .map(WhenLike::classifyScore)
                .toList());

        String fb = java.util.stream.IntStream.rangeClosed(1, 16)
                .mapToObj(WhenLike::fizzBuzz)
                .collect(java.util.stream.Collectors.joining(" "));
        System.out.println(fb);

        System.out.println(describe("kotlin"));
        System.out.println(describe(42));
        System.out.println(describe(null));
    }
}
