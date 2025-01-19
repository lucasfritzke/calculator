import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;
import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;


    // TODO: learn about dynamic tests

public class CalculatorTest {
    private static final MathContext MATH_CONTEXT = new MathContext(5, RoundingMode.HALF_UP);
    private Calculator calculator;

    @BeforeEach
    void test01(){
         this.calculator = new Calculator();
    }

    @Test
    @DisplayName("Teste soma 2 + 2 = 4")
    void test02(){
        assertEquals("4", calculator.add(2,2).toString(), "Erro no metodo add");
    }

    @Test
    @DisplayName("Teste soma 2 + 2 + 3 = 7")
    void test03(){
        assertEquals("7", calculator.add(2,2,3).toString(), "Erro no metodo add");
    }

    @Test
    @DisplayName("Teste soma negativo -2 + 2 + 7 = 7")
    void test04(){
        assertEquals("7", calculator.add(-2,2,7).toString(), "Erro no metodo sub");
    }

    @ParameterizedTest
    @DisplayName("Teste sub 5 - 2 = 3")
    @CsvSource({
            "2, 3, -1",
            "0, 0, 0",
            "15, 20, -5",
    })
    void test05(int a, int b, int result){
        assertEquals(Integer.toString(result), calculator.subtract(a,b).toString(), "Erro no metodo sub");
    }

    @Test
    @DisplayName("Teste all soma e subtração ")
    void test06(){
        assertAll("Teste som",
                () -> assertEquals(Integer.toString(10), calculator.add(0,0,2,3,4,1).toString()),
                () -> assertEquals(Integer.toString(-10), calculator.add(0,0,-2,3,-4,-2,-5).toString()),
                () -> assertEquals(Integer.toString(10), calculator.subtract(0,0,-2,-3,-4,-1).toString()),
                () -> assertEquals(Integer.toString(1_000), calculator.subtract(1_000,1,3,1,-5).toString())
        );
    }

    @ParameterizedTest
    @DisplayName("Teste Multiplicação")
    @MethodSource("multplyMethodTest")
    void test07(int a, int b,int c, BigDecimal result){
        assertEquals(result, calculator.multiply(a,b,c));
    }

    public static Stream<Arguments> multplyMethodTest() {
        return Stream.of(
                Arguments.of(5, 5, 10, BigDecimal.valueOf(250)),
                Arguments.of(0, 5, 10, BigDecimal.valueOf(0)),
                Arguments.of(1, 5, 10, BigDecimal.valueOf(50))
//                Arguments.of(Integer.MAX_VALUE, 1, 1, BigDecimal.valueOf(Integer.MAX_VALUE))
        );
    }

    @Test
    @DisplayName("Teste all divisão ")
    void test08(){
        assertAll("Teste som",
                () -> assertEquals(BigDecimal.valueOf(0), calculator.divide(0,5)),
                () -> assertEquals(BigDecimal.valueOf(1), calculator.divide(5,5)),
                () -> assertEquals(BigDecimal.valueOf(2.5), calculator.divide(5,2)),
                () -> assertEquals(BigDecimal.valueOf(-2.5), calculator.divide(-5,2)),
                () -> assertEquals(BigDecimal.valueOf(2.5), calculator.divide(-5,-2)),
                () -> assertEquals(new BigDecimal("2.6667"), calculator.divide(8,3))
        );
    }

    @ParameterizedTest
    @DisplayName("Teste divide")
    @MethodSource("divideMethodTest")
    void test09(int a, int b,int c, BigDecimal result){
        assertEquals(result, calculator.divide(a,b,c));
    }

    public static Stream<Arguments> divideMethodTest() {
        return Stream.of(
                Arguments.of(100, 5, 2, BigDecimal.valueOf(10)),
                Arguments.of(0, 5, 10, BigDecimal.valueOf(0)),
                Arguments.of(-1_000, 2, 5, BigDecimal.valueOf(-100))
//                Arguments.of(Integer.MAX_VALUE, 1, 1, BigDecimal.valueOf(Integer.MAX_VALUE))
        );
    }

    @ParameterizedTest
    @DisplayName("Teste divide Exception")
    @MethodSource("divideExpectionMethodTest")
    void test10(int a, int b,int c, ArithmeticException result){
        ArithmeticException ex = Assertions.assertThrows(ArithmeticException.class,()-> calculator.divide(a,b,c));
        assertEquals(result.getMessage(), ex.getMessage());
    }

    public static Stream<Arguments> divideExpectionMethodTest() {
        return Stream.of(
                Arguments.of(100, 0, 2, new ArithmeticException("Não existe divisão por zero")),
                Arguments.of(0, 5, 0, new ArithmeticException("Não existe divisão por zero")),
                Arguments.of(0, 0, 5, new ArithmeticException("Não existe divisão por zero")),
                Arguments.of(0, 0, 0, new ArithmeticException("Não existe divisão por zero"))
        );
    }

    @ParameterizedTest
    @DisplayName("Teste potencia")
    @MethodSource("powMethodTest")
    void test11(int a, int b, int c, int d, BigDecimal result){
        assertEquals(result, calculator.pow(a,b,c,d));
    }

    public static Stream<Arguments> powMethodTest() {
        return Stream.of(
                Arguments.of(0, 5, 2, 6, BigDecimal.valueOf(0)),
                Arguments.of(2, 2, 2, 2, BigDecimal.valueOf(256)),
                Arguments.of(1, 2, 5, 10, BigDecimal.valueOf(1))
        );
    }

    @Test
    @DisplayName("Teste potencia com expoente negativo")
    void test12(){
        assertThrows(ArithmeticException.class, () -> calculator.pow(3,-2));
    }

    @Test
    @DisplayName("Teste potencia com expoente negativo")
    void test13(){
        assertThrows(ArithmeticException.class, () -> calculator.pow(3,2,4,-2));
    }

    @Test
    @DisplayName("Teste calculadora 5 + 5 - 2 = 8")
    void test14(){
        assertEquals(BigDecimal.valueOf(8), calculator.calculate("5 + 5 - 2"));
    }

    @Test
    @DisplayName("Teste calculadora -5 * -5 + 10 = 35")
    void test15(){
        assertEquals(BigDecimal.valueOf(35), calculator.calculate("-5 * -5 + 10"));
    }

    public static Stream<Arguments> calculatorMethodTest() {
        return Stream.of(
                Arguments.of("5 + (2 ^ 4 - 1)", "20"),
                Arguments.of("5 + (2 ^ (4 - 1))", "13"),
                Arguments.of("0 + 0 -0", "0"),
                Arguments.of("5 + 2 / (3 - 8) ^ 5 ^ 2", "5"),
                Arguments.of("8 / 3", "2.6666666667")
        );
    }

    @ParameterizedTest
    @DisplayName("Teste calculadora")
    @MethodSource("calculatorMethodTest")
    void test16(String expression, String result){
        assertEquals(new BigDecimal(result, MATH_CONTEXT), calculator.calculate(expression));
    }


}
