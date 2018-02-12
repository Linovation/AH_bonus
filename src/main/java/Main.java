import com.jayway.jsonpath.Configuration;
import com.jayway.jsonpath.JsonPath;
import net.minidev.json.JSONArray;
import net.minidev.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.List;


public class Main {

    public static String getHTML(String urlToRead) throws Exception {
        StringBuilder result = new StringBuilder();
        URL url = new URL(urlToRead);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        String line;
        while ((line = rd.readLine()) != null) {
            result.append(line);
        }
        rd.close();
        return result.toString();
    }

    public static void main(String[] args) throws Exception {
        String input = getHTML("https://www.ah.nl/service/rest/delegate?url=/bonus&_=1517753219151");
        Object document = Configuration.defaultConfiguration().jsonProvider().parse(input);
        JSONArray products = JsonPath.read(input, "$..product");
       // List<String> discount = JsonPath.read(input, "$..product.discount.label") ;
        //products.forEach(product -> System.out.println(product) );
       // discount.forEach(discounts -> System.out.println(discounts));
        System.out.println("done");
        for(int i=0; i<products.size(); i++){

            String description = JsonPath.read(products.get(i),"$.description");
            String discountType = JsonPath.read(products.get(i),"$.discount.label");
            String unitSize = JsonPath.read(products.get(i),"$.unitSize");
            Double pricenow = JsonPath.read(products.get(i),"$.priceLabel.now");
            Double pricewas = JsonPath.read(products.get(i),"$.priceLabel.was");

            System.out.println(description);
            System.out.println(discountType);
            System.out.println("Unit Size:"+unitSize);
            System.out.println("Now:"+pricenow);
            System.out.println("Was:"+pricewas);

        }
    }



}