# Kotlin Cheatsheet pour développeurs Java

Ce document est une cheat sheet destinée aux développeurs Java souhaitant découvrir ou approfondir Kotlin. 
Elle met en avant les idiomes et bonnes pratiques Kotlin, avec des exemples comparant Java 8, Java 21 et Kotlin.

---

## 1) Déclarer une data class

Idée clé: une data class génère equals, hashCode, toString, copy et supporte la destructuration.

### Java 8
```java
public final class User {
    private final String name;
    private final int age;

    public User(String name, int age) {
        this.name = name;
        this.age  = age;
    }

    public String getName() { return name; }
    public int getAge() { return age; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof User)) return false;
        User other = (User) o;
        return age == other.age &&
               (name != null ? name.equals(other.name) : other.name == null);
    }

    @Override
    public int hashCode() {
        int result = (name != null ? name.hashCode() : 0);
        result = 31 * result + age;
        return result;
    }

    @Override
    public String toString() {
        return "User(name=" + name + ", age=" + age + ")";
    }
}
```

### Java 21 (record)
```java
public record User(String name, int age) { }
```

### Kotlin idiomatique
```kotlin
data class User(
    val name: String,
    val age: Int
)

fun main() {
    val u1 = User("Alice", 30)
    val u2 = u1.copy(age = 31)

    println(u1)               // User(name=Alice, age=30)
    println(u2 == u1)         // false
    val (n, a) = u2           // destructuration
    println("$n a $a ans")
}
```

## 2) Implémenter une interface

Idée clé: les interfaces Kotlin peuvent contenir des propriétés et des implémentations par défaut.

### Java 8
```java
public interface Greeter {
    String greet(String name);

    default String greetCasual(String name) {
        return "Hi " + name;
    }
}

public class ConsoleGreeter implements Greeter {
    @Override
    public String greet(String name) {
        return "Hello " + name;
    }
}
```

### Java 21 (identique côté interface par défaut)
```java
public interface Greeter {
    String greet(String name);
    default String greetCasual(String name) { return "Hi " + name; }
}

public final class ConsoleGreeter implements Greeter {
    @Override
    public String greet(String name) { return "Hello " + name; }
}
```

### Kotlin idiomatique
```kotlin
interface Greeter {
    fun greet(name: String): String
    fun greetCasual(name: String): String = "Hi $name"
}

class ConsoleGreeter : Greeter {
    override fun greet(name: String): String = "Hello $name"
}

fun main() {
    val g: Greeter = ConsoleGreeter()
    println(g.greet("Alice"))       // Hello Alice
    println(g.greetCasual("Alice")) // Hi Alice
}
```

### Diagramme
```
+------------------------+          implements         +----------------------+
|        Greeter         | <-------------------------- |    ConsoleGreeter    |
+------------------------+                             +----------------------+
| + greet(name): String  |                             | + greet(name): String|
| + greetCasual(name):   |                             +----------------------+
|   String (default)     |
+------------------------+
```

## 3) Trailing lambda (lambda en dernier paramètre)

Idée clé: si le dernier paramètre d’une fonction est une lambda, on peut la sortir des parenthèses.

### Java 8
```java
import java.util.function.Function;

public class Runner {
    public static <T, R> R withValue(T value, Function<T, R> block) {
        return block.apply(value);
    }

    public static void main(String[] args) {
        String result = withValue(42, v -> "val=" + v);
        System.out.println(result);
    }
}
```

### Java 21 (identique ici)
```java
import java.util.function.Function;

public class Runner {
    public static <T, R> R withValue(T value, Function<T, R> block) {
        return block.apply(value);
    }

    public static void main(String[] args) {
        String result = withValue(42, v -> "val=" + v);
        System.out.println(result);
    }
}
```

### Kotlin idiomatique
```kotlin
inline fun <T, R> withValue(value: T, block: (T) -> R): R = block(value)

fun main() {
    // Sans trailing lambda
    val r1 = withValue(42, { v -> "val=$v" })

    // Avec trailing lambda (idiomatique)
    val r2 = withValue(42) { v -> "val=$v" }

    // Inférence de type avec it
    val r3 = withValue(42) { "val=$it" }

    println(r1)
    println(r2)
    println(r3)
}
```

## 4) Propriétés et getters/setters automatiques

