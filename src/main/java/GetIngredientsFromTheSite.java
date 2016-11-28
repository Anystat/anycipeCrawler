import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by Lofv on 17.11.2016.
 */
public class GetIngredientsFromTheSite {

    private final String SITE = "http://www.bazareceptov.ru/ingredients.php?page=";
    private List fullListOfIngredients = new LinkedList();
    MongoConnector mongoConnector=new MongoConnector();
    String baseName="anycipe_crawler";
    String collectionName="ingredients";


    public void parsing() {

        Document doc = null;
        for (int i = 1; i < 9; i++) {

            /**
             * @connection перебираем все страницы с ингредиентами на этом сайте
             */
            Connection connection = Jsoup.connect(SITE + i).userAgent("Mozilla/5.0");
            try {
                doc = connection.get();
            } catch (IOException e) {
                e.printStackTrace();
            }

            /**
             * @table отбираем 7-ую таблицу с классом txt и затем отбираем по тегу tr/td нужные нам элементы
             */
            Element table = doc.body().getElementsByClass("txt").get(6);
            Elements rows = table.select("tr");

            for (int k = 1; k < rows.size(); k++) { //first row is the col names so skip it.
                Element row = rows.get(k);
                Elements cols = row.select("td");

                for (int j = 0; j < cols.size(); j++) {
                    fullListOfIngredients.add(cols.get(j).text());
                }
            }

        }
        mongoConnector.insertIngredientsIntoDBFromTheSite(baseName,collectionName,fullListOfIngredients);
    }



}
