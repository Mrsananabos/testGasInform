package annabos.ru.palindrome;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static org.junit.Assert.*;

public class PalindromeCheckerTest {

    @DataProvider
    public Object[][] data() {
        return new Object[][]{
                {true, "Я Иду с МЕчем С! уд и, Я"},
                {true, "В9ОЛ5ГУ3 ДИВ не5сет тесен ВИ99Д УГЛ5433ОВ"},
                {false,"я иду с мечем"},
                {false, null}
        };
    }

    @Test(dataProvider = "data")
    public void inputIsPalidrome(boolean result, String input){
        assertEquals(result, PalindromeChecker.isPalindrome(input));
    }

}