import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
class CalculatorTest {
    @Test
    @DisplayName("Calculator Addition")
    void add() {
        int a = 7;
        int b = 3;
        int expected = a+b;
        Calculator calculator = new Calculator();
        Assertions.assertEquals(expected, calculator.add(a,b));
    }
    @Test
    @DisplayName("Calculator Division")
    void div() {
        int a = 7;
        int b=3;
        int expected = a/b;
        Calculator calculator = new Calculator();
        Assertions.assertEquals(expected, calculator.div(a,b));
    }
    @Test
    @DisplayName("Calculator Multiplication")
    void mul() {
        int a = 7;
        int b=3;
        int expected = a*b;
        Calculator calculator = new Calculator();
        Assertions.assertEquals(expected, calculator.mul(a,b));
    }
    @Test
    @DisplayName("Calculator Subtraction")
    void sub() {
        int a = 7;
        int b=3;
        int expected = a-b;
        Calculator calculator = new Calculator();
        Assertions.assertEquals(expected, calculator.sub(a,b));
    }
}