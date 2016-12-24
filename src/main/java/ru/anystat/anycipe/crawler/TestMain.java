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
//        String regex = "(^[½|¼|¾]?\\d{0,5}[\\u2013\\.,/-]?\\d{0,5}]?)?(\\u00A0|\\u0020)?((г.|г|кг.|кг|мл.|мл|л.|литр|л|ч.л.|ч.л|ст.л.|ст.л)?(\\u00A0|\\u0020))?(.*$)";
        String regex = "(^[½|¼|¾]?\\d{0,5}[\\u2013\\.,/-]?\\d{0,5}]?)?(\\u00A0|\\u0020)?((г.|г|кг.|кг|мл.|мл|л.|л|литр|ч.л.|ч.л|ст.л.|ст.л)(\\u00A0|\\u0020))?(.*$)";

//        String text = "растительное масло";


        Document doc;

        try {
            Connection connection = Jsoup.connect(SITE).userAgent(USER_AGENT);
            doc = connection.get();

            Elements ingred = doc.body().select(".p-ingredient");

            for (Element elem : ingred) {

                pattern = Pattern.compile(regex);
                matcher = pattern.matcher(elem.text());

                while (matcher.find()) {

                    for (int i = 1; i < matcher.groupCount() + 1; i++) {

                        String match = matcher.group(i);
                        if (matcher.group(i) != null) {
                            System.out.println((matcher.group(i).equals("\u00A0")) ||(matcher.group(i).equals("\u0020"))||(matcher.group(i).equals("")));
                            System.out.println(matcher.group(i).equals("\u00A0"));
                            System.out.println(matcher.group(i).equals("\u0020"));
                            System.out.println(matcher.group(i).equals(""));
                            System.out.println(!((matcher.group(i).equals("\u00A0"))||(matcher.group(i).equals("\u0020"))|(matcher.group(i).equals(""))));
                            if (!((matcher.group(i).equals("\u00A0"))||(matcher.group(i).equals("\u0020"))|(matcher.group(i).equals("")))) {
                        System.out.println(matcher.group(i));
                            } else {
                                System.out.println("i = " + i + "  " + matcher.group(i));
                            }
                        }
                    }
                }
            }


        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
