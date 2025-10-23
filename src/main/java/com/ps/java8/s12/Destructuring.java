package com.ps.java8.s12;

import java.util.AbstractMap;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * Java 8 ne supporte pas nativement la destructuration.
 * On extrait donc manuellement via des getters/indices.
 */
public class Destructuring {

    // Petit Pair minimaliste pour la démonstration (pas de dépendance externe)
    public static final class Pair<A, B> {
        private final A first;
        private final B second;
        public Pair(A first, B second) { this.first = first; this.second = second; }
        public A getFirst() { return first; }
        public B getSecond() { return second; }
        @Override public String toString() { return "(" + first + ", " + second + ")"; }
    }

    public static Pair<Integer, Integer> compute() {
        return new Pair<>(21, 21);
    }

    public static void main(String[] args) {
        // 1) Simuler la "destructuration" d'un Pair
        Pair<Integer, Integer> p = compute();
        Integer x = p.getFirst();
        Integer y = p.getSecond();
        System.out.println("x=" + x + ", y=" + y);

        // 2) Avec une classe classique
        Person person = new Person("Alice", 30);
        String name = person.getName();
        int age = person.getAge();
        System.out.println(name + " a " + age + " ans");

        // 3) Sur des Map.Entry
        List<Map.Entry<String, Integer>> entries = Arrays.asList(
                new AbstractMap.SimpleEntry<>("kotlin", 3),
                new AbstractMap.SimpleEntry<>("java", 2)
        );
        for (Map.Entry<String, Integer> e : entries) {
            String word = e.getKey();
            Integer count = e.getValue();
            System.out.println(word + " → " + count);
        }
    }

    public static final class Person {
        private final String name;
        private final int age;
        public Person(String name, int age) { this.name = name; this.age = age; }
        public String getName() { return name; }
        public int getAge() { return age; }
    }
}
