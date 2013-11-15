/*
* @(#)Text
*
* @version 1.0 15.08.2013
*
* Copyright notice
*/

package kudelin.oleg.files;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 * Входной текстовый файл
 * 
 *  @author Куделин Олег
 */

public class Text {
	
	private static Logger Log = Logger.getLogger(Text.class.getName());
	
	private static ResourceBundle res = ResourceBundle.getBundle("locale");
	
	private String currentFileName; // Текущее имя открытого файла
	
	private BufferedReader inputFile;
	
	private int readLine = 0; // Число прочитанных строк, занимаемых предложением
	private String remainder = "";       // Остаток строки после предложения

	/**
	 * Считывает входной тектовый файл.
	 * Может передавать его построчно или по предложениям
	 * @param fileName Имя входного файла
	 */
	
	public Text(String fileName) {
		this.currentFileName = fileName;
		if (!this.currentFileName.isEmpty()) {
			this.openFile();
		} else {
			Log.log(Level.INFO, res.getString("INPUT_STRING_IS_EMPTY"));
		}
	}

	/**
	 * Закрывает текущий файл
	 */
	
	public void close() {
		if (inputFile != null) {
			try {
				inputFile.close();
				currentFileName = "";
			} catch (IOException e) {
				Log.log(Level.SEVERE, e.getMessage());
			}
		} else {
			Log.log(Level.WARNING, res.getString("FILE_IS_NOT_OPEN"));
		}
	}
	
	public int getNumberReadLine() {
		return readLine;
	}	
	
	/**
	 *  Открывает новый файл на чтение, старый при этом закрывается
	 * @param fileName имя нового файла
	 */
	
	public void openNewFile(String fileName) {
		if (fileName.isEmpty()) {
			Log.log(Level.WARNING, res.getString("INPUT_STRING_IS_EMPTY"));
			return;
		}
		if (!this.currentFileName.isEmpty()){
			close();
		}
		this.currentFileName = fileName;
		this.openFile();
	}

	/**
	 *  Возвращает строку из файла (до символа перевода строки)
	 *  null - если ошибка или строк больше нет
	 *  @return строка
	 */
	
	public String getLine() {
		try {
			return (inputFile.readLine());
		} catch (IOException e) {
			Log.log(Level.SEVERE, e.getMessage());
			return null;
		}
	}
	
	/**
	 *  Получает предложение из файла, дополнительно вставляет символы переноса строки для HTML 	
	 * @return предложение или null, если нечего возвращать
	 */
	
	public String getSentence() {
		readLine = 0;
		String line = null;
		StringBuilder strBuilder = new StringBuilder();
		while( (line = getRemainder()) != null) {
			int pointPosition = line.indexOf(".");
			if (pointPosition != -1) {
				strBuilder.append(line.substring(0, pointPosition + 1)); 
				setRemainder(line.substring(pointPosition + 1, line.length()));
		    	return strBuilder.toString().trim();
		    } else {
		    	strBuilder.append(line);
		    	setRemainder("");
		    }
		}
		setRemainder("");
		if (strBuilder.toString().isEmpty()) {
			return null; 
		} else {
			return strBuilder.toString().trim().substring(0, strBuilder.toString().trim().length() - 4);
		}
	}	
	
	private String getRemainder() {
		if (remainder.isEmpty()) {
			String s;
			try {
				s = inputFile.readLine();
				if (s == null ) {
					remainder = null; 
				} else {
					remainder = s + "<br>";
					readLine++;
				}
			} catch (IOException e) {
				remainder = null;
				Log.log(Level.SEVERE, e.getMessage());
			}
		}
		return remainder;
	}
	
	private void setRemainder(String str) {
		remainder = str;
	}	
	
	private void openFile() {
		try {
			inputFile = new BufferedReader(new InputStreamReader(new FileInputStream(this.currentFileName), "utf-8"));
		} catch (IOException e) {
			Log.log(Level.SEVERE, e.getMessage());
		}		
	}	
}
