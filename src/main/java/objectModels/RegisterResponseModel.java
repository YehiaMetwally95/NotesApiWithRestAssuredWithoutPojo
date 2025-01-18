package objectModels;

import com.google.gson.JsonObject;
import io.qameta.allure.Step;
import io.restassured.response.Response;
import lombok.Getter;
import yehiaEngine.assertions.CustomAssert;
import yehiaEngine.managers.ApisManager;

import java.util.List;

import static io.restassured.RestAssured.given;
import static yehiaEngine.managers.ApisManager.*;

public class RegisterResponseModel {
    //Variables
    JsonObject requestObject;
    //Getter Methods
    @Getter
    Response response;

    //Constructor to pass the response from Request Model to Response Model
    public RegisterResponseModel(JsonObject requestObject , Response response) {
        this.requestObject = requestObject;
        this.response = response;
    }

    //Validation Methods
    @Step("validateMassageFromResponse")
    public RegisterResponseModel validateMassageFromResponse(String message) {
        String actualMessage = getJsonStringValueFromResponse(response,"message");
        CustomAssert.assertEquals(actualMessage,message);
        return this;
    }

    @Step("validateStatusFromResponse")
    public RegisterResponseModel validateStatusFromResponse(String statusCode) {
        int actualStatusCode = ApisManager.getResponseCode(response);
        CustomAssert.assertEquals(actualStatusCode,Integer.parseInt(statusCode));
        return this;
    }

    @Step("validateSuccessFromResponse")
    public RegisterResponseModel validateSuccessFromResponse(String successFlag) {
        boolean actualSuccess = getJsonBooleanValueFromResponse(response,"success");
        CustomAssert.assertEquals(actualSuccess,Boolean.parseBoolean(successFlag));
        return this;
    }

    @Step("validateNameFromResponse")
    public RegisterResponseModel validateNameFromResponse() {
        String actualName = getJsonStringValueFromResponse(response,"data.name");
        String expectedName = requestObject.get("name").getAsString();
        CustomAssert.assertEquals(actualName,expectedName);
        return this;
    }

    @Step("validateEmailFromResponse")
    public RegisterResponseModel validateEmailFromResponse() {
        String actualEmail = getJsonStringValueFromResponse(response,"data.email");
        String expectedEmail = requestObject.get("email").getAsString();
        CustomAssert.assertEquals(actualEmail,expectedEmail);
        return this;
    }

    public JsonObject getRequest() {
        return requestObject;
    }

    public List<String> getUserCredentials(){
        return List.of(requestObject.get("email").getAsString(),requestObject.get("password").getAsString());
    }

/*    //Get Needed Data from Registration Model and pass it to Login Model
    @Step("Get New User Credentials")
    public LoginRequestModel getNewUserCredentials() {

        return new LoginRequestModel(
                requestObject.getEmail(),
                requestObject.getPassword()
        );
    }*/
}
