package bliss.com.user_service.service;

import bliss.com.payload.dto.UserDto;
import bliss.com.payload.response.AuthResponse;

public interface AuthService {

    AuthResponse login(String email, String password) throws Exception;
    AuthResponse signup(UserDto req) throws Exception;
}
