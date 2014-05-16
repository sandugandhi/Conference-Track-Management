package com.techconf.schedulelogic;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class KnapSackSolverTest {

	@Test(timeout = 0100)
	public void testSolver() {

		KnapSackSolverRequest req = new KnapSackSolverRequest();
		int W = 2000;
		int N = 6;
		req.setMaxKnapSackSize(W);
		req.setNumSize(N);
		int[] profit = { 0, 944, 99, 734, 49, 669, 963 };
		int[] weight = { 0, 1349, 1078, 294, 222, 73, 719 };
		req.setProfit(profit);
		req.setWeight(weight);
		KnapSackSolverResponse res = new KnapSackSolverResponse();
		boolean[] expecteds = { false, false, false, true, true, true, true };
		int[] iexpecteds = { 0, 0, 0, 1, 1, 1, 1 };

		KnapSackSolver ksSolver = new KnapSackSolver();
		try {
			ksSolver.solver(req, res);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		assertEquals(expecteds.length, res.getTake().length);

		int[] iactuals = new int[N + 1];
		iactuals[0] = 0;
		int i = 0;
		for (boolean b : res.getTake()) {
			if (b) {
				iactuals[i] = 1;
			}
			i++;
		}
		assertArrayEquals(iexpecteds, iactuals);
	}

	@Rule
	public ExpectedException exception = ExpectedException.none();

	@Test
	public void testforNullPointerException() {

		KnapSackSolverRequest req = new KnapSackSolverRequest();
		int W = 2000;
		int N = 6;
		req.setMaxKnapSackSize(W);
		req.setNumSize(N);
		int[] profit = { 0, 944, 99, 734, 49, 669, 963 };
		int[] weight = { 0, 0, 0 };// { 0, 1349, 1078, 294, 222, 73, 719 };
		req.setProfit(profit);
		req.setWeight(weight);
		KnapSackSolverResponse res = new KnapSackSolverResponse();
		boolean[] expecteds = { false, false, false, true, true, true, true };
		int[] iexpecteds = { 0, 0, 0, 1, 1, 1, 1 };

		KnapSackSolver ksSolver = new KnapSackSolver();
		try {

			ksSolver.solver(req, res);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			// e.printStackTrace();
			exception.expect(java.lang.NullPointerException.class);
		}

		assertEquals(expecteds.length, res.getTake().length);

		int[] iactuals = new int[N + 1];
		iactuals[0] = 0;
		int i = 0;
		for (boolean b : res.getTake()) {
			if (b) {
				iactuals[i] = 1;
			}
			i++;
		}
		assertArrayEquals(iexpecteds, iactuals);
	}

}
