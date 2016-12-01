package ru.anystat.anycipe.crawler;

import org.apache.log4j.Logger;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Lofv on 25.11.2016.
 */
public class RecursionCrawler {

    final Logger logger = Logger.getLogger(RecursionCrawler.class);
    private final String USER_AGENT = "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/535.1 (KHTML, like Gecko) Chrome/13.0.782.112 Safari/535.1";
    private String mainUrl;
    private Set<String> pagesVisited = new HashSet<String>();
    private List<String> pagesToVisit = new LinkedList<String>();

    RecursionCrawler() {
    }

    RecursionCrawler(ArrayList<String> urls) {

        for (String url : urls) {
            mainUrl = url;

            Recursion(mainUrl);
        }
    }

    public void Recursion(String url) {

        ParsingSay7Info pars = new ParsingSay7Info();

        String regex = "^" + mainUrl.toString() + "(cook/|recipe/|linkz_start).+$";
        Pattern pattern = Pattern.compile(regex);

        try {
            Connection connection = Jsoup.connect(url).userAgent(USER_AGENT);
            Document document = connection.get();

            if ((connection.response().statusCode() == 200) && (connection.response().contentType().contains("text/html"))) {
                if (url.contains("recipe/")) {
                    pars.parsing(document);
                } else {
                    Elements linksOnPage = document.select("a[href]");

                    for (Element link : linksOnPage) {
                        Matcher matcher = pattern.matcher(link.absUrl("href"));

                        while (matcher.find()) {
                            pagesToVisit.add(matcher.group());
                        }
                    }
                }
            } else {
                logger.error("Something wrong with site`s content or connection response");
            }
            String nextUrl = nextUrl();
            if (nextUrl != null) {
                Recursion(nextUrl);
            } else {
                logger.warn("Конец кина, ссылки кончились");
            }
        } catch (IOException ioe) {
            logger.error("We were not successful in our HTTP request");
        }

    }

    private String nextUrl() {
        String nextUrl;
        do {
            logger.info("Pages left: " + pagesToVisit.size());
            if (pagesToVisit.isEmpty()) {
                return nextUrl = null;
            } else {
                nextUrl = pagesToVisit.remove(0);
            }
        } while (pagesVisited.contains(nextUrl));
        pagesVisited.add(nextUrl);
        return nextUrl;
    }

}
