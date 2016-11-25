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
 * Created by 23 on 25.11.2016.
 */
public class RecursionTestCrawler {
    private final String USER_AGENT = "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/535.1 (KHTML, like Gecko) Chrome/13.0.782.112 Safari/535.1";
    private String regex;
    private int count;
    private ParsingSay7Info pars;
    private Pattern pattern;
    private Matcher matcher;

    private Set<String> pagesVisited = new HashSet<String>();
    private List<String> pagesToVisit = new LinkedList<String>();

    RecursionTestCrawler() {
        // default constructor
    }

    RecursionTestCrawler(ArrayList<String> urls) {
        for (String url : urls) {
            testRecursion(url);
        }
    }

    public void testRecursion(String url) {
        System.out.println("Start crawler with url = " + url);
        pars = new ParsingSay7Info();


        regex = url.toString() + "(recipe/)|(linkz_start/).+$";
        pattern = Pattern.compile(regex);

        try {
            Connection connection = Jsoup.connect(url).userAgent(USER_AGENT);
            Document document = connection.get();

            if ((connection.response().statusCode() == 200) && (connection.response().contentType().contains("text/html"))) {
                if (url.contains("recipe/")) {

                    System.out.println("\n**Visiting** Received web page at " + url);
                    pars.parsing(document);
                } else {
                    System.out.println("url didn`t consist /recipe/");

                    Elements linksOnPage = document.select("a[href]");
                    count = 0;

                    for (Element link : linksOnPage) {

                        matcher = pattern.matcher(link.absUrl("href"));

                        while (matcher.find()) {

                            pagesToVisit.add(matcher.group());
                            count++;
                        }
                    }
                    System.out.println("Found (" + linksOnPage.size() + ") links" + " and were added (" + count + ") to the List");
                }
                testRecursion(nextUrl());
            } else {
                System.out.println("Something wrong with site`s content or connection response");
            }
        } catch (IOException ioe) {
            System.out.println("We were not successful in our HTTP request");
        }
    }


    private String nextUrl() {
        String nextUrl;
        do {
            nextUrl = pagesToVisit.remove(0);
        } while (pagesVisited.contains(nextUrl));
        pagesVisited.add(nextUrl);
        return nextUrl;
    }


}
