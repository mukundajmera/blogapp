package com.example.blogapp.users;

import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

@DataJpaTest
@ActiveProfiles("test")
public class UserRepositoryTests {
    @Autowired
    private UsersRepository usersRepository;


    @Test
    @Order(1)
    void can_create_users(){
        var user = UserEntitiy.builder()
                .username("majmera")
                .email("majmera@gmail.com")
                .build();

        usersRepository.save(user);
    }

    @Test
    @Order(2)
    void can_find_users(){
        var user = UserEntitiy.builder()
                .username("majmera")
                .email("majmera@gmail.com")
                .build();

        usersRepository.save(user);

        var users = usersRepository.findAll();
        assert users.size() == 1;
    }

}
