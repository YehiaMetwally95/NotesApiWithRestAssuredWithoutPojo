package objectModels;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.google.gson.JsonObject;
import io.qameta.allure.Step;
import io.restassured.response.Response;
import pojoClasses.ChangePasswordRequestPojo;
import pojoClasses.ChangePasswordResponsePojo;
import pojoClasses.LoginRequestPojo;
import static yehiaEngine.managers.ApisManager.MethodType.*;
import static yehiaEngine.managers.ApisManager.ContentType.*;
import static yehiaEngine.managers.ApisManager.ParameterType.*;
import static yehiaEngine.managers.ApisManager.AuthType.*;
import static yehiaEngine.managers.ApisManager.MakeAuthRequest;
import static yehiaEngine.managers.ApisManager.getResponseBody;
import static yehiaEngine.managers.PropertiesManager.getPropertiesValue;
import static yehiaEngine.utilities.RandomDataGenerator.generateStrongPassword;

public class ChangePasswordRequestModel {
    //Variables
    String changePasswordEndpoint = getPropertiesValue("baseUrlApi")+"users/change-password";
    JsonObject requestObject = new JsonObject();
    Response response;

    @Step("Prepare Change Password Request")
    //Method to get Request Body of ChangePassword from Login Results
    public ChangePasswordRequestModel prepareChangePasswordRequestWithRandomPassword(String oldPassword)
    {
        requestObject.addProperty("currentPassword",oldPassword);
        requestObject.addProperty("newPassword",generateStrongPassword());
        return this;
    }

    @Step("Send ChangePassword Request")
    //Method to Execute ChangePassword Request
    public ChangePasswordResponseModel sendChangePasswordRequest(String token) {

        response = MakeAuthRequest(POST, changePasswordEndpoint,requestObject,URLENCODED,XAuthToken,
                        token,null,null);

        return new ChangePasswordResponseModel(requestObject, response);
    }

/*    //Facade Method
    @Step("Change User Password")
    public LoginRequestPojo changeUserPassword() throws JsonProcessingException {
        return prepareChangePasswordRequestWithRandomPassword()
                .sendChangePasswordRequest()
                .validateStatusFromResponse("200")
                .getUserCredentialsWithNewPassword()
                .prepareLoginRequest()
                .sendLoginRequest()
                .validateStatusFromResponse("200")
                .validateTokenExists()
                .getRequestPojoObject();
    }*/
}
