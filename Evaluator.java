import java.util.*;

public class Evaluator {
	public static int eval(String expression) {

		int n = expression.length();
		int i = 0;
		while (i < n && expression.charAt(i) != '(') i++;
		if (i < n) {
			int start = i++;
			for (int count = 1; count != 0; i++) {
				if (expression.charAt(i) == '(') count++;
				if (expression.charAt(i) == ')') count--;
			}
			int partial = eval(expression.substring(start+1, i-1));
			return eval(expression.substring(0,start) + partial + expression.substring(i));
		}

		Stack<Integer> vals = new Stack<Integer>();
		Stack<Character> ops = new Stack<Character>();
		ops.push('+');

		i = 0;
		while (i < n) {
			if (isOperation(expression.charAt(i)) && i > 0 && !isOperation(expression.charAt(i-1))) {
				ops.push(expression.charAt(i++));
			} else {
				int start = i++;
				while (i < n && !isOperation(expression.charAt(i))) i++;
				int value = Integer.valueOf(expression.substring(start, i));
				vals.push(value);
				while (!ops.isEmpty() && isHigherPres(ops.peek())) {
					int a = vals.pop();
					int b = vals.pop();
					char op = ops.pop();
					vals.push(calculate(b, op, a));
				}
			}
		}
		int result = 0;
		while (!vals.isEmpty() && !ops.isEmpty()) {
			result = calculate(result, ops.pop(), vals.pop());
		}
		return result;
	}

	private static boolean isOperation(char x) {
		return x == '-' || x == '+' || x == '*' || x == '/';
	}

	private static boolean isHigherPres(char op) {
		return op == '*' || op == '/';
	}

	private static int calculate(int a, char op, int b) {
		if (op == '+') return a + b;
		if (op == '-') return a - b;
		if (op == '*') return a * b;
		if (op == '/') return a / b;
		System.out.println("Operation " + op + " unknown");
		return 0;
	}

	public static void main(String[] args) {
		for (String arg : args) {
			System.out.println(arg + " = " + Evaluator.eval(arg));
		}
	}
}