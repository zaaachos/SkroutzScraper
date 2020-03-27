import org.jsoup.Jsoup;                     //imports
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class Skroutz {

    public static void main(String[] args) {
        /*
            Skroutz url to scrape
         */
        final String url = "https://www.skroutz.gr/c/40/kinhta-thlefwna/m/15053/Xiaomi.html?from=latest_keyphrases&keyphrase=redmi+note+8&o=xiaomi+redmi+note+8&order_by=pricevat";
        /*
            Try block
         */
        try {
            final Document page = Jsoup.connect(url).get();     //Scrappe webpage

            String search = "span.search-bar-input-wrapper input[value]";       //Trying to modify the search bar in order to search for the user query
            Elements searcher = page.select(search);                        //Capture the HTML value.
            System.out.println(searcher.size());                            //Prints the size we get from our elements we read.

            for(Element e:searcher) {
                System.out.println(e.attr("value"));            //Prints the context that user search for.
            }


        }
        /*
            Catch block
         */
        catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
