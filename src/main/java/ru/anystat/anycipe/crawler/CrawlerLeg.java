package ru.anystat.anycipe.crawler;

import org.apache.log4j.Logger;
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
    private int lastFlag;
    private int stepFlag = 0;
    private String mainUrl;
    private final String USER_AGENT = "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/535.1 (KHTML, like Gecko) Chrome/13.0.782.112 Safari/535.1";
    private List<String> links = new LinkedList<String>();
    private List<Document> pagesWithRecipes = new LinkedList<Document>();
    private final Logger logger = Logger.getLogger(CrawlerLeg.class);


    public boolean crawl(String url, int i) {

        checkFlag(i, url);
        stepFlag++;

        String regex = "^" + mainUrl + "(cook/|recipe/|linkz_start).+$";
        Pattern pattern = Pattern.compile(regex);

        links.clear();

        try {
            Connection connection = Jsoup.connect(url).userAgent(USER_AGENT);
            Document document = connection.get();

            if ((connection.response().statusCode() == 200) && (url.contains("recipe/"))) {
                logger.info("\n**Visiting** Received web page at " + url);
                pagesWithRecipes.add(document);
            }

            if (!connection.response().contentType().contains("text/html")) {
                logger.error("**Failure** Retrieved something other than HTML");
                return false;
            }

            Elements linksOnPage = document.select("a[href]");
            logger.info("Found (" + linksOnPage.size() + ") links");

            int count = 0;
            for (Element link : linksOnPage) {
                Matcher matcher = pattern.matcher(link.absUrl("href"));

                while (matcher.find()) {
                    links.add(matcher.group());
                    count++;
                }
            }
            logger.info("Added (" + count + ") additional links");
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

        if (lastFlag != currentFlag) {
            lastFlag = currentFlag;
            mainUrl = url;
        }
    }

    public List<String> getLinks() {
        return links;
    }

    public List<Document> getPagesWithRecipes() {
        return pagesWithRecipes;
    }

}
