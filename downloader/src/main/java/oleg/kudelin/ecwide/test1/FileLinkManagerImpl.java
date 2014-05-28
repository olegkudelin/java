/*
* @(#)OutputFileManagerImpl
*
* @version 1.0 16.05.2014
*
* Copyright notice
*/

package oleg.kudelin.ecwide.test1;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ResourceBundle;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Управляет файлами-ссылками. Выдает FileLink, которые еще не скачивались,
 * В случае, если ссылка уже была скачана и сохранена под другим именем файла, 
 * производит его копирование под новым именем. 
 * Если такая же ссылка в процессе скачивания, 
 * оставляет FileLink в список ожидания
 * @author Куделин Олег
 */
public class FileLinkManagerImpl implements FileLinkManager {


    private final ConcurrentHashMap<String, FileLink> downloadedLinkList = new ConcurrentHashMap<>();
    private final ConcurrentLinkedQueue<FileLink> waitForDownloadedLinkList = new ConcurrentLinkedQueue<>();
    private final ConcurrentLinkedQueue<FileLink> errorForDownloadedLinkList = new ConcurrentLinkedQueue<>();
    private FileLinkSource fileLinkSource;
    private static final Logger log = Logger.getLogger(FileLink.class.getName());
    private static final ResourceBundle res = ResourceBundle.getBundle("locale");    
    
    /**
     * Устанавливает источник FileLink
     * @param fileLinkSource источник FileLink
     */
    public void setFileLinkManager(FileLinkSource fileLinkSource) {
        if (fileLinkSource == null) {
            log.log(Level.WARNING, res.getString("INPUT_EMPTY_POINTER"));
        } 
        this.fileLinkSource = fileLinkSource;
    }
    
    @Override
    public FileLink getFileLink() {
        if (fileLinkSource == null) {
            log.log(Level.WARNING, res.getString("INPUT_EMPTY_POINTER"));
            return null;
        }
        
        FileLink fileLink = getFileLinkFromSource();
        return fileLink != null ? fileLink : getFileLinkFromWaiteList();
    }
    
    private FileLink getFileLinkFromSource() {
        FileLink linkAndFileNameString  = fileLinkSource.getNextFileLink();
        while (linkAndFileNameString != null) {
            
            FileLink fileLink = downloadedLinkList.putIfAbsent(linkAndFileNameString.getUrl(), linkAndFileNameString);
           
            if (fileLink == null) {
                return linkAndFileNameString;
            }
            
            if (fileLink.getRealName().compareTo(linkAndFileNameString.getRealName()) != 0) {
                switch (fileLink.getStatus()) {
                    case FINISH: {
                        copyFile(fileLink.getName(), linkAndFileNameString.getName());
                        break;
                    }
                    case ERORR: {
                        if (downloadedLinkList.replace(fileLink.getUrl(), fileLink, linkAndFileNameString)) {
                            errorForDownloadedLinkList.offer(fileLink);
                            return linkAndFileNameString;
                        } else {
                            continue;
                        }
                    }
                    case IN_PROGRESS:
                    case WAIT: {
                        waitForDownloadedLinkList.offer(linkAndFileNameString);
                        break;
                    }
                    
                }
            } else {
                deleteUnusedFile(linkAndFileNameString.getName());
            }
            linkAndFileNameString  = fileLinkSource.getNextFileLink();
        }
        return null;
    }
    
    private FileLink getFileLinkFromWaiteList() {
        int i = 100;
        while ((!waitForDownloadedLinkList.isEmpty())&&(i!=0)) {
            i--;
            FileLink waitFileLink = waitForDownloadedLinkList.poll();
            FileLink downloadedFileLink = downloadedLinkList.putIfAbsent(waitFileLink.getUrl(), waitFileLink);
            if (downloadedFileLink == null) {
                    return waitFileLink;
            }
            switch (downloadedFileLink.getStatus()) {
                    case FINISH: {
                        copyFile(downloadedFileLink.getName(), waitFileLink.getName());
                        break;
                    }
                    case ERORR: {
                        if (downloadedLinkList.replace(downloadedFileLink.getUrl(), downloadedFileLink, waitFileLink)) {
                            errorForDownloadedLinkList.offer(downloadedFileLink);
                            return waitFileLink;
                        } else {
                            continue;
                        }
                    }
                    case IN_PROGRESS:
                    case WAIT: {
                        waitForDownloadedLinkList.offer(waitFileLink);
                        break;
                    }
                }
        }
        return null;
    }
    
    private void copyFile(String source, String destination){
        try {
            Path sourcePath = Paths.get(source);
            Path destinationPath = Paths.get(destination);
            Files.copy(sourcePath, destinationPath, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException ex) {
            log.log(Level.SEVERE, ex.toString());
        }

    }
    
    private void deleteUnusedFile(String fileName) {
        try {
            Files.deleteIfExists(Paths.get(fileName));
        } catch (IOException ex) {
            log.log(Level.SEVERE, ex.toString());
        }
    }
    

}
