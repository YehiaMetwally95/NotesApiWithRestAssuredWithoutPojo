package objectModels;

import com.google.gson.JsonObject;
import io.qameta.allure.Step;
import io.restassured.response.Response;
import lombok.Getter;
import org.testng.Assert;
import pojoClasses.CreateNoteRequestPojo;
import pojoClasses.CreateNoteResponsePojo;
import pojoClasses.GetNoteRequestPojo;
import pojoClasses.GetNoteResponsePojo;
import yehiaEngine.assertions.CustomAssert;
import yehiaEngine.managers.ApisManager;

import static yehiaEngine.managers.ApisManager.getJsonBooleanValueFromResponse;
import static yehiaEngine.managers.ApisManager.getJsonStringValueFromResponse;

public class GetNoteResponseModel {
    //Variables
    JsonObject requestObject;
    @Getter
    Response response;

    //Constructor to pass the response from Request Model to Response Model
    public GetNoteResponseModel(JsonObject requestObject , Response response) {
        this.requestObject = requestObject;
        this.response= response;
    }

    //Validation Methods
    @Step("validateMassageFromResponse")
    public GetNoteResponseModel validateMassageFromResponse(String message) {
        String actualMessage = getJsonStringValueFromResponse(response,"message");
        CustomAssert.assertEquals(actualMessage,message);
        return this;
    }

    @Step("validateStatusFromResponse")
    public GetNoteResponseModel validateStatusFromResponse(String statusCode) {
        int actualStatusCode = ApisManager.getResponseCode(response);
        CustomAssert.assertEquals(actualStatusCode,Integer.parseInt(statusCode));
        return this;
    }

    @Step("validateSuccessFromResponse")
    public GetNoteResponseModel validateSuccessFromResponse(String successFlag) {
        boolean actualSuccess = getJsonBooleanValueFromResponse(response,"success");
        CustomAssert.assertEquals(actualSuccess,Boolean.parseBoolean(successFlag));
        return this;
    }

    @Step("validateTitleFromResponse")
    public GetNoteResponseModel validateTitleFromResponse(String title) {
        String actualTitle = getJsonStringValueFromResponse(response,"data.title");
        CustomAssert.assertEquals(actualTitle,title);
        return this;
    }

    @Step("validateDescriptionFromResponse")
    public GetNoteResponseModel validateDescriptionFromResponse(String description) {
        String actualDescription = getJsonStringValueFromResponse(response,"data.description");
        CustomAssert.assertEquals(actualDescription,description);
        return this;
    }

    @Step("validateCategoryFromResponse")
    public GetNoteResponseModel validateCategoryFromResponse(String category) {
        String actualCategory = getJsonStringValueFromResponse(response,"data.category");
        CustomAssert.assertEquals(actualCategory,category);
        return this;
    }

    @Step("validateNoteStatusFromResponse")
    public GetNoteResponseModel validateNoteStatusFromResponse(boolean status) {
        boolean actualStatus = getJsonBooleanValueFromResponse(response,"data.completed");
        CustomAssert.assertEquals(actualStatus,status);
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
