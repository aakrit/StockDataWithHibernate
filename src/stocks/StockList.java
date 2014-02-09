package stocks;

/**
 * Created with IntelliJ IDEA.
 * User: aakritprasad
 * Date: 2/5/14
 * Time: 6:10 AM
 * To change this template use File | Settings | File Templates.
 */
public class StockList
{


    private String symbol, name;
    private int stockId;

    public StockList(){}
    public StockList(String symbol, String name){
        this.symbol = symbol;
        this.name = name;
    }
    public int getStockId() {
        return stockId;
    }

    public void setStockId(int stockId) {
        this.stockId = stockId;
    }
    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


}
