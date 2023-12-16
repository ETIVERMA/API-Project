package qtriptest.APITests;
import io.restassured.http.ContentType;
import io.restassured.module.jsv.JsonSchemaValidator;
import io.restassured.path.json.JsonPath;
import io.restassured.response.ResponseBody;
//import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.*;
import org.testng.asserts.SoftAssert;
import io.restassured.http.Method;
import io.restassured.matcher.ResponseAwareMatcher;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.RestAssured;
import org.json.simple.JSONObject;
import java.io.File;
import java.util.LinkedHashMap;
import java.util.UUID;
public class testCase_API_02 {
 @Test(groups = "API Tests")
 public void searchCity(){
    RestAssured.baseURI ="https://content-qtripdynamic-qa-backend.azurewebsites.net";
    RestAssured.basePath ="/api/v1/cities";

    String getPayload = "[{\"id\":\"bengaluru\",\"city\":\"Bengaluru\",\"description\":\"100+Places\",\"image\":\"https://images.pexels.com/photos/3573382/pexels-photo-3573382.jpeg?auto=compress&cs=tinysrgb&dpr=2&h=750&w=1260\"}]";
    RequestSpecification postHttpRequest = RestAssured.given().contentType(ContentType.JSON).queryParam("q", "beng");
    postHttpRequest.body(getPayload);
    Response response =postHttpRequest.request(Method.GET);

    int statusCode = response.getStatusCode();
    Assert.assertEquals(statusCode, 200);
    String body = response.getBody().asString();
   // System.out.println(response.getBody().asString());
    //JsonPath jsonPath = response.jsonPath();
    JsonPath jsonPath = new JsonPath(body);
    String desc = jsonPath.getString("description");
    System.out.println("----"+desc);
    Assert.assertEquals(desc, "[100+ Places]", "not matching");
    int searchSize = jsonPath.getInt("size()");
    Assert.assertEquals(searchSize, 1);
    System.out.println(searchSize);
    //String rootPath = System.getProperty("user.dir");
    File schema = new File("./src/test/resources/schema.json");
    JsonSchemaValidator schemaValidator = JsonSchemaValidator.matchesJsonSchema(schema);
    response.then().assertThat().body(schemaValidator);
   }
}
