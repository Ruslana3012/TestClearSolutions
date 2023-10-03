package com.example.javapracticaltest_clearsolutions;

import com.example.javapracticaltest_clearsolutions.controllers.UserController;
import com.example.javapracticaltest_clearsolutions.models.User;
import com.example.javapracticaltest_clearsolutions.services.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Calendar;
import java.util.Date;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(UserController.class)
public class UserControllerIntegrationTest {
    @Autowired
    private MockMvc mvc;
    @MockBean
    private UserService userService;

    @Test
    public void createNewUser() throws Exception {
        mvc.perform(MockMvcRequestBuilders
                        .post("/create/new/user")
                        .content(asJsonString(new User("buch.rus@gmail.com", "Ruslana", "Buchynska", new Date(2000, Calendar.DECEMBER, 30), "IF", "0995575482")))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void updateUserFields() throws Exception {
        mvc.perform(MockMvcRequestBuilders
                        .put("/update/user/{email}", "buch.rus@gmail.com")
                        .content(asJsonString(new User("buch@gmail.com", "Rus", "Buch", new Date(2012, Calendar.DECEMBER, 30), "IF", "0995575482")))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void getUsersByBirthDate() throws Exception {
        mvc.perform(MockMvcRequestBuilders
                        .get("/get/users/{from}/{to}", "01.01.2000", "01.01.2030")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void deleteUser() throws Exception {
        mvc.perform(MockMvcRequestBuilders
                        .delete("/delete/{email}", "buch.rus@gmail.com"))
                .andExpect(status().isOk());
    }

    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
