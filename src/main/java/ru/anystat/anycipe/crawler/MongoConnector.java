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

    private MongoDatabase db;
    private MongoClient mongoClient;
    private final String URL = "85.143.221.95";
    private final Logger logger = Logger.getLogger(MongoConnector.class);


    public void insertIngredientsIntoDBFromTheSite(String databaseName, String collectionName, List ingredients) {
        List<Document> listOfIngredients = new ArrayList();

        mongoClient = new MongoClient(URL);
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
        mongoClient = new MongoClient(URL);
        db = mongoClient.getDatabase(databaseName);
        db.getCollection(collectionName).insertMany(receipts);
        mongoClient.close();
    }

    public void checkContentOfCollectionMangoDb(String databaseName, String collectionName) {
        mongoClient = new MongoClient(URL);
        db = mongoClient.getDatabase(databaseName);
        Iterable iterable = db.getCollection(collectionName).find();
        logger.info("Подключение к " + collectionName);

        for (Object document : iterable) {
            logger.info("Коллекция " + collectionName + " содержит " + document);
        }
        logger.info("Отключение");
        mongoClient.close();
    }

    public void checkCollectionsMongoDB(String databaseName) {
        mongoClient = new MongoClient(URL);
        db = mongoClient.getDatabase(databaseName);
        Iterable<String> iterable = db.listCollectionNames();

        for (String collectionName : iterable) {
            logger.info("Ваша база данных " + "\"" + db.getName() + "\"" + " содержит Коллекцию с именем " + collectionName);
        }
        mongoClient.close();
    }

    public void checkBasesNamesOfMongoDB() {
        mongoClient = new MongoClient(URL);

        logger.info("Подключение к серверу");
        Iterable<String> iterable = mongoClient.listDatabaseNames();

        for (String baseName : iterable) {
            logger.info("На сервере имются следующие базы: ");
            logger.info("BaseName = " + baseName);
        }
        mongoClient.close();
    }

    public void deleteCollection(String databaseName, String collectionName) {
        mongoClient = new MongoClient(URL);
        db = mongoClient.getDatabase(databaseName);
        logger.warn("Подключение к базе " + db.getName());
        db.getCollection(collectionName).deleteMany(new Document());
        logger.warn("Содержимое коллекции " + collectionName + " удалено");
        mongoClient.close();
    }
}
