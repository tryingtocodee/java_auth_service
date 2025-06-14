package java_auth_service_com.example.java_auth_service.Service;

import java_auth_service_com.example.java_auth_service.Entity.UserInfo;
import java_auth_service_com.example.java_auth_service.Entity.UserRole;
import java_auth_service_com.example.java_auth_service.Model.UserInfoDto;
import java_auth_service_com.example.java_auth_service.Repo.UserRepo;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.HashSet;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;


@Data
@AllArgsConstructor
@Component
public class UserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    private final UserRepo userRepo;

    @Autowired
    private final PasswordEncoder passwordEncoder;

    private static final Logger log = LoggerFactory.getLogger(UserDetailsServiceImpl.class);

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserInfo user = userRepo.findByUsername(username);
        if(user == null){
            log.error("username not found");
            throw new UsernameNotFoundException("username not found ");
        }
        log.info("user authenticated");
        return new CustomUserDetails(user);
    }

    public UserInfo checkIfUserExists(UserInfoDto userInfoDto){
        return userRepo.findByUsername(userInfoDto.getUsername());
    }

    public String signUpUser(UserInfoDto userInfoDto){
        //validate user attributes
        userInfoDto.setPassword(passwordEncoder.encode(userInfoDto.getPassword()));
        if(Objects.nonNull(checkIfUserExists(userInfoDto))){
            return null;
        }

        String userId = UUID.randomUUID().toString();
        UserInfo userInfo = new UserInfo(userId , userInfoDto.getUsername() , userInfoDto.getPassword() , new HashSet<>());
       userRepo.save(userInfo);
       return userId;
    }

    public String getUserByUsername(String username){
        return Optional.of(userRepo.findByUsername(username))
                .map(UserInfo::getUserId)
                .orElse(null);
    }
}
