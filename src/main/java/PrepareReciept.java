import org.bson.Document;

import java.util.ArrayList;
import java.util.List;

import static java.util.Arrays.asList;

/**
 * Created by Lofv on 24.11.2016.
 */

  class PrepareReciept {


    static List<Document> listOfReciepts = new ArrayList<Document>();
    static Document listOfIngredients = new Document();
    static Document reciept;

    public static void setListOfIngredients(String receiptName, String link, ArrayList ingredients, String descriptrion, String instruction) {


        for (int i = 0; i < ingredients.size(); i++) {
           listOfIngredients.append(i+1+" ingredient", ingredients.get(i));
           // listOfIngredients.put(i+" ingredient", ingredients.get(i));
        }


        reciept = new Document("reciept", new Document()
                .append("recieptName",receiptName)
                .append("link", link)
                .append("ingredients", asList(listOfIngredients))
                .append("description", descriptrion)
                .append("instruction", instruction));


        insertReceptIntoDoc(reciept);
    }


    public static void insertReceptIntoDoc(Document doc) {

        listOfReciepts.add(doc);
    }

    public List<Document> getListOfReciepts(){
        return listOfReciepts;
    }
}


