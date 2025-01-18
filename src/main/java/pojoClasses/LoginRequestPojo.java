package pojoClasses;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@Builder
public class LoginRequestPojo {
    //variables
    private String email;
    private String password;
}
