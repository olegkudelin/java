/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package oleg.kudelin.ecwide.test1;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

/**
 *
 * @author oleg
 */
public class OutputFileNamesTest {
    private OutputFileNames outputFileNames;

    @DataProvider
    public Object[][] getInputData() {
        return new Object[][]{
            {"links.txt", "links.txt"},
            {"links.txt", "links1.txt"},
            {"links_2.txt ", "links_2.txt"},
            {" links_1.txt", "links_1.txt"},
            {"links_file.txt", "links_file.txt"},
            {null, null},
            {"links_/file.txt", null}            
        };
    }

    @DataProvider(parallel = true)
    public Object[][] getInputDataParallel() {
        return new Object[][]{
            {"links_links.txt", "links_links.txt"},
            {"links_linksa.txt", "links_linksa.txt"},
            {"links_linkss.txt", "links_linkss.txt"},
            {null, null},
            {"links_/file.txt", null},            
            {"links_linksw.txt", "links_linksw.txt"},
            {"links_linkse.txt", "links_linkse.txt"},
            {"links_linksr.txt", "links_linksr.txt"},
            {"links_linkst.txt", "links_linkst.txt"},
            {null, null},
            {"links_linksy.txt", "links_linksy.txt"},
            {"links_linksu.txt", "links_linksu.txt"},
            {"links_linksi.txt", "links_linksi.txt"},
            {"links_linkso.txt", "links_linkso.txt"},
            {null, null},
            {"links_linksp.txt", "links_linksp.txt"},
            {"links_linksd.txt", "links_linksd.txt"},
            {"links_linksf.txt", "links_linksf.txt"},
            {"links_linksg.txt", "links_linksg.txt"},
            {"links_linksh.txt", "links_linksh.txt"}
       };
    }    
    
    @BeforeClass
    public void beforeClass() {
        outputFileNames = new OutputFileNames();
    }

    @Test
    public void testSetDirName() {
        String dirName = System.getProperty("java.io.tmpdir") + System.getProperty("file.separator") + "downloader";
        try {
            Files.createDirectories(FileSystems.getDefault().getPath(dirName));
        } catch (IOException ex) {
            Logger.getLogger(OutputFileNamesTest.class.getName()).log(Level.SEVERE, null, ex);
        }
        boolean outResult = outputFileNames.setOutputDirectory(dirName);
        Assert.assertTrue(outResult);
//        Path result = outputFileNames.getFileName(fileName);
//        Assert.assertEquals(result.getFileName(), returnFileName);
    }    
    
    
    @Test(dataProvider = "getInputData", dependsOnMethods = "testSetDirName")
    public void testAnnotetedClass(String fileName, String returnFileName) {
        Path result = outputFileNames.getFileName(fileName);
        if (returnFileName != null) {
            Assert.assertEquals(result.toString(), outputFileNames.getDirPath() + System.getProperty("file.separator") + returnFileName);
        }   
    }

    @Test(dataProvider = "getInputDataParallel", dependsOnMethods = {"testSetDirName", "testAnnotetedClass"})
    public void testGetFileNameByThread(String fileName, String returnFileName) {
        Path result = outputFileNames.getFileName(fileName);
        if (returnFileName != null) {
            Assert.assertEquals(result.toString(), outputFileNames.getDirPath() + System.getProperty("file.separator") + returnFileName);
        }   
    }    
    
    @AfterClass
    public void deleteDirectory() {
        deleteDir(new File(outputFileNames.getDirPath()));
    }

    public boolean deleteDir(File dir) {
        if (dir.isDirectory()) {
            String[] children = dir.list();
            for (int i = 0; i < children.length; i++) {
                if (!deleteDir(new File(dir, children[i]))) {
                    return false;
                }
            }
        }
        return dir.delete();
    }
}
