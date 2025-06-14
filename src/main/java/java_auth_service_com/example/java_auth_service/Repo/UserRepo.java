package java_auth_service_com.example.java_auth_service.Repo;

import java_auth_service_com.example.java_auth_service.Entity.UserInfo;
import java_auth_service_com.example.java_auth_service.Model.UserInfoDto;
import org.apache.catalina.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepo extends CrudRepository<UserInfo, String> {
    public UserInfo findByUsername(String username);
}
