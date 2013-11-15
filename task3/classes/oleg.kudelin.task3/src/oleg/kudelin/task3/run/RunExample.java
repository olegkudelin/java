package oleg.kudelin.task3.run;

import oleg.kudelin.task3.queries.Queries;

public class RunExample {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Queries queries = new Queries();
		System.out.println("Список магазинов с товаром \"Волжанка\": " + queries.getShopListByProductName("Волжанка"));
		System.out.println("Список магазинов с товаром \"Апельсин\" и ценой в два раза выше: " + queries.getExpensiveShopListByProductName("Апельсин"));
		System.out.println("Список магазинов с товаром \"Апельсин\" и низкой ценой: " + queries.getShopListByProductNameWithMinimalPrice("Апельсин"));
		try {
			queries.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
