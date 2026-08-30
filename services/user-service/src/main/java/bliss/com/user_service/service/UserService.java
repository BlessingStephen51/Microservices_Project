package bliss.com.user_service.service;

import bliss.com.payload.dto.UserDto;
import bliss.com.user_service.model.User;

import java.util.List;

public interface UserService {
    UserDto getUserByEmail(String email) throws Exception;
    UserDto getUserById(Long id) throws Exception;
    List<UserDto> getAllUsers();

}
