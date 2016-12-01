package ru.anystat.anycipe.crawler;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class CrawlerLeg {

    private final String USER_AGENT = "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/535.1 (KHTML, like Gecko) Chrome/13.0.782.112 Safari/535.1";
    private List<String> links = new LinkedList<String>();
    private String mainUrl;
    private int lastFlag;
    private int stepFlag = 0;


    public boolean crawl(String url, int i) {

        checkFlag(i, url);
        stepFlag++;

//        ParsingSay7Info pars = new ParsingSay7Info();

        String regex = "^" + mainUrl + "(cook/|recipe/|linkz_start).+$";
        Pattern pattern = Pattern.compile(regex);

        links.clear();

        try {
            Connection connection = Jsoup.connect(url).userAgent(USER_AGENT);
            Document document = connection.get();

            if ((connection.response().statusCode() == 200) && (url.contains("recipe/"))) // 200 is the HTTP OK status code
            // indicating that everything is great.
            {
                System.out.println("\n**Visiting** Received web page at " + url);
//                pars.parsing(document);
                new ParsingSay7Info(document);
            }
            if (!connection.response().contentType().contains("text/html")) {
                System.out.println("**Failure** Retrieved something other than HTML");
                return false;
            }

            Elements linksOnPage = document.select("a[href]");
            System.out.println("Found (" + linksOnPage.size() + ") links");

            int count = 0;
            for (Element link : linksOnPage) {
                Matcher matcher = pattern.matcher(link.absUrl("href"));

                while (matcher.find()) {
                    links.add(matcher.group());
                    count++;
                }
            }
            System.out.println("Added (" + count + ") additional links");
            return true;
        } catch (IOException ioe) {

            return false;
        }
    }

    private void checkFlag(int currentFlag, String url) {

        if (stepFlag == 0) {
            lastFlag = currentFlag;
            mainUrl = url;
        }

        if (lastFlag == currentFlag) {

        } else {
            lastFlag = currentFlag;
            mainUrl = url;
        }

    }

    public List<String> getLinks() {
        return links;
    }


}
