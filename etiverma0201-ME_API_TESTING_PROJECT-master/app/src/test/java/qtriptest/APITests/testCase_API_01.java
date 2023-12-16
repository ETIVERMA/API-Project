
package qtriptest.APITests;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.ResponseBody;
import org.apache.poi.hssf.usermodel.HSSFBorderFormatting;
//import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.*;
import org.testng.asserts.SoftAssert;
import io.restassured.http.Method;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.RestAssured;
import java.util.HashMap;
import java.util.Random;
import java.util.UUID;
import org.json.simple.JSONObject;



public class testCase_API_01 {
    // @BeforeTest
    // public void registerPost(){
    // RestAssured.baseURI = "https://content-qtripdynamic-qa-backend.azurewebsites.net";
    // RestAssured.basePath ="/api/v1/register";
    // String postPayload = "{\"email\":\"ayz333@gmail.com\",\"password\":\"Password\",\"confirmpassword\":\"Password\"}";
    // RequestSpecification postHttpRequest = RestAssured.given().header("Content-Type", "application/json");
    // postHttpRequest.body(postPayload);
    // Response response =  postHttpRequest.request(Method.POST);
    // int statusCode = response.getStatusCode();
    // Assert.assertEquals(statusCode, 201);
    
  // }
     
   @Test(groups = "API Tests")
    public void userlogin(){
    RestAssured.baseURI ="https://content-qtripdynamic-qa-backend.azurewebsites.net";
    RestAssured.basePath ="/api/v1/login"; 
    String postPayload = "{\"email\":\"ayz333@gmail.com\",\"password\":\"Password\"}";
    RequestSpecification postHttpRequest = RestAssured.given().header("Content-Type", "application/json");
    postHttpRequest.body(postPayload);
    
    Response response =  postHttpRequest.request(Method.POST); 
    String body = response.getBody().asString();
    //String respBody = receivedResponse.getBody().asString();
    System.out.println(response.asPrettyString());
    
    int statusCode = response.getStatusCode();
    Assert.assertEquals(statusCode, 201);
     
    JsonPath jsonpath = response.jsonPath();
    String id = jsonpath.getString("data.token").toString();
    String token = jsonpath.getString("data.id").toString();
    System.out.println(token);
    System.out.println(id);

   }
}
   
