CREATE DATABASE stockInfo;
USE stockInfo;

DROP TABLE IF EXISTS StockHistory;
DROP TABLE IF EXISTS StockList;

CREATE TABLE IF NOT EXISTS StockList
(
stockSymbol varchar(10) NOT NULL,
stockName varchar(30),
PRIMARY KEY (stockSymbol)
) Engine=InnoDB;

CREATE TABLE IF NOT EXISTS StockHistory
(
recordId int AUTO_INCREMENT NOT NULL PRIMARY KEY,
stockSymbol varchar(10) NOT NULL,
recordDate varchar(20),
dayOpen decimal(8,2) NOT NULL,
dayHigh decimal(8,2) NOT NULL,
dayLow decimal(8,2) NOT NULL,
dayClose decimal(8,2) NOT NULL,
volume int,
recordCreateTime TIMESTAMP,
INDEX (stockSymbol),
FOREIGN KEY (stockSymbol) REFERENCES StockList(stockSymbol)
)Engine=InnoDB;




