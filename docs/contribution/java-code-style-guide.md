### Indentation and Formatting
- 4-space indentation, no tabs.
- Opening braces `{` should be at the end of the line.
- Closing braces `}` should be on a new line.

```java
public class Sample {
    public static void main(String[] args) {
        // Code
    }
}
```


### Naming Conventions
- Class names should be nouns, in mixed case with the first letter of each internal word capitalized (`MyClass`).
- Method names should be verbs, with the first letter lowercase and the first letter of internal words capitalized (`myMethod`).
- Variable names should be in camelCase (`myVariable`).


### Comments
- Use Javadoc comments (`/** ... */`) for classes, methods, and multi-line or complex code blocks.
- Use single-line comments (`//`) for code clarification.


### Imports
- Avoid using wildcard imports (`import java.util.*;`), list individual classes instead.


### Line Length
- Maximum line length should generally be 80-100 characters.


### Exception Handling
- Catch specific exceptions, not generic ones.
- Don't catch an exception you can't handle.

```java
try {
    // code that may throw an IOException
} catch (IOException e) {
    e.printStackTrace();
}
```


### Code Structure
- Declare member variables at the top of a class.
- Always use `try-with-resources` or `try-finally` to handle resources like streams and sockets.


### Class and Method Structure
- Only one public class per `.java` file.
- Order of members: `public > protected > package-level (no access modifier) > private`.


### Variable Declarations
- Declare one variable per line for better readability.
- Initialize variables at the time of declaration when possible.


### Other Conventions
- Use `final` variables when you don't need to reassign.
- Use `StringBuilder` for concatenating multiple strings in loops.