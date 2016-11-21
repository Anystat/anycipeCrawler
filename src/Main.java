import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import java.io.IOException;

/**
 * Created by Dred on 07.11.2016.
 */
public class Main {


    public static void main(String[] args) {

        // https://coderwall.com/p/nvu6jq/how-to-create-a-web-crawler-in-java  чтение сайтов из дока
//http://www.netinstructions.com/how-to-make-a-simple-web-crawler-in-java/

        //http://www.programmersforum.ru/showthread.php?t=58898 как примерно реализовать перевод падежа
        /*
        http://www.javaprobooks.ru/java-%D0%BF%D1%80%D0%BE%D0%B3%D1%80%D0%B0%D0%BC%D0%BC%D0%B8%D1%80%D0%BE%D0%B2%D0%B0%D0%BD%D0%B8%D0%B5/%D0%BF%D1%80%D0%BE%D0%B3%D1%80%D0%B0%D0%BC%D0%BC%D0%B8%D1%80%D0%BE%D0%B2%D0%B0%D0%BD%D0%B8%D0%B5-%D0%BD%D0%B0-java-%D0%B8%D1%82%D0%B5%D1%80%D0%B0%D1%82%D0%BE%D1%80%D1%8B-%D0%B2-%D0%BA%D0%BE%D0%BB.html
Map pets = new HashMap();
pets.put("Мурзик", "кот");
pets.put("Бобик", "собака");
pets.put("Кеша", "попугай");

Map.Entry set = pets.entrySet();
Iterator&gt; iter = set.iterator();

while (iter.hasNext()) {
Map.Entry pet = iter.next();
System.out.println(pet.getKey() + " это " + pet.getValue());
}
[/code]

После компилляции и запуска этого примера мы получим:

Мурзик это кот
Бобик это собака
Кеша это попугай

*/

        //  String listOfSites = "res\\ListOfSites.txt";
        //String listOfIngredients = "res\\ListOfIngredients.txt";

        // Filereader.read(listOfSites);
        //  Filereader.read(listOfIngredients);

          //Crawler spider = new Crawler();
          //spider.search(Filereader.read(listOfSites));

GetIngredientsFromTheSite ing=new GetIngredientsFromTheSite();
        ing.parsing();







    }





}
