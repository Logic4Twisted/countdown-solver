import java.util.*;


public class Tester {

	public static int[] bigOnes = {10, 25, 50, 75, 100};

	public static void main(String[] args) {

		Random r = new Random();
		int target = 100 + r.nextInt(900);
		System.out.println("Target is:" + target);
		int[] nums = {75, 25, 6, 7 , 8 , 9};

		int big1 = r.nextInt(bigOnes.length);
		nums[0] = bigOnes[big1];
		int big2 = r.nextInt(bigOnes.length-1);
		if (big2 >= big1) big2++;
		nums[1] = bigOnes[big2];

		for (int i = 2; i < 6; i++) {
			nums[i] = 1 + r.nextInt(8);
		}

		String numsStr = "Numbers are:";
		for (int i = 0; i < nums.length; i++) {
			if (i > 0) numsStr += ",";
			numsStr += nums[i];
		}
		System.out.println(numsStr);


		// Do the solving part
		Solver solver = new Solver(target, nums);

		solver.solve();

		if (solver.isSolutionFound()) {
			System.out.println("It is possible");
			HashSet<String> solutions = solver.getSolutionSet();
			System.out.println("" + solutions.size() + " solutions found");
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
