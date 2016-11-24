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
    private String regex;
    private List<String> links = new LinkedList<String>();
    private int count;
    private int flagParsingStart = 0;
    private ParsingSay7Info pars;
    private Pattern pattern;
    private Matcher matcher;
    private String mainUrl;
    /**
     * This performs all the work. It makes an HTTP request, checks the response, and then gathers
     * up all the links on the page. Perform a searchForWord after the successful crawl
     *
     * @param url - The URL to visit
     * @return whether or not the crawl was successful
     */
    public boolean crawl(String url) {

        if (flagParsingStart == 0) {
            mainUrl=url;//Флаг сделан для того, чтобы проверять прошла первая итерация или нет. Надо придумать, как сделать красивее.
            regex = url.toString() + ".*$";       //Так как без флага при второй итерации и делее получается новая ссылка, которая еще больше ссужает поиск страниц
        } else {
            regex = url.toString() + "recipe/.*$";
        }

        pars = new ParsingSay7Info();
        pattern = Pattern.compile(regex);


        flagParsingStart++;
        links.clear();

        try {
            Connection connection = Jsoup.connect(url).userAgent(USER_AGENT);
            Document document = connection.get();



            if ((connection.response().statusCode() == 200)&&(url.toString().contains("recipe"))) // 200 is the HTTP OK status code
            // indicating that everything is great.
            {
                pars.parsing(document);
                System.out.println("\n**Visiting** Received web page at " + url);
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
                System.out.println(link);
                System.out.println(matcher.find());
                while (matcher.find()) {
                    links.add(matcher.group());
                    System.out.println("true ++");
                    System.out.println(matcher.group());
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


    public List<String> getLinks() {
        return links;
    }

}
