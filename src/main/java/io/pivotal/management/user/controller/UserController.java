package io.pivotal.management.user.controller;

import io.pivotal.management.user.model.User;
import io.pivotal.management.user.repository.UserRepository;
import io.pivotal.management.user.service.SecurityService;
import io.pivotal.management.user.service.UserDataSevice;
//import io.swagger.annotations.Api;
//import io.swagger.annotations.ApiOperation;
//import io.swagger.annotations.ApiResponse;
//import io.swagger.annotations.ApiResponses;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.print.attribute.standard.Media;
import java.util.*;


//@Api( value = "RestoreAPI")
@Slf4j
@RestController
public class UserController {

    private SecurityService securityService;

    private UserDataSevice userDataSevice;


    public UserController(SecurityService securityService, UserDataSevice userDataSevice) {
        this.securityService = securityService;
        this.userDataSevice = userDataSevice;
    }

    @GetMapping(value = "/", produces = MediaType.APPLICATION_JSON_VALUE)
    public Map<String,String> serviceUp() {
        Map<String,String> result = new HashMap<>();
        result.put("data","success");
        return result;
    }

    @GetMapping(value = "/count", produces = MediaType.APPLICATION_JSON_VALUE)
    public long getCount() {
        return this.userDataSevice.getUserCount();
    }

    @GetMapping(value = "/user/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public User getUser(@PathVariable String id) {
        User user = new User();
        user.setId(id);
        user = this.userDataSevice.retrieveUser(id);
        return user;
    }

    @PostMapping(value = "/user", consumes = MediaType.APPLICATION_JSON_VALUE)
    public User createUser(@RequestBody User user) {
        //Map<String,String> result = new HashMap<>();
        log.info("creating a new user in the system");
        User result = this.userDataSevice.createUser(user);
        return user;
    }

    @PutMapping(value = "/user/{id}/{firstname}/{lastname}/{username}", produces = MediaType.APPLICATION_JSON_VALUE)
    public User updateUser(@PathVariable String id, @PathVariable String firstname, @PathVariable String lastname, @PathVariable String username) {
        User user = this.userDataSevice.updateUser(id,firstname,lastname,username);
        return user;
    }

    @DeleteMapping(value = "/user/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Map<String,String> deleteUser(@PathVariable String id) {
        log.info("removing a user from the system");
        Map<String,String> result = new HashMap<>();
        if(this.userDataSevice.deleteUserById(id)) {
            result.put("data","success");
        } else {
            result.put("data","failed");
        }
        return result;
    }

    @GetMapping(value = "/users", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<User> getAllUsers() {
        List<User> result = new ArrayList<>();
        result = userDataSevice.retrieveAllUsers();
        return result;
    }
}
