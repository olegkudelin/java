/*
* @(#)LimitingTrafficImpl
*
* @version 1.0 16.05.2014
*
* Copyright notice
*/

package oleg.kudelin.ecwide.test1;

import java.util.concurrent.atomic.AtomicLong;

/**
 * Ограничивант объем скачиваемых данных за единицу времени
 * Ограничение идет по принципу бассейна, объем которого зависит 
 * от прошедшего времени и уже скачанного объема
 * Каждый поток при необходимости забирает из него часть доступного объема данных
 * объем бассейна при этом уменьшается
 * увеличение объема в бассейне происходит с течением времени
 * Класс потокобезопасный
 * @author Куделин Олег
 */
public class LimitingTrafficImpl implements LimitingTraffic {

    private final int numberThreads;
    private final double trafficLimit;    //   байт/милисек
    private final AtomicLong fullTrafficDownload; // байт
    private final long timeBeginWork; //миллисек
    
    /**
     * Конструктор ограничителя
     * @param numberThreads Число потоков, которые будут запрашивать 
     *        доступный объем (нужен для балансировки загрузки потоков)
     * @param trafficLimit Ограничение скорости скачивания в байт/миллисек 
     */
    public LimitingTrafficImpl(int numberThreads, double trafficLimit) {
        this.numberThreads = numberThreads;
        this.trafficLimit = trafficLimit;
        timeBeginWork = System.currentTimeMillis();
        fullTrafficDownload = new AtomicLong(0);
    }
    
    @Override
    public long getBufferSize() {
        long result = 0;
        long fullTraffic = 0;
        do {            
            long durationTime = System.currentTimeMillis() - timeBeginWork;
            fullTraffic = fullTrafficDownload.get();
            long accessibleByteCount = (long) (durationTime * trafficLimit) - fullTraffic;
            result = accessibleByteCount > 0 ? accessibleByteCount / numberThreads : 0;
        } while (!fullTrafficDownload.compareAndSet(fullTraffic, fullTraffic + result));
        return result;
    }

    @Override
    public void setUnusedBufferSize(long realUnDownloadByte) {
        fullTrafficDownload.getAndAdd(-realUnDownloadByte);
    }
    
    /**
     * Возвращает выданные объем за все время работы
     * @return объем в байтах
     */
    public long getFullDownloadTraffic() {
        return fullTrafficDownload.get();
    }
    
    /**
     * Возвращает полное время работы
     * @return время в миллисекундах
     */
    public long getTimeWork() {
        return System.currentTimeMillis() - timeBeginWork;
    }

}
