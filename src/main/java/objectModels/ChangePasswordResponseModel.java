package objectModels;

import com.google.gson.JsonObject;
import io.qameta.allure.Step;
import io.restassured.response.Response;
import lombok.Getter;
import org.testng.Assert;
import pojoClasses.ChangePasswordRequestPojo;
import pojoClasses.ChangePasswordResponsePojo;
import yehiaEngine.assertions.CustomAssert;
import yehiaEngine.managers.ApisManager;

import java.util.List;

import static yehiaEngine.managers.ApisManager.getJsonBooleanValueFromResponse;
import static yehiaEngine.managers.ApisManager.getJsonStringValueFromResponse;

public class ChangePasswordResponseModel {
    //Variables
    JsonObject requestObject;
    @Getter
    Response response;

    //Constructor to pass the response from Request Model to Response Model
    public ChangePasswordResponseModel(JsonObject requestObject , Response response) {
        this.requestObject = requestObject;
        this.response = response;
    }

    //Validation Methods
    @Step("validateMassageFromResponse")
    public ChangePasswordResponseModel validateMassageFromResponse(String message) {
        String actualMessage = getJsonStringValueFromResponse(response,"message");
        CustomAssert.assertEquals(actualMessage,message);
        return this;
    }

    @Step("validateStatusFromResponse")
    public ChangePasswordResponseModel validateStatusFromResponse(String statusCode) {
        int actualStatusCode = ApisManager.getResponseCode(response);
        CustomAssert.assertEquals(actualStatusCode,Integer.parseInt(statusCode));
        return this;
    }

    @Step("validateSuccessFromResponse")
    public ChangePasswordResponseModel validateSuccessFromResponse(String successFlag) {
        boolean actualSuccess = getJsonBooleanValueFromResponse(response,"success");
        CustomAssert.assertEquals(actualSuccess,Boolean.parseBoolean(successFlag));
        return this;
    }

    //Getter Methods
    public JsonObject getRequest() {
        return requestObject;
    }

    public String getNewPassword(){
        return requestObject.get("newPassword").getAsString();
    }

/*    //Get Needed Data from ChangePassword Model and pass it to Login Model
    @Step("Get New Credentials")
    public LoginRequestModel getUserCredentialsWithNewPassword() {
        return new LoginRequestModel(userEmail, requestObject.getNewPassword());
    }*/
}
