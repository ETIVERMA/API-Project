package qtriptest.APITests;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.http.Method;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
//import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.Test;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.UUID;
import org.json.simple.JSONObject;

public class testCase_API_04 {
    @Test(groups = "API Tests")
    public void invalidRegistration(){
        RestAssured.baseURI ="https://content-qtripdynamic-qa-backend.azurewebsites.net";
        RestAssured.basePath= "/api/v1/register";
        
        Random random = new Random();
        String email = random.nextInt()+"@gmail.com";
        HashMap<String,String> hmap = new HashMap<>();
        hmap.put("email", email);
        hmap.put("password","Password");
        hmap.put("confirmpassword","Password");
        
        JSONObject json = new JSONObject(hmap);
        RequestSpecification postHttpRequest = RestAssured.given().header("Content-Type", "application/json");
        postHttpRequest.body(json);
    
        Response response =  postHttpRequest.request(Method.POST);
        int statusCode = response.getStatusCode();
        System.out.println(statusCode);
        Assert.assertEquals(statusCode, 201);
       
        
        RequestSpecification postHttpRequest2 = RestAssured.given().header("Content-Type", "application/json");
        postHttpRequest2.body(json);
        Response response2 = postHttpRequest2.request(Method.POST);
        int statusCode2 = response2.getStatusCode();
        String responseBody = response2.asPrettyString();
        System.out.println(responseBody);
        System.out.println(statusCode2);
        Assert.assertEquals(statusCode2, 400);
       
        

    }   
    
}

  

