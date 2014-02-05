CREATE DATABASE stockInfo;
USE stockInfo;

DROP TABLE IF EXISTS StockList;
CREATE TABLE IF NOT EXISTS StockList
(
stockId int AUTO_INCREMENT NOT NULL,
stockSymbol varchar(6) NOT NULL,
stockName varchar(30),
PRIMARY KEY (stockId)
) Engine=InnoDB;

DROP TABLE IF EXISTS StockHistory;
CREATE TABLE IF NOT EXISTS StockHistory
(
recordId int AUTO_INCREMENT NOT NULL PRIMARY KEY,
stockIdValue int NOT NULL,
dayHigh int NOT NULL,
dayLow int NOT NULL,
dayClose int NOT NULL,
volume int,
recordDate varchar(20),
recordCreateTime TIMESTAMP,
INDEX (stockIdValue),
FOREIGN KEY (stockIdValue) REFERENCES StockList(stockId)
)Engine=InnoDB;





