package ru.anystat.anycipe.crawler;

import org.bson.Document;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Lofv on 24.11.2016.
 */

class PrepareReceipt {

    private static List<Document> listOfReceipts = new ArrayList<Document>();

    public static void createReceipt(String receiptName, String link, List<String> ingredients, String description, String instruction) {

        List<Document> ingredientsList = new ArrayList<Document>();

        for (int i = 0; i < ingredients.size(); i++) {
            //listOfIngredients.append(i+1+"name",ingredients.get(i));
            ingredientsList.add(new Document("name", ingredients.get(i)));
           // listOfIngredients.append(i + 1 + " ingredient", ingredients.get(i));
            // listOfIngredients.put(i+" ingredient", ingredients.get(i));
        }

        Document receipt = new Document("receipt", receiptName)
                .append("link", link)
                //.append("ingredients", asList(listOfIngredients))
                .append("ingredients", ingredientsList)
                .append("description", description)
                .append("instruction", instruction);

  /*      receipt = new Document("receipt", new Document()
                .append("receipt",receiptName)
                .append("link", link)
                .append("ingredients", asList(listOfIngredients))
                .append("description", description)
                .append("instruction", instruction));*/


        listOfReceipts.add(receipt);
    }

    public static List<Document> getListOfReceipts() {
        return listOfReceipts;
    }
}


