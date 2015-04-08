package com.oleg.wordcounter;


import javax.annotation.Nonnull;

/**
 * Определяет и формирутет критерии поиска слов
 *
 * @author Kudelin Oleg (kudelinon@mail.ru) 30.03.15.
 *
 */
public class SearchOptions {

	protected static final String PARAMS_IGNORE_CASE = "-i";
	protected static final String PARAMS_PATTERN = "-pattern";

	private String fileName;
	private boolean ignoreCase = false;
	private String wordPattern;

	public SearchOptions() {
	}

	public SearchOptions(String[] params) throws IllegalAccessException {
		if (params != null) {
			setParams(params);
		}
	}

	/**
	 * Сбрасывает текущие критерии поиска и определяет новые критерии из массива строк
	 * Ожидает массив, сформированный из строки вида "-ignoreCase -pattern word_substring fileName" разбитием ее по пробельным символам
	 * @param params - массив параметров поиска
	 * @throws IllegalAccessException - если массив параметров некорректен
	 */
	public void setParams(@Nonnull String[] params) throws IllegalAccessException {
		resetCurrentOptions();
		for (int i = 0; i < params.length; i++) {
			if (PARAMS_PATTERN.equalsIgnoreCase(params[i])) {
				if (params.length > i) {
					wordPattern = params[++i];
				} else {
					throw new IllegalAccessException("Cannot find word pattern");
				}
			} else if (PARAMS_IGNORE_CASE.equalsIgnoreCase(params[i])) {
				ignoreCase = true;
			} else {
				fileName = params[i];
			}
		}
		if (fileName == null) {
			throw new IllegalAccessException("Cannot find file name");
		}
	}

	/**
	 * Возвращает перечень ожидаемых параметров для отображения в подсказке
	 *
	 * @return строка параметров
	 */
	public static String showUsageString() {
		return "[" + PARAMS_IGNORE_CASE + "] [" + PARAMS_PATTERN + " word_pattern] file_name";
	}

	/**
	 * Полное имя (с путем) входного файла
	 *
	 * @return полное имя входного файла
	 */
	public String getFileName() {
		return fileName;
	}

	/**
	 * Флаг игнорирования регистра
	 *
	 * @return true если нужно ли игнорировать регистр при поиске
	 */
	public boolean isIgnoreCase() {
		return ignoreCase;
	}

	/**
	 * Подстрока искомого слова или слово целиком
	 *
	 * @return подстрока
	 */
	public String getWordPattern() {
		return wordPattern;
	}

	private void resetCurrentOptions() {
		fileName = null;
		ignoreCase =false;
		wordPattern = null;
	}
}
