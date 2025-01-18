package objectModels;

import com.google.gson.JsonObject;
import io.qameta.allure.Step;
import io.restassured.response.Response;
import lombok.Getter;
import org.testng.Assert;
import pojoClasses.LoginRequestPojo;
import pojoClasses.LoginResponsePojo;
import yehiaEngine.assertions.CustomAssert;
import yehiaEngine.managers.ApisManager;

import static yehiaEngine.managers.ApisManager.getJsonBooleanValueFromResponse;
import static yehiaEngine.managers.ApisManager.getJsonStringValueFromResponse;

public class LoginResponseModel {
    //Variables
    JsonObject requestObject;
    @Getter
    Response response;

    //Constructor to pass the response from Request Model to Response Model
    public LoginResponseModel(JsonObject requestObject , Response response) {
        this.requestObject = requestObject;
        this.response = response;
    }

    //Validation Methods
    @Step("validateMassageFromResponse")
    public LoginResponseModel validateMassageFromResponse(String message) {
        String actualMessage = getJsonStringValueFromResponse(response,"message");
        CustomAssert.assertEquals(actualMessage,message);
        return this;
    }

    @Step("validateStatusFromResponse")
    public LoginResponseModel validateStatusFromResponse(String statusCode) {
        int actualStatusCode = ApisManager.getResponseCode(response);
        CustomAssert.assertEquals(actualStatusCode,Integer.parseInt(statusCode));
        return this;
    }

    @Step("validateSuccessFromResponse")
    public LoginResponseModel validateSuccessFromResponse(String successFlag) {
        boolean actualSuccess = getJsonBooleanValueFromResponse(response,"success");
        CustomAssert.assertEquals(actualSuccess,Boolean.parseBoolean(successFlag));
        return this;
    }

    @Step("Validate Token Exists ")
    public LoginResponseModel validateTokenExists() {
        CustomAssert.assertTrue(getJsonStringValueFromResponse(response,"data.token") != null);
        return this;
    }

    //Getter Methods
    public JsonObject getRequest() {
        return requestObject;
    }

    @Step("Get Password")
    //Get Password from Request
    public String getPassword()
    {
        return requestObject.get("password").getAsString();
    }

    @Step("Get Token")
    //Get Password from Request
    public String getToken()
    {
        return getJsonStringValueFromResponse(response,"data.token");
    }

/*    //Get Needed Token from Login Model and pass it to Note Model
    @Step("Navigate to CreateNote")
    public CreateNoteRequestModel navigateToCreateNote()
    {
        return new CreateNoteRequestModel(responseObject.getData().getToken());
    }*/

/*    //Get Needed Token and UserCredentials from Login Model and pass it to ChangePassword Model
    @Step("Navigate to ChangePassword")
    public ChangePasswordRequestModel navigateToChangePassword()
    {
        return new ChangePasswordRequestModel(
                responseObject.getData().getToken(),
                responseObject.getData().getEmail(),
                requestObject.getPassword()
        );
    }*/
}
