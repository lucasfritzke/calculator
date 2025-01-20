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
        assertEquals("4", calculator.add(BigDecimal.valueOf(2),BigDecimal.valueOf(2)).toString(), "Erro no metodo add");
    }

    @Test
    @DisplayName("Teste soma 2 + 2 + 3 = 7")
    void test03(){
        assertEquals(new BigDecimal("7"),
                calculator.add(BigDecimal.valueOf(2), BigDecimal.valueOf(2), BigDecimal.valueOf(3)),
                "Erro no método add");
    }

    @Test
    @DisplayName("Teste soma negativo -2 + 2 + 7 = 7")
    void test04(){
        assertEquals(new BigDecimal("7"), calculator.add(BigDecimal.valueOf(-2), BigDecimal.valueOf(2), BigDecimal.valueOf(7)), "Erro no método add");
    }

    @ParameterizedTest
    @DisplayName("Teste sub 5 - 2 = 3")
    @CsvSource({
            "2, 3, -1",
            "0, 0, 0",
            "15, 20, -5",
    })
    void test05(int a, int b, int result){
        assertEquals(new BigDecimal(result), calculator.subtract(BigDecimal.valueOf(a), BigDecimal.valueOf(b)), "Erro no método subtract");
    }

    @Test
    @DisplayName("Teste all soma e subtração ")
    void test06(){
        assertAll("Testes de soma e subtração",
                () -> assertEquals(new BigDecimal("10"), calculator.add(BigDecimal.valueOf(0), BigDecimal.valueOf(2), BigDecimal.valueOf(3), BigDecimal.valueOf(4), BigDecimal.valueOf(1))),
                () -> assertEquals(new BigDecimal("-10"), calculator.add(BigDecimal.valueOf(-2), BigDecimal.valueOf(3), BigDecimal.valueOf(-4), BigDecimal.valueOf(-2), BigDecimal.valueOf(-5))),
                () -> assertEquals(new BigDecimal("6"), calculator.subtract(BigDecimal.valueOf(-2), BigDecimal.valueOf(-3), BigDecimal.valueOf(-4), BigDecimal.valueOf(-1))),
                () -> assertEquals(new BigDecimal("1000"), calculator.subtract(BigDecimal.valueOf(1000), BigDecimal.valueOf(1), BigDecimal.valueOf(3), BigDecimal.valueOf(1), BigDecimal.valueOf(-5)))
        );
    }

    @ParameterizedTest
    @DisplayName("Teste Multiplicação")
    @MethodSource("multplyMethodTest")
    void test07(BigDecimal a, BigDecimal b, BigDecimal c, BigDecimal result){
        assertEquals(result, calculator.multiply(a,b,c));
    }

    public static Stream<Arguments> multplyMethodTest() {
        return Stream.of(
                Arguments.of(BigDecimal.valueOf(5), BigDecimal.valueOf(5), BigDecimal.valueOf(10), BigDecimal.valueOf(250)),
                Arguments.of(BigDecimal.valueOf(0), BigDecimal.valueOf(5), BigDecimal.valueOf(10), BigDecimal.valueOf(0)),
                Arguments.of(BigDecimal.valueOf(1), BigDecimal.valueOf(5), BigDecimal.valueOf(10), BigDecimal.valueOf(50))
//
        );
    }

    @Test
    @DisplayName("Teste all divisão ")
    void test08(){
        assertAll("Testes de divisão",
                () -> assertEquals(BigDecimal.valueOf(0), calculator.divide(BigDecimal.valueOf(0), BigDecimal.valueOf(5))),
                () -> assertEquals(BigDecimal.valueOf(1), calculator.divide(BigDecimal.valueOf(5), BigDecimal.valueOf(5))),
                () -> assertEquals(BigDecimal.valueOf(2.5), calculator.divide(BigDecimal.valueOf(5), BigDecimal.valueOf(2))),
                () -> assertEquals(BigDecimal.valueOf(-2.5), calculator.divide(BigDecimal.valueOf(-5), BigDecimal.valueOf(2))),
                () -> assertEquals(BigDecimal.valueOf(2.5), calculator.divide(BigDecimal.valueOf(-5), BigDecimal.valueOf(-2))),
                () -> assertEquals(new BigDecimal("2.6667"), calculator.divide(BigDecimal.valueOf(8), BigDecimal.valueOf(3)))
        );
    }

    @ParameterizedTest
    @DisplayName("TTeste divisão parametrizada")
    @MethodSource("divideMethodTest")
    void test09(BigDecimal a, BigDecimal b, BigDecimal c, BigDecimal result) {
        assertEquals(result, calculator.divide(a, b, c));
    }

    public static Stream<Arguments> divideMethodTest() {
        return Stream.of(
                Arguments.of(BigDecimal.valueOf(100), BigDecimal.valueOf(5), BigDecimal.valueOf(2), BigDecimal.valueOf(10)),
                Arguments.of(BigDecimal.valueOf(0), BigDecimal.valueOf(5), BigDecimal.valueOf(10), BigDecimal.valueOf(0)),
                Arguments.of(BigDecimal.valueOf(-1_000), BigDecimal.valueOf(2), BigDecimal.valueOf(5), BigDecimal.valueOf(-100))
//
        );
    }

    @ParameterizedTest
    @DisplayName("Teste divide Exception")
    @MethodSource("divideExpectionMethodTest")
    void test10(BigDecimal a, BigDecimal b,BigDecimal c, ArithmeticException result){
        ArithmeticException ex = Assertions.assertThrows(ArithmeticException.class,()-> calculator.divide(a,b,c));
        assertEquals(result.getMessage(), ex.getMessage());
    }

    public static Stream<Arguments> divideExpectionMethodTest() {
        return Stream.of(
                Arguments.of(BigDecimal.valueOf(100), BigDecimal.valueOf(0), BigDecimal.valueOf(2),
                        new ArithmeticException("Não existe divisão por zero")),
                Arguments.of(BigDecimal.valueOf(0), BigDecimal.valueOf(5), BigDecimal.valueOf(0),
                        new ArithmeticException("Não existe divisão por zero")),
                Arguments.of(BigDecimal.valueOf(0), BigDecimal.valueOf(0), BigDecimal.valueOf(5),
                        new ArithmeticException("Não existe divisão por zero")),
                Arguments.of(BigDecimal.valueOf(0), BigDecimal.valueOf(0), BigDecimal.valueOf(0),
                        new ArithmeticException("Não existe divisão por zero"))
        );
    }

    @ParameterizedTest
    @DisplayName("Teste potencia")
    @MethodSource("powMethodTest")
    void test11(BigDecimal a, BigDecimal b, BigDecimal c, BigDecimal d, BigDecimal result){
        assertEquals(result, calculator.pow(a,b,c,d));
    }

    public static Stream<Arguments> powMethodTest() {
        return Stream.of(
                Arguments.of(BigDecimal.valueOf(0), BigDecimal.valueOf(5), BigDecimal.valueOf(2), BigDecimal.valueOf(6), BigDecimal.valueOf(0)),
                Arguments.of(BigDecimal.valueOf(2), BigDecimal.valueOf(2), BigDecimal.valueOf(2), BigDecimal.valueOf(2), BigDecimal.valueOf(256)),
                Arguments.of(BigDecimal.valueOf(1), BigDecimal.valueOf(2), BigDecimal.valueOf(5), BigDecimal.valueOf(10), BigDecimal.valueOf(1))
        );
    }

    @Test
    @DisplayName("Teste potencia com expoente negativo")
    void test12(){
        assertThrows(ArithmeticException.class, () -> calculator.pow(BigDecimal.valueOf(3),BigDecimal.valueOf(-2)));
    }

    @Test
    @DisplayName("Teste potencia com expoente negativo")
    void test13(){
        assertThrows(ArithmeticException.class, () -> calculator.pow(BigDecimal.valueOf(3), BigDecimal.valueOf(-2)));
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
                Arguments.of("0 + 0 - 0", "0"),
                Arguments.of("5 + 2 / (3 - 8) ^ 5 ^ 2", "5.00000"),
                Arguments.of("8 / 3", "2.6666666667"),
                Arguments.of("5 / 2", "2.5"),
                Arguments.of("5.0 / 2", "2.5"),
                Arguments.of("2.5 * 2", "5.0")
        );
    }

    @ParameterizedTest
    @DisplayName("Teste calculadora")
    @MethodSource("calculatorMethodTest")
    void test16(String expression, String result){
        assertEquals(new BigDecimal(result, MATH_CONTEXT), calculator.calculate(expression));
    }

    @Test
    @DisplayName("Teste expressão completa 2 + (4 * 2) / 3")
    void test17(){
        assertEquals(new BigDecimal("4.6667", MATH_CONTEXT), calculator.calculate("2 + (4 * 2) / 3"));
    }

    @Test
    @DisplayName("Teste expressão completa 2 + (4 * 2 ^ 2) / 3")
    void test18(){
        assertEquals(new BigDecimal("7.3333333", MATH_CONTEXT), calculator.calculate("2 + (4 * 2 ^ 2) / 3"));
    }

    @Test
    @DisplayName("Teste expressão completa 2 + ( 4 ^ 2 * 2 ) / 3")
    void test19(){
        assertEquals(new BigDecimal("12.66667", MATH_CONTEXT), calculator.calculate("2 + ( 4 ^ 2 * 2 ) / 3"));
    }

    @Test
    @DisplayName("Teste expressão completa 2.0 + ( 4 ^ 2 * 2 ) / -3")
    void test20(){
        assertEquals(new BigDecimal("-8.667", MATH_CONTEXT), calculator.calculate("2.0 + ( 4 ^ 2 * 2 ) / -3"));
    }


}
