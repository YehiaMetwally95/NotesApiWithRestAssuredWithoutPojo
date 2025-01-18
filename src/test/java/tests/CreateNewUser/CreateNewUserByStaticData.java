package tests.CreateNewUser;

import objectModels.LoginRequestModel;
import objectModels.RegisterRequestModel;
import org.testng.annotations.Test;
import yehiaEngine.managers.JsonManager;

import java.util.List;

public class CreateNewUserByStaticData {
    String jsonFilePath = "src/test/resources/Test_Data_Json_Files/CreateNewUserTestData.json";
    JsonManager json;
    String token;

    @Test
    public void createNewUserByStaticDataFromJsonFile() {
        json = new JsonManager(jsonFilePath);
        new RegisterRequestModel()
                //Prepare Register Request then Send it
                .prepareRegisterRequestFromJsonFile(json.getData("RegisterDataInputs[0]"))
                .sendRegisterRequest()
                //Validations on Register Response
                .validateMassageFromResponse(json.getData("Messages.Register"))
                .validateSuccessFromResponse(json.getData("SuccessFlag.Register"))
                .validateStatusFromResponse(json.getData("StatusCode.Register"))
                .validateNameFromResponse()
                .validateEmailFromResponse();

        token =
        new LoginRequestModel()
                .prepareLoginRequest(json.getData("RegisterDataInputs[0].email"),json.getData("RegisterDataInputs[0].password"))
                .sendLoginRequest()
                //Validations on Login Response
                .validateMassageFromResponse(json.getData("Messages.Login"))
                .validateSuccessFromResponse(json.getData("SuccessFlag.Login"))
                .validateStatusFromResponse(json.getData("StatusCode.Login"))
                .validateTokenExists()
                .getToken();
        System.out.println(token);
    }
}
