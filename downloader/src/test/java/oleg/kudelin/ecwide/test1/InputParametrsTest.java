/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package oleg.kudelin.ecwide.test1;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
/**
 *
 * @author oleg
 */
public class InputParametrsTest {
	
    private InputParametrs inputParametrs;

    @DataProvider
    public Object[][] getInputData() {
        return new Object[][]{
            {new String[] {"-n", "5", "-l", "2000k", "-f", "links.txt", "-o", "/hhhh/:output_.folder"}, true, 5, 2048.0, "links.txt", "/hhhh/:output_.folder"},
            {new String[] {"-n", "-1", "-l", "2000b", "-f", "links.txt", "-o", "/hhhh/:output_.folder"}, true, 1, 2.0, "links.txt", "/hhhh/:output_.folder"},
            {new String[] {"-n", "0", "-l", "2000m", "-f", "links.txt", "-o", "/hhhh/:output_.folder"}, true, 1, 2097152.0, "links.txt", "/hhhh/:output_.folder"},
            {new String[] {"-n", "0", "-l", "2000m", "-f", "links.txt", "-o"}, false, 1, 2097152.0, "links.txt", "/hhhh/:output_.folder"},
            {new String[] {null, "0", "-l", "2000m", "-f", "links.txt", "-o", "/hhhh/:output_.folder"}, false, 1, 2097152.0, "links.txt", "/hhhh/:output_.folder"},
            {new String[] {"-n", null, "-l", "2000m", "-f", "links.txt", "-o", "/hhhh/:output_.folder"}, false, 1, 2097152.0, "links.txt", "/hhhh/:output_.folder"}

        };
    }
    
    @BeforeClass
    public void beforeClass() {
        inputParametrs = new InputParametrs();
    }

    @Test(dataProvider = "getInputData")
    public void testAnnotetedClass(String[] args, boolean res, int numThread, double speed, String fileName, String pathName) {
        boolean result = inputParametrs.setParametrs(args);
        Assert.assertEquals(result, res);
        if (result) {
            Assert.assertEquals(inputParametrs.getNumberThreads(), numThread);
            Assert.assertEquals(inputParametrs.getMaxSpeed(), speed);
            Assert.assertEquals(inputParametrs.getInputFileNameWithLinks(), fileName);
            Assert.assertEquals(inputParametrs.getOutputDirectory(), pathName);
        }
    }	    
}
