package annabos.ru.palindrome;

public class PalindromeChecker {

    public static boolean isPalindrome(String input) {
        boolean result = false;
        StringBuilder word = new StringBuilder();
        if (input != null) {
            input.chars()
                    .mapToObj(x -> (char) x)
                    .filter(x -> (Character.isLetter(x)))
                    .forEach(word::append);
            result = word.toString().equalsIgnoreCase(word.reverse().toString());
        }
        return result;
    }
}
