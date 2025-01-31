package io.pivotal.management.user.controller;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;
import io.pivotal.management.user.model.User;
import io.pivotal.management.user.service.SecurityService;
import io.pivotal.management.user.service.UserDataSevice;
import org.mockito.Mock;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.hamcrest.Matchers.containsString;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

@ExtendWith(MockitoExtension.class)
@ExtendWith(SpringExtension.class)
public class UserControllerTest {

    UserController controller;

    private MockMvc mockMvc;

    @Mock
    private SecurityService mockSecurityService;

    @Mock
    private UserDataSevice mockUserDataSevice;

    static String ID = "anyId";

    @BeforeEach
    public void setUp() {
        controller = new UserController(mockSecurityService, mockUserDataSevice);
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    @Test
    public void serviceUpTest() throws Exception {
        this.mockMvc.perform(get("/")).andDo(print()).andExpect(status().isOk()); //.andExpect(content().json("{'data':'success'}"));
    }

    @DisplayName("Given and id, when a request is sent, a status of ok is returned")
    @Test
    public void getUserTest() throws Exception {
        String FAKE_ID = "somefakeid";
        //when(repository.existsById(FAKE_ID)).thenReturn(true);
        //doNothing().when(repository).deleteById(FAKE_ID);
        this.mockMvc.perform(get("/user/" + FAKE_ID)).andDo(print()).andExpect(status().isOk());
    }

    @Test
    public void createUserTest() throws Exception {
        // Given
        User testUser = User.builder().firstname("John").lastname("Doe").username("johndoe").build();
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
        String requestJson = ow.writeValueAsString(testUser );

        // When
        this.mockMvc.perform(post("/user")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("johndoe")));
    }

    @DisplayName("Given user information, when user information is sent for updating, the system returns ok")
    @Test
    public void updateUserTest() throws Exception {
        String firstname = "anyfirstname";
        String lastname = "anylastname";
        String username = "anyusername";
        when(mockUserDataSevice.updateUser(ID,firstname,lastname,username)).thenReturn(new User());
        this.mockMvc.perform(put("/user/"+ID+"/"+firstname+"/"+lastname+"/"+username)).andDo(print()).andExpect(status().isOk());
    }

    @DisplayName("Given a specific user id, when a request is made to delete a user, the response should be ok")
    @Test
    public void deleteUserTest() throws Exception {
        this.mockMvc.perform(delete("/user/2")).andDo(print()).andExpect(status().isOk());
    }

    @DisplayName("Given a user id, when a request is made to delete the user, if the user already exists, then should return success")
    @Test
    public void whenDeleteIsCalledShouldReturnSuccess() {
        // Given
        String expected = "success";
        // When
        when(mockUserDataSevice.deleteUserById(ID)).thenReturn(true);
        String actual =  controller.deleteUser(ID).get("data");
        // Then
        assertEquals(expected, actual);
    }

    @DisplayName("Given a user id, when a request is made to delete the user, if the user does not exist, then should return failed")
    @Test
    public void whenDeleteIsNotCalledShouldReturnSuccess() {
        // Given
        String expected = "failed";
        // When
        when(mockUserDataSevice.deleteUserById(ID)).thenReturn(false);

        String actual =  controller.deleteUser(ID).get("data");
        // Then
        assertEquals(expected, actual);
    }

    @Test
    public void getListOfUsersTest() throws Exception {
        this.mockMvc.perform(get("/users")).andExpect(status().isOk());
    }
}
