package ru.anystat.anycipe.crawler;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoDatabase;
import org.apache.log4j.Logger;
import org.bson.Document;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by Lofv on 13.11.2016.
 */

public class MongoConnector {
    final Logger logger = Logger.getLogger(MongoConnector.class);
    String url = "85.143.221.95";
    MongoDatabase db;
    MongoClient mongoClient;

    public void mongoConnect(String databaseName) {  //Этот конструктор для добавляния в базу только ингредиентов ( тестовый вариант) в именительном падеже

        mongoClient = new MongoClient(url);
        db = mongoClient.getDatabase(databaseName); // сейчас это databaseName=test,

        // checkCollectionsMongoDB(db);
        checkBasesNamesOfMongoDB(mongoClient);
//        deleteCollection(db, "receipts");
//        deleteCollection(db, "ingredients");
//        checkContentOfCollectionMangoDb(db, "receipts");
//        checkContentOfCollectionMangoDb(db, "ingredients");

        mongoClient.close();
    }


    /*
    * @ В методе insertIngredientsIntoDBFromTheSite в качестве databaseName и collectionName
    * надо использовать те базу и коллекцию куда занести ингредиенты взятые с 1 какого-то сайта в Именительном падеже
     */

    public void insertIngredientsIntoDBFromTheSite(String databaseName, String collectionName, List ingredients) {
        List<Document> listOfIngredients = new ArrayList();

        mongoClient = new MongoClient(url);
        db = mongoClient.getDatabase(databaseName);


        for (int i = 0; i < ingredients.size(); i++) {
            listOfIngredients.add(new Document("name", ingredients.get(i))
                    .append("category", null));
        }
        db.getCollection(collectionName).insertMany(listOfIngredients);
        logger.info(ingredients.size() + " ингредиентов добавлено в базу");
        mongoClient.close();


    }

    public void insertReceiptToMongoDB(String databaseName, String collectionName, List<Document> receipts) {
        mongoClient = new MongoClient(url);
        db = mongoClient.getDatabase(databaseName);
        db.getCollection(collectionName).insertMany(receipts);
        mongoClient.close();

    }

    public void checkContentOfCollectionMangoDb(MongoDatabase base, String collectionName) {
        Iterable iterable = base.getCollection(collectionName).find();
        logger.info("Подключение к " + collectionName);

        for (Object document : iterable) {
            logger.info("Коллекция " + collectionName + " содержит " + document);
        }
        logger.info("Отключение");
    }

    public void checkCollectionsMongoDB(MongoDatabase base) {
        Iterable<String> iterable = base.listCollectionNames();

        for (String collectionName : iterable) {
            System.out.println("Ваша база данных " + "\"" + base.getName() + "\"" + " содержит Коллекцию с именем " + collectionName);
        }
    }

    public void checkBasesNamesOfMongoDB(MongoClient client) {
        logger.info("Подключение к серверу");
        Iterable<String> iterable = client.listDatabaseNames();

        for (String baseName : iterable) {
            logger.info("На сервере имются следующие базы: ");
            logger.info("BaseName = " + baseName);
        }
    }

    public void deleteCollection(MongoDatabase base, String collectionName) {
        logger.warn("Подключение к базе " + base.getName());
        base.getCollection(collectionName).deleteMany(new Document());
        logger.warn("Содержимое коллекции " + collectionName + " удалено");
    }
}
