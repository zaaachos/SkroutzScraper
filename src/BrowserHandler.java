import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;

import javax.xml.stream.events.EndElement;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
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
    public HashMap<String, String> getDetails(){
        HashMap<String, String> details = new HashMap<String, String>();        //Initialize a Set with the details of the cheapest product we get from Skroutz.

        /*
            Try block
         */
        try {
            Document page = Jsoup.connect(main.getUrl()).get();     //Scrappe webpage

            String search = "span.search-bar-input-wrapper input[value]";       //Trying to modify the search bar in order to search for the user's choice
            Element searcher = page.select(search).first();                        //Capture the HTML value.
            driver.get(main.getUrl());


            Thread.sleep(500);     //wait

            driver.findElement(By.cssSelector(search)).sendKeys(this.getUser());          //modify search bar with the user choice

            Thread.sleep(500);     //wait

            driver.findElement(By.cssSelector(search)).submit();                        //submit

            main.setUrl(driver.getCurrentUrl());                //Get the new url.


            String list = "main.content.cf div.wrapper ol li.cf.card div h2 a";                       //Try to search for the product link with css attributes.
                                                                                                        //This is the list with all results we got.

            page = Jsoup.connect(main.getUrl()).get();               //Now, our page watch at our new link.
            Element product_link = page.select(list).first();           //Get the first element of the list, which is the cheapest product.

            Thread.sleep(500);     //wait

            /*
                   Error - 404. Didn't find anything.
             */
            try{
                driver.findElement(By.cssSelector("div.error-404.no-results"));
                System.out.println("Error 404. I didn't find anything with query " + this.getUser());
                return null;


            }catch (Exception e){

            }

            /*
                    Manufacturer page handler.
             */
            try {
                driver.findElement(By.id("manufacturer_show"));
                String popular_items = "ul.cf.list.tile.minisku.full li.card h2 a[href]";                   //Capture the most popular of the items in the popular items list.
                Element popular = page.selectFirst(popular_items);

                main.setUrl("https://www.skroutz.gr/"+popular.attr("href"));
                driver.get(main.getUrl());

            }catch (Exception e){

            }

            /*
                    Other Cases of search.
             */
            try{
                driver.findElement(By.id("search_search"));
                String others = "ol.cf.list.tile.minisku.full li.card h2 a[href]";                   //Capture the most popular of the items in the popular items list.
                Element other = page.selectFirst(others);

                main.setUrl("https://www.skroutz.gr/"+other.attr("href"));
                driver.get(main.getUrl());
            }catch (Exception e){

            }

            /*
                    Products page handler.
             */
            try{
                driver.findElement(By.id("categories_show"));
                main.setUrl("https://www.skroutz.gr/"+product_link.attr("href"));                       //Capture the cheapest of the products.
                driver.get(main.getUrl());
            }
            catch (Exception e){

            }



            ////////////////////////////////////////////////////////   Now trying to get the details we want   ///////////////////////////////////////////////////////////////////////////////////


            main.setUrl(driver.getCurrentUrl());                //Set the url as the cheapest product link.
            System.out.println("\n\n" + main);
            String info = "main.scrollable div.prices.js-prices.section.content ol.list.sku-list.blp-enabled li.cf.card.js-product-card";                //Get Shop Info.



            page = Jsoup.connect(main.getUrl()).get();               //Now, our page watch at our new link.
            Element shop_info = page.select(info).first();           //Get the first element of the list, which is the cheapest product.



            String shop_name = info + " div.shop.cf div.shop-name";         //get the name of the shop
            Element name = page.selectFirst(shop_name);

            details.put("Shop Name",name.text());        //save tha shop name in details.

            String product_name = info + " div.description div.item h3 a[title]";           //get the original name of the product.
            String shop_website = info + " div.description div.item h3 a[href]";            //get the shop website.
            Element prod_name = page.selectFirst(product_name);
            Element shoplink = page.selectFirst(shop_website);


            String seller = "https://www.skroutz.gr/" + shoplink.attr("href");              //save the website link.

            details.put("Shop Website",seller);                          //store the shop website to details.


            details.put("Product FullName",prod_name.text());        //save the product name in details.

            String availability = info + " div.description div.item p.availability span.availability";          //get product availability
            Element avail = page.selectFirst(availability);

            details.put("Availability",avail.text());       //save the availability in details.

            String product_price = info + " div.price a";                   //get the product price.
            Element price = page.selectFirst(product_price);

            details.put("Price",price.text());                      //store the cheapest price we found.

            String extraCost1 = info + " div.price div div.price-content span.final-price a";
            String final_price = driver.findElement(By.cssSelector(extraCost1)).getText();



            Thread.sleep(500);     //wait


            //main.setUrl(seller);
            //driver.get(main.getUrl());


            Thread.sleep(5000);     //wait

            driver.close();         //close the browser.



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
