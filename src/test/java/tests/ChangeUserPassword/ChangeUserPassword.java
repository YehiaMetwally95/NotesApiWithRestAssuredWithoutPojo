package tests.ChangeUserPassword;

import objectModels.ChangePasswordRequestModel;
import objectModels.LoginRequestModel;
import objectModels.RegisterRequestModel;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import yehiaEngine.managers.JsonManager;

import java.util.List;

public class ChangeUserPassword {
    String jsonFilePath = "src/test/resources/Test_Data_Json_Files/ChangeUserPasswordTestData.json";
    JsonManager json;
    String token;
    List<String> userCredentials;
    String newPassword;

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
    public void changeUserPassword() {
        newPassword =
        new ChangePasswordRequestModel()
                .prepareChangePasswordRequestWithRandomPassword(userCredentials.get(1))
                .sendChangePasswordRequest(token)
                .validateMassageFromResponse(json.getData("Messages.ChangePassword"))
                .validateSuccessFromResponse(json.getData("SuccessFlag.ChangePassword"))
                .validateStatusFromResponse(json.getData("StatusCode.ChangePassword"))
                .getNewPassword();

        new LoginRequestModel()
                .prepareLoginRequest(userCredentials.get(0),newPassword)
                .sendLoginRequest()
                //Validations on Login Response
                .validateMassageFromResponse(json.getData("Messages.Login"))
                .validateSuccessFromResponse(json.getData("SuccessFlag.Login"))
                .validateStatusFromResponse(json.getData("StatusCode.Login"))
                .validateTokenExists();
    }
}
