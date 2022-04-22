import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

import static io.restassured.RestAssured.given;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.path.xml.XmlPath;
import io.restassured.response.Response;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class LibraryAPI {

    public static void main(String[] args) throws IOException {

        DataDriven d=new DataDriven();
        ArrayList data=d.getData("RestAssured");

        // System.out.println(data.get(0));
        System.out.println(data.get(1));
        System.out.println(data.get(2));
        System.out.println(data.get(3));
        System.out.println(data.get(4));

        //Hashmap stores Key Value pair (like dictionary)
        HashMap<String, Object> map = new HashMap<>();
        map.put("name", data.get(1));
        map.put("isbn", data.get(2));
        map.put("aisle", data.get(3));
        map.put("author", data.get(4));

//        {
//            "name": "Fantasy",
//            "isbn" : "bk123445",
//            "aisle" :"13",
//            "author":"rich"
//
//        }



       // System.out.println(map);
        RestAssured.baseURI="http://216.10.245.166";
       // System.out.println("Rest Assures Test");
        Response resp=given().
                header("Content-Type","application/json").
                body(map).
                when().log().all().
                post("/Library/Addbook.php").
                then().assertThat().statusCode(200).
                extract().response();
       // System.out.println("response is: "+ resp.toString());
        JsonPath js= ReusableMethods.rawToJson(resp);
        //System.out.println(js);
        String id=js.get("ID");
        System.out.println("Book ID is: " +id);





    }
}
