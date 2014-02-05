package stocks;

/**
 * Created with IntelliJ IDEA.
 * User: aakritprasad
 * Date: 2/4/14
 * Time: 8:32 PM
 * To change this template use File | Settings | File Templates.
 */
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.Scanner;
import java.util.Iterator;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.Transaction;

public class StockData{

    private static SessionFactory factory;
    private static StockData sd;

    private static Scanner file;
    private static String[] line;
    private static double dayOpen, dayHigh, dayLow, dayClose;
    private static int volume;
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
            volume = Integer.parseInt(line[i++]);
            sd.addStock(stockSymbol, null);
            sd.addStockHistory(stockSymbol, date, dayOpen, dayHigh, dayLow, dayClose, volume);
            numRecords++;
        }
        file.close();
        System.out.println("Inserted "+numRecords+" records in database");
    }

    public void addStock(String stockSymbol, String stockName){
        Session session = factory.openSession();
        Transaction trans = null;
        try{
            trans = session.beginTransaction();
            StockList stock = new StockList(stockSymbol, stockName);
            session.save(stock);
            trans.commit();
        }catch (HibernateException e) {
            if (trans!=null) trans.rollback();
            e.printStackTrace();
        }finally {
            session.close();
        }
    }
    public void addStockHistory(String sym, String date, double open, double high,
                                double low, double close, int vol){
        Session session = factory.openSession();
        Transaction trans = null;
        try{
            trans = session.beginTransaction();
            StockHistory stock = new StockHistory(sym, date, open, high, low, close, vol);
            session.save(stock);
            trans.commit();
        }catch (HibernateException e) {
            if (trans!=null) trans.rollback();
            e.printStackTrace();
        }finally {
            session.close();
        }
    }
    public void listStocks(){
        Session session = factory.openSession();
        Transaction trans = null;
        try{
            trans = session.beginTransaction();
            List stocks = session.createQuery("FROM StockList").list();
            for (Iterator i = stocks.iterator(); i.hasNext();){
                StockList st = (StockList) i.next();
                System.out.println("Symbol: "+st.getSymbol()+" Name: "+ st.getName());
            }
            trans.commit();
        }catch (HibernateException e) {
            if (trans!=null) trans.rollback();
            e.printStackTrace();
        }finally {
            session.close();
        }
    }
    //list of all stocks by recoreds
    public void listStockRecords(String symbol){
        Session session = factory.openSession();
        Transaction trans = null;
        try{
            trans = session.beginTransaction();
            List stocks = session.createQuery("FROM StockHistory S WHERE S.stockSymbol = "+symbol+" ORDER BY S.recordDate ASC").list();
            for (Iterator i = stocks.iterator(); i.hasNext();){
                StockList st = (StockList) i.next();
                System.out.println("Symbol: "+st.getSymbol()+" Name: "+ st.getName());
            }
            trans.commit();
        }catch (HibernateException e) {
            if (trans!=null) trans.rollback();
            e.printStackTrace();
        }finally {
            session.close();
        }
    }
    public static void printValues() throws InterruptedException
    {
        for (String s: line) System.out.println(s);

        Thread.sleep(2000);
    }

    public static void main(String[] args)
    {
        sd = new StockData();
        try{
            Class.forName("com.mysql.jdbc.Driver");
            factory = new Configuration().configure().buildSessionFactory();
        }catch (Throwable ex) {
            System.err.println("Failed to create sessionFactory object." + ex);
            throw new ExceptionInInitializerError(ex);
        }
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

        //Type an SQL Query to search for results

    }

}

