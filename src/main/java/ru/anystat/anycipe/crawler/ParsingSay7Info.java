package ru.anystat.anycipe.crawler;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by Lofv on 16.11.2016.
 */
public class ParsingSay7Info {

    private String link;
    private String recipeName;
    private String instruction;
    private String description;
    private LinkedList<String> ingredients = new LinkedList<String>();
    private List<org.bson.Document> listOfRecipes = new LinkedList<org.bson.Document>();


    public void parsing(List<Document> document) {

        for (int i = 0; i < document.size(); i++) {
          Document doc= document.get(i);

            Elements elements = doc.select(".p-summary");
            description = elements.text();
            recipeName = doc.title();
            link = doc.baseUri();

            ingredients(doc);
            instruction(doc);

            createRecipe(recipeName, link, ingredients, description, instruction);
        }
    }

    private void createRecipe(String receiptName, String link, List<String> ingredients, String description, String instruction) {

        List<org.bson.Document> ingredientsList = new ArrayList<org.bson.Document>();

        for (int i = 0; i < ingredients.size(); i++) {
            ingredientsList.add(new org.bson.Document("name", ingredients.get(i)));
                  }

        org.bson.Document recipe = new org.bson.Document("recipe", receiptName)
                .append("link", link)
                .append("ingredients", ingredientsList)
                .append("description", description)
                .append("instruction", instruction);

        listOfRecipes.add(recipe);
    }

    private void ingredients(Document document) {

        ingredients.clear();
        Elements elements = document.body().select(".p-ingredient");

        for (Element element : elements) {
            ingredients.add(element.text());
        }
    }

    private void instruction(Document document) {

        Elements elements = document.body().select(".e-instructions");
        instruction = elements.text();
    }

    public List<org.bson.Document> getListOfRecipes() {
        return listOfRecipes;
    }

}
