package objectModels;
import static yehiaEngine.managers.ApisManager.MakeRequest;
import static yehiaEngine.managers.PropertiesManager.getPropertiesValue;
import static yehiaEngine.utilities.RandomDataGenerator.*;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.google.gson.JsonObject;
import io.qameta.allure.Step;
import io.restassured.response.Response;
import yehiaEngine.managers.JsonManager;

import static yehiaEngine.managers.ApisManager.MethodType.*;
import static yehiaEngine.managers.ApisManager.ContentType.*;

public class RegisterRequestModel {

    //Variables
    String registerEndpoint = getPropertiesValue("baseUrlApi")+"users/register";
    JsonObject requestObject = new JsonObject();
    Response response;

    @Step("Prepare Registration Request Statically From Json File")
    //Method to get Request Body inputs from Json File Statically
    public RegisterRequestModel prepareRegisterRequestFromJsonFile(String userData) {
        requestObject = JsonManager.convertJsonStringToJsonObject(userData);
        return this;
    }

    @Step("Prepare Registration Request Dynamically With Random Values")
    //Method to set Request Body inputs from TimeStamp Dynamically
    public RegisterRequestModel prepareRegisterRequestWithRandomValues(){
        requestObject.addProperty("name",generateUniqueName());
        requestObject.addProperty("email",generateUniqueEmail());
        requestObject.addProperty("password",generateStrongPassword());
        return this;
    }

    @Step("Send Register Request")
    //Method to Execute Registration Request
    public RegisterResponseModel sendRegisterRequest() {
        response = MakeRequest(POST, registerEndpoint,requestObject, URLENCODED);
        return new RegisterResponseModel(requestObject,response);
    }

    //Facade Methods
/*    @Step("Register new User And Login")
    public LoginResponseModel registerNewUserWithRandomData() throws JsonProcessingException {

        return prepareRegisterRequestWithRandomValues()
                .sendRegisterRequest()
                .validateStatusFromResponse("201")
                .getNewUserCredentials()
                .prepareLoginRequest()
                .sendLoginRequest()
                .validateStatusFromResponse("200")
                .validateTokenExists();
    }

    @Step("Register new User And Login")
    public LoginResponseModel registerNewUser(String userJsonObject) throws JsonProcessingException {

        return prepareRegisterRequestFromJsonFile(userJsonObject)
                .sendRegisterRequest()
                .validateStatusFromResponse("201")
                .getNewUserCredentials()
                .prepareLoginRequest()
                .sendLoginRequest()
                .validateStatusFromResponse("200")
                .validateTokenExists();
    }*/
}
