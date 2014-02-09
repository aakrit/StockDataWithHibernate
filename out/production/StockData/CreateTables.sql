CREATE DATABASE stockInfo;
USE stockInfo;

DROP TABLE IF EXISTS StockHistory;
DROP TABLE IF EXISTS StockList;

CREATE TABLE IF NOT EXISTS StockList
(
stockId int AUTO_INCREMENT PRIMARY KEY,
stockSymbol varchar(10) NOT NULL,
stockName varchar(100)
) Engine=InnoDB;

CREATE TABLE IF NOT EXISTS StockHistory
(
recordId int AUTO_INCREMENT PRIMARY KEY,
stockId int NOT NULL,
recordDate varchar(20),
dayOpen decimal(8,2) NOT NULL,
dayHigh decimal(8,2) NOT NULL,
dayLow decimal(8,2) NOT NULL,
dayClose decimal(8,2) NOT NULL,
volume int,
recordCreateTime TIMESTAMP,
INDEX (stockId),
FOREIGN KEY (stockId) REFERENCES StockList(stockId)
)Engine=InnoDB;




