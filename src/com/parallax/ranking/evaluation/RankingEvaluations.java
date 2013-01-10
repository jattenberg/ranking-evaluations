package com.parallax.ranking.evaluation;

import java.util.List;

import com.google.common.collect.Lists;

public class RankingEvaluations {

	/**
	 * 
	 * computes the spearmans footrule measure of dischordancy
	 * 
	 * @param positions
	 *            list containing the true and predicted rank of results </b>
	 *            the predicted position is index in the list, the truth is the
	 *            integer value.
	 * @return value of spearmansFootrule [0, \infty)
	 */
	public static double spearmansFootrule(List<Integer> positions) {
		double total = 0d;
		for (int pos = 0; pos < positions.size(); pos++) {
			total += Math.abs(pos - positions.get(pos));
		}
		return total;
	}

	/**
	 * 
	 * computes kendalls tau A measure of ranking correlations
	 * http://en.wikipedia.org/wiki/Kendall_tau_rank_correlation_coefficient
	 * 
	 * @param positions
	 *            list containing the true and predicted rank of results </b>
	 *            the predicted position is index in the list, the truth is the
	 *            integer value.
	 * @return value of kendalls tau A, [-1, 1]
	 */
	public static double kendallsTauA(List<Integer> positions) {
		if (positions.size() < 2) {
			return 0d;
		}

		double dischordantPairs = inversionCounts(positions);
		double numPairs = positions.size() * (positions.size() - 1) / 2d;
		double concordantPairs = numPairs - dischordantPairs;

		return (concordantPairs - dischordantPairs) / numPairs;
	}

	private static double inversionCounts(List<Integer> positions) {
		if (positions.size() < 2)
			return 0;

		int m = (positions.size() + 1) / 2;
		List<Integer> left = Lists.newArrayList(positions.subList(0, m));
		List<Integer> right = Lists.newArrayList(positions.subList(m,
				positions.size()));

		return inversionCounts(left) + inversionCounts(right)
				+ merge(positions, left, right);
	}

	private static double merge(List<Integer> positions, List<Integer> left,
			List<Integer> right) {
		int i = 0, j = 0, count = 0;
		while (i < left.size() || j < right.size()) {
			if (i == left.size()) {
				positions.set(i + j, right.get(j));
				j++;
			} else if (j == right.size()) {
				positions.set(i + j, left.get(i));
				i++;
			} else if (left.get(i) <= right.get(j)) {
				positions.set(i + j, left.get(i));
				i++;
			} else {
				positions.set(i + j, right.get(j));
				count += left.size() - i;
				j++;
			}
		}
		return count;
	}
}
