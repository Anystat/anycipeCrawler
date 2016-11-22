import com.mongodb.Block;
import com.mongodb.MongoClient;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

import java.util.ArrayList;
import java.util.List;

import static java.util.Arrays.asList;


/**
 * Created by Dred on 13.11.2016.
 */

public class MongoConnector {
    String uri = "85.143.221.95";
    MongoDatabase db;
    MongoClient mongoClient;


    public void mongoConnect(String databaseName) {

        mongoClient = new MongoClient(uri);
        db = mongoClient.getDatabase(databaseName); // сейчас это databaseName=test,

        checkCollectionsMongoDB(db);
       // checkContentOfCollectionMangoDb(db, "receipts");
        //checkBasesNamesOfMongoDB(mongoClient);
        deleteCollection(db,"receipts");
        checkCollectionsMongoDB(db);
        mongoClient.close();
    }

    public void mongoConnect(String databaseName, String collectionName, String receiptName, String link, ArrayList<String> ingredientUnits, String descriptrion, String instruction) {

        mongoClient = new MongoClient(uri);
        db = mongoClient.getDatabase(databaseName); // сейчас это databaseName=test,

        insertReceiptToMongoDB(collectionName, receiptName, link, ingredientUnits, descriptrion, instruction);
        checkContentOfCollectionMangoDb(db, collectionName);

        mongoClient.close();
    }

    /*
    * @ В методе insertIngredientsIntoDBFromTheSite в качестве databaseName и collectionName
    * надо использовать те базу и коллекцию куда занести ингредиенты взятые с 1 какого-то сайта в Именительном падеже
     */

    public void insertIngredientsIntoDBFromTheSite(String databaseName, String collectionName, List ingredients) {
        mongoClient = new MongoClient(uri);
        db = mongoClient.getDatabase(databaseName);

        Document listOfIngredients = new Document();
        for (int i = 0; i < ingredients.size(); i++) {
            listOfIngredients.append(i + " ингредиент", ingredients.get(i));
        }

        db.getCollection(collectionName).insertOne(  //ингредиенты взятые из 1 какого-то сайта. Процесс производится в классе GetIngredientsFromTheSite
                new Document("Список ингредиентов", asList(listOfIngredients)));

        mongoClient.close();

    }


    public void insertReceiptToMongoDB(String collectionName, String receiptName, String link, ArrayList ingredients, String descriptrion, String instruction) {

        Document listOfIngredients = new Document();
        for (int i = 0; i < ingredients.size(); i++) {
            listOfIngredients.append(i + " ингредиент", ingredients.get(i));
        }

        //находим коллекцию в которую нам надо что-то добавить. Если ее нет, то она создается, поэтому надо быть аккуратным с названиями

        db.getCollection(collectionName).insertOne(  //receipts
                new Document(receiptName, new Document()//борщ
                        .append("link", link)
                        .append("ingredients", asList(listOfIngredients))
                        .append("description", descriptrion)
                        .append("instruction", instruction)));

    }
/*          db.getCollection(collectionName).insertOne(  //receipts
                   new Document(receiptName, new Document()//борщ
                           .append("link", link)
                           .append("ingredients", asList(
                                   new Document().append("элемент", asd.get(i))                ///////////// Вот так можно добавлять массивы с ингредиентами. Надо разобраться как в массив впихнуть кучу массивов
                                           .append("name", ingredientName)  //мясо
                                           .append("amount", ingredientAmount) //100
                                           .append("unit", ingredientUnit)))   //г
                           .append("description", descriptrion)
                           .append("instruction", instruction)));  //как готовить
 String ingredientName, String ingredientAmount, String ingredientUnit
       }
       */


/*
        db.getCollection("cuisine").insertOne(
                new Document("instruction",
                        new Document()
                                .append("street", "2 Avenue")
                                .append("constitution", asList(new Document()
                                .append("name",ingredientName)
                                .append("amount", ingredientAmount)
                                .append("unit", ingredientUnit)
                                ))));*/

/*        "instruction": "борщ",
                "ingredients": [
        {
            "name": "томат",
                "amount": 100,
                "unit": "грамм"
        },
        {
            "name": "чеснок",
                "amount": "",
                "unit": ""
        }
        ],*/

