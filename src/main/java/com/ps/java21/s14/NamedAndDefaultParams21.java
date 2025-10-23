package com.ps.java21.s14;

/**
 * Java 21 ne propose pas de paramètres nommés, ni de valeurs par défaut
 * sur les paramètres. Les patterns restent similaires à Java 8, mais
 * les records simplifient les DTO, et on peut fournir des usines statiques.
 */
public class NamedAndDefaultParams21 {

    // --- Valeurs par défaut via surcharge (approche simple et directe) ---
    public static String greet(String name) {
        return greet(name, "!", "Hello");
    }

    public static String greet(String name, String punctuation) {
        return greet(name, punctuation, "Hello");
    }

    public static String greet(String name, String punctuation, String prefix) {
        return prefix + " " + name + punctuation;
    }

    // --- "Paramètres nommés" via Builder, avec Record pour le DTO ---
    public record Mail(String to, String subject, String body, boolean urgent) {
        public String summary() { return "to=" + to + "; subj=" + subject + "; urgent=" + urgent; }

        public static Builder builder() { return new Builder(); }

        public static final class Builder {
            private String to;                         // requis
            private String subject = "(no subject)";   // défaut
            private String body = "";                  // défaut
            private boolean urgent = false;            // défaut

            public Builder to(String to) { this.to = to; return this; }
            public Builder subject(String subject) { this.subject = subject; return this; }
            public Builder body(String body) { this.body = body; return this; }
            public Builder urgent(boolean urgent) { this.urgent = urgent; return this; }
            public Mail build() {
                if (to == null) throw new IllegalStateException("to is required");
                return new Mail(to, subject, body, urgent);
            }
        }

        // Usines pratiques, jouant le rôle de valeurs par défaut
        public static Mail of(String to) { return new Mail(to, "(no subject)", "", false); }
        public static Mail of(String to, String subject) { return new Mail(to, subject, "", false); }
    }

    public static void main(String[] args) {
        System.out.println(greet("Alice"));
        System.out.println(greet("Bob", "?"));
        System.out.println(greet("Charly", "!", "Hi"));

        Mail m1 = Mail.of("team@example.com");
        Mail m2 = Mail.builder().to("boss@example.com").subject("Weekly report").urgent(true).build();
        System.out.println(m1.summary());
        System.out.println(m2.summary());
    }
}