Idée clé: val/var créent getters/setters automatiquement; on peut personnaliser si besoin.

### Java 8
```java
public class Counter {
    private int value;

    public int getValue() { return value; }
    public void setValue(int v) { this.value = v; }
}
```

### Java 21 (identique pour classes régulières; records = immutables)
```java
public class Counter {
    private int value;
    public int getValue() { return value; }
    public void setValue(int v) { this.value = v; }
}
```

### Kotlin idiomatique
```kotlin
class Counter {
    var value: Int = 0
        set(v) {
            require(v >= 0) { "value must be >= 0" }
            field = v
        }
}

fun main() {
    val c = Counter()
    c.value = 10
    println(c.value) // 10
}
```

## 5) Null-safety

Idée clé: types nullables (String?), opérateurs sûrs (?.), Elvis (?:), assertion non-nulle (!!), et let/also/run.

### Java 8
```java
import java.util.Objects;

public class NullSafetyExample {
    public static String safeUpper(String s) {
        if (s == null) return "N/A";
        return s.toUpperCase();
    }

    public static String requireNonNullUpper(String s) {
        return Objects.requireNonNull(s, "s must not be null").toUpperCase();
    }

    public static void main(String[] args) {
        System.out.println(safeUpper(null)); // N/A
        System.out.println(requireNonNullUpper("hello")); // HELLO
    }
}
```

### Java 21
> Idem Java 8, pas de null-safety au niveau du type système. On peut utiliser @Nullable/@NonNull et outils statiques.

### Kotlin idiomatique
```kotlin
fun safeUpper(s: String?): String {
    // Opérateur sûr + Elvis
    return s?.uppercase() ?: "N/A"
}

fun requireNonNullUpper(s: String?): String {
    // requireNotNull -> échoue tôt avec message clair
    val nonNull = requireNotNull(s) { "s must not be null" }
    return nonNull.uppercase()
}

fun main() {
    val a: String? = null
    val b: String? = "hello"

    println(safeUpper(a))        // N/A
    println(safeUpper(b))        // HELLO

    // Utilisation idiomatique avec let
    b?.let { println("Length = ${it.length}") }

    // Éviter !! sauf si vous avez une garantie contractuelle
    // val crash = a!!.length // NPE si dé-commenté
}
```

## 6) Extension functions (méthodes d’extension)

Idée clé: ajouter des fonctions à un type sans héritage ni déco (résolution statique).

### Java 8
```java
// Pas d'extensions natives. On écrit une méthode utilitaire statique.
public final class StringUtils {
    private StringUtils() {}

    public static String surround(String s, String prefix, String suffix) {
        return prefix + s + suffix;
    }
}

class Demo {
    public static void main(String[] args) {
        System.out.println(StringUtils.surround("core", "[", "]")); // [core]
    }
}
```

### Java 21
```java
// Idem; on peut utiliser méthodes statiques dans interfaces, mais pas d’extensions au sens Kotlin.
public interface StringExt {
    static String surround(String s, String prefix, String suffix) {
        return prefix + s + suffix;
    }
}

class Demo {
    public static void main(String[] args) {
        System.out.println(StringExt.surround("core", "[", "]"));
    }
}
```

### Kotlin idiomatique
```kotlin
// Extension function sur String
fun String.surround(prefix: String, suffix: String): String = "$prefix$this$suffix"

// Extension property (lecture seule) d'exemple
val String.middle: Char?
    get() = if (isNotEmpty()) this[length / 2] else null

// Extensions de portée (fichier) pour un usage ciblé.
fun main() {
    println("core".surround("[", "]")) // [core]
    println("abc".middle)              // b
    println("".middle)                 // null
}
```

### Notes idiomatiques
- Les extensions ne modifient pas la classe cible; la résolution est statique selon le type statique, pas le type runtime.
- Préfixer les extensions d’utilitaires par le package attendu pour éviter collisions.

## 7) Sealed classes (hiérarchies fermées)

Idée clé: sealed limite les sous-types à un module/fichier, permettant un when exhaustif sans else.

