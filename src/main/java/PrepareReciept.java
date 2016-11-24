import org.bson.Document;

import java.util.ArrayList;
import java.util.List;

import static java.util.Arrays.asList;

/**
 * Created by Lofv on 24.11.2016.
 */

public class PrepareReciept {


    private List<Document> listOfReciepts = new ArrayList<Document>();
    private Document listOfIngredients = new Document();
    private Document reciept;

    public void setListOfIngredients(String receiptName, String link, ArrayList ingredients, String descriptrion, String instruction) {


        for (int i = 0; i < ingredients.size(); i++) {
            listOfIngredients.append("ingredient", ingredients.get(i));
        }


        reciept = new Document(receiptName, new Document()
                .append("link", link)
                .append("ingredients", asList(listOfIngredients))
                .append("description", descriptrion)
                .append("instruction", instruction));


        insertReceptIntoDoc(reciept);
    }


    private void insertReceptIntoDoc(Document doc) {
        listOfReciepts.add(doc);
    }

    public List<Document> getListOfReciepts(){
        return listOfReciepts;
    }
}


