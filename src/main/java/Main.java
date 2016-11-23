/**
 * Created by Dred on 07.11.2016.
 */
public class Main {


    public static void main(String[] args) {
        String listOfSites = "src\\main\\resources\\ListOfSites.txt";
        String listOfIngredients = "src\\main\\resources\\ListOfIngredients.txt";



/*      https://coderwall.com/p/nvu6jq/how-to-create-a-web-crawler-in-java  чтение сайтов из дока
        http://www.netinstructions.com/how-to-make-a-simple-web-crawler-in-java/
        http://www.programmersforum.ru/showthread.php?t=58898 как примерно реализовать перевод падежа
        http://www.javaprobooks.ru/java-%D0%BF%D1%80%D0%BE%D0%B3%D1%80%D0%B0%D0%BC%D0%BC%D0%B8%D1%80%D0%BE%D0%B2%D0%B0%D0%BD%D0%B8%D0%B5/%D0%BF%D1%80%D0%BE%D0%B3%D1%80%D0%B0%D0%BC%D0%BC%D0%B8%D1%80%D0%BE%D0%B2%D0%B0%D0%BD%D0%B8%D0%B5-%D0%BD%D0%B0-java-%D0%B8%D1%82%D0%B5%D1%80%D0%B0%D1%82%D0%BE%D1%80%D1%8B-%D0%B2-%D0%BA%D0%BE%D0%BB.html
        http://www.netinstructions.com/how-to-make-a-simple-web-crawler-in-java/
*/

//          MongoConnector mongoConnector = new MongoConnector();
 //       GetIngredientsFromTheSite site = new GetIngredientsFromTheSite();
//        site.parsing();
//        mongoConnector.connectionOpen();
//        mongoConnector.mongoConnect("anycipe_crawler"); // для удаления содержимого коллекции
        Crawler spider = new Crawler();
        spider.search(Filereader.read(listOfSites));
//          mongoConnector.connectionClose();


    }


}
