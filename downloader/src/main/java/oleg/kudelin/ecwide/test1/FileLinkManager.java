/*
* @(#)OutputFileManager
*
* @version 1.0 16.05.2014
*
* Copyright notice
*/
package oleg.kudelin.ecwide.test1;

/**
 * Управляет файлами-ссылками. 
 * @author Куделин Олег
 */
public interface FileLinkManager {
    /**
     * Выдает FileLink для скачивания
     * @return FileLink или null если нечего скачивать
     */
    public FileLink getFileLink();
}
