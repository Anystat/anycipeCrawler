package ru.anystat.anycipe.crawler;

import java.util.*;


public class Crawler {

    private Set<String> pagesVisited = new HashSet<String>();
    private List<String> pagesToVisit = new LinkedList<String>();


    public void search(ArrayList<String> url) {

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

                leg.crawl(currentUrl,i); // Lots of stuff happening here. Look at the crawl method in ru.anystat.anycipe.crawler.CrawlerLeg
                pagesToVisit.addAll(leg.getLinks());

                System.out.println("\n**Done** Visited " + pagesVisited.size() + " web page(s)");
                System.out.println("Pages left: " + pagesToVisit.size());

            }
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
