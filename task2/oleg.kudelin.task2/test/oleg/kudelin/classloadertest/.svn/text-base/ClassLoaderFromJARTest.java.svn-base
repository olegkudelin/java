package oleg.kudelin.classloadertest;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import oleg.kudelin.classloader.ClassLoaderFromJAR;

public class ClassLoaderFromJARTest {

	private ClassLoaderFromJAR classLoaderFromJAR;
	
	@BeforeClass
	public void beforeClass() {
		classLoaderFromJAR = new ClassLoaderFromJAR();
		classLoaderFromJAR.setJarFile("task2_1.jar");
	}	
	@Test
	public void testAnnotetedClass() {
		String result = classLoaderFromJAR.runExternalConverter("d:\\work\\СимбирСофт\\task2\\output.txt");
		Assert.assertEquals(result, "output0.html");
	}	
	
}
