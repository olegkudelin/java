package com.oleg.wordcounter;

import javax.annotation.Nonnull;
import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.function.ToLongFunction;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * Считает число слов в строке
 * Если при исоздании не было ничего передано возвращает полное число слов
 * Если при создании была указана строка, то считает только те слова,
 * в которых встречатся указанная строка.
 * Дополнительно можно указать следует ли учитывать регистр, если подсчитывается определенное слово.
 *
 * @author  Kudelin oleg (kudelinon@mail.ru) on 28.03.15.
 */
class WordCounter implements ToLongFunction<String> {

	private Pattern pattern;
	private static final String WORD_DELIMETER = "\\p{Punct}|\\p{Space}";
	private static final Logger log = Logger.getLogger(WordCounter.class.getName());

	/**
	 * Конструирует объект WordCounter c в соответствии с критериями поиска
	 */
	public static class Builder {
		private String wordSubstring = null;
		private boolean ignoreCase = false;

		/**
		 * Устанавливает подстроку слова, которое будет учитыватья при поиске
		 * @param wordSubstring
		 * @return Builder
		 */
		public Builder wordSubstring(String wordSubstring) {
			this.wordSubstring = wordSubstring;
			return this;
		};

		/**
		 * Устанавливает флаг, ижно ли игнорировать регистр при поиске
		 * @param isIgnoreCase true - если нужно игнорировать
		 * @return Builder
		 */
		public Builder ignoreCase(boolean isIgnoreCase) {
			this.ignoreCase = isIgnoreCase;
			return this;
		}

		/**
		 * Конструирует WordCounter с учетом критериев поиска
		 * @return WordCounter
		 */
		public WordCounter build() {
			if (wordSubstring != null) {
				return new WordCounter(wordSubstring, ignoreCase);
			} else {
				return new WordCounter();
			}
		}

	};

	private WordCounter() {
		this.pattern = Pattern.compile("[^" + WORD_DELIMETER + "]+");
	}

	private WordCounter(@Nonnull String pattern, boolean ignoreCase) {
		String regExpPattern = "(?:^|" + WORD_DELIMETER + ")[^" + WORD_DELIMETER+ "]*" + Pattern.quote(pattern);
		this.pattern = (ignoreCase) ?  Pattern.compile(regExpPattern, Pattern.CASE_INSENSITIVE | Pattern.UNICODE_CASE) : Pattern.compile(regExpPattern);
	}

	/**
	 * Считает число слов в строке. Правило поиска зависит от переданных
	 * параметорв при создании экземпляра объекта
	 *
	 * @param value строка, в которой ведется поиск
	 * @return число найденных слов
	 */
	@Override
	public long applyAsLong(String value) {
		Matcher matcher = pattern.matcher(value);
		long count = 0;
		while (matcher.find()) {
			count++;
		}
		return count;
	}

	 /**
	  * Подсчитывает число слов во входном файле
	  * Можно подсчитать полное число слов или число пределенных слов, которое можно задать подстрокой, которая является его частью
	  * Дополнительно можно указать учитывать регистр или нет
	  *
	  * Вызов программы "WordCounter [-ignoreCase] [-pattern word_substring] fileName"
	  *
 	  * @param args
	  */
	public static void main(String[] args) {
		try {
			SearchOptions searchOptions = new SearchOptions(args);
			Path path = FileSystems.getDefault().getPath(searchOptions.getFileName());
			try (BufferedReader bufferedReader = Files.newBufferedReader(path, Charset.defaultCharset())) {
				WordCounter wordCounter = new WordCounter.Builder()
						.wordSubstring(searchOptions.getWordPattern())
						.ignoreCase(searchOptions.isIgnoreCase())
						.build();
				long wordCount = bufferedReader
						.lines()
						.mapToLong(wordCounter)
						.sum();
				System.out.println("Found " + wordCount + " words");
			} catch (IOException e) {
				log.log(Level.SEVERE, "Can't read input file", e);
			}
		} catch (IllegalAccessException e) {
			log.log(Level.SEVERE, "Error input params", e);
			System.out.println("Usage: " + WordCounter.class.getSimpleName() + " " + SearchOptions.showUsageString());
		}
	}

}
