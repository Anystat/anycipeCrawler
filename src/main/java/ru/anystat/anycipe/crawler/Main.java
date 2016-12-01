package ru.anystat.anycipe.crawler;

/**
 * Created by Lofv on 07.11.2016.
 */
public class Main {


    public static void main(String[] args) {
        String listOfSites = "src\\main\\resources\\ListOfSites.txt";
        String listOfIngredients = "src\\main\\resources\\ListOfIngredients.txt";
        final String collectionName = "receipts";
        final String baseName = "anycipe_crawler";

        MongoConnector mongoConnector = new MongoConnector();
        PrepareReceipt prepareReciept = new PrepareReceipt();
        GetIngredientsFromTheSite getIngredients = new GetIngredientsFromTheSite();
        Crawler spider = new Crawler();
        Filereader filereader = new Filereader(listOfSites);
        ParsingSay7Info parsingSay7Info=new ParsingSay7Info();


//       getIngredients.parsing();

        spider.search(filereader.getList());

//        mongoConnector.insertReceiptToMongoDB(baseName, collectionName, prepareReciept.getListOfRecipes());
        mongoConnector.insertReceiptToMongoDB(baseName, collectionName, parsingSay7Info.getListOfRecipes());
//        mongoConnector.mongoConnect("anycipe_crawler"); // для удаления содержимого коллекции
//       RecursionCrawler recursionTestCrawler = new RecursionCrawler(filereader.getList());

    }


}
