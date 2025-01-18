package objectModels;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.google.gson.JsonObject;
import io.qameta.allure.Step;
import io.restassured.response.Response;
import pojoClasses.GetNoteRequestPojo;
import pojoClasses.GetNoteResponsePojo;
import yehiaEngine.managers.JsonManager;

import java.util.HashMap;
import java.util.Map;

import static yehiaEngine.managers.ApisManager.ParameterType.*;
import static yehiaEngine.managers.ApisManager.AuthType.*;
import static yehiaEngine.managers.ApisManager.*;
import static yehiaEngine.managers.PropertiesManager.getPropertiesValue;
public class GetNoteRequestModel {

    //Variables
    String getNoteEndpoint = getPropertiesValue("baseUrlApi")+"notes/{id}";
    JsonObject requestObject = new JsonObject();
    Response response;

    @Step("Prepare Get Note Request With Note ID")
    public GetNoteRequestModel prepareGetNoteRequestWithNoteID(String noteID) {
        requestObject.addProperty("id",noteID);
        return this;
    }

    @Step("Send Get Note Request")
    //Method to Execute Get Note Request
    public GetNoteResponseModel sendGetNoteRequest(String token) {
        response = GetAuthRequest(getNoteEndpoint,PATH,requestObject,XAuthToken,
                token,null,null);

        return new GetNoteResponseModel(requestObject,response);
    }
}
