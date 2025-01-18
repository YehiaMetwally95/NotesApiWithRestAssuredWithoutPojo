package tests.CreateNewNote;

import objectModels.CreateNoteRequestModel;
import objectModels.GetNoteRequestModel;
import objectModels.LoginRequestModel;
import objectModels.RegisterRequestModel;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import yehiaEngine.managers.JsonManager;

import java.util.List;

public class CreateNewNoteWithStaticData {
    String jsonFilePath = "src/test/resources/Test_Data_Json_Files/CreateNewNoteTestData.json";
    JsonManager json;
    String token;
    List<String> userCredentials;
    String noteID;

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
    public void createNewNoteByStaticDataFromJsonFile() {
        noteID =
        new CreateNoteRequestModel()
                .prepareCreateNoteRequestFromJsonFile(json.getData("NoteDataInputs[0]"))
                .sendCreateNoteRequest(token)
                //Validations on Register Response
                .validateMassageFromResponse(json.getData("Messages.CreateNote"))
                .validateSuccessFromResponse(json.getData("SuccessFlag.CreateNote"))
                .validateStatusFromResponse(json.getData("StatusCode.CreateNote"))
                .validateTitleFromResponse()
                .validateDescriptionFromResponse()
                .validateCategoryFromResponse()
                .validateNoteStatusFromResponse()
                .getNoteID();

        new GetNoteRequestModel()
                .prepareGetNoteRequestWithNoteID(noteID)
                .sendGetNoteRequest(token)
                .validateMassageFromResponse(json.getData("Messages.GetNote"))
                .validateSuccessFromResponse(json.getData("SuccessFlag.GetNote"))
                .validateStatusFromResponse(json.getData("StatusCode.GetNote"))
                .validateTitleFromResponse(json.getData("NoteDataInputs[0].title"))
                .validateDescriptionFromResponse(json.getData("NoteDataInputs[0].description"))
                .validateCategoryFromResponse(json.getData("NoteDataInputs[0].category"))
                .validateNoteStatusFromResponse(Boolean.parseBoolean(json.getData("NoteDataInputs[0].NoteStatus")));
    }
}
