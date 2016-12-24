package ru.anystat.anycipe.crawler;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Lofv on 16.11.2016.
 */
public class ParsingSay7Info {

    private String link;
    private String recipeName;
    private String instruction;
    private String description;
    private LinkedList<org.bson.Document> ingredients = new LinkedList<org.bson.Document>();
    private List<org.bson.Document> listOfRecipes = new ArrayList();

    public void parsing(List<Document> document) {

        for (int i = 0; i < document.size(); i++) {
            Document doc = document.get(i);

            Elements elements = doc.select(".p-summary");
            description = elements.text();
            recipeName = doc.title();
            link = doc.baseUri();

            ingredients(doc);
            instruction(doc);

            createRecipe(recipeName, link, ingredients, description, instruction);
        }
    }

    private void createRecipe(String receiptName, String link, List<org.bson.Document> ingredients, String description, String instruction) {

        List<org.bson.Document> ingredientsList = new ArrayList<org.bson.Document>();

        for (int i = 0; i < ingredients.size(); i++) {
            ingredientsList.add(new org.bson.Document("ingredient", ingredients.get(i)));
        }

        org.bson.Document recipe = new org.bson.Document("recipe", receiptName)
                .append("link", link)
                .append("ingredients", ingredientsList)
                .append("description", description)
                .append("instruction", instruction);

        listOfRecipes.add(recipe);

    }

    private void ingredients(Document document) {

        Matcher matcher;
        Pattern pattern;
        String value;
        String unit;
        String name;
        String space = "\u0020";
        String nbsp = "\u00A0";
        String regExpValue = "(^[½|¼|¾]?\\d{0,5}[\u2013\\.,/-]?\\d{0,5}]?)?";
        String regExpUnit = "(г.|г|кг.|кг|мл.|мл|л.|л|ч.л.|ч.л|ст.л.|ст.л|головка|литр|стакан)";
        String regExpSpaces = space + "|" + nbsp;
        String regExpName = "(.*$)";
        String regex = regExpValue + "(" + regExpSpaces + ")?" + "(" + regExpUnit + "(" + regExpSpaces + "))?" + regExpName;


        ingredients.clear();

        Elements elements = document.body().select(".p-ingredient");

        for (Element element : elements) {

            pattern = Pattern.compile(regex);
            matcher = pattern.matcher(element.text());

            if (matcher.find()) {

                name = null;
                value = null;
                unit = null;

                for (int i = 1; i < matcher.groupCount() + 1; i++) {

                    if (matcher.group(i) != null) {
                        if (!((matcher.group(i).equals(nbsp)) || (matcher.group(i).equals(space)) || (matcher.group(i).equals("")))) {
                            if (i == 1) {
                                value = matcher.group(i);
                            }
                            if (i == 3) {
                                unit = matcher.group(i);
                            }
                            if (i == 6) {
                                name = matcher.group(i);
                            }
                        }
                    }
                }
                ingredients.add(new org.bson.Document("value", value)
                        .append("unit", unit)
                        .append("name", name));
            }
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
