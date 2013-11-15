/*
* @(#)IClassFinder
*
* @version 1.0 15.08.2013
*
* Copyright notice
*/

package oleg.kudelin.classloader.jaranalysis;

/**
 * Интерфейс для поиска и получения байт-кода классов
 * по полному имени и по названию аннотации  
 * 
 * @author Куделин Олег
 */

public interface IClassFinder {
	public byte[] getBodyClassByNameAnnoteted(String annotationName);
	public byte[] getBodyClassByName(String className);
}