### Java 8
```java
// Non supporté nativement. On imite avec classes package-private + constructeurs privés
// mais pas d'exhaustivité vérifiée par le compilateur.
abstract class Result<T> {
    private Result() {}
    static final class Success<T> extends Result<T> {
        final T value;
        Success(T value) { this.value = value; }
    }
    static final class Error<T> extends Result<T> {
        final String message;
        Error(String message) { this.message = message; }
    }
}

class Demo {
    static <T> String render(Result<T> r) {
        if (r instanceof Result.Success) {
            T v = ((Result.Success<T>) r).value;
            return "OK: " + v;
        } else if (r instanceof Result.Error) {
            String m = ((Result.Error<T>) r).message;
            return "ERR: " + m;
        }
        throw new IllegalStateException("Unknown");
    }

    public static void main(String[] args) {
        System.out.println(render(new Result.Success<>("data")));
        System.out.println(render(new Result.Error<>("boom")));
    }
}
```

### Java 21 (sealed + pattern matching)
```java
sealed interface Result<T> permits Success, Error { }

record Success<T>(T value) implements Result<T> { }
record Error<T>(String message) implements Result<T> { }

public class Demo {
    static <T> String render(Result<T> r) {
        return switch (r) {
            case Success<T> s -> "OK: " + s.value();
            case Error<T> e   -> "ERR: " + e.message();
        };
    }

    public static void main(String[] args) {
        System.out.println(render(new Success<>("data")));
        System.out.println(render(new Error<>("boom")));
    }
}
```

### Kotlin idiomatique
```kotlin
// Hiérarchie fermée: sous-types limités
sealed interface Result<out T> {
    data class Success<T>(val value: T) : Result<T>
    data class Error(val message: String, val cause: Throwable? = null) : Result<Nothing>
}

fun <T> render(r: Result<T>): String =
    when (r) {
        is Result.Success -> "OK: ${r.value}"
        is Result.Error   -> "ERR: ${r.message}"
        // Pas besoin de else: when exhaustif grâce à sealed
    }

fun main() {
    val ok: Result<String> = Result.Success("data")
    val ko: Result<String> = Result.Error("boom")

    println(render(ok))
    println(render(ko))
}
```

### Diagramme
```
            +-----------------------+
            |   Result<T> (sealed)  |
            +-----------------------+
               ^               ^
               |               |
+-------------------------+  +----------------------+
| Success<T> (data class) |  | Error (data class)   |
| - value: T              |  | - message: String    |
+-------------------------+  | - cause: Throwable?  |
                             +----------------------+
```

### Points idiomatiques
- Préférer sealed interface avec sous-types data class pour bénéficier des copy/equals/toString.
- Utiliser Result<Nothing> côté erreur si la variante ne contient pas de valeur de succès.
- Le when exhaustif évite les else attrape-tout et renforce la sûreté au refactoring.

## 8) L’opérateur Elvis (?:)

Idée clé: retourne l’opérande de gauche s’il n’est pas nul, sinon l’opérande de droite. Utile pour valeurs par défaut, exceptions, retours précoces.

### Java 8
```java
public class ElvisExample {
    public static String displayName(String nickname, String fullName) {
        // Équivalent "manuel" de Elvis
        String name = (nickname != null) ? nickname : fullName;
        if (name == null) {
            name = "Unknown";
        }
        return name;
    }

    public static String required(String input) {
        if (input == null) {
            throw new IllegalArgumentException("input required");
        }
        return input;
    }

    public static void main(String[] args) {
        System.out.println(displayName(null, "Alice")); // Alice
        System.out.println(displayName(null, null));    // Unknown
        System.out.println(required("ok"));             // ok
    }
}
```

### Java 21
```java
// Même approche que Java 8. Possibilité d'utiliser Objects.requireNonNullElse, requireNonNullElseGet.
import java.util.Objects;

public class ElvisExample21 {
    public static String displayName(String nickname, String fullName) {
        String name = Objects.requireNonNullElse(nickname, fullName);
        return Objects.requireNonNullElse(name, "Unknown");
    }

    public static String required(String input) {
        return Objects.requireNonNull(input, "input required");
    }
}
```

