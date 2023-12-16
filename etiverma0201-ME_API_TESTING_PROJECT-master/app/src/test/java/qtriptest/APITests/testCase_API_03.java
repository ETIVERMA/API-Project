 package qtriptest.APITests;

//import groovyjarjarasm.asm.commons.Method;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.http.Method;
import io.restassured.module.jsv.JsonSchemaValidator;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
//import org.json.JSONObject;
import org.openqa.selenium.json.Json;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import org.json.simple.JSONObject;
import java.io.File;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Random;
import java.util.UUID;

public class testCase_API_03 {
@Test(groups = "API Tests")
public void registerPost(){
RestAssured.baseURI = "https://content-qtripdynamic-qa-backend.azurewebsites.net";
RestAssured.basePath ="/api/v1/register";
 //String postPayload = "{\"email\":\"ayz3331@gmail.com\",\"password\":\"Password\",\"confirmpassword\":\"Password\"}";
Random random = new Random();
String randomEmail = "test"+random.nextInt()+"@gmail.com";
HashMap<String, String> hmap = new HashMap<>();
hmap.put("email", randomEmail);
hmap.put("password", "Password");
hmap.put("confirmpassword", "Password");
JSONObject json1 = new JSONObject(hmap);

// String postPayload = "{\"email\":\"ayz2212@gmail.com\",\"password\":\"Password\",\"confirmpassword\":\"Password\"}";
RequestSpecification postHttpRequest1 = RestAssured.given().header("Content-Type", "application/json");
postHttpRequest1.body(json1);
System.out.println(postHttpRequest1.body(json1).toString());
Response response1 = postHttpRequest1.request(Method.POST);
int statusCode1 = response1.getStatusCode();
Assert.assertEquals(statusCode1, 201);
System.out.println(statusCode1);

//LOGIN
RestAssured.basePath ="/api/v1/login";
hmap.remove("confirmpassword");
JSONObject json2 = new JSONObject(hmap);
RequestSpecification postHttpRequest2 = RestAssured.given().header("Content-Type", "application/json");
postHttpRequest2.body(json2);
System.out.println(postHttpRequest2.body(json2).toString());
Response response2 = postHttpRequest2.request(Method.POST);
int statusCode2 = response2.getStatusCode();
Assert.assertEquals(statusCode2, 201);
System.out.println(statusCode2);

// NEW RESERVATION
//RestAssured.baseURI ="https://content-qtripdynamic-qa-backend.azurewebsites.net";
//RestAssured.basePath ="/api/v1/reservations/new";

JsonPath jsonpath = response2.jsonPath();
String token = jsonpath.get("data.token").toString();
String id = jsonpath.get("data.id").toString();
JSONObject jsonObject1 = new JSONObject();
String name = "Test1" + random.nextInt();
jsonObject1.put("userId", id);
jsonObject1.put("name", name);
jsonObject1.put("date", "2023-12-12");
jsonObject1.put("person", "1");
jsonObject1.put("adventure", "2447910730");
        
RestAssured.basePath = "/api/v1/reservations/new";
RequestSpecification postHttpRequest3 =  RestAssured.given().header("Authorization",
                 " Bearer " + token).header("Content-Type", "application/json").queryParam("q", "beng");
postHttpRequest3.body(jsonObject1);
Response response3 = postHttpRequest3.request(Method.POST);
int statusCode3 = response3.getStatusCode();
Assert.assertEquals(statusCode3, 200,"Booking is successfully done");
System.out.println(response3.prettyPeek());
System.out.println(statusCode3);

// JsonPath jsonPath3 = response3.jsonPath();
// System.out.println(jsonPath3.toString());

// RESERVATION CONFIRMATION
RestAssured.baseURI="https://content-qtripdynamic-qa-backend.azurewebsites.net";
RestAssured.basePath="/api/v1/reservations";
JSONObject json4 = new JSONObject(hmap);
RequestSpecification postHttpRequest4 = RestAssured.given().header("Content-Type", "application/json");
//postHttpRequest2.body(json4);
postHttpRequest4.queryParam("id", id);
postHttpRequest4.header("Authorization", "Bearer " + token);

Response response4 = postHttpRequest4.request(Method.GET);
int statusCode4 = response4.getStatusCode();
String responseBody = response4.asPrettyString();
System.out.println(responseBody);
System.out.println(statusCode4);
Assert.assertEquals(statusCode4, 200,"Booking is not successfully done");

}
}
