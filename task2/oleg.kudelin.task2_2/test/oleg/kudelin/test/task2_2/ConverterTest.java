package oleg.kudelin.test.task2_2;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import oleg.kudelin.task2_2.Converter;


public class ConverterTest {

	private String fileName =  System.getProperty("user.home") + "\\output" + ".txt";
	private Converter convTest;
	
	@BeforeClass
	public void beforeClass() {
//		out = new PrintWriter(new BufferedWriter(new FileWriter(this.dirPath + "\\output" + numberFile.toString() + ".html")));
		PrintWriter out = null;
		try {
			out = new PrintWriter(fileName, "utf-8");
			out.println("Полчает предложение из файла, дополнительно вставляет символы переноса строки для HTML");
			out.println("Возвращает строку из файла. (до символа перевода строки)");
			out.println("out = new PrintWriter(new. BufferedWriter(new FileWriter(this.dirPath. + \" + numberFile.toString() + \".html\")));");
			out.println("");
			out.println("Возвращает строку из файла (до символа. перевода строки)");
			convTest = new Converter(fileName);
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
	public void ConvertTest() {
		String fileName = convTest.start();
		InputStreamReader inputStreamReader = null;
		try {
			inputStreamReader = new InputStreamReader(new FileInputStream(fileName), "utf-8");
			File file = new File(fileName);
			char[] line = new char[(int) file.length()];
			int readNumber = inputStreamReader.read(line);
			StringBuilder sb = new StringBuilder();
			sb.append(line,0,readNumber);
			Assert.assertEquals(sb.toString(), "<!doctype html> <html><style type='text/css'> .from_dic { font-weight: "+
												"bold; font-style: italic } </style><head><meta charset='UTF-8'><title>"+
												"Тестовое задание</title></head><body>Полчает  предложение  из  файла,  "+
												"дополнительно  вставляет  символы  переноса  строки  для  HTMLВозвращает  "+
												"строку  из  файла.  (до  символа  перевода  строки)out  =  new  "+
												"PrintWriter(new.  BufferedWriter(new  FileWriter(this.dirPath.  +  \"  "+
												"+  numberFile.toString()  +  \".html\")));Возвращает  строку  из  файла  "
												+"(до  символа.  перевода  строки)</html> </body>");
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
