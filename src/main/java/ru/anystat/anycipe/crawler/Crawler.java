package ru.anystat.anycipe.crawler;

import org.apache.log4j.Logger;

import java.util.*;


public class Crawler {

    private final Logger logger = Logger.getLogger(Crawler.class);
    private Set<String> pagesVisited = new HashSet<String>();
    private List<String> pagesToVisit = new LinkedList<String>();

    public void search(ArrayList<String> url) {

        ParsingSay7Info pars = new ParsingSay7Info();
        CrawlerLeg leg = new CrawlerLeg();

        for (int i = 0; i < url.size(); i++) {
            pagesToVisit.add(url.get(i).toString());
            for (int j = 0; j < pagesToVisit.size(); j++) {
                String currentUrl;
                if (pagesToVisit.isEmpty()) {
                    currentUrl = url.get(i).toString();
                } else {
                    currentUrl = nextUrl();
                }
                leg.crawl(currentUrl, i);
                pagesToVisit.addAll(leg.getLinks());

                logger.info("\n**Done** Visited " + pagesVisited.size() + " web page(s)");
                logger.info("Pages left: " + pagesToVisit.size());
            }
        }
        pars.parsing(leg.getPagesWithRecipes());
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
