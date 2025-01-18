package tests.CreateNewNote;

import com.google.gson.JsonObject;
import io.restassured.response.Response;
import objectModels.CreateNoteRequestModel;
import objectModels.GetNoteRequestModel;
import objectModels.LoginRequestModel;
import objectModels.RegisterRequestModel;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import static yehiaEngine.managers.ApisManager.*;
import yehiaEngine.managers.JsonManager;

import java.util.List;

import static yehiaEngine.managers.ApisManager.getJsonStringValueFromResponse;

public class CreateNewNoteWithRandomData {
    String jsonFilePath = "src/test/resources/Test_Data_Json_Files/CreateNewNoteTestData.json";
    JsonManager json;
    String token;
    List<String> userCredentials;
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

    @Test
    public void createNewNoteByRandomDataDynamically() {
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

        new GetNoteRequestModel()
                .prepareGetNoteRequestWithNoteID(getJsonStringValueFromResponse(responseBody,"data.id"))
                .sendGetNoteRequest(token)
                .validateMassageFromResponse(json.getData("Messages.GetNote"))
                .validateSuccessFromResponse(json.getData("SuccessFlag.GetNote"))
                .validateStatusFromResponse(json.getData("StatusCode.GetNote"))
                .validateTitleFromResponse(getJsonStringValueFromResponse(responseBody,"data.title"))
                .validateDescriptionFromResponse(getJsonStringValueFromResponse(responseBody,"data.description"))
                .validateCategoryFromResponse(getJsonStringValueFromResponse(responseBody,"data.category"))
                .validateNoteStatusFromResponse(getJsonBooleanValueFromResponse(responseBody,"data.completed"));
    }
}
