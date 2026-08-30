package bliss.com.user_service.service.impl;

import bliss.com.enums.UserRole;
import bliss.com.payload.dto.UserDto;
import bliss.com.payload.response.AuthResponse;
import bliss.com.user_service.config.JwtProvider;
import bliss.com.user_service.mapper.UserMapper;
import bliss.com.user_service.model.User;
import bliss.com.user_service.repository.UserRepository;
import bliss.com.user_service.service.AuthService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;



@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtProvider jwtProvider;
    private final CustomUserDetailsService customUserDetailsService;


    /*
         1. Check if email already exists
         2. Encode password using BCrypt
         3. Save user in database
         4. Generate JWT token
         5. Return token and user information
     */



    @Override
    public AuthResponse signup(UserDto req) throws Exception {
        User existingUser = userRepository.findByEmail(req.getEmail());
        if(existingUser!=null){
            throw new Exception("email already registered!");
        }
        if(req.getRole()== UserRole.ROLE_SYSTEM_ADMIN){
            throw new Exception("you cannot sign up system admin!");

        }

        User newUser = User.builder()
                .email(req.getEmail())
                .password(passwordEncoder.encode(req.getPassword()))
                .phone(req.getPhone())
                .role(req.getRole())
                .fullName(req.getFullName())
                .lastLogin(LocalDateTime.now())
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();

        User savedUser = userRepository.save(newUser);

        Authentication authentication = new UsernamePasswordAuthenticationToken(
                savedUser.getEmail(), savedUser.getPassword()
        );

        String jwt = jwtProvider.generateToken(
                authentication, savedUser.getId()
        );

        AuthResponse authResponse = new AuthResponse();
        authResponse.setJwt(jwt);
        authResponse.setUser(UserMapper.toDto(savedUser));
        authResponse.setTitle("Welcome" + savedUser.getFullName());
        authResponse.setMessage("Registered Successfully!");

        return authResponse;

    }

    /*
        1. Load user by email
        2. compare password with BCrypt
        3. Update 'LastLogin' time
        4. Generate JWT token
        5. Return token and user information
     */


    @Override
    public AuthResponse login(String email, String password) throws Exception {
        Authentication authentication = authentication(email, password);


        User user = userRepository.findByEmail(email);
        user.setLastLogin(LocalDateTime.now());
        userRepository.save(user);

        String jwt = jwtProvider.generateToken(authentication, user.getId());

        AuthResponse authResponse = new AuthResponse();
        authResponse.setJwt(jwt);
        authResponse.setUser(UserMapper.toDto(user));
        authResponse.setTitle("Welcome Back" + user .getFullName());
        authResponse.setMessage("Login Successfully!");

        return authResponse ;
    }



        private Authentication authentication(String email, String password) throws Exception {


            UserDetails userDetails = customUserDetailsService.loadUserByUsername(email);

            if(!passwordEncoder.matches(
                    password, userDetails.getPassword()
            )) {
                throw new Exception("invalid password");
            }
            return  UsernamePasswordAuthenticationToken.authenticated(userDetails, null,
                    userDetails.getAuthorities()
            );
        }


    }




