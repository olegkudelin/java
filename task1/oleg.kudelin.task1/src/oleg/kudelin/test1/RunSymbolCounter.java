package oleg.kudelin.test1;

import java.util.ResourceBundle;

public class RunSymbolCounter {

	
	private static ResourceBundle res = ResourceBundle.getBundle("locale");	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		if (args.length != 1) {
			System.out.println(res.getString("PROGRAM_USAGE") + " java -jar task1 stringLine");
			System.exit(-1);
		}
		SymbolCounter symbolCounter = new SymbolCounter();
		System.out.println(res.getString("NUMBER_SYMBOLS") + " " + symbolCounter.getNumberUniqueSymbol(args[0]));
		symbolCounter.saveState();
	}	

}
