package objectModels;

import com.google.gson.JsonObject;
import io.qameta.allure.Step;

import io.restassured.response.Response;
import lombok.Getter;
import pojoClasses.UpdateNoteRequestPojo;
import pojoClasses.UpdateNoteResponsePojo;
import yehiaEngine.assertions.CustomAssert;
import yehiaEngine.managers.ApisManager;

import static yehiaEngine.managers.ApisManager.getJsonBooleanValueFromResponse;
import static yehiaEngine.managers.ApisManager.getJsonStringValueFromResponse;

public class UpdateNoteResponseModel {
    //Variables
    JsonObject requestObject;
    @Getter
    Response response;

    //Constructor to pass the response from Request Model to Response Model
    public UpdateNoteResponseModel(JsonObject requestObject , Response response) {
        this.requestObject = requestObject;
        this.response= response;
    }

    //Validation Methods
    @Step("validateMassageFromResponse")
    public UpdateNoteResponseModel validateMassageFromResponse(String message) {
        String actualMessage = getJsonStringValueFromResponse(response,"message");
        CustomAssert.assertEquals(actualMessage,message);
        return this;
    }

    @Step("validateStatusFromResponse")
    public UpdateNoteResponseModel validateStatusFromResponse(String statusCode) {
        int actualStatusCode = ApisManager.getResponseCode(response);
        CustomAssert.assertEquals(actualStatusCode,Integer.parseInt(statusCode));
        return this;
    }

    @Step("validateSuccessFromResponse")
    public UpdateNoteResponseModel validateSuccessFromResponse(String successFlag) {
        boolean actualSuccess = getJsonBooleanValueFromResponse(response,"success");
        CustomAssert.assertEquals(actualSuccess,Boolean.parseBoolean(successFlag));
        return this;
    }

    @Step("Validate Description From Response")
    public UpdateNoteResponseModel validateDescriptionFromResponse() {
        String actualDescription = getJsonStringValueFromResponse(response,"data.description");
        String expectedDescription = requestObject.get("description").getAsString();
        CustomAssert.assertEquals(actualDescription,expectedDescription);
        return this;
    }

    @Step("Validate Title From Response")
    public UpdateNoteResponseModel validateTitleFromResponse() {
        String actualTitle = getJsonStringValueFromResponse(response,"data.title");
        String expectedTitle = requestObject.get("title").getAsString();
        CustomAssert.assertEquals(actualTitle,expectedTitle);
        return this;
    }

    @Step("Validate Category From Response")
    public UpdateNoteResponseModel validateCategoryFromResponse() {
        String actualCategory = getJsonStringValueFromResponse(response,"data.category");
        String expectedCategory = requestObject.get("category").getAsString();
        CustomAssert.assertEquals(actualCategory,expectedCategory);
        return this;
    }

    @Step("Validate NoteStatus From Response")
    public UpdateNoteResponseModel validateNoteStatusFromResponse() {
        boolean actualStatus = getJsonBooleanValueFromResponse(response,"data.completed");
        boolean expectedStatus = requestObject.get("completed").getAsBoolean();
        CustomAssert.assertEquals(actualStatus,expectedStatus);
        return this;
    }

    //Getter Methods
    public JsonObject getRequest() {
        return requestObject;
    }

    @Step("Get Note ID")
    public String getNoteID(){
        return getJsonStringValueFromResponse(response,"data.id");
    }
}
