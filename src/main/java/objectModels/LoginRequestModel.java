package objectModels;

import com.google.gson.JsonObject;
import io.qameta.allure.Step;
import io.restassured.response.Response;
import static yehiaEngine.managers.ApisManager.MakeRequest;
import static yehiaEngine.managers.PropertiesManager.getPropertiesValue;
import static yehiaEngine.managers.ApisManager.MethodType.*;
import static yehiaEngine.managers.ApisManager.ContentType.*;


public class LoginRequestModel {

    //Variables
    String loginEndpoint = getPropertiesValue("baseUrlApi")+"users/login";
    JsonObject requestObject = new JsonObject();
    Response response;

    @Step("Prepare Login Request")
    //Method to get Request Body of Login from Registration Results
    public LoginRequestModel prepareLoginRequest(String userName,String password)
    {
        requestObject.addProperty("email",userName);
        requestObject.addProperty("password",password);
        return this;
    }

    @Step("Send Login Request")
    //Method to Execute Login Request
    public LoginResponseModel sendLoginRequest() {
        response = MakeRequest(POST, loginEndpoint, requestObject, URLENCODED);
        return new LoginResponseModel(requestObject, response);
    }

/*   //Facade Method
    @Step("Login With Existing User")
    public LoginResponseModel loginWithExistingUser() throws JsonProcessingException {
        return
                new LoginRequestModel(userEmail,userPassword)
                        .prepareLoginRequest()
                        .sendLoginRequest()
                        .validateStatusFromResponse("200")
                        .validateTokenExists();
    }*/
}
