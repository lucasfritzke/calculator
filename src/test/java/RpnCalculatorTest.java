import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RpnCalculatorTest {

    private static final RpnCalculator rpnCalc = new RpnCalculator();

    @Test
    @DisplayName("Teste Simples Notação Polonesa 2 + 2")
    void test01(){
        assertEquals("2 2 +", rpnCalc.convertRpn("2 + 2"));
    }

    @Test
    @DisplayName("Teste Simples Notação Polonesa 23 + 2")
    void test02(){
        assertEquals("23 2 +", rpnCalc.convertRpn("23 + 2"));
    }

    @Test
    @DisplayName("Teste Simples Notação Polonesa 5 + 2 / (3 - 8) ^ 5 ^ -2")
    void test03(){
        assertEquals("-2 2 3 8 - -5 ^ 2 ^ / +", rpnCalc.convertRpn("-2 + 2/(3 - 8) ^ -5 ^ 2"));
    }
}