### Kotlin idiomatique
```kotlin
fun displayName(nickname: String?, fullName: String?): String =
    nickname ?: fullName ?: "Unknown"

fun required(input: String?): String =
    input ?: throw IllegalArgumentException("input required")

fun lookupOrCompute(cache: Map<String, String>, key: String, supplier: () -> String): String {
    // Elvis avec appel paresseux via supplier
    return cache[key] ?: supplier()
}

fun main() {
    println(displayName(null, "Alice"))     // Alice
    println(displayName(null, null))        // Unknown
    println(required("ok"))                 // ok

    val cache = mapOf("k" to "v")
    println(lookupOrCompute(cache, "k") { "computed" })    // v
    println(lookupOrCompute(cache, "x") { "computed" })    // computed
}
```

### Notes idiomatiques
- Elvis est souvent combiné avec l’appel sûr ?. et let.
- Pour lever une exception par défaut: x ?: error("msg") ou x ?: throw ...
- Préférer Elvis plutôt que des if (x == null) répétitifs.

## 9) Fonctions infix

Idée clé: une fonction marquée infix (membre ou extension) avec un unique paramètre peut s’écrire en style “infixe”.

- Bon usage: relations lisibles, DSL, liens explicites. Éviter les usages ambigus.

### Java 8
```java
// Pas de fonctions infix. On appelle simplement des méthodes.
public final class Pairing {
    public static <A, B> java.util.Map.Entry<A, B> to(A a, B b) {
        return new java.util.AbstractMap.SimpleEntry<>(a, b);
    }

    public static void main(String[] args) {
        var entry = Pairing.to("EUR", 100);
        System.out.println(entry.getKey() + " -> " + entry.getValue());
    }
}
```

### Java 21
```java
// Idem, pas d'infix. On peut utiliser records pour de petits tuples.
public record Pair<A, B>(A first, B second) { }

class Demo {
    public static void main(String[] args) {
        var p = new Pair<>("EUR", 100);
        System.out.println(p.first() + " -> " + p.second());
    }
}
```

### Kotlin idiomatique
```kotlin
// Infix d'extension pour créer une Pair lisible
infix fun <A, B> A.to(b: B): Pair<A, B> = Pair(this, b)

// Infix métier d'exemple: appliquer un pourcentage à une valeur entière
infix fun Int.percentOf(base: Int): Int = (base * this) / 100

fun main() {
    val p = "EUR" to 100               // infix standard (on a redéfini to pour l'exemple)
    val discount = 15 percentOf 200    // 15% de 200 = 30

    println(p)         // (EUR, 100)
    println(discount)  // 30
}
```

### Bonnes pratiques
- Utiliser infix pour des relations claires et peu ambiguës.
- Limiter aux cas 1-1 sémantiquement évidents; documenter le sens.
- Préférer des noms verbaux parlants: grants, connectsTo, assignedTo, etc.

## 10) Surcharge d’opérateurs

Idée clé: surcharger explicitement via operator fun pour des opérations à sens mathématique clair. Préserver immutabilité et invariants.

Cas d’usage: Amount en € avec addition sûre.

Règles:
- Amount est immuable
- Addition uniquement si devise identique, sinon exception
- Multiplication par un scalaire entier
- Comparaison possible après vérification de devise

### Java 8
```java
import java.math.BigDecimal;
import java.util.Objects;

public final class Amount {
    private final BigDecimal value;
    private final String currency;

    public Amount(BigDecimal value, String currency) {
        this.value = value;
        this.currency = currency;
    }

    public BigDecimal value() { return value; }
    public String currency() { return currency; }

    public Amount plus(Amount other) {
        if (!Objects.equals(currency, other.currency)) {
            throw new IllegalArgumentException("Currency mismatch");
        }
        return new Amount(value.add(other.value), currency);
    }

    public Amount times(int factor) {
        return new Amount(value.multiply(BigDecimal.valueOf(factor)), currency);
    }

    @Override public String toString() {
        return value + " " + currency;
    }
}
```

### Java 21
```java
import java.math.BigDecimal;

public record Amount(BigDecimal value, String currency) {
    public Amount {
        if (value == null || currency == null || currency.isBlank()) {
            throw new IllegalArgumentException("Invalid amount");
        }
    }
    public Amount plus(Amount other) {
        if (!currency.equals(other.currency)) throw new IllegalArgumentException("Currency mismatch");
        return new Amount(value.add(other.value), currency);
    }
    public Amount times(int factor) {
        return new Amount(value.multiply(BigDecimal.valueOf(factor)), currency);
    }
}
```

