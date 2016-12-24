package ru.anystat.anycipe.crawler;

/**
 * Created by Lofv on 07.11.2016.
 */
public class Main {


    public static void main(String[] args) {
        final String SITES = "src\\main\\resources\\ListOfSites.txt";
        final String INGREDIENTS = "src\\main\\resources\\ListOfIngredients.txt";
        final String collectionName = "receipts";
        final String baseName = "anycipe_crawler";
        final String URL = "85.143.221.95";

        MongoConnector mongoConnector = new MongoConnector();
        GetIngredientsFromTheSite getIngredients = new GetIngredientsFromTheSite();
        ParsingSay7Info pars=new ParsingSay7Info();
        Crawler spider = new Crawler(pars);

        Filereader filereader = new Filereader(SITES);
        spider.search(filereader.getList());

        mongoConnector.insertReceiptToMongoDB(URL,baseName,collectionName,pars.getListOfRecipes());
    }
}
