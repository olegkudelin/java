package oleg.kudelin.classloader;

import java.util.ResourceBundle;

public class ConverterRunner {

	private static ResourceBundle res = ResourceBundle.getBundle("locale");	
	
	public ConverterRunner() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		if (args.length != 2) {
			System.out.println(res.getString("PROGRAM_USAGE") + " java -jar task2 JarFileName TextFileName");
			System.exit(-1);
		}
		ClassLoaderFromJAR classLoaderFromJAR = new ClassLoaderFromJAR();
		classLoaderFromJAR.setJarFile(args[0]);		
		String result = classLoaderFromJAR.runExternalConverter(args[1]);
		System.out.println("OutputFileName:" + result);
	}

}