### Kotlin idiomatique
```kotlin
data class Amount(
    val value: java.math.BigDecimal,
    val currency: String
) {
    init {
        require(currency.isNotBlank()) { "currency required" }
    }

    operator fun plus(other: Amount): Amount {
        require(currency == other.currency) { "Currency mismatch: $currency vs ${other.currency}" }
        return copy(value = value + other.value)
    }

    operator fun times(factor: Int): Amount =
        copy(value = value * factor.toBigDecimal())

    fun compareTo(other: Amount): Int {
        require(currency == other.currency) { "Currency mismatch" }
        return value.compareTo(other.value)
    }
}

// Extensions BigDecimal pour opérateurs
private operator fun BigDecimal.plus(other: BigDecimal): BigDecimal = this.add(other)
private operator fun BigDecimal.times(other: BigDecimal): BigDecimal = this.multiply(other)

fun main() {
    // Attention au séparateur décimal selon locale: BigDecimal attend un point
    val a = Amount(BigDecimal("10,50".replace(',', '.')), "EUR")
    val b = Amount(BigDecimal("2"), "EUR")

    val total = a + b     // operator plus
    val doubled = total * 2

    println(total)        // Amount(value=12.50, currency=EUR)
    println(doubled)      // Amount(value=25.00, currency=EUR)
}
```

### Diagramme
```
+---------------------------+
|         Amount            |
+---------------------------+
| - value: BigDecimal       |
| - currency: String        |
+---------------------------+
| + operator plus(Amount)   |
| + operator times(Int)     |
| + compareTo(Amount)       |
+---------------------------+
```

### Conseils pour éviter la confusion
- Surcharger seulement les opérateurs à sémantique évidente pour le type.
- Respecter les invariants (même devise pour +, comparaison).
- Éviter des conversions implicites non métier (ex: Amount + Int si non défini).
- Préférer des méthodes nommées pour des opérations business non triviales: applyTax(rate), convertTo(targetCurrency, fxRate).

## 11) Scope functions: let (avec repères also/run/apply/with)

Idée clé: les scope functions créent un sous-scope temporaire pour opérer sur un objet.
- Travailler sur un nullable sans if != null
- Chaîner des transformations
- Limiter la portée d’une variable
- Renommer le receveur via it (ou nommer explicitement le paramètre)

Repères rapides:
- let: passe l’objet comme paramètre it; retourne le résultat du bloc
- also: passe it; retourne l’objet receveur (effets de bord)
- run: passe this; retourne le résultat du bloc
- apply: passe this; retourne l’objet receveur (construction/config)
- with(x) { ... }: comme run mais fonction top-level

### Java 8
```java
import java.util.Optional;

public class LetLike {
    public static void main(String[] args) {
        String maybe = Math.random() > 0.5 ? "kotlin" : null;

        // Alternative "let-like" avec Optional
        String upper = Optional.ofNullable(maybe)
                .map(String::toUpperCase)
                .orElse("N/A");

        System.out.println(upper);
    }
}
```

### Java 21
```java
// Idem que Java 8; Optional reste l’outil standard pour chaîner des opérations null-safe.
import java.util.Optional;

public class LetLike21 {
    public static void main(String[] args) {
        String maybe = Math.random() > 0.5 ? "kotlin" : null;
        String result = Optional.ofNullable(maybe)
                .map(s -> s.strip())
                .filter(s -> !s.isEmpty())
                .map(String::toUpperCase)
                .orElse("N/A");
        System.out.println(result);
    }
}
```

### Kotlin idiomatique (focus sur let)
```kotlin
data class User(val id: Long, val email: String?)

fun findUser(id: Long): User? =
    if (id % 2L == 0L) User(id, "user$id@exemple.fr") else null

fun sendEmail(to: String, subject: String, body: String) {
    println("Envoi email à $to: $subject\n$body")
}

fun main() {
    val user = findUser(42)

    // 1) Null-safety: exécuter seulement si non nul
    user?.email?.let { email ->
        sendEmail(email, subject = "Bienvenue", body = "Bonjour et bienvenue !")
    }

    // 2) Transformation avec let: retourne la dernière expression
    val domain = user?.email
        ?.let { it.substringAfter('@') }
        ?: "domaine-inconnu"
    println("Domaine: $domain")

    // 3) Limiter la portée: variable temporaire confinée
    val tokens = "a,b,c"
        .split(',')
        .let { parts ->
            // parts: List<String> visible uniquement ici
            parts.map { it.trim() }.filter { it.isNotEmpty() }
        }
    println(tokens)

    // 4) let pour logging ciblé tout en continuant le chaînage
    val lengthOrZero = user?.email
        ?.let { it.uppercase() }
        ?.also { println("Uppercase email: $it") } // also retourne l'objet, pas la longueur
        ?.let { it.length }
        ?: 0
    println(lengthOrZero)
}
```

