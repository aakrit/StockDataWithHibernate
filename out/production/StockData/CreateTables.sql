CREATE DATABASE stockInfo;
USE stockInfo;

DROP TABLE IF EXISTS StockInfo;
CREATE TABLE IF NOT EXISTS StockInfo
(
stockId int AUTO_INCREMENT NOT NULL PRIMARY KEY,
stockSymbol varchar(6) NOT NULL,
stockName varchar(30),
) Engine=InnoDB;

DROP TABLE IF EXISTS StockHistory;
CREATE TABLE IF NOT EXISTS StockHistory
(
recordId int AUTO_INCREMENT NOT NULL PRIMARY KEY,
stockId int NOT NULL,
dayHigh int NOT NULL,
dayLow int NOT NULL,
dayClose int NOT NULL,
volume int,
recordDate date,
recordCreateTime TIMESTAMP,
INDEX (stockId),
FOREIGN KEY (stockId) REFERENCES StockInfo(stockId)
)Engine=InnoDB;





