package com.festibites.merchant.repository;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.annotation.Rollback;

import com.festibites.merchant.model.user.User;
import com.festibites.merchant.repository.user.UserRepository;
 
@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Rollback(false)
public class UserRepositoryTests {
 
    @Autowired
    private TestEntityManager entityManager;
     
    @Autowired
    private UserRepository repo;
     
    @Test
    public void testCreateUser() {
        User user = new User();
        user.setEmail("ravikumar@gmail.com");
        user.setPassword("ravi20202");
        user.setFirstName("Ravi");
        user.setLastName("Kumar");
        user.setPhoneNumber("8173436580");
         
        User savedUser = repo.save(user);
         
        User existUser = entityManager.find(User.class, savedUser.getId());
         
        assertThat(user.getEmail()).isEqualTo(existUser.getEmail());
        assertThat(user.getFirstName()).isEqualTo(existUser.getFirstName());
        assertThat(user.getLastName()).isEqualTo(existUser.getLastName());
        assertThat(user.getPhoneNumber()).isEqualTo(existUser.getPhoneNumber());
        assertThat(user.getPassword()).isEqualTo(existUser.getPassword());         
    }
}
