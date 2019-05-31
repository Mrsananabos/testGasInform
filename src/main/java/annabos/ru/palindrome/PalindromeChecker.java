package annabos.ru.palindrome;

public class PalindromeChecker {

    public static boolean isPalindrome(String input) {
        StringBuilder word = new StringBuilder();
        input.chars()
                .mapToObj(x -> (char) x)
                .filter(x -> (Character.isLetter(x)))
                .forEach(word::append);
        return word.toString().equalsIgnoreCase(word.reverse().toString());
    }
}
