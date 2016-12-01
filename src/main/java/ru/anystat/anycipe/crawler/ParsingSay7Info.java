package ru.anystat.anycipe.crawler;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Lofv on 16.11.2016.
 */
public class ParsingSay7Info {

    //    private PrepareReceipt prepareReceipt;
    public List<org.bson.Document> listOfRecipes = new ArrayList<org.bson.Document>();
    private ArrayList<String> ingredients = new ArrayList<String>();
    private String instruction;
    private String recipeName;
    private String description;
    private String link;

    public ParsingSay7Info() {
    }

    public ParsingSay7Info(Document doc) {
//         listOfRecipes=getListOfRecipes();
        parsing(doc);
    }

    public void parsing(Document doc) {

//        prepareReceipt = new PrepareReceipt();
        Elements elements = doc.select(".p-summary");
        description = elements.text(); //Описание блюда
        recipeName = doc.title();  // Название блюда
        link = doc.baseUri();   //ссылка на рецепт

        ingredients(doc);
        instruction(doc);

//        prepareReceipt.createRecipe(recipeName, link, ingredients, description, instruction);
        createRecipe(recipeName, link, ingredients, description, instruction);
    }

    public void createRecipe(String receiptName, String link, List<String> ingredients, String description, String instruction) {

        List<org.bson.Document> ingredientsList = new ArrayList<org.bson.Document>();

        for (int i = 0; i < ingredients.size(); i++) {
            ingredientsList.add(new org.bson.Document("name", ingredients.get(i)));
            //listOfIngredients.append(i+1+"name",ingredients.get(i));
            // listOfIngredients.append(i + 1 + " ingredient", ingredients.get(i));
            // listOfIngredients.put(i+" ingredient", ingredients.get(i));
        }

        org.bson.Document recipe = new org.bson.Document("recipe", receiptName)
                //.append("ingredients", asList(listOfIngredients))
                .append("link", link)
                .append("ingredients", ingredientsList)
                .append("description", description)
                .append("instruction", instruction);

        setListOfRecipes(recipe);
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

    public void setListOfRecipes(org.bson.Document recipe) {

        listOfRecipes.add(recipe);
    }

}
