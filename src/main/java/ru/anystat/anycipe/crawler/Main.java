package ru.anystat.anycipe.crawler;

/**
 * Created by Dred on 07.11.2016.
 */
public class Main {


    public static void main(String[] args) {
        String listOfSites = "src\\main\\resources\\ListOfSites.txt";
        String listOfIngredients = "src\\main\\resources\\ListOfIngredients.txt";
        final String collectionName = "receipts";
        final String baseName = "anycipe_crawler";

        MongoConnector mongoConnector = new MongoConnector();
        PrepareReceipt prepareReciept = new PrepareReceipt();
        GetIngredientsFromTheSite site = new GetIngredientsFromTheSite();
        Crawler spider = new Crawler();



//       site.parsing();
//        mongoConnector.connectionOpen();
//          mongoConnector.connectionClose();
//        spider.search(Filereader.read(listOfSites));

//        mongoConnector.insertReceiptToMongoDB(baseName, collectionName, prepareReciept.getListOfReceipts());
        mongoConnector.mongoConnect("anycipe_crawler"); // для удаления содержимого коллекции
//        ru.anystat.anycipe.crawler.RecursionTestCrawler recursionTestCrawler = new ru.anystat.anycipe.crawler.RecursionTestCrawler(ru.anystat.anycipe.crawler.Filereader.read(listOfSites));

    }


}
