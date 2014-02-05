package stocks;

/**
 * Created with IntelliJ IDEA.
 * User: aakritprasad
 * Date: 2/5/14
 * Time: 6:14 AM
 * To change this template use File | Settings | File Templates.
 */
public class StockHistory
{
    private int recordId, volume;
    private double dayOpen, dayHigh, dayLow, dayClose;
    private String stockSymbol, recordDate;

    public StockHistory(){}
    public StockHistory(String sym, String date, double open, double high, double low, double close, int vol){
        stockSymbol = sym;
        recordDate = date;
        dayOpen = open;
        dayHigh = high;
        dayLow = low;
        dayClose = close;
        volume = vol;
    }
    public int getRecordId() {
        return recordId;
    }

    public void setRecordId(int recordId) {
        this.recordId = recordId;
    }

    public int getVolume() {
        return volume;
    }

    public void setVolume(int volume) {
        this.volume = volume;
    }

    public double getDayOpen() {
        return dayOpen;
    }

    public void setDayOpen(double dayOpen) {
        this.dayOpen = dayOpen;
    }
    public String getStockSymbol() {
        return stockSymbol;
    }

    public void setStockSymbol(String stockSymbol) {
        this.stockSymbol = stockSymbol;
    }
    public double getDayHigh() {
        return dayHigh;
    }

    public void setDayHigh(double dayHigh) {
        this.dayHigh = dayHigh;
    }

    public double getDayLow() {
        return dayLow;
    }

    public void setDayLow(double dayLow) {
        this.dayLow = dayLow;
    }

    public double getDayClose() {
        return dayClose;
    }

    public void setDayClose(double dayClose) {
        this.dayClose = dayClose;
    }

    public String getRecordDate() {
        return recordDate;
    }

    public void setRecordDate(String recordDate) {
        this.recordDate = recordDate;
    }


}
