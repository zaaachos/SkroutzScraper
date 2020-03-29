import org.jsoup.Jsoup;                     //imports
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.*;

import java.util.Scanner;


public class Skroutz {

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        System.out.print("Search for: ");
        String choice = in.nextLine();
        BrowserHandler user = new BrowserHandler(choice);
        System.out.println("\n\n" + user.getDetails());





    }
}
