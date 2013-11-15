package oleg.kudelin.classloadertest;

import oleg.kudelin.annotation.ClassRunner;
import oleg.kudelin.classloader.jaranalysis.ClassFinderInJar;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class ClassFinderInJarTest {

	private ClassFinderInJar classFinderInJar;
	
	@BeforeClass
	public void beforeClass() {
		classFinderInJar = new ClassFinderInJar();
		classFinderInJar.setJarFile("task2_1.jar");
	}	
	@Test
	public void testAnnotetedClass() {
		String result = classFinderInJar.getAnnotetedClassName(ClassRunner.class.getName());
		Assert.assertEquals(result, "oleg.kudelin.task2_1.Converter");
	}	
	
	
}
