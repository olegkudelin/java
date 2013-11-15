/*
* @(#)HTML
*
* @version 1.0 15.08.2013
*
* Copyright notice
*/

package kudelin.oleg.files;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Формирует выходной HTML
 * 
 * @author Куделин Олег
 */

public class HTML {

	private static ResourceBundle res = ResourceBundle.getBundle("locale");
	private static Logger Log = Logger.getLogger(HTML.class.getName());
	
	private String dirPath = "";
	private Integer numberFile = 0; // Номер формируемого файла
	
	private PrintWriter out; // Поток для вывода данных
	
	private String lastFullFileName = "";
	
	// Заголовок HTML
	private final String HTML_HEADER = "<!doctype html> " +
									"<html>" +
										"<style type='text/css'> .from_dic { font-weight: bold; font-style: italic } </style>" +
										"<head>" +
											"<meta charset=\'UTF-8\'>" +
											"<title>Тестовое задание</title>" +
										"</head>" +
										"<body>";
	// Завершение HTML
	private final String HTML_FOOTER = "</html> </body>";

	public HTML(String fileName) {
		this.disassembleFileParth(fileName);
		if (!fileName.isEmpty()&&openFile()) {
			out.print(HTML_HEADER);
		} else {
			if (fileName.isEmpty()) {
				Log.log(Level.WARNING, res.getString("INPUT_STRING_IS_EMPTY"));
			} else {
				Log.log(Level.WARNING, res.getString("FILE_IS_NOT_OPEN"));
			}
		}
	}

	/**
	 * Добавляет строку в текущий файл
	 * @param String line Добавляемая строка
	 */
	public void putLine(String line) {
		if ((line != null)&&(out != null)) {
			out.print(line);
		} else {
			if (line == null) {
				Log.log(Level.WARNING, res.getString("INPUT_STRING_IS_EMPTY"));
			} else {
				Log.log(Level.WARNING, res.getString("FILE_IS_NOT_OPEN"));
			}
		}
			
	}
	
	/**
	 * Начинает новый файл
	 */
	public void beginNewFile() {
		this.close();
		numberFile++; 
		if (openFile()) {
			out.print(HTML_HEADER);
		}
	}

	/**
	 * Завершает формирование 
	 * Вызов обязателен для завершения
	 * @return Имя с полным путем сформированного файла
	 */
	public String close() {
		if (out != null) {
			out.print(HTML_FOOTER);
			out.close();
			return  lastFullFileName;
		} else {
			Log.log(Level.WARNING, res.getString("FILE_IS_NOT_OPEN"));
			return  null;
		}
	}	

	/**
	 * Возвращает строку с необходимыми тегами для выделения слова 
	 * @param String str Строка
	 */
	public String getIsolateString(String str) {
		return "<span class=from_dic>" + str + "</span>";
	}

	private boolean disassembleFileParth(String dirOutput) {
		File file = new File(dirOutput);
		if (file.isDirectory() && file.exists() && file.canRead()) {
			dirPath = file.getAbsolutePath();
			return true;
		} else {
			if (!file.isDirectory()) Log.log(Level.SEVERE, res.getString("OBJECT_IS_NOT_DIRECTORY") + " " + dirOutput);
			if (!file.exists()) Log.log(Level.SEVERE, res.getString("FILE_DOESNT_EXIST") + " " + dirOutput);
			if (!file.canRead()) Log.log(Level.SEVERE, res.getString("CANNOT_READ_FILE") + " " + dirOutput);
		}
		return false;
	}	
	
	private boolean openFile() {
		try {
			lastFullFileName = this.dirPath + "\\output" + numberFile.toString() + ".html";
			out = new PrintWriter(lastFullFileName, "utf-8");
			return true;
		} catch (IOException e) {
			Log.log(Level.SEVERE, e.getMessage());
		}
		return false;
	}
	
}
