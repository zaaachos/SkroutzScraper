import org.jsoup.Jsoup;                     //imports
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.*;

import java.util.Scanner;


public class Skroutz {

    public static void main(String[] args) {
        /*
            Skroutz url to scrape
         */
        final String url = "https://www.skroutz.gr/";

        WebDriver driver = new ChromeDriver();                  //Open Browser.
        driver.get(url);

        /*
            Try block
         */
        try {
            final Document page = Jsoup.connect(url).get();     //Scrappe webpage

            String search = "span.search-bar-input-wrapper input[value]";       //Trying to modify the search bar in order to search for the user query
            Elements searcher = page.select(search);                        //Capture the HTML value.
            System.out.println(searcher.size());                            //Prints the size we get from our elements we read.
            Scanner in = new Scanner(System.in);
            System.out.print("Search for: ");                   //Ask user what he wants to search for.
            String user = in.nextLine();

            Thread.sleep(2000);     //wait

            driver.findElement(By.cssSelector(search)).sendKeys(user);          //modify search bar with the user choice

            Thread.sleep(2000);     //wait

            driver.findElement(By.cssSelector(search)).submit();                        //submit






        }
        /*
            Catch block
         */
        catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
