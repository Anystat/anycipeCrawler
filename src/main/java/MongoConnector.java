import com.mongodb.MongoClient;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

import java.util.ArrayList;
import java.util.List;

import static java.util.Arrays.asList;


/**
 * Created by Lofv on 13.11.2016.
 */

public class MongoConnector {
    String uri = "85.143.221.95";
    MongoDatabase db;
    MongoClient mongoClient;


    public MongoClient getMongoClient() {
        return mongoClient;
    }

    public void setMongoClient(MongoClient mongoClient) {
        this.mongoClient = mongoClient;
    }


    public void connectionOpen() {
        mongoClient = new MongoClient(uri);
    }

    public void connectionClose() {
        mongoClient.close();
    }


    public void mongoConnect(String databaseName) {  //Этот конструктор для добавляния в базу только ингредиентов ( тестовый вариант) в именительном падеже

        mongoClient = new MongoClient(uri);
        db = mongoClient.getDatabase(databaseName); // сейчас это databaseName=test,

        // checkCollectionsMongoDB(db);
//        checkBasesNamesOfMongoDB(mongoClient);
        deleteCollection(db, "receipts");
       deleteCollection(db, "ingredients");
        checkContentOfCollectionMangoDb(db, "receipts");
        checkContentOfCollectionMangoDb(db, "ingredients");

        mongoClient.close();
    }

 /*   public void mongoConnect(String databaseName, String collectionName, String receiptName, String link, ArrayList<String> ingredientUnits, String descriptrion, String instruction) {
        mongoClient = new MongoClient(uri);
        db = mongoClient.getDatabase(databaseName);
        insertReceiptToMongoDB(collectionName, receiptName, link, ingredientUnits, descriptrion, instruction);
        mongoClient.close();
    }*/



    /*
    * @ В методе insertIngredientsIntoDBFromTheSite в качестве databaseName и collectionName
    * надо использовать те базу и коллекцию куда занести ингредиенты взятые с 1 какого-то сайта в Именительном падеже
     */

    public void insertIngredientsIntoDBFromTheSite(String databaseName, String collectionName, List ingredients) {
        mongoClient = new MongoClient(uri);
        db = mongoClient.getDatabase(databaseName);


        Document listOfIngredients = new Document();
        for (int i = 0; i < ingredients.size(); i++) {
            listOfIngredients.append("ingredient", ingredients.get(i));
        }

        db.getCollection(collectionName).insertOne(  //ингредиенты взятые из 1 какого-то сайта. Процесс производится в классе GetIngredientsFromTheSite
                new Document("Список ингредиентов", new Document()
                        .append("ingredients", asList(listOfIngredients))
                        .append("category", null)));
        System.out.println(ingredients.size() + " ингредиентов добавлено в базу");
        mongoClient.close();

    }

    /*    public void insertReceiptToMongoDB(String collectionName, String receiptName, String link, ArrayList ingredients, String descriptrion, String instruction) {

            Document listOfIngredients = new Document();

            for (int i = 0; i < ingredients.size(); i++) {
                listOfIngredients.append("ingredient", ingredients.get(i));
            }

            //находим коллекцию в которую нам надо что-то добавить. Если ее нет, то она создается, поэтому надо быть аккуратным с названиями

            db.getCollection(collectionName).insertOne(  //receipts
                    new Document("Рецепт", new Document()
                            .append("receipt", receiptName)
                            .append("link", link)
                            .append("ingredients", asList(listOfIngredients))
                            .append("description", descriptrion)
                            .append("instruction", instruction)));

        }*/
    public void insertReceiptToMongoDB(String databaseName,String collectionName, List<Document> reciepts) {
        mongoClient = new MongoClient(uri);
        db = mongoClient.getDatabase(databaseName);
        db.getCollection(collectionName).insertMany(reciepts);
        mongoClient.close();

    }

    public void checkContentOfCollectionMangoDb(MongoDatabase base, String collectionName) {
        Iterable iterable = base.getCollection(collectionName).find();
        System.out.println("Подключение к " + collectionName);

        for (Object document : iterable) {
            System.out.println("Коллекция " + collectionName + " содержит " + document);  //печатает то, что мы внесли. Это не обязательные действия
        }
       /* FindIterable<Document> iterable = base.getCollection(collectionName).find();
        iterable.forEach(new Block<Document>() {
            @Override
            public void apply(final Document document) {
                System.out.println("Коллекция " + collectionName " содержит " + document);  //печатает то, что мы внесли. Это не обязательные действия
            }
        });*/
        System.out.println("Отключение ");
    }

    public void checkCollectionsMongoDB(MongoDatabase base) {  // Создаем итератор

        Iterable<String> iterable = base.listCollectionNames();  // присваиваем итератору значения полученные из базы

        for (String collectionName : iterable) {
            System.out.println("Ваша база данных " + "\"" + base.getName() + "\"" + " содержит Коллекцию с именем " + collectionName);  //печатаем названия коллекций, которые находятся внутри базы
        }
    }

    public void checkBasesNamesOfMongoDB(MongoClient client) {
        System.out.println("Подключение к серверу ");
        Iterable<String> iterable = client.listDatabaseNames();

        for (String baseName : iterable) {
            System.out.println("На сервере имются следующие базы: ");
            System.out.println("BaseName = " + baseName);  //печатаем названия баз в нашей Базе Данных
        }
    }

    public void deleteCollection(MongoDatabase base, String collectionName) {
        System.out.println("Подключение к базе " + base.getName());
        base.getCollection(collectionName).deleteMany(new Document());
        System.out.println("Содержимое коллекции " + collectionName + " удалено");
    }

}
