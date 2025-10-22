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