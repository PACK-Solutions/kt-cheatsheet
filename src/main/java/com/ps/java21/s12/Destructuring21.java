package com.ps.java21.s12;

/**
 * Java 21 apporte les record patterns permettant la "destructuration"
 * de records via instanceof/switch. Cela reste plus verbeux que Kotlin,
 * mais plus expressif que Java 8.
 */
public class Destructuring21 {

    public static record Pair<A, B>(A first, B second) { }
    public static record Person(String name, int age) { }

    public static Pair<Integer, Integer> compute() { return new Pair<>(21, 21); }

    public static void main(String[] args) {
        Object obj = compute();
        if (obj instanceof Pair(var x, var y)) {
            System.out.println("x=" + x + ", y=" + y);
        }

        Object o2 = new Person("Alice", 30);
        switch (o2) {
            case Person(String name, int age) -> System.out.println(name + " a " + age + " ans");
            default -> System.out.println("autre");
        }
    }
}
