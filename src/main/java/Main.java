/**
 * Created by Dred on 07.11.2016.
 */
public class Main {


    public static void main(String[] args) {
        String listOfSites = "src\\main\\resources\\ListOfSites.txt";
        String listOfIngredients = "src\\main\\resources\\ListOfIngredients.txt";
        final String collectionName = "receipts";
        final String baseName = "anycipe_crawler";

        MongoConnector mongoConnector = new MongoConnector();
        PrepareReciept prepareReciept = new PrepareReciept();
        GetIngredientsFromTheSite site = new GetIngredientsFromTheSite();
        Crawler spider = new Crawler();


//        site.parsing();
//        mongoConnector.connectionOpen();
//          mongoConnector.connectionClose();
//        spider.search(Filereader.read(listOfSites));
//
//
//        mongoConnector.insertReceiptToMongoDB(baseName, collectionName, prepareReciept.getListOfReciepts());
        mongoConnector.mongoConnect("anycipe_crawler"); // для удаления содержимого коллекции

    }


}
