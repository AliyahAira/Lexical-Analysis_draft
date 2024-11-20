import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class LexicalAnalysis {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter your code, one line at a time. Press Enter on a blank line to finish:");

        List<String> lines = new ArrayList<>();
        while (true) {
            String input = scanner.nextLine();

            // Stop if user presses Enter on a blank line
            if (input.isBlank()) {
                break;
            }

            lines.add(input);
        }

        // Para maread each line
        boolean allValid = true;
        for (String line : lines) {
            if (analyze(line)) {
                System.out.println("Line: \"" + line + "\" -> It passed the Lexical Analysis.");
            } else {
                System.out.println("Line: \"" + line + "\" -> It doesn't pass the Lexical Analysis.");
                allValid = false;
            }
        }

        if (allValid) {
            System.out.println("All lines passed the Lexical Analysis.");
        } else {
            System.out.println("Some lines failed the Lexical Analysis.");
        }

        scanner.close();
    }

    // Main tho
    private static boolean analyze(String input) {
        try {
            List<String> tokens = tokenize(input);
            parse(tokens);
            return true;
        } catch (Exception e) {
            // Catch any parsing/tokenization errors
            return false;
        }
    }

    // Tokenizer: Splits the input into tokens wihihii
    private static List<String> tokenize(String input) throws Exception {
        String[] split = input.split("\\s+|(?=[=;])|(?<=[=;])");
        List<String> tokens = new ArrayList<>();
        for (String token : split) {
            if (!token.isBlank()) {
                tokens.add(token);
            }
        }

        if (!tokens.contains(";")) {
            throw new Exception("Missing semicolon.");
        }

        return tokens;
    }

    // Parser: Validates the syntax based on Java rules
    private static void parse(List<String> tokens) throws Exception {
        // Expecting: <data type> <identifier> = <value>;
        if (tokens.size() < 4 || !tokens.get(tokens.size() - 1).equals(";")) {
            throw new Exception("Invalid syntax.");
        }

        String dataType = tokens.get(0);
        String identifier = tokens.get(1);
        String equalsSign = tokens.get(2);
        String value = tokens.get(3);

        // Validating the data type
        if (!isValidDataType(dataType)) {
            throw new Exception("Invalid data type.");
        }

        // para mavalidate si identifier
        if (!isValidIdentifier(identifier)) {
            throw new Exception("Invalid identifier.");
        }

        // Validating th assignment operator
        if (!equalsSign.equals("=")) {
            throw new Exception("Missing '='.");
        }

        // Validating value based on data type
        if (!isValidValue(dataType, value)) {
            throw new Exception("Invalid value for type " + dataType + ".");
        }
    }

    // Validates the data type
    private static boolean isValidDataType(String dataType) {
        return dataType.matches("int|String|double|float|char|boolean");
    }

    // Validates the identifier
    private static boolean isValidIdentifier(String identifier) {
        return identifier.matches("[a-zA-Z_][a-zA-Z0-9_]*");
    }

    // Validates the value based on the data type
    private static boolean isValidValue(String dataType, String value) {
        switch (dataType) {
            case "int":
                return value.matches("\\d+");
            case "double":
            case "float":
                return value.matches("\\d+(\\.\\d+)?");
            case "char":
                return value.matches("'.'"); // Single character in single quotes
            case "String":
                return value.matches("\".*\""); // Enclosed in double quotes
            case "boolean":
                return value.matches("true|false");
            default:
                return false;
        }
    }
}
