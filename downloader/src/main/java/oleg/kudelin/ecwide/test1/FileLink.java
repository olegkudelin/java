/*
* @(#)FileLink
*
* @version 1.0 16.05.2014
*
* Copyright notice
*/

package oleg.kudelin.ecwide.test1;

import java.util.Objects;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Файл-ссылка
 * Содержит имя файла, путь по которому он бах сохранен,
 * http-ссылку на него, а также текущий статус (скачивается, ожидает скачивания, 
 * была ошибка или завершен)
 * @author Куделин Олег
 */
public class FileLink {
    private final String url;
    private final String fullName;
    private final String realName;
    private FileLinkStatus currentStatus;
    private static final Logger Log = Logger.getLogger(FileLink.class.getName());
    private static final ResourceBundle res = ResourceBundle.getBundle("locale");
    
    /**
     * Создает файл-ссылку
     * @param url - http ссылка на файл
     * @param name - имя файла с путем под которым был сохранен файл
     * @param realName  - желаемое имя файла. Может не совпадать с тем, под которым сохранен
     */
    public FileLink(String url, String name, String realName) {
        if (url != null) {
            this.url = url;
        } else {
            this.url = "";
            Log.log(Level.WARNING, res.getString("INPUT_STRING_IS_EMPTY"));
        }
        if (name != null) {
            this.fullName = name;
        } else {
            this.fullName = "";
            Log.log(Level.WARNING, res.getString("INPUT_STRING_IS_EMPTY"));
        }
        if (realName != null) {
            this.realName = realName;
        } else {
            this.realName = "";
            Log.log(Level.WARNING, res.getString("INPUT_STRING_IS_EMPTY"));
        }        
        currentStatus = FileLinkStatus.WAIT; 
    }

    /**
     * Возвращает http ссылку на файл
     * @return http ссылка
     */
    public String getUrl() {
        return url;
    }

    /**
     * Возвращает полное имя файла
     * @return имя
     */
    public String getName() {
        return fullName;
    }
    
    /**
     * Возвращает имя файла. Может не совпадать с тем, под которым сохранен
     * @return имя
     */
    public String getRealName() {
        return realName;
    }   
    
    /**
     * Устанавливает текущий статус
     * @param currentStatus статус
     */
    public synchronized void setStatus(FileLinkStatus currentStatus) {
        this.currentStatus = currentStatus;
    } 
    
    /**
     * Возвращает текущий статус
     * @return стату
     */
     public synchronized FileLinkStatus getStatus() {
        return currentStatus;
    }
     
    @Override
    public int hashCode() {
        int hash = 5;
        hash = 23 * hash + Objects.hashCode(this.url);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final FileLink other = (FileLink) obj;
        return Objects.equals(this.url, other.url);
    }
    
    @Override
    public String toString() {
        return url + " " + fullName;
    }
    
}
