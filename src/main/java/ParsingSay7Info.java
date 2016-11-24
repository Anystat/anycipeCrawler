import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import java.util.ArrayList;

/**
 * Created by Lofv on 16.11.2016.
 */
public class ParsingSay7Info {


    private String instruction;
    private String receiptName;
    private String description;
    private String link;
    private ArrayList<String> ingredients = new ArrayList<String>();
    private PrepareReciept prepareReciept;

    // коннект сюда происходит из класса Crawler. Туда ссылка на сайт попадает из текстового документа, в который будем вносить новые сайты
    public void parsing(Document doc) {
        prepareReciept = new PrepareReciept();

        Elements elements = doc.select(".p-summary");
        description = elements.text(); //Описание блюда
        receiptName = doc.title();  // Название блюда
        link = doc.baseUri();

        ingredients(doc);
        instruction(doc);

        PrepareReciept.setListOfIngredients(receiptName, link, ingredients, description, instruction);
        //prepareReciept.setListOfIngredients(receiptName, link, ingredients, description, instruction);
    }

    public void ingredients(Document document) {
        ingredients.clear();

        Elements elements = document.body().select(".p-ingredient");

        for (Element element : elements) {
            ingredients.add(element.text());

        }
    }

    public void instruction(Document document) {

        Elements elements = document.body().select(".e-instructions");
        instruction = elements.text();
    }


}
