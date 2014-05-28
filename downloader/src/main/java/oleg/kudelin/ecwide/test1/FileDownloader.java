/*
* @(#)FileDownloader
*
* @version 1.0 16.05.2014
*
* Copyright notice
*/

package oleg.kudelin.ecwide.test1;

import java.util.Arrays;
import java.util.ResourceBundle;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FileDownloader {
    
    
    private static final Logger log = Logger.getLogger(FileDownloader.class.getName());
    private static final ResourceBundle res = ResourceBundle.getBundle("locale");        
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        InputParametrs inputParametrs = new InputParametrs();
        if (!inputParametrs.setParametrs(args)) {
            System.out.println(res.getString("PROGRAM_USAGE") + " java -jar utility.jar -n <threads count> -l <speed[b]|[k]|[m]> -o <output folder> -f <file with links>");
            System.exit(0);
        }
        LimitingTrafficImpl limitingTraffic = new LimitingTrafficImpl(inputParametrs.getNumberThreads(), inputParametrs.getMaxSpeed());
        FileLinkManager linkManager = FileLinkManagerBuilder.getInstance(inputParametrs);
        if (linkManager == null) {
            log.log(Level.SEVERE, res.getString("INPUT_EMPTY_POINTER") + "  FileLinkManager");
            System.exit(-1);
        }
        
        ExecutorService exec = Executors.newFixedThreadPool(inputParametrs.getNumberThreads());
        for (int i = 0; i < inputParametrs.getNumberThreads(); i++) {
            exec.execute(new Downloader(limitingTraffic,linkManager));
        }
        exec.shutdown();        
        try {
            exec.awaitTermination(50L, TimeUnit.HOURS);
            System.out.println("Time work: " + limitingTraffic.getTimeWork() + " ms   total download: " + limitingTraffic.getFullDownloadTraffic()  + " b");
            System.out.println("Middle speed: " + limitingTraffic.getFullDownloadTraffic()/limitingTraffic.getTimeWork() + " kb/s");
        } catch (InterruptedException ex) {
            Logger.getLogger(FileDownloader.class.getName()).log(Level.SEVERE, null, ex);
        }
        FileLinkManagerBuilder.close();
        
    }
    
    private static int getCountMatchInInputParams(String[] args) {
        Matcher matcher = Pattern.compile("-n,\\s(\\d+)|-l,\\s(\\d+[bkM])|-o,\\s(\\S[^,\\]]+)|-f,\\s(\\S[^,\\]]+)").matcher(Arrays.asList(args).toString());
        int countMatch = 0;
        while (matcher.find()) {
            countMatch++;
        }
        return countMatch;
    }
    

    

}
