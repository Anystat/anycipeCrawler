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

        MongoConnector mongoConnector = new MongoConnector();
        GetIngredientsFromTheSite getIngredients = new GetIngredientsFromTheSite();
        Crawler spider = new Crawler();
        Filereader filereader = new Filereader(SITES);
        ParsingSay7Info parsingSay7Info = new ParsingSay7Info();


        spider.search(filereader.getList());



    }


}
