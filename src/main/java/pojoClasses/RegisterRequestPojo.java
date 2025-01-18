package pojoClasses;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.extern.jackson.Jacksonized;

@Getter
@Setter
@ToString
@Jacksonized
@Builder
public class RegisterRequestPojo {
    //variables
    @JsonProperty("Name") private String name;
    @JsonProperty("Email") private String email;
    @JsonProperty("Password") private String password;
}