       /* db.getCollection("restaurants").insertOne(
                new Document("address",
                        new Document()
                                .append("street", "2 Avenue")
                                .append("zipcode", "10075")
                                .append("building", "1480")
                                .append("coord", asList(-73.9557413, 40.7720266))
                )
                        .append("borough", "Manhattan")
                        .append("cuisine", "Italian")
                        .append("grades", asList(
                                new Document()
                                        .append("date", format.parse("2014-10-01T00:00:00Z"))
                                        .append("grade", "A")
                                        .append("score", 11),
                                new Document()
                                        .append("date", format.parse("2014-01-16T00:00:00Z"))
                                        .append("grade", "B")
                                        .append("score", 17)))
                        .append("name", "Vella")
                        .append("restaurant_id", "41704620"));
*/
   /*Document{{_id=58222598795d8f82e759b1f5,
   address=Document{
   {street=Morris Park Ave,
    zipcode=10462
    building=1007,
    coord=[-73.856077, 40.848447]}},
   borough=Bronx,
   cuisine=Bakery,
   grades=[Document{{date=Mon Mar 03 11:00:00 VLAT 2014, grade=A, score=2}},
           Document{{date=Wed Sep 11 11:00:00 VLAT 2013, grade=A, score=6}},
           Document{{date=Thu Jan 24 11:00:00 VLAT 2013, grade=A, score=10}},
           Document{{date=Wed Nov 23 11:00:00 VLAT 2011, grade=A, score=9}},
           Document{{date=Thu Mar 10 10:00:00 VLAT 2011, grade=B, score=14}}],
   name=Morris Park Bake Shop,
   restaurant_id=30075445}}



                        */

        /*

        {
  "instruction": "борщ",
  "ingredients": [
    {
      "name": "томат",
      "amount": 100,
      "unit": "грамм"
    },
    {
      "name": "чеснок",
      "amount": "",
      "unit": ""
    }
  ],
  "persons": 2,
  "energy": {
    "calorific value": 250,
    "protein": 10,
    "fat": 5,
    "carbohydrates": 10
  },
  "description": "Hello World",
  "image": {
    "_id": "",
    "chunkSize": "",
    "uploadDate": "",
    "length": "",
    "md5": "",
    "filename": ""
  }
}
*/


    public void checkContentOfCollectionMangoDb(MongoDatabase base,  String collectionName) {
        Iterable iterable = base.getCollection(collectionName).find();


        for (Object document : iterable) {
            System.out.println("Коллекция " + collectionName+" содержит " + document);  //печатает то, что мы внесли. Это не обязательные действия
        }
       /* FindIterable<Document> iterable = base.getCollection(collectionName).find();
        iterable.forEach(new Block<Document>() {
            @Override
            public void apply(final Document document) {
                System.out.println("Коллекция " + collectionName " содержит " + document);  //печатает то, что мы внесли. Это не обязательные действия
            }
        });*/
    }

    public void checkCollectionsMongoDB(MongoDatabase base) {  // Создаем итератор

        Iterable<String> iterable = base.listCollectionNames();  // присваиваем итератору значения полученные из базы

        for (String collectionName : iterable) {
            System.out.println("Ваша база данных " + "\"" + base.getName() + "\"" + " содержит Коллекцию с именем " + collectionName);  //печатаем названия коллекций, которые находятся внутри базы
        }
    }

    public void checkBasesNamesOfMongoDB(MongoClient client) {

        Iterable<String> iterable = client.listDatabaseNames();

        for (String baseName : iterable) {
            System.out.println("BaseName = " + baseName);  //печатаем названия баз в нашей Базе Данных
        }
    }

    public void deleteCollection(MongoDatabase base, String collectionName) {

        base.getCollection(collectionName).deleteMany(new Document());
    }

}
