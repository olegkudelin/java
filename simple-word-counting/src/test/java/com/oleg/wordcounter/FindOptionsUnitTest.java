package com.oleg.wordcounter;

import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.*;

public class FindOptionsUnitTest {

	private static final String FILE_NAME = "fileName";
	private static final String WORD = "word";
	private static SearchOptions searchOptions;

	@BeforeClass
	public static void init() {
		searchOptions = new SearchOptions();
	}

	@Test
	public void testSetAllParams() throws IllegalAccessException {
		String[] params = {SearchOptions.PARAMS_PATTERN, WORD, SearchOptions.PARAMS_IGNORE_CASE, FILE_NAME};
		searchOptions.setParams(params);
		assertEquals(searchOptions.getFileName(), FILE_NAME);
		assertEquals(searchOptions.isIgnoreCase(), true);
		assertEquals(searchOptions.getWordPattern(), WORD);
	}

	@Test
	public void testSetNoneParams() throws IllegalAccessException {
		String[] params = {FILE_NAME};
		searchOptions.setParams(params);
		assertEquals(searchOptions.getFileName(), FILE_NAME);
		assertEquals(searchOptions.isIgnoreCase(), false);
		assertEquals(searchOptions.getWordPattern(), null);
	}
}