package kudelin.oleg.test.files;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;

import kudelin.oleg.files.Text;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

/**
 * Входной текстовый файл
 * @param String fileName - Имя входного файла 
 */

public class TextTest {
	
	private String fileName =  System.getProperty("user.home") + "\\output" + ".txt";
	private Text text; 

	@BeforeClass
	public void beforeClass() {
		PrintWriter out = null;
		try {
			out = new PrintWriter(fileName, "utf-8");
			out.println("Полчает предложение из файла, дополнительно вставляет символы переноса строки для HTML");
			out.println("Возвращает строку из файла. (до символа перевода строки)");
			out.println("out = new PrintWriter(new. BufferedWriter(new FileWriter(this.dirPath. + \" + numberFile.toString() + \".html\")));");
			out.println("");
			out.println("Возвращает строку из файла (до символа. перевода строки)");
			text = new Text(fileName);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if (out != null) out.close();
		}

	}		
	
	@Test
	public void testReadFile() {
		Assert.assertEquals(text.getSentence(), "Полчает предложение из файла, дополнительно вставляет символы переноса строки для HTML<br>Возвращает строку из файла.");
		Assert.assertEquals(text.getNumberReadLine(), 2);
		Assert.assertEquals(text.getSentence(), "(до символа перевода строки)<br>out = new PrintWriter(new.");
		Assert.assertEquals(text.getNumberReadLine(), 1);
		Assert.assertEquals(text.getSentence(), "BufferedWriter(new FileWriter(this.");
		Assert.assertEquals(text.getNumberReadLine(), 0);
		Assert.assertEquals(text.getSentence(), "dirPath.");
		Assert.assertEquals(text.getNumberReadLine(), 0);
		Assert.assertEquals(text.getSentence(), "+ \" + numberFile.");
		Assert.assertEquals(text.getNumberReadLine(), 0);
		Assert.assertEquals(text.getSentence(), "toString() + \".");
		Assert.assertEquals(text.getNumberReadLine(), 0);
		Assert.assertEquals(text.getSentence(), "html\")));<br><br>Возвращает строку из файла (до символа.");
		Assert.assertEquals(text.getNumberReadLine(), 2);
		Assert.assertEquals(text.getSentence(), "перевода строки)");
		Assert.assertEquals(text.getNumberReadLine(), 0);
		Assert.assertEquals(text.getSentence(), null);
		Assert.assertEquals(text.getNumberReadLine(), 0);

	}	
	
}
