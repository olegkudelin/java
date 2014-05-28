/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package oleg.kudelin.ecwide.test1;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

/**
 *
 * @author oleg
 */
public class FileLinkManagerImplTest {
        private FileLinkSourceFromFile fileLinkManagerImpl;
        OutputFileNames outputFileNames;

    @DataProvider
    public Object[][] fileLinksData() {
        return new Object[][]{
            {"http://www.ibm.com/developerworks/data/library/cognos/infrastructure/cognos_specific/page520-pdf.pdf", "page520-pdf.pdf"},
            {"http://www.ibm.com/developerworks/data/library/cognos/infrastructure/cognos_specific/page520_images/page520_figure1.gif", "page520_figure1.gif"},
            {"http://www.ibm.com/developerworks/data/library/cognos/infrastructure/cognos_specific/page520_images/page520_figure2.gif", "page520_figure2.gif"},
            {"http://www.ibm.com/developerworks/data/library/cognos/infrastructure/cognos_specific/page520_images/page520_figure3.gif", "page520_figure3.gif"},
            {"http://www.ibm.com/developerworks/data/library/cognos/infrastructure/cognos_specific/page520_images/page520_figure4.gif", "page520_figure4.gif"},
            {"http://www.ibm.com/developerworks/data/library/cognos/infrastructure/cognos_specific/page520_images/page520_figure5.gif", "page520_figure5.gif"},
            {"http://www.ibm.com/developerworks/data/library/cognos/infrastructure/cognos_specific/page520_images/page520_figure6.gif", "page520_figure6.gif"},
            {"http://www.ibm.com/developerworks/data/library/cognos/infrastructure/cognos_specific/page520_images/page520_figure7.gif", "page520_figure7.gif"},
            {"http://www.ibm.com/developerworks/data/library/cognos/infrastructure/cognos_specific/page520_images/page520_figure8.gif", "page520_figure8.gif"},
            {"http://www.ibm.com/developerworks/data/library/cognos/infrastructure/cognos_specific/page520_images/page520_figure9.gif", "page520_figure9.gif"}
        };
    };
    
    @BeforeClass
    public void beforeClass() {
        outputFileNames = new OutputFileNames();
        String dirName = System.getProperty("java.io.tmpdir") + System.getProperty("file.separator") + "downloader";
                try {
            Files.createDirectories(FileSystems.getDefault().getPath(dirName));
        } catch (IOException ex) {
            Logger.getLogger(OutputFileNamesTest.class.getName()).log(Level.SEVERE, null, ex);
        }
        outputFileNames.setOutputDirectory(dirName);
        fileLinkManagerImpl = new FileLinkSourceFromFile(outputFileNames);

    }
 
    @Test()
    public void setFileWithLink() {
        InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream("links.txt");
        Assert.assertNotNull(inputStream);
        boolean res = fileLinkManagerImpl.setFileWithFileLinks(inputStream);
        Assert.assertTrue(res);
    }
    
    
    @Test(dataProvider = "fileLinksData", dependsOnMethods = "setFileWithLink")
    public void getFileLink(String link, String fileName) {
        FileLink fileLink = fileLinkManagerImpl.getNextFileLink();
        Assert.assertEquals(fileLink.getUrl(), link);
        Assert.assertEquals(fileLink.getRealName(), fileName);
        Assert.assertEquals(fileLink.getName(), outputFileNames.getDirPath() + System.getProperty("file.separator") + fileName);
    }
    
    @AfterClass
    public void deleteDirectory() {
        deleteDir(new File(outputFileNames.getDirPath()));
    }

    public boolean deleteDir(File dir) {
        if (dir.isDirectory()) {
            String[] childrenList = dir.list();
            for (String children : childrenList) {
                if (!deleteDir(new File(dir, children))) {
                    return false;
                }
            }
        }
        return dir.delete();
    }
}
