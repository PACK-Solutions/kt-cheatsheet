package com.ps.java21.s13;

/**
 * Java 21: switch expressions + pattern matching pour se rapprocher de Kotlin `when`.
 */
public class WhenLike21 {

    sealed interface Shape permits Circle, Rectangle, Unknown {}
    static final class Circle implements Shape { final double radius; Circle(double r){ this.radius=r; } }
    static final class Rectangle implements Shape { final double width, height; Rectangle(double w, double h){ this.width=w; this.height=h; } }
    static final class Unknown implements Shape { final String label; Unknown(String l){ this.label=l; } }

    static double area(Shape s) {
        return switch (s) {
            case Circle c -> Math.PI * c.radius * c.radius;
            case Rectangle r -> r.width * r.height;
            case Unknown u -> Double.NaN;
        };
    }

    static String classifyScore(int score) {
        return switch (score) {
            default -> {
                if (score >= 90 && score <= 100) yield "A";
                else if (score >= 75 && score <= 89) yield "B";
                else if (score >= 60 && score <= 74) yield "C";
                else if (score >= 0 && score <= 59) yield "D";
                else yield "Invalide";
            }
        };
    }

    static String fizzBuzz(int n) {
        return switch (0) { // switch sans sujet direct: on encode avec gardes
            // on peut aussi écrire un if/else, mais on montre les guards de switch
            default -> {
                if (n % 15 == 0) yield "FizzBuzz";
                else if (n % 3 == 0) yield "Fizz";
                else if (n % 5 == 0) yield "Buzz";
                else yield Integer.toString(n);
            }
        };
    }

    static String describe(Object x) {
        return switch (x) {
            case null -> "null";
            case String s -> "String(len=" + s.length() + ")";
            case Number n -> "Number(" + n + ")";
            default -> "Autre(" + x.getClass().getSimpleName() + ")";
        };
    }

    public static void main(String[] args) {
        Shape[] shapes = { new Circle(2.0), new Rectangle(3.0, 4.0), new Unknown("?") };
        for (Shape s : shapes) System.out.println("area(" + s + ") = " + area(s));

        System.out.println(java.util.Arrays.asList(95, 80, 67, 10, -1).stream()
                .map(WhenLike21::classifyScore)
                .toList());

        String fb = java.util.stream.IntStream.rangeClosed(1, 16)
                .mapToObj(WhenLike21::fizzBuzz)
                .collect(java.util.stream.Collectors.joining(" "));
        System.out.println(fb);

        System.out.println(describe("kotlin"));
        System.out.println(describe(42));
        System.out.println(describe(null));
    }
}
