package annabos.ru.palindrome;

import org.junit.Test;

import static org.junit.Assert.*;

public class PalindromeCheckerTest {

    @Test
    public void inputIsPalidrome(){
        assertTrue(PalindromeChecker.isPalindrome("Я Иду с МЕчем С! уд и, Я"));
    }

    @Test
    public void inputIsNotPalidrome(){
        assertFalse(PalindromeChecker.isPalindrome("я иду с мечем"));
    }

}