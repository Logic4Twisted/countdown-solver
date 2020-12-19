import java.util.*;

public class Tester {

	public static void main(String[] args) {

		Random r = new Random();
		int target = 100 + r.nextInt(900);
		int[] nums = {75, 25, 6, 7 , 8 , 9};
		Solver solver = new Solver(target, nums);

		solver.solve();

		if (solver.isSolutionFound()) {
			System.out.println("It is possible");
			for (String str : solver.getSolutionSet()) {

				int result = Evaluator.eval(str);
				if (result != target) System.out.println("!!!!!!  What, this is not correct !!!!");
				
				System.out.println(str + " = " + Evaluator.eval(str));

			}
		} else {
			System.out.println("Its not possible");
		}
	}
}
