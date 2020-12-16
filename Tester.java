import java.util.*;

public class Tester {

	public static void main(String[] args) {
		int target = 936;
		int[] nums = {75, 25, 6, 7 , 8 , 9};
		Solver solver = new Solver(target, nums);

		solver.solve();

		if (solver.isSolutionFound()) {
			System.out.println("It is possible");
			for (String str : solver.getSolutionSet()) {
				
				System.out.println(str + " = " + Evaluator.eval(str));

			}
		} else {
			System.out.println("Its not possible");
		}
	}
}
