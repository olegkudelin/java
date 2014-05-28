/*
* @(#)OutputFileNames
*
* @version 1.0 16.05.2014
*
* Copyright notice
*/


package oleg.kudelin.ecwide.test1;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Формирует выходные файлы для записи
 * Для работы необходимо передать каталог, в котором будут создаваться файлы
 * Объект получает имя файла, проверяет есть ли такой в каталоге,
 * если есть то, прибавляя числовой индекс к названию, ищет несуществующий.
 * После этого создает пустой файл и возвращет его название
 * 
 * @author Куделин Олег
 */
public class OutputFileNames {

    private Path directoryPath;
    private static final Logger log = Logger.getLogger(OutputFileNames.class.getName());
    private static final ResourceBundle res = ResourceBundle.getBundle("locale");

    /**
    * Устанавливает каталог для записи
    * @param name полный путь к каталогу
    * @return true, если каталог существует и доступен для записи и чтения
    *         false иначе
    */
    public boolean setOutputDirectory(String name) {
        if (name == null) {
            log.log(Level.WARNING, res.getString("INPUT_STRING_IS_EMPTY"));
            return false;
        }

        Path pathOutputFiles;
        try {
            pathOutputFiles = Paths.get(name);
        } catch (InvalidPathException ex) {
            log.log(Level.SEVERE, res.getString(name + ":/n" + ex.toString()));
            return false;
        }
        
        if (!checkDirPath(pathOutputFiles)) {
            return false;
        }
        
        synchronized(this) {
            directoryPath = pathOutputFiles;
        }
        return true;
    }

    /**
     * Возвращает имя файла с полным путем
     * @param fileName Имя файла без пути
     * @return Путь и имя файла
     */
    public Path getFileName(String fileName) {
        if (fileName == null) {
            log.log(Level.SEVERE, res.getString("INPUT_EMPTY_POINTER"));
            return null;
        }
        Matcher matcher = Pattern.compile("(^.*)\\.([^.]*)$").matcher(fileName.trim());

        String fileNameWithoutExtention = "";
        String fileNameExtention = "";
        
        while (matcher.find()) {
            if (matcher.group(1) != null) {
                fileNameWithoutExtention = matcher.group(1);
            }
            if (matcher.group(2) != null) {
                fileNameExtention = matcher.group(2);
            }
            
        }
        return getNotExistFileName(fileNameWithoutExtention, fileNameExtention);

    }
    
    /**
     * Возвращает текущий каталог каталог
     * @return каталог
     */
    public String getDirPath() {
        if (directoryPath != null) {
            return directoryPath.toString();
        } else {
            log.log(Level.SEVERE, res.getString("INPUT_EMPTY_POINTER"));
            return null;
        }
    }
    
    private synchronized Path getNotExistFileName(String fileNameWithoutExtention, String extention) {

        Path pathFileLinkList = Paths.get(directoryPath.toString(), fileNameWithoutExtention + "." + extention);
        int i = 1;
        while (Files.exists(pathFileLinkList)) {
            pathFileLinkList = Paths.get(directoryPath.toString(), fileNameWithoutExtention + i + "." + extention);
            i++;
        }
        try {
            Files.createFile(pathFileLinkList);
            return pathFileLinkList;
        } catch (IOException ex) {
                log.log(Level.SEVERE, null, ex.toString());
        }
        return null;    
    }
    
    private boolean checkDirPath(Path path) {
        if (!Files.isDirectory(path)) {
            log.log(Level.SEVERE, res.getString("DIRECTORY_DOESNT_EXIST") + ": " + path);
            return false;
        }
        if (!Files.isReadable(path)) {
            log.log(Level.SEVERE, res.getString("CANNOT_READ_DIRECTORY") + ": " + path);
            return false;
        }
        if (!Files.isWritable(path)) {
            log.log(Level.SEVERE, res.getString("CANNOT_WRITE_DIRECTORY") + ": " + path);
            return false;
        }
        return true;
    }
}
