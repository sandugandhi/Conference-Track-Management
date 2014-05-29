package com.techconf.schedulelogic;

public class KnapSackSolver extends ScheduleLogic {

	public KnapSackSolver() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * KnapSack algorithm http://en.wikipedia.org/wiki/Knapsack_problem Source :
	 * Copyright © 2000–2011, Robert Sedgewick and Kevin Wayne.
	 * http://introcs.cs.princeton.edu/java/96optimization/Knapsack.java.html
	 **/

	@Override
	public void solver(Object request, Object response) throws Exception {
		// TODO Auto-generated method stub
		int W = ((KnapSackSolverRequest) request).getMaxKnapSackSize();
		int N = ((KnapSackSolverRequest) request).getNumSize();
		int[] profit = ((KnapSackSolverRequest) request).getProfit();
		int[] weight = ((KnapSackSolverRequest) request).getWeight();

		// opt[n][w] = max profit of packing items 1..n with weight limit w
		// sol[n][w] = does opt solution to pack items 1..n with weight limit w
		// include item n?
		int[][] opt = new int[N + 1][W + 1];
		boolean[][] sol = new boolean[N + 1][W + 1];

		for (int n = 1; n <= N; n++) {
			for (int w = 1; w <= W; w++) {

				// don't take item n
				int option1 = opt[n - 1][w];

				// take item n
				int option2 = Integer.MIN_VALUE;
				if (weight[n] <= w)
					option2 = profit[n] + opt[n - 1][w - weight[n]];

				// select better of two options
				opt[n][w] = Math.max(option1, option2);
				sol[n][w] = (option2 > option1);
			}
		}

		// determine which items to take
		boolean[] take = new boolean[N + 1];
		for (int n = N, w = W; n > 0; n--) {
			if (sol[n][w]) {
				take[n] = true;
				w = w - weight[n];
			} else {
				take[n] = false;
			}
		}
		((KnapSackSolverResponse) response).setTake(take);
		return;
	}

}
