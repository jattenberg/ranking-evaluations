package com.parallax.ranking.evaluation;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.Test;

import com.google.common.collect.Lists;

public class TestRankingEvaluations {

	List<Integer> right = Lists.newArrayList(0, 1, 2);
	List<Integer> almost = Lists.newArrayList(0, 2, 1);
	List<Integer> almost2 = Lists.newArrayList(2, 0, 1);
	List<Integer> wrong = Lists.newArrayList(2, 1, 0);
	List<Integer> wrong2 = Lists.newArrayList(2, 0, 1);

	@Test
	public void testKendalsTau() {
		assertEquals(1, RankingEvaluations.kendallsTauA(right), 0.0001);
		assertEquals(-1, RankingEvaluations.kendallsTauA(wrong), 0.0001);
		assertEquals(1d / 3d, RankingEvaluations.kendallsTauA(almost), 0.00001);
		assertEquals(-1d / 3d, RankingEvaluations.kendallsTauA(almost2),
				0.00001);
	}

	@Test
	public void testSpearmansFootrule() {
		assertEquals(0, RankingEvaluations.spearmansFootrule(right), 0.0001);
		assertEquals(2, RankingEvaluations.spearmansFootrule(almost), 0.0001);
		assertEquals(4, RankingEvaluations.spearmansFootrule(almost2), 0.0001);
		assertEquals(4, RankingEvaluations.spearmansFootrule(wrong), 0.00001);
		assertEquals(4, RankingEvaluations.spearmansFootrule(wrong2), 0.00001);
	}
}
