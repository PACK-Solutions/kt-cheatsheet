package com.ps.java8.s14;

/**
 * Java 8 n'a ni paramètres nommés ni valeurs par défaut.
 * On émule:
 *  - par surcharge de méthodes (telescoping) pour des valeurs par défaut
 *  - par un Builder fluent pour simuler des "paramètres nommés"
 */
public class NamedAndDefaultParams {

    // --- Exemple 1: valeurs par défaut via surcharge ---
    public static String greet(String name) {
        return greet(name, "!", "Hello");
    }

    public static String greet(String name, String punctuation) {
        return greet(name, punctuation, "Hello");
    }

    public static String greet(String name, String punctuation, String prefix) {
        return prefix + " " + name + punctuation;
    }

    // --- Exemple 2: "paramètres nommés" via Builder ---
    public static final class Mail {
        private final String to;
        private final String subject;
        private final String body;
        private final boolean urgent;

        private Mail(Builder b) {
            this.to = b.to;
            this.subject = b.subject;
            this.body = b.body;
            this.urgent = b.urgent;
        }

        public String summary() {
            return "to=" + to + "; subj=" + subject + "; urgent=" + urgent;
        }

        public static final class Builder {
            private String to;                     // requis
            private String subject = "(no subject)"; // défaut
            private String body = "";               // défaut
            private boolean urgent = false;         // défaut

            public Builder to(String to) { this.to = to; return this; }
            public Builder subject(String subject) { this.subject = subject; return this; }
            public Builder body(String body) { this.body = body; return this; }
            public Builder urgent(boolean urgent) { this.urgent = urgent; return this; }

            public Mail build() {
                if (to == null) throw new IllegalStateException("to is required");
                return new Mail(this);
                
            }
        }
    }

    public static void main(String[] args) {
        // Surcharges pour valeurs par défaut
        System.out.println(greet("Alice"));          // Hello Alice!
        System.out.println(greet("Bob", "?"));      // Hello Bob?
        System.out.println(greet("Charly", "!", "Hi")); // Hi Charly!

        // Builder pour paramètres "nommés"
        Mail m1 = new Mail.Builder()
                .to("team@example.com")
                .build();
        Mail m2 = new Mail.Builder()
                .to("boss@example.com")
                .subject("Weekly report")
                .urgent(true)
                .build();
        System.out.println(m1.summary());
        System.out.println(m2.summary());
    }
}
