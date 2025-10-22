# Guidelines Junie — Générer le code et la documentation du README

Contexte: votre équipe de développeurs passe de Java à Kotlin. Ce dépôt sert de cheatsheet pour adopter les idiomes Kotlin en gardant des parallèles avec Java 8 et Java 21.

Objectif: fournir un format standard et reproductible pour ajouter de nouveaux cas d’usage, avec du code qui compile et une documentation claire dans le README.

---

## 1) Principe général
- Un cas d’usage = une section numérotée sNN (01, 02, …) dans le README.
- Chaque cas d’usage comporte:
  1. Un titre et une « idée clé » concise.
  2. Une version Java 8 (si applicable).
  3. Une version Java 21 (si applicable).
  4. Une version Kotlin idiomatique (complète et exécutable).
  5. Optionnel: un diagramme ASCII (fencé en code block) et des notes/best practices.
- Le code Kotlin et Java vit dans `src/main/kotlin` et `src/main/java` et DOIT compiler via Gradle.

## 2) Organisation des packages et fichiers
- Kotlin: `com.ps.kotlin.sNN` (ex: `s01`, `s02`, …)
  - Exemple: `src/main/kotlin/com/ps/kotlin/s03/TrailingLambda.kt` correspond à la section 3 du README.
- Java 8: `com.ps.java8.sNN`
  - Exemple: `src/main/java/com/ps/java8/s02/ConsoleGreeter.java`.
- Java 21: `com.ps.java21.sNN`
  - Exemple: `src/main/java/com/ps/java21/s02/ConsoleGreeter.java`.
- Conserver la numérotation entre README et packages pour retrouver facilement les exemples.

## 3) Règles de style de présentation (README)
- Markdown strict avec titres `##` pour chaque section numérotée.
- Code fences avec language tag explicite:
  - Java: ```java
  - Kotlin: ```kotlin
  - Diagrammes ASCII: ``` (sans language ou `text`), pour préserver l’alignement monospace.
- Donner un intitulé clair aux sous-sections: « Java 8 », « Java 21 », « Kotlin idiomatique », « Diagramme », « Notes idiomatiques », « Bonnes pratiques ».
- Brevité et lisibilité: aller à l’essentiel, commenter les points idiomatiques dans le code si utile.

## 4) Template prêt-à-copier pour un nouveau cas d’usage

Copiez ce squelette dans le README et adaptez.

### Modèle README

```
## NN) <Titre du cas d’usage>

Idée clé: <phrase courte sur l’idiome/la pratique>

### Java 8
```java
// package com.ps.java8.sNN;
// Placez la/les classes sous src/main/java/com/ps/java8/sNN/
public class Example { /* … */ }
```

### Java 21
```java
// package com.ps.java21.sNN;
public class Example21 { /* … */ }
```

### Kotlin idiomatique
```kotlin
// package com.ps.kotlin.sNN
fun main() { /* … */ }
```

### Diagramme
```
+--------------------+
|   Classes clés     |
+--------------------+
```

### Notes idiomatiques
- Point 1
- Point 2
```

### Modèle code (emplacements)
- Kotlin: `src/main/kotlin/com/ps/kotlin/sNN/<Nom>.kt`
- Java 8: `src/main/java/com/ps/java8/sNN/<Nom>.java`
- Java 21: `src/main/java/com/ps/java21/sNN/<Nom>.java`

## 5) Bonnes pratiques de code (Kotlin)
- Préférer les data classes, extensions, sealed hierarchies, null-safety, scope functions, infix avec parcimonie.
- Respecter l’immutabilité par défaut; n’exposer des vars que si nécessaire.
- Utiliser les opérateurs (operator fun) seulement si le sens est évident (ex: addition d’Amount même devise).
- Fournir des `main()` minimaux pour illustrer la sortie attendue.

## 6) Vérifier que le code compile
- Pré-requis: Java 21, Gradle wrapper fourni.
- Commandes:
  - macOS/Linux: `./gradlew build`
  - Windows: `gradlew.bat build`
- Tous les exemples doivent compiler. Corrigez packages/noms si erreur.

## 7) Synchroniser Code ↔ README
- Le README doit contenir des extraits identiques à ceux présents dans `src/…`. Évitez de maintenir deux variantes divergentes.
- Quand vous changez un exemple:
  1. Modifiez d’abord le fichier source (Kotlin/Java).
  2. Copiez-collez le snippet pertinent dans le README, en conservant le language tag.
  3. Vérifiez la compilation.

## 8) Contribution avec Junie (prompts utiles)
- « Ajoute une nouvelle section sNN sur <thème>, en fournissant Java 8, Java 21 et un exemple Kotlin idiomatique exécutable. »
- « Crée les classes Kotlin/Java correspondantes aux snippets du README, dans les bons packages sNN. »
- « Mets à jour le README avec les fences et la numérotation cohérente. »
- « Vérifie la compilation Gradle et corrige les packages. »

## 9) Définitions de done
- Code placé dans les bons dossiers et packages sNN, qui compile.
- README mis à jour: section numérotée, snippets correctement fencés, diagramme optionnel, notes si utile.
- L’exemple Kotlin possède un `main()` ou un bloc de test/démonstration minimal.

## 10) FAQ rapide
- Q: Dois-je toujours fournir Java 8 et Java 21 ?
  - R: Si ce n’est pas pertinent (feature absente), documentez-le brièvement et fournissez au moins l’implémentation Kotlin idiomatique.
- Q: Comment dessiner un diagramme ?
  - R: Utilisez un code fence sans language; gardez un style simple en monospace.
- Q: Où mettre les utilitaires communs ?
  - R: Préférer des fichiers Kotlin dédiés par section sNN pour que chaque exemple reste autonome et pédagogique.
