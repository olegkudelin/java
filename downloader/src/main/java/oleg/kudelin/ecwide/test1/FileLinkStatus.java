/*
* @(#)FileLinkStatus
*
* @version 1.0 16.05.2014
*
* Copyright notice
*/

package oleg.kudelin.ecwide.test1;


/**
 * Статус фала-ссылки
 * 
 * @author Куделин Олег
 */
public enum FileLinkStatus {
    /**
     * Скачивание завершено, файл сохранен
     */
    FINISH,
    /**
     * В процессе скачивания
     */
    IN_PROGRESS,
    /**
     * Ожидает очереди
     */
    WAIT,
    /**
     * Была ошибка при скачивании
     */
    ERORR
}
