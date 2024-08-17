package com.example.blogapp.users;

import com.example.blogapp.common.dtos.ErrorResponse;
import com.example.blogapp.security.JWTService;
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
    private final JWTService jwtService;

    public UserController(UsersService usersService, ModelMapper modelMapper, JWTService jwtService){
        this.usersService = usersService;
        this.modelMapper = modelMapper;
        this.jwtService = jwtService;
    }

    @GetMapping("")
    String getUsers(){
        return "Users;";
    }

    @PostMapping("")
    ResponseEntity<UserResponse> signupUser(@RequestBody CreateUserRequest request){
        var user = usersService.createUser(request);
        URI userURI = URI.create("/users/" + user.getId());

        var savedUser = modelMapper.map(user, UserResponse.class);
        savedUser.setToken(jwtService.createJWT(savedUser.getId()));

        return ResponseEntity.created(userURI)
                .body(savedUser);
    }

    @PostMapping("/login")
    ResponseEntity<UserResponse> loginUser(@RequestBody LoginUserRequest request){
        UserEntitiy user = usersService.loginUser(request.getUsername(), request.getPassword());

        var userResponse = modelMapper.map(user, UserResponse.class);
        userResponse.setToken(jwtService.createJWT(user.getId()));

        return ResponseEntity.ok(userResponse);
    }

    @ExceptionHandler({
            UsersService.UserNotFoundException.class,
            UsersService.InvaildCredentialsException.class
    })
    ResponseEntity<ErrorResponse> handleUserNotFoundException(Exception ex){
        String message = "Something went wrong";
        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;

        if(ex instanceof UsersService.UserNotFoundException) {
            message = ex.getMessage();
            status = HttpStatus.NOT_FOUND;
        } else if(ex instanceof UsersService.InvaildCredentialsException) {
            message = ex.getMessage();
            status = HttpStatus.UNAUTHORIZED;
        }

        return ResponseEntity
                .status(status)
                .body(ErrorResponse
                        .builder()
                        .message(message)
                        .build());
    }
}
