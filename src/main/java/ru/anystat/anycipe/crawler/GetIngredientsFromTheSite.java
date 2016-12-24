package ru.anystat.anycipe.crawler;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Lofv on 17.11.2016.
 */
public class GetIngredientsFromTheSite {

    private final String URL = "null";
    private final String baseName = "null";
    private final String collectionName = "null";
    private final String SITE = "http://www.bazareceptov.ru/ingredients.php?page=";
    private final String USER_AGENT = "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/535.1 (KHTML, like Gecko) Chrome/13.0.782.112 Safari/535.1";
    private List fullListOfIngredients = new ArrayList();
    private MongoConnector mongoConnector = new MongoConnector();


    public void list() {

        Document doc = null;
        for (int i = 1; i < 9; i++) {

            Connection connection = Jsoup.connect(SITE + i).userAgent(USER_AGENT);
            try {
                doc = connection.get();
            } catch (IOException e) {
                e.printStackTrace();
            }

            Element table = doc.body().getElementsByClass("txt").get(6);
            Elements rows = table.select("tr");

            for (int k = 1; k < rows.size(); k++) {
                Element row = rows.get(k);
                Elements cols = row.select("td");

                for (int j = 0; j < cols.size(); j++) {
                    fullListOfIngredients.add(cols.get(j).text());
                }
            }
        }
        mongoConnector.insertIngredientsIntoDBFromTheSite(URL, baseName, collectionName, fullListOfIngredients);
    }

}
