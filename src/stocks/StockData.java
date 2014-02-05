package stocks;

/**
 * Created with IntelliJ IDEA.
 * User: aakritprasad
 * Date: 2/4/14
 * Time: 8:32 PM
 * To change this template use File | Settings | File Templates.
 */
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Date;
import java.util.Scanner;
import java.util.StringTokenizer;
import java.util.ArrayList;
import java.util.List;

public class StockData{

    private static Scanner file;
    private static String[] line;
    private static double dayHigh, dayLow, dayClose;
    private static long volume;
    private static String stockSymbol, stockName;
    private static Date date;

    public static void readInputFromFile() throws IOException{
        String read;
        int lineIncrement = 0;
        boolean done = false;
        boolean matrixB = false;
        while (file.hasNextLine())
        {
            line = file.nextLine().split(",");
            stockSymbol = line[0];
            date = Date.valueOf(line[1]);
            dayHigh = Double.parseDouble(line[2]);
            dayLow = Double.parseDouble(line[3]);
            dayClose = Double.parseDouble(line[4]);
            volume = Integer.parseInt(line[5]);

            insertIntoDataBaseUsingHibernate();
        }
        file.close();
    }

    public static void insertIntoDataBaseUsingHibernate()
    {

    }

    public static void main(String[] args)
    {
        if(args.length == 0)
        {
            try//get the filename to read from and the number of threads to launch
            {
                file = new Scanner(new FileReader("input_file"));
                readInputFromFile();
            }catch (Exception e)
            {
                e.printStackTrace();
            }
        }
        if(args.length == 1)
        {
            try//get the filename to read from and the number of threads to launch
            {
                file = new Scanner(new FileReader(args[0]));
                readInputFromFile();
            }catch (Exception e)
            {
                e.printStackTrace();
            }
        }

    }

}

