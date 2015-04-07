package com.oleg.wordcounter;

import org.junit.Test;

import static org.junit.Assert.*;

public class WordCounterUnitTest {

	@Test
	public void testCountWordAllWord() {
		WordCounter counter = new WordCounter.Builder().build();
		String line = "Tree - Большая, красивая трава. Растет;не Черном\nморе";
		assertEquals(counter.applyAsLong(line), 8);
	}

	@Test
	public void testCountWordWithPatter_IgnoreCase() {
		WordCounter counter = new WordCounter.Builder()
				.wordSubstring("р")
				.ignoreCase(true)
				.build();
		String line = "Tree - Большая, красивая трава. Растет;не Черном\nморе";
		assertEquals(counter.applyAsLong(line), 5);
	}

	@Test
	public void testCountWordWithPatter_NotIgnoreCase() {
		WordCounter counter = new WordCounter.Builder()
				.wordSubstring("р")
				.ignoreCase(false)
				.build();
		String line = "Tree - Большая, красивая трава. Растет;не Черном море";
		assertEquals(counter.applyAsLong(line), 4);
	}
}