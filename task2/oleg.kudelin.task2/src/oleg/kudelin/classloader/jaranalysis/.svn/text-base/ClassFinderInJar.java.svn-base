/*
* @(#)ClassFinderInJar
*
* @version 1.0 15.08.2013
*
* Copyright notice
*/
package oleg.kudelin.classloader.jaranalysis;

import java.io.DataInputStream;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.ResourceBundle;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.logging.Level;
import java.util.logging.Logger;

import javassist.bytecode.AnnotationsAttribute;
import javassist.bytecode.ClassFile;

/**
 * Класс для поиска и получения байт-кода классов из jar архивов
 * по полному имени и по названию аннотации, а также поиска имени класса по аннотации
 * 
 * для использования необходимо 
 * 1. После создания передать имя jar файла - setJarFile(String jarFileName)
 * 2. Для завершения и закрытия файла вызвать closeFile()
 * 
 * @author Куделин Олег
 */

public class ClassFinderInJar implements IClassFinder {

	private static Logger Log = Logger.getLogger(ClassFinderInJar.class.getName());
	private static ResourceBundle res = ResourceBundle.getBundle("locale");
	
	private JarFile jarFile = null;
	private List<JarEntry> jarEntryList = null;
	
	/**
	 * Устанавливает имя входного jar файла
	 * 
	 * @param jarFileName имя jar файла с полным путем
	 * @return true если открытие файла произошло успешно
	 */
	
	public boolean setJarFile(String jarFileName) {
		if (canOpenFile(jarFileName)&&loadJarFile(jarFileName)) {
			jarEntryList = new ArrayList<JarEntry>();
			loadEntityList();
			return true;
		} else {
			return false;
		}
	}
	
	/**
	 * Закрывает файл
	 */
	
	public void closeFile() {
		if (jarFile != null) {
			try {
				jarFile.close();
				jarFile = null;
			} catch (IOException e) {
				Log.log(Level.SEVERE, e.getMessage());
			}
		}
	}
	
	/**
	 * Возвращает имя класса, помеченного анотацией
	 * @param annotationName Имя аннотации
	 * @return Имя класса или null если класса нет
	 */
	
	public String getAnnotetedClassName(String annotationName) {
        for ( JarEntry entry : jarEntryList) {
            try {
				if (checkClassFileOnAnnoteted(new DataInputStream(jarFile.getInputStream(entry)), annotationName)) {
					return getClassNameFromFilePath(entry.getName());
				}
			} catch (IOException e1) {
				Log.log(Level.SEVERE, e1.getMessage());
			}
        }
		return null;
	}
	
	/**
	 * Возвращает тело класса по имени
	 * @param annotationName Имя класса
	 * @return Байт код класса или null если класса нет
	 */	
	
	public byte[] getBodyClassByName(String className) {
        for ( JarEntry entry : jarEntryList) {
            try {
            	if ((getClassNameFromFilePath(entry.getName())).compareTo(className) == 0) {
            		DataInputStream dataStream = new DataInputStream(jarFile.getInputStream(entry));
            		byte[] b =  new byte[(int) entry.getSize()];
            		dataStream.read(b);
            		return b;
            	}
			} catch (IOException e1) {
				Log.log(Level.SEVERE, e1.getMessage());
			}
        }
		return null;		
	}

	/**
	 * Возвращает тело класса по имени аннотации
	 * @param annotationName Имя аннотации
	 * @return Байт код класса или null если класса нет
	 */		
	
	public byte[] getBodyClassByNameAnnoteted(String annotationName) {
        for ( JarEntry entry : jarEntryList) {
            try {
            	if (checkClassFileOnAnnoteted(new DataInputStream(jarFile.getInputStream(entry)), annotationName)) {
            		DataInputStream dataStream = new DataInputStream(jarFile.getInputStream(entry));
            		byte[] b =  new byte[(int) entry.getSize()];
            		dataStream.read(b);
            		return b;
            	}
			} catch (IOException e1) {
				Log.log(Level.SEVERE, e1.getMessage());
			}
        }
		return null;		
	}	
	
	private String getClassNameFromFilePath(String filePath) {
		return filePath.replace("/", ".").substring(0,filePath.length() - 6);
	}

	private void loadEntityList() {
        for ( Enumeration<JarEntry> e = jarFile.entries(); e.hasMoreElements(); ) {
            JarEntry entry = e.nextElement();
            if (( !entry.isDirectory() )&&(entry.getName().endsWith(".class"))) {
            	jarEntryList.add(entry);
            }
        }
	}
	
	private boolean loadJarFile(String jarFileName) {
		if ( jarFile != null ) {
			Log.log(Level.INFO, res.getString("FILE_ALREADY_LOAD"));
			return true;
		}
		if (canOpenFile(jarFileName)) {
			try {
				jarFile = new JarFile( jarFileName );
				return true;
			} catch (IOException e) {
				Log.log(Level.SEVERE, e.getMessage());
			}
		}
		return false;
	}
	
	private boolean canOpenFile(String jarFileName) {
		File jarFile = new File(jarFileName);
		if (jarFile.isFile() && jarFile.exists() && jarFile.canRead()) {
			return true;
		} else {
			if (!jarFile.isFile()) Log.log(Level.SEVERE, res.getString("OBJECT_IS_NOT_FILE") + " " + jarFileName);
			if (!jarFile.exists()) Log.log(Level.SEVERE, res.getString("FILE_DOESNT_EXIST") + " " + jarFileName);
			if (!jarFile.canRead()) Log.log(Level.SEVERE, res.getString("CANNOT_READ_FILE") + " " + jarFileName);
		}
		return false;
	}

	private boolean checkClassFileOnAnnoteted(DataInputStream dataInputStream, String annotationName) {
		try {
			ClassFile classFile = new ClassFile(dataInputStream);
			AnnotationsAttribute annotationsAttribute = (AnnotationsAttribute) classFile.getAttribute(AnnotationsAttribute.visibleTag);
			if (annotationsAttribute == null) {
				Log.log(Level.INFO, res.getString("FILE_DOESNT_EXIST_ANNOTETED_CLASS") + ": " + classFile.getName());
				return false; 
			}
			for (javassist.bytecode.annotation.Annotation ann : annotationsAttribute.getAnnotations()) {
				if (ann.getTypeName().compareTo(annotationName) == 0) {
					return true;
				}
			}
		} catch (IOException e) {
			Log.log(Level.SEVERE, e.getMessage());
		}
		return false;
	}

	
}
