package tests.TestClass;

import com.google.gson.JsonObject;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import yehiaEngine.managers.ApisManager;

import java.util.HashMap;
import java.util.Map;

import static yehiaEngine.managers.ApisManager.*;

public class TestClass {

    @Test
    public void test1(){
        String endpoint = "https://reqres.in/api/users/{user_id}";

        JsonObject object = new JsonObject();
        object.addProperty("user_id",3);

        Map<String,Object> map = new HashMap<>();
        map.put("user_id",3);

        GetRequest(endpoint, ApisManager.ParameterType.PATH,object);

        GetRequest(endpoint, ApisManager.ParameterType.PATH,map);

    }

    @Test
    public void test2(){
        String endpoint = "https://automationexercise.com/api/brandsList";

        JsonObject object = new JsonObject();
        object.addProperty("email","jmetwallym@gmail.com");

        Map<String,Object> map = new HashMap<>();
        map.put("email","jmetwallym@gmail.com");

        Response response1;
        Response response2;
        Response response3;


        response1 = GetRequest(endpoint, ParameterType.NULL,null);

        response2= GetRequest(endpoint, ParameterType.NULL,null);

        System.out.println(getJsonStringValueFromResponse(response1,"brands[0].brand"));
        System.out.println(getJsonStringValueFromResponse(response2,"brands[0].brand"));

        System.out.println(getJsonIntValueFromResponse(response1,"brands[0].id"));
        System.out.println(getJsonIntValueFromResponse(response2,"brands[0].id"));

        System.out.println(getJsonObjectFromResponse(response1,"brands[1]"));
        System.out.println(getJsonObjectFromResponse(response2,"brands[1]"));

        System.out.println(getListOfObjectsFromResponse(response1,"brands"));
        System.out.println(getListOfObjectsFromResponse(response2,"brands"));
    }
}
