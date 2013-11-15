/*
* @(#)ClassLoaderConveter
*
* @version 1.0 15.08.2013
*
* Copyright notice
*/

package oleg.kudelin.classloader;

import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

import oleg.kudelin.classloader.jaranalysis.IClassFinder;

/**
 * Загрузчик классов 
 * Получает тело класса из интерфейса IClassFinder, который необходимо передать при создании
 * Загружает классы по имени класса и по названию аннотации
 * 
 * @author Куделин Олег
 *
 */

public class ClassLoaderConveter extends ClassLoader {

	private static Logger Log = Logger.getLogger(ClassLoaderConveter.class.getName());
	private static ResourceBundle res = ResourceBundle.getBundle("locale");
	
	private IClassFinder finder;
	
	public ClassLoaderConveter() {
		super();
	}

	
	public ClassLoaderConveter( IClassFinder finder ) {
		super();
		setClassFinder(finder);
	}
	
	public void setClassFinder( IClassFinder finder ) {
		this.finder = finder;
		if (finder == null) {
			Log.log(Level.WARNING, res.getString("INPUT_EMPTY_POINTER"));
		}
	}

	/**
	 * Поиск класса по его имени
	 * 
	 * @return экземпляр класса или null если не найдено
	 */
	
	@Override
	public Class<?> findClass(String className) {
		if (className == null) {
			Log.log(Level.WARNING, res.getString("INPUT_STRING_IS_EMPTY"));
			return null;
		}
		byte[] b = finder.getBodyClassByName(className);
		if (b != null) {
			return defineClass(className, b, 0, b.length);
		} else {
			try {
				return super.findClass(className);
			} catch (ClassNotFoundException e) {
				Log.log(Level.WARNING, e.getMessage());
			}
		}
		return null;
	}

	/**
	 * Поиск класса по имени аннотации
	 * 
	 * @return экземпляр класса или null если не найдено
	 */
	
	public Class<?> findClassByAnnotation(String annotationName) {
		if (annotationName == null) {
			Log.log(Level.WARNING, res.getString("INPUT_STRING_IS_EMPTY"));
			return null;
		}
		byte[] b = finder.getBodyClassByNameAnnoteted(annotationName);
		return defineClass(null, b, 0, b.length);
	}
	
	
}
