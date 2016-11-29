package ru.anystat.anycipe.crawler;

import java.util.*;


public class Crawler {

    String currentUrl;
    CrawlerLeg leg;
    private Set<String> pagesVisited = new HashSet<String>();
    private List<String> pagesToVisit = new LinkedList<String>();

    public void search(ArrayList url) {

        leg = new CrawlerLeg();


        for (int i = 0; i < url.size(); i++) {
            pagesToVisit.add(url.get(i).toString());
            for (int j = 0; j < pagesToVisit.size(); j++) {

                if (pagesToVisit.isEmpty()) {
                    currentUrl = url.get(i).toString();
                } else {
                    currentUrl = nextUrl();
                }

                leg.crawl(currentUrl,i); // Lots of stuff happening here. Look at the crawl method in ru.anystat.anycipe.crawler.CrawlerLeg
                pagesToVisit.addAll(leg.getLinks());

                System.out.println("\n**Done** Visited " + pagesVisited.size() + " web page(s)");
                System.out.println("Pages left: " + pagesToVisit.size());

            }
        }
    }

    /**
     * Returns the next URL to visit (in the order that they were found). We also do a check to make
     * sure this method doesn't return a URL that has already been visited.
     *
     * @return
     */
    private String nextUrl() {
        String nextUrl;
        do {
            nextUrl = pagesToVisit.remove(0);
        } while (pagesVisited.contains(nextUrl));
        pagesVisited.add(nextUrl);
        return nextUrl;
    }


}
