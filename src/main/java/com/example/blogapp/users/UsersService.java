package com.example.blogapp.users;

import com.example.blogapp.users.dtos.CreateUserRequest;
import org.modelmapper.ModelMapper;
import org.springframework.boot.Banner;
import org.springframework.stereotype.Service;

@Service
public class UsersService {

    private final UsersRepository usersRepository;
    private final ModelMapper modelMapper;

    public UsersService(UsersRepository usersRepository, ModelMapper modelMapper){
        this.usersRepository = usersRepository;
        this.modelMapper = modelMapper;
    }

    public UserEntitiy createUser(CreateUserRequest request){
        UserEntitiy newUser = modelMapper.map(request, UserEntitiy.class);
        //TODO encrypt and add password
//        var newUser = UserEntitiy.builder()
//                .username(request.getUsername())
//                .email(request.getEmail())
//                .build();
//
        return usersRepository.save(newUser);
    }

    public UserEntitiy getUser(String username){
        return usersRepository.findByUsername(username).orElseThrow(() -> new UserNotFoundException(username));
    }

    public UserEntitiy getUser(Long userId){
        return usersRepository.findById(userId).orElseThrow(() -> new UserNotFoundException(userId));
    }

    public UserEntitiy loginUser(String username, String password){
        var user = usersRepository.findByUsername(username).orElseThrow(() -> new UserNotFoundException(username));
        //TODO: check password
        return user;
    }

    public

    static class UserNotFoundException extends IllegalArgumentException{
        public UserNotFoundException(String username){
            super("User with username : "+ username + " not found");
        }

        public UserNotFoundException(Long authorId){
            super("User with ID : "+ authorId + " not found");
        }
    }

}
