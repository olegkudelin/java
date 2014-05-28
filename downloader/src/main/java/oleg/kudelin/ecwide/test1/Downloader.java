/*
* @(#)Downloader
*
* @version 1.0 16.05.2014
*
* Copyright notice
*/

package oleg.kudelin.ecwide.test1;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.FileChannel;
import java.nio.channels.ReadableByteChannel;

import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Скачивает файлы по HTTP протоколу и сохраняет на диск
 * в соответствии с заданным ограничением скорости, которое регулирует LimitingTraffic
 * 
 * @author Куделин Олег
 */
public class Downloader implements Runnable {

    private final LimitingTraffic limitTraffic;
    private final FileLinkManager linkManager;
    private static final Logger log = Logger.getLogger(Downloader.class.getName());
    private static final ResourceBundle res = ResourceBundle.getBundle("locale");    
    
    /**
     * Конструктор
     * @param limitTraffic ограничитель скорости скачивания
     * @param linkManager менеджер файл-ссылок
     */
    public Downloader(LimitingTraffic limitTraffic, FileLinkManager linkManager) {
        this.limitTraffic = limitTraffic;
        this.linkManager = linkManager;
    }
    
    /**
     * Запускает процесс скачивания файлов. 
     * LinkManager предоставляет доступные ссылки для скачивания. Работа продолжается до тех пор, пока 
     * LinkManager не вернет null.
     */
    @Override
    public void run() {
        if ((limitTraffic == null)||(linkManager == null)) {
            log.log(Level.SEVERE, res.getString("INPUT_EMPTY_POINTER"));
            return;
        }
        FileLink fileLink;
        while ((fileLink = linkManager.getFileLink()) != null) {
            fileLink.setStatus(FileLinkStatus.IN_PROGRESS);
            try {
                URL url = new URL(fileLink.getUrl());
                if (downloadFile(url, fileLink.getName())) {
                    fileLink.setStatus(FileLinkStatus.FINISH);
                } else {
                    fileLink.setStatus(FileLinkStatus.ERORR);
                }
            } catch (MalformedURLException ex) {
                log.log(Level.SEVERE, ex.getLocalizedMessage());
            }
            if (Thread.interrupted()) {
                break;
            }
        }
    }
    
    private boolean downloadFile(URL url, String outputFileName) {
        try (InputStream inputFile = url.openStream(); 
             FileOutputStream outputStream = new FileOutputStream(outputFileName);
             ReadableByteChannel chanels = Channels.newChannel(inputFile);
             FileChannel fileChannel = outputStream.getChannel()) {
 
            long pos = 0;
            long count;
            long bufferSize = getNotZerroBufferSize();
            while ((count = fileChannel.transferFrom(chanels, pos, bufferSize)) != 0) {
                if (Thread.interrupted()) {
                    return false;
                }
                pos += count;
                returtUnusedBuffer(count, bufferSize);
                bufferSize = getNotZerroBufferSize();
            }
            returtUnusedBuffer(count, bufferSize);
            return true;                 
            
        } catch (IOException ex) {
            log.log(Level.SEVERE, null, ex);
            return false;
        }
    }
    
    private long getNotZerroBufferSize() {
        long bufferSize;
        for (;;) {
            bufferSize = limitTraffic.getBufferSize();
            if (bufferSize == 0) {
                try {
                    Thread.sleep(100);
                } catch (InterruptedException ex) {
                    log.log(Level.SEVERE, ex.toString());
                }
            } else {
                return bufferSize;
            }
        }
    }
    
    private void returtUnusedBuffer(long readByteCount, long bufferSize) {
    if (readByteCount < bufferSize) {
        limitTraffic.setUnusedBufferSize(bufferSize - readByteCount);
        }        
    }
    
}
