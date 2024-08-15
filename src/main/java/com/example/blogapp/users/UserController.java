package com.example.blogapp.users;

import com.example.blogapp.common.dtos.ErrorResponse;
import com.example.blogapp.users.dtos.CreateUserRequest;
import com.example.blogapp.users.dtos.LoginUserRequest;
import com.example.blogapp.users.dtos.UserResponse;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UsersService usersService;
    private final ModelMapper modelMapper;

    public UserController(UsersService usersService, ModelMapper modelMapper){
        this.usersService = usersService;
        this.modelMapper = modelMapper;
    }

    @GetMapping("")
    String getUsers(){
        return "Users;";
    }

    @PostMapping("")
    ResponseEntity<UserResponse> signupUser(@RequestBody CreateUserRequest request){
        var user = usersService.createUser(request);
        URI userURI = URI.create("/users/" + user.getId());
        return ResponseEntity.created(userURI)
                .body(modelMapper.map(user, UserResponse.class));
    }

    @PostMapping("/login")
    ResponseEntity<UserResponse> loginUser(@RequestBody LoginUserRequest request){
        UserEntitiy user = usersService.loginUser(request.getUsername(), request.getPassword());
        return ResponseEntity.ok(modelMapper.map(user, UserResponse.class));
    }

    @ExceptionHandler({
            UsersService.UserNotFoundException.class
    })
    ResponseEntity<ErrorResponse> handleUserNotFoundException(Exception ex){
        String message = "Something went wrong";
        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;

        if(ex instanceof UsersService.UserNotFoundException) {
            message = ex.getMessage();
            status = HttpStatus.NOT_FOUND;
        }

        return ResponseEntity
                .status(status)
                .body(ErrorResponse
                        .builder()
                        .message(message)
                        .build());
    }
}
