import java.util.Arrays;
import java.util.List;
import java.util.Stack;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class RpnCalculator {

    public String convertRpn(String expression) {
        String resut = this.infixToRpn(expression);
        resut = resut.replaceAll("\\s{2,}", " "); // caracter em branco com duas ou mais occorrencias
        return resut.trim();
    }

    private String infixToRpn(String expression) {
        int operator =0; int digit =0;
        Stack<String> stack = new Stack<String>();
        StringBuilder output = new StringBuilder();

        expression= expression.replaceAll("\\("," ( ");
        expression = expression.replaceAll("\\)"," ) ");
        String[] tokens = expression.split("\\s+");

        for (String token : tokens) {

            if (token.matches("(-?[0-9]+\\.?[0-9]+|-?[0-9]+)") || token.equals(" ")) {
                output.append(token+" ");
                digit++;
                continue;
            }

            if (token.equals("(")) {
                stack.push(token);
                output.append(" "); // Quando remove o parenteses é necessário colocar um espaço, para o caso 3/(2 + 4)
                continue;
            }

            if (token.equals(")")) {

                while (!stack.isEmpty() && !stack.peek().equals("(")) {
                    output.append(" "+stack.pop()+" ");
                    operator++;

                }

                if (stack.isEmpty()) {
                    throw new ArithmeticException("Expressão inválida");
                }

                stack.pop();
                continue;// remove opening parentheses
            }
            int precedence = this.getPrecedence(token);
            if (precedence == -1) {
                throw new ArithmeticException("Expressão inválida");
            }

            while (!stack.isEmpty()
                    && precedence <= getPrecedence(stack.peek())
                    && hasLeftAssociativity(token)) {

                output.append(" " + stack.pop()+" ");
                operator++;
            }
            stack.push(token);
        }

        while (!stack.isEmpty()) {
            if (stack.peek().equals("(")) {
                throw new ArithmeticException("Expressão inválida");
            }
            output.append(" " + stack.pop()+ " ");
            operator++;
        }

        if(digit == (operator+1)){ // validates if there are no more operators than digits
            return output.toString();
        } else {
            throw new ArithmeticException("Expressão inválida");
        }



    }


    // Operator has Left --> Right associativity
    static boolean hasLeftAssociativity(String token) {
        return token.equals("+") || token.equals("-") || token.equals("/") || token.equals("*") || token.equals("^");
    }

    private static int getPrecedence(String token) {
        if (token.equals("+") || token.equals("-"))
            return 1;
        else if (token.equals("/") || token.equals("*"))
            return 2;
        else if (token.equals("^"))
            return 3;
        else
            return -1;
    }
}
