package objectModels;

import io.qameta.allure.Step;
import org.testng.Assert;
import pojoClasses.CreateNoteRequestPojo;
import pojoClasses.CreateNoteResponsePojo;
import pojoClasses.GetNoteRequestPojo;
import pojoClasses.GetNoteResponsePojo;

public class GetNoteResponseModel {
    //ObjectsFromPojoClasses
    GetNoteRequestPojo requestObject;
    GetNoteResponsePojo responseObject;

    //Variables
    String token;

    //Constructor to pass the response from Request Model to Response Model
    public GetNoteResponseModel(GetNoteRequestPojo requestObject,
                                GetNoteResponsePojo responseObject,String token) {
        this.requestObject = requestObject;
        this.responseObject= responseObject;
        this.token = token;
    }

    //Validation Methods
    @Step("validateMassageFromResponse")
    public GetNoteResponseModel validateMassageFromResponse(String message) {
        Assert.assertEquals(responseObject.getMessage(),message);
        return this;
    }

    @Step("validateStatusFromResponse")
    public GetNoteResponseModel validateStatusFromResponse(String statusCode) {
        Assert.assertEquals(responseObject.getStatus(),Integer.parseInt(statusCode));
        return this;
    }

    @Step("validateSuccessFromResponse")
    public GetNoteResponseModel validateSuccessFromResponse(String successFlag) {
        Assert.assertEquals(responseObject.isSuccess(),Boolean.parseBoolean(successFlag));
        return this;
    }

    @Step("validateTitleFromResponse")
    public GetNoteResponseModel validateTitleFromResponse(String title) {
        Assert.assertEquals(responseObject.getData().getTitle(),title);
        return this;
    }

    @Step("validateDescriptionFromResponse")
    public GetNoteResponseModel validateDescriptionFromResponse(String description) {
        Assert.assertEquals(responseObject.getData().getDescription(),description);
        return this;
    }

    @Step("validateCategoryFromResponse")
    public GetNoteResponseModel validateCategoryFromResponse(String category) {
        Assert.assertEquals(responseObject.getData().getCategory(),category);
        return this;
    }

    @Step("validateNoteStatusFromResponse")
    public GetNoteResponseModel validateNoteStatusFromResponse(String status) {
        Assert.assertEquals(responseObject.getData().isCompleted(),Boolean.parseBoolean(status));
        return this;
    }

    //Getter Methods
    public GetNoteRequestPojo getRequestPojoObject() {
        return requestObject;
    }

    public GetNoteResponsePojo getResponsePojoObject() {
        return responseObject;
    }

    @Step("Get Note ID")
    //Get Needed Token & Note ID from GetNote Model and pass it to UpdateNote Model
    public UpdateNoteRequestModel getNoteId()
    {
        return new UpdateNoteRequestModel(responseObject.getData().getId(),token);
    }
}
