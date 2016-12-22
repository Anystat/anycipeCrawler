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
    private LinkedList<String> ingredients = new LinkedList<String>();
    private List<org.bson.Document> listOfRecipes = new LinkedList<org.bson.Document>();


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
        Matcher matcher;
        Pattern pattern;
        String regex = "(^[½|¼|¾]?\\d{0,5}[\\u2013\\.,/-]?\\d{0,5}]?)?(\\u00A0|\\u0020)?(г.|г|кг.|кг|мл.|мл|л.|л|ч.л.|ч.л|ст.л.|ст.л)?(\\u00A0|\\u0020)(.*$)";
        ingredients.clear();
        Elements elements = document.body().select(".p-ingredient");

        for (Element element : elements) {

            pattern = Pattern.compile(regex);
            matcher = pattern.matcher(element.text());


            while (matcher.find()) {


                String space = "\u0020";
                String nbsp = "\u00A0";
                char spaces='\u0020';
                for (int i = 0; i < matcher.groupCount(); i++) {
                    String matches=matcher.group(i);
                    System.out.println("i = " + i + ":  " + matcher.group(i));
                    System.out.println(matches==space);
                    System.out.println(matches==nbsp);
                   /* String match=matcher.group(i);
                    if ((matcher.group(i) != null) && (!(matcher.group(i).equals("\\u0020"))) &&(!(matcher.group(i).equals("\\u00A0")))) {
                        System.out.println("i = " + i + ":  " + matcher.group(i));
                    }*/
                }
                //ingredients.add(element.text());
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

 /*     if ((matcher.group(i).equals("\\u0020"))){
                        System.out.println("i = " + i + ":  " + matcher.group(i));
                    }*/
//  System.out.println("i = " + i + ":  " + matcher.group(i));

                   /* if (!(matcher.group(i) == null)&&(matcher.group(i).equals('\u0020'))&&(matcher.group(i).equals('\u00A0'))) {
                    }*/ /* if (matcher.group(i).equals("\u0020")) {

                    } else if (matcher.group(i).equals("\u00A0")) {
                        String spaces = matcher.group(i);
                        //   System.out.println("i = " + i + " = &nbsp" );
                    } else*/
//System.out.println(matcher.group(i)=="\\u00A0");
                   /* if (!(matcher.group(i) == null)){
                        System.out.println("i+1 = " + i + ":  " + matcher.group(i));
                    }*/