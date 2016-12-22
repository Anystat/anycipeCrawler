package ru.anystat.anycipe.crawler;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TestMain {


    public static void main(String[] args) {
        final String SITE = "http://www.say7.info/cook/recipe/127-Lazanya.html";
        final String USER_AGENT = "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/535.1 (KHTML, like Gecko) Chrome/13.0.782.112 Safari/535.1";

        Matcher matcher;
        Pattern pattern;

        String text = "200-250 г листов лазаньи";


        Document doc;

        try {
            Connection connection = Jsoup.connect(SITE).userAgent(USER_AGENT);
            doc = connection.get();

//            Elements elements = doc.select(".p-summary");

            Elements ingred = doc.body().select(".p-ingredient");
            System.out.println('\u2013');
            System.out.println('\u00bd');
            //–
            for (Element elem : ingred) {
                //System.out.println(elem.text());

               // pattern = Pattern.compile("(^[½|¼|¾]?\\d{0,5}[\\.,/-]?\\d{0,5}]?)?(\\u00A0|\\u0020)?(г.|г|кг.|кг|мл.|мл|л.|л|ч.л.|ч.л|ст.л.|ст.л)?(\\u00A0|\\u0020)(.*$)");  // это в matcher.split
                pattern=Pattern.compile("^\\d{0,3}[\\.-\\u2013]?");

                matcher = pattern.matcher(elem.text());

                while (matcher.find()) {

                    for (int i = 0; i < matcher.groupCount() + 1; i++) {

                        String match = matcher.group(i);
                        System.out.println("i = " + i + "  " + match);
                    }
                }
            }


        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
