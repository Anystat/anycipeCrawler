package ru.anystat.anycipe.crawler;

import org.bson.Document;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static org.junit.Assert.assertEquals;

/**
 * @author ustits
 */
public class PrepareReceiptTest {

    private String receiptName;
    private String link;
    private List<String> ingredients;
    private String description;
    private String instruction;

    @Before
    public void setUp() throws Exception {
        Random random = new Random();
        receiptName = String.valueOf(random.nextDouble());
        link = String.valueOf(random.nextDouble());
        ingredients = new ArrayList<String>();
        for (int i = 0; i < random.nextInt(10); i++) {
            ingredients.add(String.valueOf(random.nextDouble()));
        }
        description = String.valueOf(random.nextDouble());
        instruction = String.valueOf(random.nextDouble());
    }

    @Test
    public void testCorrectDocumentCreated() throws Exception {
        PrepareReceipt.createReceipt(receiptName, link, ingredients, description, instruction);

        assertEquals(1, PrepareReceipt.getListOfReceipts().size());

        Document document = PrepareReceipt.getListOfReceipts().get(0);

        assertEquals(receiptName, document.get("receipt"));
        assertEquals(link, document.get("link"));
        List<Document> ingredients = (ArrayList<Document>) document.get("ingredients");

        for (int i = 0; i < ingredients.size(); i++) {
            assertEquals(this.ingredients.get(i), ingredients.get(i).get("name"));
        }
        assertEquals(this.ingredients.size(), ingredients.size());

        assertEquals(description, document.get("description"));
        assertEquals(instruction, document.get("instruction"));
    }
}