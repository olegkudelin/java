package oleg.kudelin.test1;

import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.testng.annotations.BeforeClass;


public class SymbolCounterTest {

	private SymbolCounter symbolCounter = null;
	private SymbolCounter symbolCounter_2 = null;

	@DataProvider
	public Object[][] getNumberUniqueSymbolData() {
		return new Object[][] {
				new Object[] { null , 0},
				new Object[] { "asdfghj", 7 },
				new Object[] { "asdfghj", 0 },
				new Object[] { "aqdfghj", 1 },
				new Object[] { "", 0 },
				new Object[] { " ", 1 },
				new Object[] { " ASdf", 2 },
				new Object[] { null, 0 },
				new Object[] { "ertyu", 5 },
				new Object[] { "asdfghj", 0 },
				new Object[] { "ertyu", 0 }
		};
	}

	@DataProvider
	public Object[][] getNumberUniqueSymbolData_2() {
		return new Object[][] {
				new Object[] { null , 0},
				new Object[] { "asdfghj", 0 },
				new Object[] { "asdfghj", 0 },
				new Object[] { "", 0 },
				new Object[] { null, 0 },
				new Object[] { "ertyu", 0 },
				new Object[] { "asdfghj", 0 },
				new Object[] { "asjfghjn", 1 },
				new Object[] { "asdfol", 2 },				
				new Object[] { "ertyu", 0 }
		};
	}
	
	@BeforeClass
	public void beforeClass() {
		symbolCounter = new SymbolCounter();
		symbolCounter.clearSimbolList();
	}

// Проверка работы при пустом исходном списке	
	@Test(dataProvider = "getNumberUniqueSymbolData")
	public void getNumberUniqueSymbolTest(String testString, int expected) {
		int numberSymbol = symbolCounter.getNumberUniqueSymbol(testString);
		Assert.assertEquals(numberSymbol, expected);
	}

// Проверка сохранения списка	
	@Test(dependsOnMethods = { "getNumberUniqueSymbolTest" })
	public void saveStateTest() {
		symbolCounter.saveState();
	}

// Создание списка и загрузка сохраненных данных	
	@Test(dependsOnMethods = { "getNumberUniqueSymbolTest", "saveStateTest" })
	public void createSecondSymbolCounter() {
		symbolCounter_2 = new SymbolCounter();
		Assert.assertNotEquals(symbolCounter_2, null);
	}
	
// Проверка правильности выгрузки - восстановления списка	
	@Test(dataProvider = "getNumberUniqueSymbolData_2", dependsOnMethods = { "getNumberUniqueSymbolTest", "saveStateTest", "createSecondSymbolCounter" })
	public void getNumberUniqueSymbolTest_2(String testString, int expected) {
		int numberSymbol = symbolCounter_2.getNumberUniqueSymbol(testString);
		Assert.assertEquals(numberSymbol, expected);
	}
	
}
