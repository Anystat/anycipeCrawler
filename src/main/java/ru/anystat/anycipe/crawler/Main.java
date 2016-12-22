package ru.anystat.anycipe.crawler;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
        spider.search(filereader.getList());

    }
}
