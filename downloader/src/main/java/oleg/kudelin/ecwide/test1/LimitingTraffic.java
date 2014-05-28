/*
* @(#)LimitingTraffic
*
* @version 1.0 16.05.2014
*
* Copyright notice
*/


package oleg.kudelin.ecwide.test1;

/**
 * Интерфейс контролирующий объем скачанных данных
 * @author Куделин Олег
 */
public interface LimitingTraffic {

    /**
    * Расчитывает и возвращает доступный для скачивания размер буфера, 
    * чтобы не превысить заданную скорость
    * @return размер буфера
    */
    public long getBufferSize();
    
    /**
     * Корректирует размер доступного буфера в случае, если разрешенный 
     * объем данных был выбран не полностью
     * @param undownloadByte размер массива данных, который не удалось использовать
     */
    public void setUnusedBufferSize(long undownloadByte);
}
