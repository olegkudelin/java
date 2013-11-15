/**
 * Реализует преобразование входного файла посредством внешнего класса, 
 * который находиться в jar-архиве и помечен соответствующими аннотациями
 * 
 */


package oleg.kudelin.classloader;

import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

import oleg.kudelin.annotation.ClassRunner;
import oleg.kudelin.annotation.MethodRunner;
import oleg.kudelin.classloader.jaranalysis.ClassFinderInJar;

/**
 * Реализует преобразование входного файла посредством внешнего класса, 
 * который находиться в jar-архиве и помечен соответствующими аннотациями
 * 
 * @author Куделин Олег
 */
public class ClassLoaderFromJAR {

	private static Logger Log = Logger.getLogger(ClassLoaderFromJAR.class.getName());
	private static ResourceBundle res = ResourceBundle.getBundle("locale");
	
	private static ClassLoaderConveter classLoaderConvert; // загружает классы
	private static ClassFinderInJar finder; // поиск классов в jar архиве
	
	
	public ClassLoaderFromJAR() {
		classLoaderConvert = new ClassLoaderConveter();
	}
	
	/**
	 * Закрывает jar файл
	 */
	public void close() {
		if (finder != null) {
			finder.closeFile();
		}
	}
	
	/**
	 * Устанавливает новый jar файл
	 * 
	 * @param jarFileName - jar файл с внешними файлами
	 * @return true если подключение прошло успешно
	 */
	public boolean setJarFile(String jarFileName) {
		close();
		finder = new ClassFinderInJar();
		if (finder.setJarFile(jarFileName)) {
			classLoaderConvert.setClassFinder(finder);
			return true;
		} else {
			Log.log(Level.WARNING, res.getString("ERROR_CONNECT_JAR_FILE"));
			return false;
		}
	}
	
	/**
	 * Преобразует текстовый файл вызовом внешнего класса
	 * @param inputFileName Текстовый файл
	 * @return Имя выходного файла или null, если были ошибки
	 */
	public String runExternalConverter(String inputFileName) {
		Class<?> classObject = classLoaderConvert.findClassByAnnotation(ClassRunner.class.getName());
		Method method = findAnnotetidMethodInClass(classObject, MethodRunner.class.getName());
		return runMethod(getObjectInstance( classObject, inputFileName), method);
	}
	
	private Object getObjectInstance(Class<?> clazz, String inputFileName) {
		Constructor<?>[] constructorList = clazz.getConstructors();
		for (Constructor<?> constructor : constructorList) {
			Class<?>[] paramTypeList = constructor.getParameterTypes();
			if (paramTypeList.length == 1) {
				Object[] inputValue = {new String(inputFileName)};
				try {
					return constructor.newInstance(inputValue);
				} catch (InstantiationException e) {
					Log.log(Level.INFO, "InstantiationException " + e.getMessage());
				} catch (IllegalAccessException e) {
					Log.log(Level.INFO, "IllegalAccessException " + e.getMessage());
				} catch (IllegalArgumentException e) {
					Log.log(Level.INFO, "IllegalArgumentException " + e.getMessage());
				} catch (InvocationTargetException e) {
					Log.log(Level.INFO, "InvocationTargetException " + e.getMessage());
				}			
			}
		}
		return null;		
	}
	
	private String runMethod(Object object, Method method) {
		if (( object == null )||(method == null)) {
			Log.log(Level.INFO, res.getString("INPUT_EMPTY_POINTER"));
			return null;
		}
		try {
			Object o = method.invoke(object);
			if (o instanceof String) {
				return (String) o;
			} else {
				return null;
			}
		} catch (IllegalAccessException e) {
			Log.log(Level.INFO, "IllegalAccessException " + e.getMessage());
		} catch (IllegalArgumentException e) {
			Log.log(Level.INFO, "IllegalArgumentException " + e.getMessage());
		} catch (InvocationTargetException e) {
			Log.log(Level.INFO, "InvocationTargetException " + e.getMessage());
		}		
		return null;
	}
	
	private Method findAnnotetidMethodInClass(Class<?> classIn, String annotationName) {
		if (classIn == null) {
			Log.log(Level.INFO, "Передан пустой класс");
			return null;
		}
		Method[] metodList = classIn.getMethods();
		for (Method method : metodList) {
			Annotation[] annotationList = method.getAnnotations();
			for (Annotation annotation : annotationList) {
				if (annotation.annotationType().getName().equals(annotationName)) return method; 
			}
		}
		Log.log(Level.INFO, "Метод с анотацией " + annotationName + " не найден");
		return null;
	}
	
	
}
