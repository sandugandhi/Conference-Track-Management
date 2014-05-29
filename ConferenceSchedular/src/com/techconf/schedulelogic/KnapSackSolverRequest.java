package com.techconf.schedulelogic;

public class KnapSackSolverRequest {

	public int[] getProfit() {
		return profit;
	}

	public void setProfit(int[] profit) {
		this.profit = profit;
	}

	public int[] getWeight() {
		return weight;
	}

	public void setWeight(int[] weight) {
		this.weight = weight;
	}

	public int getNumSize() {
		return numSize;
	}

	public void setNumSize(int numSize) {
		this.numSize = numSize;
	}

	public int getMaxKnapSackSize() {
		return MaxKnapSackSize;
	}

	public void setMaxKnapSackSize(int maxKnapSackSize) {
		MaxKnapSackSize = maxKnapSackSize;
	}

	private int profit[];
	private int weight[];
	private int numSize;
	private int MaxKnapSackSize;

	public KnapSackSolverRequest() {
		// TODO Auto-generated constructor stub

	}

}