### Notes idiomatiques
- Utiliser let pour transformer et produire une nouvelle valeur (souvent combiné à l’Elvis ?:).
- Nommer explicitement le paramètre de let (email ->) améliore la lisibilité quand it devient ambigu.
- Préférer also pour les effets de bord qui ne doivent pas interrompre la chaîne (log, métriques).

### Variantes rapides pour situer let
```kotlin
// also: side-effect, retourne l'objet d'origine
val list = mutableListOf(1, 2, 3)
    .also { println("Avant: $it") }
    .apply { add(4) }        // apply: configure, retourne la liste
    .also { println("Après:  $it") }

// run: calcul d'un résultat à partir d'un receveur
val result = StringBuilder().run {
    append("Hello")
    append(" ")
    append("Kotlin")
    length // retourne la longueur
}

// with: similaire à run mais en fonction top-level
val text = with(StringBuilder()) {
    append("Scoped ")
    append("with")
    toString() // retourne le résultat
}
```

## 12) Destructuration (Pair, data class, Map.Entry)

Idée clé: Kotlin offre la destructuration via componentN sur Pair/Triple, data classes et les entrées de Map. En Java 8 on extrait manuellement; Java 21 apporte les record patterns.

### Java 8
```java
// package com.ps.java8.s12;

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
```

### Java 21
```java
// package com.ps.java21.s12;

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
```

### Kotlin idiomatique
```kotlin
// package com.ps.kotlin.s12

// Exemple de destructuration en Kotlin
// - Pair et Triple (via componentN)
// - data class (componentN générés automatiquement)
// - Entries de Map
// - Ignorer des composants avec _

data class Person(val name: String, val age: Int)

data class HttpResult(val code: Int, val body: String?)

fun compute(): Pair<Int, Int> = 21 to 21

fun httpCall(ok: Boolean): HttpResult = if (ok) HttpResult(200, "{...}") else HttpResult(500, null)

fun main() {
    // 1) Pair
    val (x, y) = compute() // destructuration de Pair
    println("x=$x, y=$y")

    // 2) data class
    val p = Person("Alice", 30)
    val (n, a) = p // component1 -> name, component2 -> age
    println("$n a $a ans")

    // 3) Map.Entry
    val frequencies = mapOf("kotlin" to 3, "java" to 2)
    for ((word, count) in frequencies) {
        println("$word → $count")
    }

    // 4) Ignorer un composant avec _
    val (_, justAge) = p
    println("only age = $justAge")

    // 5) Destructurer dans un let pour limiter la portée
    httpCall(ok = true).let { (code, body) ->
        println("HTTP $code, body size = ${body?.length ?: 0}")
    }
}
```

### Diagramme
```
+-----------------------+
|      Destructuring    |
+-----------------------+
| Pair, data class, Map |
+-----------------------+
```

### Notes idiomatiques
- Kotlin génère automatiquement componentN pour les data classes, ce qui alimente la destructuration.
- Préférer la destructuration dans des scopes limités (for, let) pour garder la lisibilité.
- En Java 21, les record patterns simplifient l’extraction mais restent plus verbeux que Kotlin.


## 13) when: contrôle de flux expressif

Idée clé: `when` est une expression exhaustive en Kotlin, permet des correspondances par valeur, plage et type avec smart casts. En Java 8 on enchaîne if/else; Java 21 rapproche avec switch expressions et pattern matching.

### Java 8
```java
// package com.ps.java8.s13;
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
                .replaceAll("[\\[\\] ]", ""));
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
```

### Java 21
```java
// package com.ps.java21.s13;
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
        return switch (0) {
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
```

