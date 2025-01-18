package objectModels;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.google.gson.JsonObject;
import io.qameta.allure.Step;
import io.restassured.response.Response;
import yehiaEngine.managers.JsonManager;

import java.util.Arrays;
import java.util.List;

import static yehiaEngine.managers.ApisManager.MakeAuthRequest;
import static yehiaEngine.managers.ApisManager.getResponseBody;
import static yehiaEngine.managers.PropertiesManager.getPropertiesValue;
import static yehiaEngine.utilities.RandomDataGenerator.*;
import static yehiaEngine.managers.ApisManager.MethodType.*;
import static yehiaEngine.managers.ApisManager.ContentType.*;
import static yehiaEngine.managers.ApisManager.AuthType.*;

public class CreateNoteRequestModel {

    //Variables
    String createNoteEndpoint = getPropertiesValue("baseUrlApi")+"notes/";
    JsonObject requestObject = new JsonObject();
    Response response;

    @Step("Prepare CreateNote Request Statically from Json File")
    //Method to get Request Body inputs from Json File Statically
    public CreateNoteRequestModel prepareCreateNoteRequestFromJsonFile(String noteData) {
        requestObject = JsonManager.convertJsonStringToJsonObject(noteData);
        return this;
    }

    @Step("Prepare CreateNote Request Dynamically With Random Values")
    //Method to set Request Body inputs from TimeStamp Dynamically
    public CreateNoteRequestModel prepareCreateNoteRequestWithRandomValues(){
        requestObject.addProperty("title",generateUniqueName());
        requestObject.addProperty("description",generateDescription());
        requestObject.addProperty("category",generateItemFromList(List.of("Home","Work","Personal")));
        return this;
    }

    @Step("Send CreateNote Request")
    //Method to Execute CreateNode Request
    public CreateNoteResponseModel sendCreateNoteRequest(String token) {

        response = MakeAuthRequest(POST, createNoteEndpoint,requestObject,URLENCODED,XAuthToken,
                        token,null,null);

        return new CreateNoteResponseModel(requestObject,response);
    }

/*    //Facade Method
    @Step("Create new Note")
    public UpdateNoteRequestModel createNewNote(String noteJsonObject,String title,
                                                String description,String category,String noteStatus) throws JsonProcessingException {
        return prepareCreateNoteRequestFromJsonFile(noteJsonObject)
                .sendCreateNoteRequest()
                .getNoteId()
                .sendGetNoteRequest()
                .validateTitleFromResponse(title)
                .validateDescriptionFromResponse(description)
                .validateCategoryFromResponse(category)
                .validateNoteStatusFromResponse(noteStatus)
                .getNoteId();
    }

    @Step("Create new Note")
    public UpdateNoteRequestModel createNewNoteWithDynamicRandomValues() throws JsonProcessingException {
        return prepareCreateNoteRequestWithRandomValues()
                .sendCreateNoteRequest()
                .validateStatusFromResponse("200")
                .validateTitleFromResponse()
                .validateDescriptionFromResponse()
                .validateCategoryFromResponse()
                .validateNoteStatusFromResponse()
                .getNoteId()
                .sendGetNoteRequest()
                .validateStatusFromResponse("200")
                .getNoteId();
    }*/
}
