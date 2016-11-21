import java.util.*;


public class Crawler {
    private static final int MAX_PAGES_TO_SEARCH = 100;
    private Set<String> pagesVisited = new HashSet<String>();
    private List<String> pagesToVisit = new LinkedList<String>();
    private ParsingSay7Info pars;


    //  http://www.netinstructions.com/how-to-make-a-simple-web-crawler-in-java/


    public void search(ArrayList url) {
        pars = new ParsingSay7Info();
        String currentUrl;

        CrawlerLeg leg = new CrawlerLeg();


        for (int i = 0; i < url.size(); i++) {
            pagesToVisit.add(url.get(i).toString());
            for (int j = 0; j < pagesToVisit.size(); j++) {


                if (pagesToVisit.isEmpty()) {
                    currentUrl = url.get(i).toString();

                } else {


                    currentUrl = nextUrl();
                }

                leg.crawl(currentUrl); // Lots of stuff happening here. Look at the crawl method in CrawlerLeg
                pagesToVisit.addAll(leg.getLinks());
                System.out.println("\n**Done** Visited " + pagesVisited.size() + " web page(s)");

                  pars.parsing(CrawlerLeg.htmlDocument);

               /* for (int j = 0; j < searchWord.size(); j++) {

                    boolean success = leg.searchForWord(searchWord.get(j).toString());
                    if (success) {
                        System.out.println(String.format("**Success** Word %s found at %s", searchWord.get(j).toString(), currentUrl));
                        // break;
                    }
                    this.pagesToVisit.addAll(leg.getLinks());
                }
                System.out.println("\n**Done** Visited " + this.pagesVisited.size() + " web page(s)");
*/


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
