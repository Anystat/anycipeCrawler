import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;

/**
 * Created by Dred on 16.11.2016.
 */
public class ParsingSay7Info {

    private final String collectionName = "receipts";
    private final String baseName = "anycipe_crawler";
    private String instruction;
    private String receiptName;
    private String description;
    private String link;
    private ArrayList<String> ingredients = new ArrayList<String>();
    private MongoConnector mongoConnector;

    // коннект сюда происходит из класса Crawler. Туда ссылка на сайт попадает из текстового документа, в который будем вносить новые сайты
    public void parsing(Document doc) {
        mongoConnector = new MongoConnector();
     /*   Document doc = null;
        try {
            doc = Jsoup.connect(url).userAgent(CrawlerLeg.USER_AGENT).get();

        } catch (IOException e) {
            e.printStackTrace();
        }*/

        Elements elements = doc.select(".p-summary");
        description = elements.text(); //Описание блюда
        receiptName = doc.title(); // or System.out.println(sdd.getElementsByTag("h1").text()); // Название блюда
        link = doc.baseUri();

        ingredients(doc);
        instruction(doc);

        mongoConnector.mongoConnect(baseName, collectionName, receiptName, link, ingredients, description, instruction);
    }

    public void ingredients(Document document) {
        // int i = 0;
        // Matcher matcher;
        // Pattern pattern = Pattern.compile("(^\\d{0,3}\\u00A0)|((кг|г|мл|л|ч.л|ст.л)\\u0020)|(.*)$");//"^(\\d{0,3}\\s)?|(кг|г|мл|л|ч.л|ст.л\\s)?(\\w*)$"  получится как и обычно 300 г куриного филе
        ingredients.clear();

        Elements elements = document.body().select(".p-ingredient");

        for (Element element : elements) {
            ingredients.add(element.text());
            // matcher = pattern.matcher(element.text());//ingredients.get(0).text() //\u00A0 - это символ неразрывного пробела http://www.fileformat.info/info/unicode/char/00a0/index.htm
            //while (matcher.find()) {
            //
            //              ingredients.add(matcher.group());
            //             System.out.println("Array i=" + i + ";" + ingredients.get(i)+"s");
            //          i++;
            //   }
        }
    }

    public void instruction(Document document) {

        Elements elements = document.body().select(".e-instructions");
        instruction = elements.text();

    }
}
