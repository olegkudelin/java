/*
* @(#)SymbolCounter
*
* @version 1.0 08.08.2013
*
* Copyright notice
*/

package oleg.kudelin.test1;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Arrays;
import java.util.HashSet;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
* 	Подсчитывает число уникальных символов в строке. 
* 	При этом встреченные символы сохраняюся и учитываются при следующем поиске
*
* @author Куделин Олег
*/

public class SymbolCounter {

	private final String FILE_NAME_CURRENT_STATE_SAVE = "SymbolCounter_foundSymbolList.class"; //  Имя файла для сохранения состояния
	
	private static ResourceBundle res = ResourceBundle.getBundle("locale");
	private static Logger log = Logger.getLogger(SymbolCounter.class.getName());
	private Set<Character> foundSymbolList = null; // Список встреченных символов
	
	
	public SymbolCounter() {
		restoreFoundSymbolList();
	}

	/**
	 * Сохраняет текущее состояние объекта
	 */
	public void saveState() {
		saveFoundSymbolList();
	}

	/**
	 * Подсчитывает число уникальных символов в строке.
	 * @param testString Строка, в которой необходимо подсчитать символы 
	 */
	public int getNumberUniqueSymbol(String testString) {
		if (testString == null || testString.isEmpty()) {
			log.log(Level.INFO, res.getString("INPUT_STRING_IS_EMPTY"));
			return 0;
		}
		Character[] symbolsArray = getCharacterArray(testString.toCharArray());
		Set<Character> symbolsList = new HashSet<Character>(Arrays.asList(symbolsArray));
		return checkSymbols(symbolsList);
	}

	/**
	 * Сбрасывает текущее состояние объекта
	 */
	public void clearSimbolList() {
		if (foundSymbolList != null) foundSymbolList.clear();
	}

	private Character[] getCharacterArray(char[] charArray) {
		if (charArray == null) {
			log.log(Level.INFO, res.getString("INPUT_EMPTY_POINTER"));
			return null; 
		}
		Character[] symbolsList = new Character[charArray.length];
		for (int i = 0; i < charArray.length; i++) symbolsList[i] = new Character(charArray[i]);
		return symbolsList;
	}
	
	private int checkSymbols(Set<Character> symbolsList) {
		int numberUniqueSymbol = 0;
		for (Character symbol : symbolsList) {
			if (!foundSymbolList.contains(symbol)) {
				numberUniqueSymbol++;
				foundSymbolList.add(symbol);
			}
		}
		return numberUniqueSymbol;
	}

	private void saveFoundSymbolList() {
		ObjectOutputStream outStream = null;
		try {
			outStream = new ObjectOutputStream(new FileOutputStream(System.getProperty("user.home") + System.getProperty("file.separator") + FILE_NAME_CURRENT_STATE_SAVE));
			outStream.writeObject(foundSymbolList);
			outStream.close();
		} catch (Exception e) {
			log.log(Level.ALL, e.getMessage());
		}
		if (outStream != null)
			try {
				outStream.close();
			} catch (IOException e) {
				log.log(Level.ALL, e.getMessage());			}
	}
	
	private void restoreFoundSymbolList() {
		String fileName = System.getProperty("user.home") + System.getProperty("file.separator") + FILE_NAME_CURRENT_STATE_SAVE; 
		if (!checkFileOnAccess(fileName)||!loadFromFile(fileName)) {
			log.log(Level.INFO, res.getString("PREV_DATA_NOT_LOAD"));
			foundSymbolList = new HashSet<Character>();
		}
	}
	
	private boolean checkFileOnAccess(String fileName) {
		File file = new File(fileName);
		if (file.canRead()) {
			return true;
		} else {
			if (!file.exists()) log.log(Level.INFO, res.getString("FILE_DOESNT_EXIST") + " " + fileName);
				else if (!file.isFile()) log.log(Level.INFO, res.getString("OBJECT_IS_NOT_FILE") + " " + fileName); 
					else log.log(Level.INFO, res.getString("CANNOT_READ_FILE") + " " + fileName);
			return false;
		}
	}

	@SuppressWarnings("unchecked")
	private boolean loadFromFile(String fileName) {
		ObjectInputStream inStream = null;
		try {
			inStream = new ObjectInputStream(new FileInputStream(fileName));
			Object o = inStream.readObject();
			if (o instanceof HashSet<?>) foundSymbolList = (HashSet<Character>)o; 
			inStream.close();
			if (foundSymbolList != null) return true;
		} catch (Exception e) {
			log.log(Level.ALL, e.getMessage());
			e.printStackTrace();
		}
		if (inStream != null)
			try {
				inStream.close();
			} catch (IOException e) {
				log.log(Level.ALL, e.getMessage());
			}
		return false;
	}

}
