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
    // We'll use a fake USER_AGENT so the web server thinks the robot is a normal web browser.
    private final String USER_AGENT = "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/535.1 (KHTML, like Gecko) Chrome/13.0.782.112 Safari/535.1";
    private List<String> links = new LinkedList<String>();
    private String regex;
    private int count;
    private ParsingSay7Info pars;
    private Pattern pattern;
    private Matcher matcher;
    private String mainUrl;
    private int lastFlag;
    private int stepFlag = 0;


    /**
     * This performs all the work. It makes an HTTP request, checks the response, and then gathers
     * up all the links on the page. Perform a searchForWord after the successful crawl
     *
     * @param url - The URL to visit
     * @return whether or not the crawl was successful
     */


    public boolean crawl(String url, int i) {

        checkFlag(i, url);
        stepFlag++;

        pars = new ParsingSay7Info();

        regex = "^" + mainUrl.toString() + "(cook/|recipe/|linkz_start).+$";
        pattern = Pattern.compile(regex);

        links.clear();

        try {
            Connection connection = Jsoup.connect(url).userAgent(USER_AGENT);
            Document document = connection.get();

            if ((connection.response().statusCode() == 200) && (url.toString().contains("recipe/"))) // 200 is the HTTP OK status code
            // indicating that everything is great.
            {
                System.out.println("\n**Visiting** Received web page at " + url);
                pars.parsing(document);
            }
            if (!connection.response().contentType().contains("text/html")) {
                System.out.println("**Failure** Retrieved something other than HTML");
                return false;
            }

            Elements linksOnPage = document.select("a[href]");
            System.out.println("Found (" + linksOnPage.size() + ") links");


            count = 0;
            for (Element link : linksOnPage) {
                matcher = pattern.matcher(link.absUrl("href"));

                while (matcher.find()) {
                    links.add(matcher.group());
                    //  System.out.println(matcher.group());
                    count++;
                }
            }
            System.out.println("Added (" + count + ") additional links");
            return true;
        } catch (IOException ioe) {
            // We were not successful in our HTTP request
            return false;
        }
    }


    public void checkFlag(int currentFlag, String url) {


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
