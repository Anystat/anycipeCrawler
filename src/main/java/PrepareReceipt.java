import org.bson.Document;

import java.util.ArrayList;
import java.util.List;

import static java.util.Arrays.asList;

/**
 * Created by Lofv on 24.11.2016.
 */

class PrepareReceipt {


    static List<Document> listOfReciepts = new ArrayList<Document>();
    static Document listOfIngredients = new Document();
    static Document receipt;

    public static void setListOfIngredients(String receiptName, String link, ArrayList ingredients, String descriptrion, String instruction) {





        for (int i = 0; i < ingredients.size(); i++) {
            listOfIngredients.append(i+1+"name",ingredients.get(i));
           // listOfIngredients.append(i + 1 + " ingredient", ingredients.get(i));
            // listOfIngredients.put(i+" ingredient", ingredients.get(i));
        }

        receipt = new Document("receipt", receiptName)
                .append("link", link)
                .append("ingredients", asList(listOfIngredients))
                .append("description", descriptrion)
                .append("instruction", instruction);

  /*      receipt = new Document("receipt", new Document()
                .append("receipt",receiptName)
                .append("link", link)
                .append("ingredients", asList(listOfIngredients))
                .append("description", descriptrion)
                .append("instruction", instruction));*/


        listOfReciepts.add(receipt);

    }



    public List<Document> getListOfReceipts() {
        return listOfReciepts;
    }
}


