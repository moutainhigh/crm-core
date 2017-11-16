package com.cafe.crm.controllers.boss.registration;

import com.cafe.crm.dto.registration.UserRegistrationForm;
import com.cafe.crm.services.impl.user.UserRegistrationServiceImpl;
import com.cafe.crm.services.interfaces.user.UserService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.embedded.LocalServerPort;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;


import javax.validation.Valid;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.CoreMatchers.containsString;



@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class RegistrationControllerIT {

    private TestRestTemplate restTemplate = new TestRestTemplate();

    @Autowired
    private UserRegistrationServiceImpl userRegistrationService;

    @Autowired
    private UserService userService;

    @Valid
    private UserRegistrationForm user = new UserRegistrationForm();

    @LocalServerPort
    private int port = 8080;

    @Before
    public void setUp() throws Exception {
    }

    @Test
    public void showRegistrationPage() throws Exception {
        ResponseEntity<String> responseEntity = restTemplate.getForEntity(createURLWithPort("/registration"), String.class);
        assertThat(responseEntity.getBody(),containsString("Регистрация в системе"));
    }

/*
    @Test
    public void registerNewCompanyAndUser() throws Exception {
        user.setCompanyName("testCompany");
        user.setEmail("test@test.ru");
        user.setFirstName("testFirstName");
        user.setLastName("testLastName");
        user.setPassword("testPass");
        user.setPhone("88888888888");
        userRegistrationService.registerUser(user);
        assertThat(userService.findByEmail("test@test.ru"), is(user));
    }
*/
    @Test
    public void handleUserUpdateException() throws Exception {
    }

    private String createURLWithPort(String uri) {
        return "http://localhost:" + port + uri;
    }

}