package java_auth_service_com.example.java_auth_service.Model;

import java_auth_service_com.example.java_auth_service.Entity.UserInfo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserInfoDto extends UserInfo {
    private String email;
}
