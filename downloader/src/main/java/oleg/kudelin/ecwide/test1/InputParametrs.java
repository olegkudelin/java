/*
* @(#)InputParametrs
*
* @version 1.0 16.05.2014
*
* Copyright notice
*/

package oleg.kudelin.ecwide.test1;

import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Содержит входные параметры, переданные программе
 * Ожидает строковый массив вида:
 *         -n
 *         <threads count>
 *         -l
 *         <speed[b]|[k]|[m]> 
 *         -o <output folder> 
 *         -f 
 *         <file with links>
 * @author Куделин Олег
 */
public class InputParametrs {

    private int numberThreads;
    private double maxSpeed; //byte/msec
    private String inputFileNameWithLinks;
    private String outputDirectory;
    
    private static final Logger log = Logger.getLogger(InputParametrs.class.getName());
    private static final ResourceBundle res = ResourceBundle.getBundle("locale");           

    /**
     * Возвращает число потоков
     * @return число потоков
     */
    public int getNumberThreads() {
        return numberThreads;
    }

    /**
     * Возвращает максимальную скорость скачивания
     * @return максимальная скорость
     */
    public double getMaxSpeed() {
        return maxSpeed;
    }

    /**
     * Возвращает имя файла, содержащего список со ссылками и именами файлов
     * @return имя файла
     */
    public String getInputFileNameWithLinks() {
        return inputFileNameWithLinks;
    }

    /**
     * Возвращает каталог для сохранения скачаных файлов
     * @return каталог
     */
    public String getOutputDirectory() {
        return outputDirectory;
    }

    /**
     * Разбирает и устанавливает переданные на вход программы параметры
     * @param args[] массив входных параметров 
     * @return true если переданы все параметры и они корректны,
     *         false иначе
     */
    public boolean setParametrs(String[] args) {
        if (args.length != 8) {
            return false;
        }
        int result = 0;
        for (int i = 0; i < 4; i++) {
            if ((args[i*2] != null)&&(args[i*2].contains("-n"))) {
                if (!setNumberThreads(args[i*2 + 1])) {
                    return false;
                }
                result += 1;
            }
            if ((args[i*2] != null)&&(args[i*2].contains("-l"))) {
                if (!setMaxSpeed(args[i*2 + 1])) {
                    return false;
                }
                result += 10;
            }
            if ((args[i*2] != null)&&(args[i*2].contains("-o"))) {
                outputDirectory = args[i*2 + 1];
                result += 100;
            }
            if ((args[i*2] != null)&&(args[i*2].contains("-f"))) {
                inputFileNameWithLinks = args[i*2 + 1];
                result += 1000;
            }
        }
        return result == 1111;
    }    
    
    private boolean setNumberThreads(String number) {
        try {
            numberThreads = Math.abs(Integer.parseInt(number));
            if (numberThreads < 1) {
                numberThreads = 1;
            }
            return true;
        } catch (NumberFormatException ex) {
            log.log(Level.SEVERE, res.getString("PARAMETR_MUST_BE_THE_NUMBER") + ": number threads " + number);
            return false;
        }
    }

    private boolean setMaxSpeed(String number) {
        if ((number == null)||(number.length() < 2)) {
            log.log(Level.SEVERE, "{0}: speed ", res.getString("INPUT_STRING_IS_EMPTY"));
            return false;
        }
        Character suffix = number.charAt(number.length() - 1);
        try {
            maxSpeed = Math.abs(Integer.parseInt(number.substring(0, number.length() - 1)));//(sec)
            maxSpeed = maxSpeed * getMultiplex(suffix)/1000;//msec
            return true;
        } catch (NumberFormatException ex) {
            log.log(Level.SEVERE, res.getString("PARAMETR_MUST_BE_THE_NUMBER") + ": maxspeed " + number);
            return false;
        }
    }    
    
    
    private static int getMultiplex(Character suffix) {
        if (suffix.compareTo('k') == 0) {
            return 1024;
        } else
        if (suffix.compareTo('b') == 0) {
            return 1;
        } else
            if (suffix.compareTo('m') == 0) {
            return 1024 * 1024;
        } else {
            return 1;
        }
    }    
}
