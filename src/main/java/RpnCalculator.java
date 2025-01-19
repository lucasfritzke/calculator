import java.util.Stack;

public class RpnCalculator {

    public String convertRpn(String expression) {
        String resut = this.infixToRpn(expression);
        resut = resut.replaceAll("\\s{2,}", " "); // caracter em branco com duas ou mais occorrencias
        return resut.trim();
    }

    private String infixToRpn(String expression) {

        Stack<Character> stack = new Stack<Character>();
        StringBuilder output = new StringBuilder("");

        for (char character : expression.toCharArray()) {

            if (Character.isDigit(character) || character == ' ') {
                output.append(character);
                continue;
            }

            if (character == '(') {
                stack.push(character);
                output.append(" "); // Quando remove o parenteses é necessário colocar um espaço, para o caso 3/(2 + 4)
                continue;
            }

            if (character == ')') {

                while (!stack.isEmpty() && stack.peek() != '(') {
                    output.append(" "+stack.pop()+" ");
                }
                stack.pop();
                continue;// remove opening parentheses
            }
            int precedence = this.getPrecedence(character);
            if (precedence == -1) {
                throw new ArithmeticException("Expressão inválida");
            }

            while (!stack.isEmpty()
                    && precedence <= getPrecedence(stack.peek())
                    && hasLeftAssociativity(character)) {

                output.append(" " + stack.pop()+" ");
            }
            stack.push(character);
        }

        while (!stack.isEmpty()) {
            if (stack.peek() == '(') {
                throw new ArithmeticException("Expressão inválida");
            }
            output.append(" " + stack.pop()+ " ");
        }
        return output.toString();

    }


    // Operator has Left --> Right associativity
    static boolean hasLeftAssociativity(char ch) {
        if (ch == '+' || ch == '-' || ch == '/' || ch == '*' || ch == '^') {
            return true;
        } else {
            return false;
        }
    }

    private static int getPrecedence(char c) {
        if (c == '+' || c == '-')
            return 1;
        else if (c == '*' || c == '/')
            return 2;
        else if (c == '^')
            return 3;
        else
            return -1;
    }
}