### Kotlin idiomatique
```kotlin
// package com.ps.kotlin.s13

sealed interface Shape

data class Circle(val radius: Double) : Shape

data class Rectangle(val width: Double, val height: Double) : Shape

data class Unknown(val label: String) : Shape

fun area(shape: Shape): Double = when (shape) {
    is Circle -> Math.PI * shape.radius * shape.radius // smart cast
    is Rectangle -> shape.width * shape.height
    is Unknown -> Double.NaN // exhaustif grâce à sealed interface
}

fun classifyScore(score: Int): String = when (score) {
    in 90..100 -> "A"
    in 75..89 -> "B"
    in 60..74 -> "C"
    in 0..59 -> "D"
    else -> "Invalide"
}

fun fizzBuzz(n: Int): String = when {
    n % 15 == 0 -> "FizzBuzz"
    n % 3 == 0 -> "Fizz"
    n % 5 == 0 -> "Buzz"
    else -> n.toString()
}

fun describe(x: Any?): String = when (x) {
    null -> "null"
    is String -> "String(len=${x.length})"
    is Number -> "Number($x)"
    else -> "Autre(${x::class.simpleName})"
}

fun main() {
    // 1) when + sealed: exhaustif et expressif
    val shapes: List<Shape> = listOf(Circle(2.0), Rectangle(3.0, 4.0), Unknown("?"))
    shapes.forEach { s -> println("area(${s}) = ${area(s)}") }

    // 2) when avec plages
    println((listOf(95, 80, 67, 10, -1)).map(::classifyScore))

    // 3) when sans sujet: garde booléenne
    println((1..16).joinToString(" ") { fizzBuzz(it) })

    // 4) when par type
    println(describe("kotlin"))
    println(describe(42))
    println(describe(null))
}
```

### Diagramme
```
+-------------------------+
|     Contrôle de flux    |
+-------------------------+
| Kotlin: when (expr)     |
| Java 21: switch expr    |
| Java 8: if/else/switch  |
+-------------------------+
```

### Notes idiomatiques
- `when` est une expression: retournez directement la valeur calculée.
- Exploitez les hiérarchies sealed pour l’exhaustivité et la sécurité à la compilation.
- Préférez des guards lisibles; évitez les cas trop « magiques ».

## 14) Paramètres nommés et valeurs par défaut

Idée clé: en Kotlin, on définit des valeurs par défaut directement dans la signature et on appelle avec des paramètres nommés pour la lisibilité. En Java 8/21, on émule via surcharges et/ou Builder.

### Java 8
```java
// package com.ps.java8.s14;

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
```

### Java 21
```java
// package com.ps.java21.s14;

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
```

### Kotlin idiomatique
```kotlin
// package com.ps.kotlin.s14

data class Mail(
    val to: String,
    val subject: String = "(no subject)",
    val body: String = "",
    val urgent: Boolean = false
)

fun sendMail(mail: Mail): String {
    // Ici on retournerait un ID ou on enverrait réellement le mail.
    return "to=${mail.to}; subj=${mail.subject}; urgent=${mail.urgent}"
}

// Variante avec défauts directement dans la signature de fonction
fun greet(name: String, punctuation: String = "!", prefix: String = "Hello"): String = "$prefix $name$punctuation"

fun main() {
    // 1) Appels classiques (positionnels)
    println(greet("Alice"))                  // Hello Alice!
    println(greet("Bob", "?"))              // Hello Bob?

    // 2) Paramètres nommés: on peut changer l'ordre, expliciter, et sauter des valeurs
    println(greet(name = "Charly", prefix = "Hi"))      // Hi Charly!
    println(greet(punctuation = "!!!", name = "Dana"))   // Hello Dana!!!

    // 3) Objet avec valeurs par défaut + paramètres nommés
    val m1 = Mail(to = "team@example.com")
    val m2 = Mail(to = "boss@example.com", subject = "Weekly report", urgent = true)
    println(sendMail(m1))
    println(sendMail(m2))
}
```

### Notes idiomatiques
- Préférer les paramètres nommés pour améliorer la lisibilité quand plusieurs booléens/strings se suivent.
- Éviter d’abuser des valeurs par défaut si elles masquent un besoin métier explicite; documenter-les.
- En Java, privilégier les Builders (immutables) pour éviter l’ambiguïté et conserver l’intention.
