/*
* @(#)OutputFileManagerBuilder
*
* @version 1.0 16.05.2014
*
* Copyright notice
*/

package oleg.kudelin.ecwide.test1;

/**
 * Конструирует и возвращает экзепляр FileLinkManager
 * @author Куделин Олег
 */
public class FileLinkManagerBuilder {
    
    private static FileLinkSourceFromFile fileLinkManager;
    private static FileLinkManagerImpl linkManager;
    
    /**
     * Создает FileLinkManager
     * @param inputParametrs InputParametrs
     * @return FileLinkManager или null если в процессе создания была ошибка
     */
    public static FileLinkManager getInstance(InputParametrs inputParametrs) {
        if (linkManager == null) {
            synchronized(FileLinkManagerBuilder.class) {
                if (linkManager == null) {
                    linkManager = createInstance(inputParametrs);
                }
            }
        }
        return linkManager;
    }
    
    /**
     * Закрывает ресурсы FileLinkSourceFromFile
     */
    public static synchronized void close() {
        fileLinkManager.close();
    }
    
    private static FileLinkManagerImpl createInstance(InputParametrs inputParametrs) {
        OutputFileNames outputFileNames = new OutputFileNames();
        if (!outputFileNames.setOutputDirectory(inputParametrs.getOutputDirectory())) {
            return null;
        };
        FileLinkSourceFromFile fileLinkManagerLocal = new FileLinkSourceFromFile(outputFileNames);
        if (!fileLinkManagerLocal.setFileWithFileLinks(inputParametrs.getInputFileNameWithLinks())) {
            return null;
        };
        fileLinkManager = fileLinkManagerLocal;
        FileLinkManagerImpl linkManagerLocal = new FileLinkManagerImpl();
        linkManagerLocal.setFileLinkManager(fileLinkManager);
        return linkManagerLocal;       
    }
}
