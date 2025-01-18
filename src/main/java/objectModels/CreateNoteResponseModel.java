package objectModels;

import com.google.gson.JsonObject;
import io.qameta.allure.Step;
import io.restassured.response.Response;
import lombok.Getter;
import org.testng.Assert;
import pojoClasses.CreateNoteRequestPojo;
import pojoClasses.CreateNoteResponsePojo;
import yehiaEngine.assertions.CustomAssert;
import yehiaEngine.managers.ApisManager;

import static yehiaEngine.managers.ApisManager.getJsonBooleanValueFromResponse;
import static yehiaEngine.managers.ApisManager.getJsonStringValueFromResponse;

public class CreateNoteResponseModel {
    //Variables
    JsonObject requestObject;
    //Getter Methods
    @Getter
    Response response;

    //Constructor to pass the response from Request Model to Response Model
    public CreateNoteResponseModel(JsonObject requestObject , Response response) {
        this.requestObject = requestObject;
        this.response= response;
    }

    //Validation Methods
    @Step("validateMassageFromResponse")
    public CreateNoteResponseModel validateMassageFromResponse(String message) {
        String actualMessage = getJsonStringValueFromResponse(response,"message");
        CustomAssert.assertEquals(actualMessage,message);
        return this;
    }

    @Step("validateStatusFromResponse")
    public CreateNoteResponseModel validateStatusFromResponse(String statusCode) {
        int actualStatusCode = ApisManager.getResponseCode(response);
        CustomAssert.assertEquals(actualStatusCode,Integer.parseInt(statusCode));
        return this;
    }

    @Step("validateSuccessFromResponse")
    public CreateNoteResponseModel validateSuccessFromResponse(String successFlag) {
        boolean actualSuccess = getJsonBooleanValueFromResponse(response,"success");
        CustomAssert.assertEquals(actualSuccess,Boolean.parseBoolean(successFlag));
        return this;
    }

    @Step("Validate Description From Response")
    public CreateNoteResponseModel validateDescriptionFromResponse() {
        String actualDescription = getJsonStringValueFromResponse(response,"data.description");
        String expectedDescription = requestObject.get("description").getAsString();
        CustomAssert.assertEquals(actualDescription,expectedDescription);
        return this;
    }

    @Step("Validate Title From Response")
    public CreateNoteResponseModel validateTitleFromResponse() {
        String actualTitle = getJsonStringValueFromResponse(response,"data.title");
        String expectedTitle = requestObject.get("title").getAsString();
        CustomAssert.assertEquals(actualTitle,expectedTitle);
        return this;
    }

    @Step("Validate Category From Response")
    public CreateNoteResponseModel validateCategoryFromResponse() {
        String actualCategory = getJsonStringValueFromResponse(response,"data.category");
        String expectedCategory = requestObject.get("category").getAsString();
        CustomAssert.assertEquals(actualCategory,expectedCategory);
        return this;
    }

    @Step("Validate NoteStatus From Response")
    public CreateNoteResponseModel validateNoteStatusFromResponse() {
        CustomAssert.assertFalse(getJsonBooleanValueFromResponse(response,"data.completed"));
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
