import org.jsoup.Jsoup;                     //imports
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.*;

import java.util.HashMap;
import java.util.Scanner;


public class Skroutz {

    public static void main(String[] args) {
        HashMap<String, String> temp = new HashMap<String, String>();                   //Hashmap to store our product details.

        Scanner in = new Scanner(System.in);                    //Get the user choice.
        System.out.print("Search for: ");
        String choice = in.nextLine();

        BrowserHandler user = new BrowserHandler(choice);

        temp = user.getDetails();                   //Store the results.

        if (temp != null){
            for (String name: temp.keySet()){
                String key = name.toString();
                String value = temp.get(name).toString();
                System.out.println(key + " " + value);
            }
        }

        return;

    }
}
