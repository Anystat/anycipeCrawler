package ru.anystat.anycipe.crawler;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.function.Consumer;
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
        Matcher matcher;
        Pattern pattern;
        // int i = 0;
        // Matcher matcher;
        // Pattern pattern = Pattern.compile("(^\\d{0,3}\\u00A0)|((кг|г|мл|л|ч.л|ст.л)\\u0020)|(.*)$");//"^(\\d{0,3}\\s)?|(кг|г|мл|л|ч.л|ст.л\\s)?(\\w*)$"  получится как и обычно 300 г куриного филе

        ingredients.clear();
        Elements elements = document.body().select(".p-ingredient");

        for (Element element : elements) {
            //pattern=Pattern.compile("(^\\d*\\u0020)"); // проверка только на наличие цифр в начале
            //pattern=Pattern.compile("\\u0020(кг|г|мл|л|ч.л|ст.л)\\u0020"); //проверка на единицу измерения
            ///pattern=Pattern.compile("(.*)$"); // все что угодно
                pattern = Pattern.compile("(^\\d{0,5})?(\\u00A0|\\u0020)?(г|кг|мл|л|ч.л|ст.л)?(\\u00A0|\\u0020)?(.*$)");// это в matcher.split
//            pattern=Pattern.compile("(^\\d{0,5})");
             matcher = pattern.matcher(element.text());
            String[] text = pattern.split("(^\\d{0,5})?(\\u00A0|\\u0020)?(г|кг|мл|л|ч.л|ст.л)?(\\u00A0|\\u0020)?(.*$)"); // не работает
            Arrays.asList(text).forEach(animal -> System.out.print(text + " i "));

           /*  pattern = Pattern.compile("(^\\d{0,5})?(\\u00A0|\\u0020)?(г|кг|мл|л|ч.л|ст.л)?(\\u00A0|\\u0020)?(.*$)");// это в matcher.split
           while (matcher.find()) {  рабочий вариант
                System.out.println("Group 1: "+ matcher.group(1));
                System.out.println("Group 2: "+ matcher.group(2));
                System.out.println("Group 3: "+ matcher.group(3));
                System.out.println("Group 4: "+ matcher.group(4));
                System.out.println("Group 5: "+ matcher.group(5));
                System.out.println("Whole group: "+ matcher.group());



            }*/

//            if(element.text().contains("\u00A0")){
//                pattern = Pattern.compile("(^\\d{0,3}\\u00A0)|((кг|г|мл|л|ч.л|ст.л)\\u0020)|(.*)$");//"^(\\d{0,3}\\s)?|(кг|г|мл|л|ч.л|ст.л\\s)?(\\w*)$"
//            }else {pattern = Pattern.compile("(^\\d{0,3}\\u00A0)|((кг|г|мл|л|ч.л|ст.л)\\u0020)|(.*)$");
//
//            }
            // matcher = pattern.matcher(element.text());//ingredients.get(0).text() //\u00A0 - это символ неразрывного пробела http://www.fileformat.info/info/unicode/char/00a0/index.htm
            //while (matcher.find()) {
            //
            //              ingredients.add(matcher.group());
            //             System.out.println("Array i=" + i + ";" + ingredients.get(i)+"s");
            //          i++;
            //   }
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
