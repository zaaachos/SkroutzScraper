import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import java.util.ArrayList;
import java.util.Scanner;
import java.util.Set;

public class BrowserHandler {
    final String url = "https://www.skroutz.gr/";
    String user;

    // Default Constructor.
    public BrowserHandler(){
        user = null;
    }

    // Main Constructor.
    public BrowserHandler(String use){
        user = use;
    }

    UrlHandler main = new UrlHandler(url);          //Url Handler in order to take new urls from the WebDriver.


    WebDriver driver = new ChromeDriver();


    /*
        Setter to take user choice from the console.
     */
    public void setUser(String choice){
        user = choice;
    }

    /*
        Getter to get User Choice.
     */
    public String getUser(){
        return user;
    }

    /*
        Getter to get our details about our cheapest product we find. ( price, seller, shop location etc.)
     */
    public ArrayList<String> getDetails(){
        ArrayList<String> details = new ArrayList<String>();        //Initialize a Set with the details of the cheapest product we get from Skroutz.

        /*
            Try block
         */
        try {
            Document page = Jsoup.connect(main.getUrl()).get();     //Scrappe webpage

            String search = "span.search-bar-input-wrapper input[value]";       //Trying to modify the search bar in order to search for the user's choice
            Elements searcher = page.select(search);                        //Capture the HTML value.
            driver.get(main.getUrl());


            Thread.sleep(1000);     //wait

            driver.findElement(By.cssSelector(search)).sendKeys(this.getUser());          //modify search bar with the user choice

            Thread.sleep(1000);     //wait

            driver.findElement(By.cssSelector(search)).submit();                        //submit

            main.setUrl(driver.getCurrentUrl());                //Get the new url.

            System.out.println("New url: " +main);                  //Test if we catch the right link

            String list = "main.content.cf div.wrapper section.main-content ol.list.cf.line.blp-enabled li.cf.card div.details h2 a";                       //Try to search for the product link with css attributes.
                                                                                                                                                        //This is the list with all results we got.

            page = Jsoup.connect(main.getUrl()).get();               //Now, our page watch at our new link.
            Element product_link = page.select(list).first();           //Get the first element of the list, which is the cheapest product.

            System.out.println("\n\n" + main);

            Thread.sleep(1000);     //wait

            driver.findElement(By.cssSelector(list)).click();               //click on the cheapest product url.

            main.setUrl(driver.getCurrentUrl());                //Set the url as the cheapest product link.











        }
            /*
                Catch block
             */
        catch (Exception ex) {
            ex.printStackTrace();
        }


        return details;
    }


}
