package oleg.kudelin.task3.queries;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class QueriesTest {

	private Queries queries;

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@DataProvider
	public Object[][] productNameData() {
		return new Object[][] {
				new Object[] { null , null, null , null},
				new Object[] { "Волжанка", new ArrayList(Arrays.asList("Гулливер","Пятерочка")), new ArrayList(Arrays.asList("Пятерочка")), new ArrayList(Arrays.asList("Пятерочка")) },
				new Object[] { "Апельсин", new ArrayList(Arrays.asList("Айсберг","Гулливер","Пятерочка")), new ArrayList(), new ArrayList(Arrays.asList("Гулливер")) },
				new Object[] { "aqdfghj", new ArrayList(Arrays.asList()), null, null },
				new Object[] { "", new ArrayList(), null, null },
				new Object[] { " ", new ArrayList(), null, null },
				new Object[] { " Волжанка", new ArrayList(), null, null },
				new Object[] { null, null, null, null },
				new Object[] { "Молоко", new ArrayList(Arrays.asList("Гулливер","Пятерочка")), new ArrayList(), new ArrayList(Arrays.asList("Гулливер")) },
				new Object[] { "Сахар", new ArrayList(Arrays.asList("Айсберг","Гулливер","Лампочка","Лента","Магнит","Пятерочка","Свет","Симбирка")), new ArrayList(), new ArrayList(Arrays.asList("Лента")) },
				new Object[] { "сахар", new ArrayList(), null, null },
				new Object[] { "Бананы", new ArrayList(Arrays.asList("Гулливер","Лампочка","Магнит","Пятерочка","Свет")), new ArrayList(), new ArrayList(Arrays.asList("Пятерочка","Лампочка")) }
		};
	}	
	
	@BeforeClass
	public void beforeClass() {
		queries = new Queries();
	}
	
	@Test(dataProvider = "productNameData")
	public void testQuery(String productName, List<String> shopList1, List<String> shopList2, List<String> shopList3) {
		Assert.assertEquals(queries.getShopListByProductName(productName), shopList1);
		Assert.assertEquals(queries.getExpensiveShopListByProductName(productName), shopList2);
		Assert.assertEquals(queries.getShopListByProductNameWithMinimalPrice(productName), shopList3);
	}
	
	@AfterClass
	public void close() {
		try {
			queries.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	

}
