import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.ArrayList;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class RpnCalculatorTest {

    private static final RpnCalculator rpnCalc = new RpnCalculator();


    @Test
    @DisplayName("Teste Simples Notação Polonesa 2 + 2")
    void test01() {
        assertEquals("2 2 +", rpnCalc.convertRpn("2 + 2"));
    }

    @Test
    @DisplayName("Teste Simples Notação Polonesa 23 + 2")
    void test02() {
        assertEquals("23 2 +", rpnCalc.convertRpn("23 + 2"));
    }

    @Test
    @DisplayName("Teste Simples Notação Polonesa 5 + 2 / (3 - 8) ^ 5 ^ -2")
    void test03() {
        assertEquals("-2 2 3 8 - -5 ^ 2 ^ / +", rpnCalc.convertRpn("-2 + 2 / (3 - 8) ^ -5 ^ 2"));
    }

    @ParameterizedTest
    @DisplayName("Teste para erro de Sintaxe")
    @MethodSource("convertRpnException")
    void test04(String expression, ArithmeticException exception) {
        ArithmeticException ae = Assertions.assertThrows(ArithmeticException.class, () -> rpnCalc.convertRpn(expression));
        assertEquals(exception.getMessage(), ae.getMessage());
    }

    public static Stream<Arguments> convertRpnException() {
        ArithmeticException ae = new ArithmeticException("Expressão inválida");
        return Stream.of(
                Arguments.of("5+", ae),
                Arguments.of("5 + ^", ae),
                Arguments.of("5 -6 5 5 5 ", ae),
                Arguments.of("5 + 6 a / 2 ", ae),
                Arguments.of("--5 * 6 ", ae),
                Arguments.of("7 + ( 7 * 9 ", ae),
                Arguments.of("7 + ( 7 * 9 ) ) ", ae),
                Arguments.of("(7 +  7 * 9 )) ", ae),
                Arguments.of("& 5 + 7", ae)
        );
    }

}