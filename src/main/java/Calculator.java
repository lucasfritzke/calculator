
import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.Stack;

public class Calculator {

    private static RpnCalculator rpnCalculator = new RpnCalculator();
    private static final MathContext MATH_CONTEXT = new MathContext(5, RoundingMode.HALF_UP);

    public BigDecimal add(int number1, int number2, int... other) {
        BigDecimal sum = BigDecimal.valueOf(number1);
        sum = sum.add(BigDecimal.valueOf(number2));
        for (int n : other) {
            sum = sum.add(BigDecimal.valueOf(n));
        }
        return sum;

    }

    public BigDecimal subtract(int number1, int number2, int... other) {

        BigDecimal sub = BigDecimal.valueOf(number1);
        sub = sub.subtract(BigDecimal.valueOf(number2));
        for (int n : other) {
            sub = sub.subtract(new BigDecimal(n));
        }
        return sub;
    }

    public BigDecimal multiply(int number1, int number2, int... other) {

        BigDecimal mult = BigDecimal.valueOf(number1);
        mult = mult.multiply(BigDecimal.valueOf(number2), MATH_CONTEXT);
        for (int n : other) {
            mult = mult.multiply(BigDecimal.valueOf(n), MATH_CONTEXT);
        }
        return mult;
    }

    public BigDecimal divide(int number1, int number2, int... other) throws ArithmeticException {
        if (number2 == 0) {
            throw new ArithmeticException("Não existe divisão por zero");
        }
        BigDecimal div = BigDecimal.valueOf(number1);
        div = div.divide(BigDecimal.valueOf(number2), MATH_CONTEXT);
        for (int n : other) {
            if (n == 0) {
                throw new ArithmeticException("Não existe divisão por zero");
            }
            div = div.divide(BigDecimal.valueOf(n), MATH_CONTEXT);
        }
        return div;
    }

    public BigDecimal pow(int number1, int number2, int... other) throws ArithmeticException {
        if (number2 < 0) {
            throw new ArithmeticException("Essa calculadora não trabalho com expoentes negativos");
        }
        BigDecimal p = BigDecimal.valueOf(number1);
        p = p.pow(number2);
        for (int n : other) {
            if (n < 0) {
                throw new ArithmeticException("Essa calculadora não trabalho com expoentes negativos");
            }
            p = p.pow(n);
        }
        return p;
    }

    public BigDecimal calculate(String expression) throws ArithmeticException {

        String rpnExpression = rpnCalculator.convertRpn(expression);
        System.out.println(rpnExpression);
        String[] tokens = rpnExpression.split(" ");

        Stack<String> stack = new Stack<>();
        String n1 = "";
        String n2 = "";
        BigDecimal result = BigDecimal.ZERO;

        for (String token : tokens) {

            if (token.matches("\\d+")) {
                stack.push(token);
                continue;
            }

            n2 = stack.pop();
            n1 = stack.pop();

            if (token.equals("+")) {
                n1 = this.add(Integer.parseInt(n1), Integer.parseInt(n2)).toString();
                stack.push(n1);
                continue;
            }

            if (token.equals("-")) {
                n1 = this.subtract(Integer.parseInt(n1), Integer.parseInt(n2)).toString();
                stack.push(n1);
                continue;
            }

            if (token.equals("*")) {
                n1 = this.multiply(Integer.parseInt(n1), Integer.parseInt(n2)).toString();
                stack.push(n1);
                continue;
            }

            if (token.equals("/")) {
                n1 = this.divide(Integer.parseInt(n1), Integer.parseInt(n2)).toString();
                stack.push(n1);
                continue;
            }

            if (token.equals("^")) {
                n1 = this.pow(Integer.parseInt(n1), Integer.parseInt(n2)).toString();
                stack.push(n1);
                continue;
            }
            // Se chegou até aqui, significa que chegamos ao final da string
            // e último elemento é o resultado final
            n1=stack.pop();


        }
        return new BigDecimal(n1);
    }


}
