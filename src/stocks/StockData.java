package stocks;

/**
 * Created with IntelliJ IDEA.
 * User: aakritprasad
 * Date: 2/4/14
 * Time: 8:32 PM
 * To change this template use File | Settings | File Templates.
 */
import java.io.*;
import java.util.Calendar;
import java.util.Scanner;
import java.lang.*;
//import java.util.

public class StockData{

    private static Scanner file;
    private static String[] line;
    private static double dayOpen, dayHigh, dayLow, dayClose;
    private static long volume;
    private static String stockSymbol, stockName;
    private static String date;
    private static boolean DatabaseConfig = false;

    public static void readInputFromFile() throws IOException, InterruptedException{
        String read;
        int lineIncrement = 0;
        boolean done = false;
        boolean matrixB = false;
        int numRecords = 0;
        while (file.hasNextLine())
        {
            int i = 0;
            line = file.nextLine().split(",");
            printValues();

            stockSymbol = line[i++];
            date = line[i++];
            dayOpen = Double.parseDouble(line[i++]);
            dayHigh = Double.parseDouble(line[i++]);
            dayLow = Double.parseDouble(line[i++]);
            dayClose = Double.parseDouble(line[i++]);
            volume = Long.parseLong(line[i++]);
            insertIntoDataBaseUsingHibernate();
            numRecords++;
        }
        file.close();
        System.out.println("Inserted "+numRecords+" records in database");
    }

    public static void insertIntoDataBaseUsingHibernate()
    {
         //using JDBC
         if(!DatabaseConfig){

         }
    }
    public static void printValues() throws InterruptedException
    {
        for (String s: line) System.out.println(s);

        Thread.sleep(5000);
    }

    public static void main(String[] args)
    {
        if(args.length == 0)
        {
            try//get the filename to read from and the number of threads to launch
            {
                file = new Scanner(new FileReader("/Users/aakritprasad/IdeaProjects/StockData/src/StockEndOfDayData.csv"));
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

