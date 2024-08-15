package com.example.blogapp.users;

import com.example.blogapp.users.dtos.CreateUserRequest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
@AutoConfigureTestDatabase()
@ActiveProfiles("test")
public class UsersServiceTests {


    @Autowired
    UsersService usersService;

    @Test
    void can_create_user(){
        var user = usersService.createUser(new CreateUserRequest(
                "dummy",
                "password123",
                "abc@blogapp.com"
        ));

        Assertions.assertNotNull(user);
        Assertions.assertEquals("dummy", user.getUsername());
    }
}
