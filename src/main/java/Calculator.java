
import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.Stack;

public class Calculator {

    private static final RpnCalculator rpnCalculator = new RpnCalculator();
    private static final MathContext MATH_CONTEXT = new MathContext(5, RoundingMode.HALF_UP);

    public BigDecimal add(BigDecimal number1, BigDecimal number2, BigDecimal... others) {
        BigDecimal sum = number1;
        sum = sum.add(number2);
        for (BigDecimal n : others) {
            sum = sum.add(n);
        }
        return sum;

    }

    public BigDecimal subtract(BigDecimal number1, BigDecimal number2, BigDecimal... others) {

        BigDecimal sub = number1;
        sub = sub.subtract(number2);
        for (BigDecimal n : others) {
            sub = sub.subtract(n);
        }
        return sub;
    }

    public BigDecimal multiply(BigDecimal number1, BigDecimal number2, BigDecimal... others) {

        BigDecimal mult = number1;
        mult = mult.multiply(number2, MATH_CONTEXT);
        for (BigDecimal n : others) {
            mult = mult.multiply(n, MATH_CONTEXT);
        }
        return mult;
    }

    public BigDecimal divide(BigDecimal number1, BigDecimal number2, BigDecimal... other) throws ArithmeticException {
        if (number2.equals(BigDecimal.ZERO)) {
            throw new ArithmeticException("Não existe divisão por zero");
        }
        BigDecimal div = number1;
        div = div.divide(number2, MATH_CONTEXT);
        for (BigDecimal n : other) {
            if (n.equals(BigDecimal.ZERO)) {
                throw new ArithmeticException("Não existe divisão por zero");
            }
            div = div.divide(n, MATH_CONTEXT);
        }
        return div;
    }

    public BigDecimal pow(BigDecimal number1, BigDecimal number2, BigDecimal... other) throws ArithmeticException {
        if (number2.compareTo(BigDecimal.ZERO) < 0) {
            throw new ArithmeticException("Essa calculadora não trabalho com expoentes negativos");
        }
        BigDecimal p = number1;
        p = p.pow(Integer.parseInt(number2.toString()));
        for (BigDecimal n : other) {
            if (number2.compareTo(BigDecimal.ZERO) < 0) {
                throw new ArithmeticException("Essa calculadora não trabalho com expoentes negativos");
            }
            p = p.pow(Integer.parseInt(number2.toString()));
        }
        return p;
    }

    public BigDecimal calculate(String expression) throws ArithmeticException {

        String rpnExpression = rpnCalculator.convertRpn(expression);
        String[] tokens = rpnExpression.split(" ");

        Stack<String> stack = new Stack<>();
        String n1 = "";
        String n2 = "";
        BigDecimal result = BigDecimal.ZERO;

        for (String token : tokens) {

            if (token.matches("-?[0-9]+\\.?[0-9]+|-?[0-9]+")) {
                stack.push(token);
                continue;
            }

            n2 = stack.pop();
            n1 = stack.pop();

            if (token.equals("+")) {
                n1 = this.add(new BigDecimal(n1), new BigDecimal(n2)).toString();
                stack.push(n1);
                continue;
            }

            if (token.equals("-")) {
                n1 = this.subtract(new BigDecimal(n1), new BigDecimal(n2)).toString();
                stack.push(n1);
                continue;
            }

            if (token.equals("*")) {
                n1 = this.multiply(new BigDecimal(n1), new BigDecimal(n2)).toString();
                stack.push(n1);
                continue;
            }

            if (token.equals("/")) {
                n1 = this.divide(new BigDecimal(n1), new BigDecimal(n2)).toString();
                stack.push(n1);
                continue;
            }

            if (token.equals("^")) {
                n1 = this.pow(new BigDecimal(n1), new BigDecimal(n2)).toString();
                stack.push(n1);
                continue;
            }

            // Se chegou até aqui, significa que chegamos ao final da string
            // e último elemento é o resultado final
//            n1 = stack.pop();


        }
        return new BigDecimal(n1, MATH_CONTEXT);
    }


}
