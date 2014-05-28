/*
* @(#)FileLinkManager
*
* @version 1.0 16.05.2014
*
* Copyright notice
*/
package oleg.kudelin.ecwide.test1;

/**
 * Достает и возвращает файлы-списки из внешних источников
 * @author Куделин Олег
 */
public interface FileLinkSource {
    
    /**
     * Возвращает файл-список
     * @return файл список или null если доступных больше нет
     */
    public FileLink getNextFileLink();

}
