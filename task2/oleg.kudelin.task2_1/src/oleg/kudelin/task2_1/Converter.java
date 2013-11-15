/*
* @(#)Converter
*
* @version 1.0 15.08.2013
*
* Copyright notice
*/

package oleg.kudelin.task2_1;


import kudelin.oleg.files.HTML;
import kudelin.oleg.files.Text;
import oleg.kudelin.annotation.ClassRunner;
import oleg.kudelin.annotation.MethodRunner;
/**
 * Преобразует текстовый файл в HTML, вырезая каждое второе слово в строке
 * 
 * @author Куделин Олег
 */

@ClassRunner	
public class Converter {

	private  Text text;
	private  HTML html;
	
	/**
	 * @param textFileName Имя, с полным путем, входного текстового файла
	 */
	
	public Converter(String textFileName) {
		this.text = new Text(textFileName);		
		this.html = new HTML(System.getProperty("user.home"));			
	}

	/**
	 *  Запускает процесс преобразования
	 * @return Имя преобразованного файла с полным путем
	 */
	
	@MethodRunner
	public String start() {
		String lineFromText; 
		while ((lineFromText = text.getLine()) != null) {
			html.putLine(getConvertString(lineFromText));
		}
		return html.close();
	}	
	
	private String getConvertString(String line) {
		String[] wordArray = line.split("\\p{Space}");
		StringBuilder resultLine = new StringBuilder();
		int i = 0;
		while (i < wordArray.length) {
			resultLine.append(" " + wordArray[i]);
			i += 2;
		}
		return resultLine.toString();
	}
}
