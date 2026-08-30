package bliss.com.user_service.service.impl;

import bliss.com.payload.dto.UserDto;
import bliss.com.user_service.mapper.UserMapper;
import bliss.com.user_service.model.User;
import bliss.com.user_service.repository.UserRepository;
import bliss.com.user_service.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;



    @Override
    public UserDto getUserByEmail(String email) throws Exception {
        User user = userRepository.findByEmail(email);
        if(user == null){
            throw new Exception("user not found with email");

        }
        return UserMapper.toDto(user);
    }

    @Override
    public UserDto getUserById(Long id) throws Exception {
        User user = userRepository.findById(id).orElseThrow(
                () -> new Exception("user not found with id " + id)
        );
        return UserMapper.toDto(user);

    }

    @Override
    public List<UserDto> getAllUsers() {
        List<User> users =userRepository.findAll();
        return UserMapper.toDtoList(users);
    }


    }

