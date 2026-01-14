# LAB 7: Internationalization (i18n) & JUnit Testing

## Overview
This lab covers two important Java concepts:
1. **Internationalization (i18n)** - Making applications support multiple languages
2. **JUnit 5 Testing** - Writing unit tests for Java classes

---

## Part 1: Internationalization (i18n)

### What is Internationalization?
Internationalization (i18n) is the process of designing software so it can be adapted to different languages and regions without engineering changes.

### Key Classes Used
- **`Locale`** - Represents a specific geographical, political, or cultural region
- **`ResourceBundle`** - Contains locale-specific objects (strings, messages)
- **`DateFormat`** - Formats dates according to locale conventions
- **`NumberFormat`** - Formats numbers and currencies according to locale conventions

### Files
| File | Description |
|------|-------------|
| `InternationalizationDemo.java` | Main demo application |
| `messages_en_US.properties` | English (US) language pack |
| `messages_ms_MY.properties` | Malay (Malaysia) language pack |

### How to Run
```bash
# Compile and run the main class
mvn compile exec:java -Dexec.mainClass="com.csc3402.lab.ccs3104_lab.LAB7.InternationalizationDemo"
```

### Sample Output
```
╔══════════════════════════════════════════════════════════════╗
║         INTERNATIONALIZATION (i18n) DEMONSTRATION            ║
╚══════════════════════════════════════════════════════════════╝

Available Languages / Bahasa Yang Tersedia:
  1. English (United States)
  2. Bahasa Melayu (Malaysia)

Select language (1 or 2) / Pilih bahasa (1 atau 2): 1

════════════════════════════════════════════════════════════════
Selected Locale: English (United States)
...
```

---

## Part 2: JUnit 5 Testing

### What is JUnit?
JUnit is a popular unit testing framework for Java. It helps verify that individual units of code work correctly.

### Key Annotations
| Annotation | Purpose |
|------------|---------|
| `@Test` | Marks a method as a test case |
| `@BeforeEach` | Runs before each test method |
| `@DisplayName` | Provides a readable name for the test |

### Key Assertions
| Method | Purpose |
|--------|---------|
| `assertEquals(expected, actual)` | Verifies values are equal |
| `assertTrue(condition)` | Verifies condition is true |
| `assertFalse(condition)` | Verifies condition is false |
| `assertThrows(Exception.class, () -> {...})` | Verifies exception is thrown |

### Files
| File | Description |
|------|-------------|
| `Calculator.java` | Simple calculator class to test |
| `CalculatorTest.java` | JUnit 5 test class |

### How to Run Tests
```bash
# Run all tests
mvn test

# Run specific test class
mvn test -Dtest=CalculatorTest
```

### Test Structure (AAA Pattern)
```java
@Test
void testExample() {
    // Arrange - Set up test data
    int a = 5;
    int b = 3;
    
    // Act - Perform the action
    int result = calculator.add(a, b);
    
    // Assert - Verify the result
    assertEquals(8, result);
}
```

---

## Adding JUnit 5 Dependency

Add this to your `pom.xml`:

```xml
<dependency>
    <groupId>org.junit.jupiter</groupId>
    <artifactId>junit-jupiter</artifactId>
    <version>5.10.0</version>
    <scope>test</scope>
</dependency>
```

---

## Exercises

### Exercise 1: Add More Languages
Add support for another language (e.g., Chinese, Japanese, or Arabic):
1. Create a new properties file (e.g., `messages_zh_CN.properties`)
2. Add the locale option in `InternationalizationDemo.java`

### Exercise 2: Add More Calculator Tests
Write tests for:
1. Testing `multiply` with large numbers
2. Testing `modulo` operation
3. Testing with `Integer.MAX_VALUE` and `Integer.MIN_VALUE`

### Exercise 3: Create Your Own Class to Test
Create a `StringUtils` class with methods like:
- `reverse(String s)`
- `isPalindrome(String s)`
- `countVowels(String s)`

Then write JUnit tests for each method.

---

## Learning Objectives
After completing this lab, students should be able to:
1. ✅ Understand the concept of internationalization
2. ✅ Use `Locale` and `ResourceBundle` for multi-language support
3. ✅ Format dates and currencies based on locale
4. ✅ Write unit tests using JUnit 5
5. ✅ Use `@Test`, `@BeforeEach`, and assertions
6. ✅ Test for expected exceptions using `assertThrows`
