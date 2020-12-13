import java.util.*;

public class Solver {
	int target;
	int[] nums;
	HashMap<String, HashMap<Integer, HashSet<String>>> mem;
	boolean solutionFound;
	HashSet<String> solutionSet;

	public static void main(String[] args) {
		int target = Integer.valueOf(args[0]);
		int[] nums = new int[args.length-1];
		
		for (int i = 1; i < args.length; i++) nums[i-1] = Integer.valueOf(args[i]);
		
		Solver solver = new Solver(target, nums);
		System.out.println(solver);

		//System.out.println();
		LinkedList<Integer> numsAsList = new LinkedList<Integer>();
		for (int x :  nums) numsAsList.add(x);

		HashMap<Integer, HashSet<String>> solution = solver.solve(numsAsList);


		if (solver.solutionFound) {
			System.out.println("It is possible");
			for (String str : solver.solutionSet) {
				System.out.println(str);
			}
		} else {
			System.out.println("Its not possible");
		}
	}


	Solver(int target, int[] nums) {
		this.target = target;
		this.nums = nums;
		this.solutionFound = false;
		mem = new HashMap<String, HashMap<Integer, HashSet<String>>>();
	}

	public String toString() {
		String result = "";
		for (int i = 0; i < nums.length; i++) {
			result += nums[i];
			if (i < nums.length-1) result += ",";
		}
		result += ":" + this.target;
		return result;
	}

	private static void addToMap(HashMap<Integer, HashSet<String>> map, int key, String solution) {
		if (key < 0) return;
		if (!map.containsKey(key)) map.put(key, new HashSet<String>());
		map.get(key).add(solution);
	}

	private static void addToMap(HashMap<Integer, HashSet<String>> map, int key, HashSet<String> solutions) {
		if (key < 0) return;
		if (!map.containsKey(key)) map.put(key, new HashSet<String>());
		map.get(key).addAll(solutions);
	}

	private static HashSet<String> comb(HashSet<String> s1, String op, HashSet<String> s2) {
		HashSet<String> result = new HashSet<String>();
		for (String a : s1) {
			for (String b : s2) {
				if (op.equals("+")) result.add(a + op + b);
				else result.add("(" + a + ")" + op + "(" + b + ")");
			}
		}
		return result;
	}

	private HashMap<Integer, HashSet<String>> solve(LinkedList<Integer> nums) {
		HashMap<Integer, HashSet<String>> result = new HashMap<Integer, HashSet<String>>();
		if (solutionFound) return result;
		if (nums.size() == 1) {
			int x = nums.get(0);
			addToMap(result, x, "" + x);
			addToMap(result, -x, "-" + x);
			return result;
		}
		Collections.sort(nums);
		String numsToString = join(",", nums);
		if (mem.containsKey(numsToString)) return mem.get(numsToString);

		for (LinkedList<Integer> subset : subsets(nums)) {
			LinkedList<Integer> comp = complement(nums, subset);
			if (subset.size() == 0 || comp.size() == 0 || subset.size() < comp.size()) continue;

			//System.out.println(join(",", nums) + " subsets1 " + join(",", subset) + " - " + join(",", comp));
			if (subset.size() > 0 && comp.size() > 0) {
				HashMap<Integer, HashSet<String>> s1 = solve(subset);
				HashMap<Integer, HashSet<String>> s2 = solve(comp);
				for (int r1 : s1.keySet()) {
					addToMap(result, r1, s1.get(r1));
					for (int r2 : s2.keySet()) {
						addToMap(result, r2, s2.get(r2));
						addToMap(result, r1 + r2, comb(s1.get(r1), "+", s2.get(r2)));
						addToMap(result, r1 - r2, comb(s1.get(r1), "-", s2.get(r2)));
						addToMap(result, r2 - r1, comb(s2.get(r2), "-", s1.get(r1)));
						if (r1 != 0 && r2 % r1 == 0) {
							addToMap(result, r2 / r1, comb(s2.get(r2), "/", s1.get(r1)));
						}
						if (r2 != 0 && r1 % r2 == 0) {
							addToMap(result, r1 / r2, comb(s1.get(r1), "/", s2.get(r2)));
						}
						addToMap(result, r1 * r2, comb(s1.get(r1), "*", s2.get(r2)));
					}
				}
			}
			//System.out.println(join(",", nums) + " subsets2 " + join(",", subset) + " - " + join(",", comp));
			//for (int x : result.keySet()) {
			//	System.out.println(x + ":");
			//	for (String sol : result.get(x)) System.out.println(sol);
			//}
			//System.out.println();
		}
		mem.put(numsToString, result);
		if (result.containsKey(target)) {
			solutionFound = true;
			solutionSet = result.get(target);
		}
		return result;
	}

	public void printAllSubsets(int[] nums) {
		LinkedList<Integer> list = new LinkedList<Integer>();
		for (int x : nums) list.add(x);
		List<LinkedList<Integer>> subsets = subsets(list);
		for (List<Integer> sub : subsets) {
			System.out.println(join(",", sub));
		}
		System.out.println("In total : " + subsets.size());
	}

	public static String join(String joiner, List<Integer> list) {
		StringBuilder sb = new StringBuilder();
		for (int x : list) sb.append(x + joiner);
		return sb.toString();
	}

	List<LinkedList<Integer>> subsets(LinkedList<Integer> nums) {
		List<LinkedList<Integer>> result = new LinkedList<LinkedList<Integer>>();
		result.add(asLinkedList(nums.get(0)));
		if (nums.size() == 1) {
			return result;
		}
		int x = nums.remove(0);
		for (List<Integer> part : subsets(nums)) {
			LinkedList<Integer> t1 = new LinkedList<Integer>(part);
			result.add(t1);
			LinkedList<Integer> t2 = new LinkedList<Integer>(part);
			t2.add(0, x);
			result.add(t2);
		}
		nums.add(0, x);
		return result;
	}

	private LinkedList<Integer> asLinkedList(int x) {
		LinkedList<Integer> result = new LinkedList<Integer>();
		result.add(x);
		return result;
	}

	LinkedList<Integer> complement(LinkedList<Integer> complete, LinkedList<Integer> subset) {
		LinkedList<Integer> result = new LinkedList<Integer>();
		int j = 0;
		for (int x : complete) {
			if (j == subset.size()) {
				result.add(x);
			} else if (x == subset.get(j)) {
				j++;
			} else if (x < subset.get(j)) {
				result.add(x);
			} else {
				System.out.println("This should never happend");
			}
		}
		return result;
	}

	public List<Integer> join(LinkedList<Integer> s1, LinkedList<Integer> s2) {
		LinkedList<Integer> result = new LinkedList<Integer>();
		result.addAll(s1);
		result.addAll(s2);
		Collections.sort(result);
		return result;
	}

}