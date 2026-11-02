package com.example.demo.Service;

import com.example.demo.Dto.UserDto;
import com.example.demo.Entity.User;
import com.example.demo.Repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class  UserService {
    private UserRepository userRepository;

    public UserService(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    public User createUser(UserDto userDto){

        User user = new User();

        user.setUsername(userDto.getName());
        user.setEmail(userDto.getEmail());
        user.setUserId(userDto.getUserId());
        user.setUserRole(userDto.getRole());
        user.setPhone(userDto.getPhone());

        return userRepository.save(user);
    }

    public User getUserById(Long id){
        return userRepository.getOne(id);
    }

    public List<User> getAllUsers(){
        return userRepository.findAll();
    }

    public void deleteUserById(Long id){
        userRepository.deleteById(id);
    }

    public User updateUser(UserDto userDto, Long id){
        User existingUser = userRepository.getOne(id);

            existingUser.setUsername(userDto.getName());
            existingUser.setEmail(userDto.getEmail());

        return userRepository.save(existingUser);
    }


}
