/*
* @(#)FileLinkManagerImpl
*
* @version 1.0 16.05.2014
*
* Copyright notice
*/

package oleg.kudelin.ecwide.test1;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Достает и возвращает файлы-списки из внешнего файла
 * Берет строку из файла, разбирает ее, выделяя ссылку и имя файла
 * Используя имя файла полный пусть и имя файла под которым будет 
 * сохранено содержимое ссылки
 * @author Куделин Олег
 */
public class FileLinkSourceFromFile implements FileLinkSource {

    private BufferedReader fileWithLinkList;
    private final OutputFileNames outputsFileNames;
    private static final Logger log = Logger.getLogger(FileLinkSourceFromFile.class.getName());
    private static final ResourceBundle res = ResourceBundle.getBundle("locale");     
    
    /**
     * Создает FileLinkSourceFromFile
     * @param outputsFileNames формирователь полного имени файла
     */
    public FileLinkSourceFromFile(OutputFileNames outputsFileNames) {
        this.outputsFileNames = outputsFileNames;
        if (outputsFileNames == null) {
            log.log(Level.WARNING, res.getString("INPUT_STRING_IS_EMPTY"));
        }
        
    }
    
    /**
     * Устанавливает файл со списком файл-ссылка
     * @param fileName - полное имя файла
     * @return true если файл найден и открыт на чтение, иначе false
     */
    public synchronized boolean setFileWithFileLinks(String fileName) {
        
        if (fileName == null) {
            log.log(Level.WARNING, res.getString("INPUT_STRING_IS_EMPTY"));
            return false;
        }
        
        closeFileWithLinkList();
 
        Path pathFileLinkList = checkFileForOpen(fileName);
        if (pathFileLinkList == null) {
            return false;
        }
        
        try {
            fileWithLinkList = Files.newBufferedReader(pathFileLinkList, StandardCharsets.UTF_8);
            return true;
            
        } catch (IOException ex) {
            log.log(Level.SEVERE, res.getString("FILE_IS_NOT_OPEN")+ ": " + fileName  + "/n" + ex);
            return false;
        }
    }

    /**
     * Устанавливает файл со списком файл-ссылка
     * @param fileStream входной поток файла со ссылками
     * @return true если файл найден и открыт на чтение, иначе false
     */
    public synchronized boolean setFileWithFileLinks(InputStream fileStream) {
        
        if (fileStream == null) {
            log.log(Level.WARNING, res.getString("INPUT_STRING_IS_EMPTY"));
            return false;
        }
        
        closeFileWithLinkList();
 
        fileWithLinkList = new BufferedReader(new InputStreamReader(fileStream));
        return fileWithLinkList != null;
    }  
    
    @Override
    public FileLink getNextFileLink() {
        if (outputsFileNames == null) {
            log.log(Level.SEVERE, res.getString("ABSENT_OBJECT_FOR_GENERATE_FILE_NAMES"));
            return null;
        }
        
        String fileLingAndNameString;
        while ((fileLingAndNameString = getNextLineFromFile()) != null) {
            
            Matcher matcher = Pattern.compile("^(http://.+)\\s+(\\S+)$").matcher(fileLingAndNameString.trim());
            String url = null;
            String fileName = null;
            while (matcher.find()) {
                if (matcher.group(1) != null) {
                    url = matcher.group(1);
                }
                if (matcher.group(2) != null) {
                    fileName = matcher.group(2);
                }
                
            }
            if ((url != null)&&(fileName != null)) {
                Path path = outputsFileNames.getFileName(fileName);
                if (path != null) {
                    return new FileLink(url, path.toString(), fileName);
                }
            } else {
                log.log(Level.SEVERE, res.getString("BAD_FILE_FINK") + ": " + fileLingAndNameString);
            }
        }
        return null;
         
    }      
    
    /**
     * Закрывает файл с ссылками
     */
    public void close() {
        closeFileWithLinkList();
    }
    
    private synchronized String getNextLineFromFile() {
        try {
            if (fileWithLinkList != null) {
                return fileWithLinkList.readLine();
            } else {
                 log.log(Level.SEVERE, res.getString("FILE_IS_NOT_OPEN"));
            }
        } catch (IOException ex) {
            log.log(Level.SEVERE, ex.getLocalizedMessage());
        }
        return null;
    }        

    private synchronized void closeFileWithLinkList() {
        try {
            if ((fileWithLinkList != null)&&(fileWithLinkList.ready())){
                fileWithLinkList.close();
            }
        } catch (IOException ex) {
            log.log(Level.SEVERE, ex.toString());
        }       
    }    
    
    private Path checkFileForOpen(String fileName) {
        try {
            Path pathFileLinkList = FileSystems.getDefault().getPath(fileName);
            if (!Files.exists(pathFileLinkList)) {
                log.log(Level.SEVERE, res.getString("FILE_DOESNT_EXIST")+ ": " + fileName);
                return null;
            }
            if (!Files.isReadable(pathFileLinkList)) {
                log.log(Level.SEVERE, res.getString("CANNOT_READ_FILE")+ ": " + fileName);
                return null;               
            }
            if (Files.isDirectory(pathFileLinkList)) {
                log.log(Level.SEVERE, res.getString("FilePathIsInvalid")+ ": " + fileName);
                return null;               
            }            
            return pathFileLinkList;
            
        } catch (InvalidPathException ex) {
            log.log(Level.SEVERE, res.getString("INPUT_STRING_IS_EMPTY")+ ": " + fileName  + "/n" + ex.toString());
            return null;
        }            
    }   
}
