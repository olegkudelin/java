package kudelin.oleg.test.files;

import org.testng.annotations.Test;
import org.testng.annotations.BeforeClass;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

import kudelin.oleg.files.HTML;

import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.DataProvider;

/**
 * Формирует выходной HTML
 * @param String fileName - Имя выходного файла 
 */

public class HTMLTest {

	private HTML html;
	
	@BeforeClass
	public void beforeClass() {
		html = new HTML(System.getProperty("user.home"));
	}	

	@DataProvider
	public Object[][] lineToFile() {
		return new Object[][] {
				new Object[] { null },
				new Object[] { "Тест" },
				new Object[] { " " },
				new Object[] { "на" },
				new Object[] { " " },
				new Object[] { "" },
				new Object[] { null },
				new Object[] { "правильность" },
				new Object[] { " " },
				new Object[] { "создания " },
				new Object[] { "файла" },
				new Object[] { " " },
				new Object[] { "11111" },
				new Object[] { " " },
				new Object[] { "ertyu" }
		};
	}	
	
	@Test(dataProvider = "lineToFile")
	public void createFileTest(String testString) {
		html.putLine(testString);
	}

	@AfterTest
	public void checkCreateFile() {
		InputStreamReader inputStreamReader = null;
		try {
			
			String fileName = html.close();
			inputStreamReader = new InputStreamReader(new FileInputStream(fileName), "utf-8");
			File file = new File(fileName);
			char[] line = new char[(int) file.length()];
			int readNumber = inputStreamReader.read(line);
			StringBuilder sb = new StringBuilder();
			sb.append(line,0,readNumber);
			Assert.assertEquals(sb.toString(), "<!doctype html> <html><style type='text/css'> " +
															".from_dic { font-weight: bold; font-style: italic } " +
															"</style><head><meta charset='UTF-8'><title>Тестовое задание"+
															"</title></head><body>Тест на правильность создания файла "+
															"11111 ertyu</html> </body>");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if (inputStreamReader != null)
				try {
					inputStreamReader.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		}
		
	}
}