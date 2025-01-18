package objectModels;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.google.gson.JsonObject;
import io.qameta.allure.Step;
import io.restassured.response.Response;
import pojoClasses.*;
import yehiaEngine.managers.JsonManager;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static yehiaEngine.managers.ApisManager.MakeAuthRequest;
import static yehiaEngine.managers.ApisManager.getResponseBody;
import static yehiaEngine.managers.PropertiesManager.getPropertiesValue;
import static yehiaEngine.utilities.RandomDataGenerator.*;

import static yehiaEngine.managers.ApisManager.MethodType.*;
import static yehiaEngine.managers.ApisManager.ContentType.*;
import static yehiaEngine.managers.ApisManager.ParameterType.*;
import static yehiaEngine.managers.ApisManager.AuthType.*;

public class UpdateNoteRequestModel {

    //Variables
    String updateNoteEndpoint = getPropertiesValue("baseUrlApi")+"notes/";
    JsonObject requestObject = new JsonObject();
    Response response;

    @Step("Prepare Update Note Request Statically from Json File")
    //Method to get Request Body inputs from Json File Statically
    public UpdateNoteRequestModel prepareUpdateNoteRequestFromJsonFile(String noteID,String noteData) {
        updateNoteEndpoint = updateNoteEndpoint+noteID;

        requestObject = JsonManager.convertJsonStringToJsonObject(noteData);
        return this;
    }

    @Step("Prepare Update Note Request Dynamically With Random Values")
    //Method to set Request Body inputs from TimeStamp Dynamically
    public UpdateNoteRequestModel prepareUpdateNoteRequestWithRandomValues(String noteID){
        updateNoteEndpoint = updateNoteEndpoint+noteID;

        requestObject.addProperty("title",generateUniqueName());
        requestObject.addProperty("description",generateDescription());
        requestObject.addProperty("category",generateItemFromList(List.of("Home","Work","Personal")));
        requestObject.addProperty("completed",generateItemFromList(List.of(true,false)));

        return this;
    }

    @Step("Send UpdateNote Request")
    //Method to Execute UpdateNote Request
    public UpdateNoteResponseModel sendUpdateNoteRequest(String token) {

        response = MakeAuthRequest(PUT, updateNoteEndpoint,requestObject,URLENCODED,XAuthToken
                ,token,null,null);

        return new UpdateNoteResponseModel(requestObject,response);
    }

/*    //Facade Method
    @Step("Update The Note")
    public CreateNoteRequestModel updateTheNote(String noteJsonObject, String title,
                                                String description, String category, String noteStatus) {
        prepareUpdateNoteRequestFromJsonFile(noteJsonObject)
                .sendUpdateNoteRequest()
                .validateStatusFromResponse("200")
                .getNoteID()
                .sendGetNoteRequest()
                .validateStatusFromResponse("200")
                .validateTitleFromResponse(title)
                .validateDescriptionFromResponse(description)
                .validateCategoryFromResponse(category)
                .validateNoteStatusFromResponse(noteStatus);

        return new CreateNoteRequestModel(token);
    }

    @Step("Don't Update The Note")
    public CreateNoteRequestModel noUpdate() throws JsonProcessingException {
        return new CreateNoteRequestModel(token);
    }*/
}
