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
import java.io.InterruptedIOException;
import java.util.List;
import java.util.Scanner;
import java.util.Iterator;

import org.hibernate.*;
import org.hibernate.cfg.Configuration;

public class StockData{

    private static SessionFactory factory;
    private static StockData sd;
    private static Scanner file, file2;
    private static String[] line;
    private static double dayOpen, dayHigh, dayLow, dayClose;
    private static int volume;
    private static String stockSymbol, stockName;
    private static String date;

    private static int stockSymbolRecords = 0;

    public static void readInputFromStockSymbols() throws IOException, InterruptedException {
        boolean firstRead = true;
        while(file2.hasNextLine()){
            line = file2.nextLine().split(",");
            if(firstRead){
                firstRead = false;
                continue;
            }
            stockSymbol = line[0];
            stockName = line[1];
            sd.addStock(stockSymbol, stockName);
//            printValues(line);
        }
        System.out.println("Total new stocks added: " + stockSymbolRecords);
    }
    public static void readInputFromHistoryFile() throws IOException, InterruptedException{
        int numRecords = 0;
        while (file.hasNextLine())
        {
            int i = 0;
            line = file.nextLine().split(",");
            stockSymbol = line[i++];
            date = line[i++];
            dayOpen = Double.parseDouble(line[i++]);
            dayHigh = Double.parseDouble(line[i++]);
            dayLow = Double.parseDouble(line[i++]);
            dayClose = Double.parseDouble(line[i++]);
            volume = Integer.parseInt(line[i++]);
            //query the stockid from the stocksymbol
            int stockid = sd.findStockId(stockSymbol);
            if(stockid == 0){
                System.out.println("Could not add because stock not in database yet: "+stockSymbol);
                continue;
            }
            sd.addStockHistory(stockid, date, dayOpen, dayHigh, dayLow, dayClose, volume);
            numRecords++;

        }
        file.close();
        System.out.println("Inserted "+numRecords+" stock daily data records.");
    }
    public int findStockId(String stockSymbol){
        Session session = factory.openSession();
        int stockIdVal = 0;
        try{
            List ret = session.createSQLQuery("SELECT stockid FROM stocklist WHERE stockSymbol=\""+stockSymbol+"\"").list();
            Iterator e = ret.iterator();
            if(e.hasNext() == false) {
                return stockIdVal;
            }
            stockIdVal = (Integer) e.next();

        } catch (HibernateException e){
            e.printStackTrace();
        } finally {
            session.close();
        }
        return stockIdVal;
    }
    public void addStock(String stockSymbol, String stockName) throws InterruptedException{
        //check to see if the stock symbol exists in the database table
        if(sd.doesStockSymbolExists(stockSymbol)) return;
        //if it doesn't then add it
        System.out.println("New Stock added: "+ stockSymbol + ", "+ stockName);
        Session session = factory.openSession();
        Transaction trans = null;
        try{
//            trans = session.beginTransaction();
            StockList stock = new StockList(stockSymbol, stockName);
            session.save(stock);
//            trans.commit();
        }catch (HibernateException e) {
//            if (trans!=null) trans.rollback();
            e.printStackTrace();
        }finally {
            stockSymbolRecords++;
            session.close();
        }
    }
    public boolean doesStockSymbolExists(String stockSymbol) throws InterruptedException{
        Session session = factory.openSession();
        try{
            String exist = "SELECT stockSymbol FROM stocklist WHERE stockSymbol=\""+stockSymbol+"\"";
            List ret = session.createSQLQuery(exist).list();
            Iterator e = ret.iterator();
            if(e.hasNext() == false){
                return false;    //symbol NOT found
            }
        }catch (HibernateException e) {
            e.printStackTrace();
        }finally {
            session.close();
        }
//        System.out.println("Stock Found: "+stockSymbol);
        return true;
    }
    public void addStockHistory(int stockId, String date, double open, double high,
                                double low, double close, int vol){
        Session session = factory.openSession();
        Transaction trans = null;
        try{
            trans = session.beginTransaction();
            StockHistory stock = new StockHistory(stockId, date, open, high, low, close, vol);
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
            System.out.println("Current stocks in the database");
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
    public static void printValues(String[] l) throws InterruptedException
    {
        for (String s: l) System.out.println(s);

//        Thread.sleep(1000);
    }

    public static void main(String[] args)
    {
        sd = new StockData();
        long startTime = System.currentTimeMillis();
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
                file2 = new Scanner(new FileReader("/Users/aakritprasad/IdeaProjects/StockData/src/NYSE.csv"));
                readInputFromStockSymbols();
                readInputFromHistoryFile();

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
                file2 = new Scanner(new FileReader("/Users/aakritprasad/IdeaProjects/StockData/src/NYSE.csv"));
                readInputFromStockSymbols();
                readInputFromHistoryFile();
            }catch (Exception e)
            {
                e.printStackTrace();
            }
        }
        long finishTime = System.currentTimeMillis();
        System.out.println("Time taken for computation: "+((double)((finishTime-startTime)/1000))+" seconds!");
        //Type an SQL Query to search for results
        return;
    }

}

