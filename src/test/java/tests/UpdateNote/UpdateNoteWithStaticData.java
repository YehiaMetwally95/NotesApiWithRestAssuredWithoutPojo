package tests.UpdateNote;

import io.restassured.response.Response;
import objectModels.*;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import yehiaEngine.managers.JsonManager;

import java.util.List;

import static yehiaEngine.managers.ApisManager.getJsonBooleanValueFromResponse;
import static yehiaEngine.managers.ApisManager.getJsonStringValueFromResponse;

public class UpdateNoteWithStaticData {
    String jsonFilePath = "src/test/resources/Test_Data_Json_Files/UpdateNoteTestData.json";
    JsonManager json;
    String token;
    List<String> userCredentials;
    String noteID;
    Response responseBody;

    @BeforeMethod
    public void loginWithUser() {
        json = new JsonManager(jsonFilePath);
        userCredentials =
        new RegisterRequestModel()
                //Prepare Register Request then Send it
                .prepareRegisterRequestWithRandomValues()
                .sendRegisterRequest()
                //Validations on Register Response
                .validateMassageFromResponse(json.getData("Messages.Register"))
                .validateSuccessFromResponse(json.getData("SuccessFlag.Register"))
                .validateStatusFromResponse(json.getData("StatusCode.Register"))
                .validateNameFromResponse()
                .validateEmailFromResponse()
                .getUserCredentials();

        token =
        new LoginRequestModel()
                .prepareLoginRequest(userCredentials.get(0),userCredentials.get(1))
                .sendLoginRequest()
                //Validations on Login Response
                .validateMassageFromResponse(json.getData("Messages.Login"))
                .validateSuccessFromResponse(json.getData("SuccessFlag.Login"))
                .validateStatusFromResponse(json.getData("StatusCode.Login"))
                .validateTokenExists()
                .getToken();
    }

    @BeforeMethod (dependsOnMethods = "loginWithUser")
    public void createNewNoteByRandomData() {
        responseBody =
        new CreateNoteRequestModel()
                .prepareCreateNoteRequestWithRandomValues()
                .sendCreateNoteRequest(token)
                //Validations on Register Response
                .validateMassageFromResponse(json.getData("Messages.CreateNote"))
                .validateSuccessFromResponse(json.getData("SuccessFlag.CreateNote"))
                .validateStatusFromResponse(json.getData("StatusCode.CreateNote"))
                .validateTitleFromResponse()
                .validateDescriptionFromResponse()
                .validateCategoryFromResponse()
                .validateNoteStatusFromResponse()
                .getResponse();

        noteID =
        new GetNoteRequestModel()
                .prepareGetNoteRequestWithNoteID(getJsonStringValueFromResponse(responseBody,"data.id"))
                .sendGetNoteRequest(token)
                .validateMassageFromResponse(json.getData("Messages.GetNote"))
                .validateSuccessFromResponse(json.getData("SuccessFlag.GetNote"))
                .validateStatusFromResponse(json.getData("StatusCode.GetNote"))
                .validateTitleFromResponse(getJsonStringValueFromResponse(responseBody,"data.title"))
                .validateDescriptionFromResponse(getJsonStringValueFromResponse(responseBody,"data.description"))
                .validateCategoryFromResponse(getJsonStringValueFromResponse(responseBody,"data.category"))
                .validateNoteStatusFromResponse(getJsonBooleanValueFromResponse(responseBody,"data.completed"))
                .getNoteID();
    }

    @Test
    public void updateNoteByStaticDataFromJsonFile() {
        new UpdateNoteRequestModel()
                .prepareUpdateNoteRequestFromJsonFile(noteID,json.getData("NoteDataInputs[0]"))
                .sendUpdateNoteRequest(token)
                //Validations on Register Response
                .validateMassageFromResponse(json.getData("Messages.UpdateNote"))
                .validateSuccessFromResponse(json.getData("SuccessFlag.UpdateNote"))
                .validateStatusFromResponse(json.getData("StatusCode.UpdateNote"))
                .validateTitleFromResponse()
                .validateDescriptionFromResponse()
                .validateCategoryFromResponse()
                .validateNoteStatusFromResponse();

        new GetNoteRequestModel()
                .prepareGetNoteRequestWithNoteID(noteID)
                .sendGetNoteRequest(token)
                .validateMassageFromResponse(json.getData("Messages.GetNote"))
                .validateSuccessFromResponse(json.getData("SuccessFlag.GetNote"))
                .validateStatusFromResponse(json.getData("StatusCode.GetNote"))
                .validateTitleFromResponse(json.getData("NoteDataInputs[0].title"))
                .validateDescriptionFromResponse(json.getData("NoteDataInputs[0].description"))
                .validateCategoryFromResponse(json.getData("NoteDataInputs[0].category"))
                .validateNoteStatusFromResponse(Boolean.parseBoolean(json.getData("NoteDataInputs[0].completed")));
    }
}
