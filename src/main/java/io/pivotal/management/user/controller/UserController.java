package io.pivotal.management.user.controller;

import io.pivotal.management.user.model.User;
import io.pivotal.management.user.repository.UserRepository;
import io.pivotal.management.user.service.SecurityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
public class UserController {

    @Autowired
    private UserRepository repository;

    @Autowired
    private SecurityService securityService;

    @CrossOrigin
    @RequestMapping(value = "/", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.GET)
    public Map<String,String> serviceUp() {
        Map<String,String> result = new HashMap<>();
        result.put("data","success");
        return result;
    }


    @CrossOrigin
    @RequestMapping(value = "/user/{id}", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.GET)
    public User getUser(@PathVariable String id) {
        User user = new User();
        user.setId(id);
        user = this.repository.findById(id).get();
        return user;
    }

    @CrossOrigin
    @RequestMapping(value = "/user/{firstname}/{lastname}/{username}", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.POST)
    public User createUser(@PathVariable String firstname, @PathVariable String lastname, @PathVariable String username) {
        //Map<String,String> result = new HashMap<>();
        User user = new User(firstname,lastname,username, securityService.securePassword(SecurityService.DEFAULT_PASSWORD), securityService.getSalt());
        User result = this.repository.insert(user);
        return user;
    }

    @CrossOrigin
    @RequestMapping(value = "/user", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.PUT)
    public User updateUser(Long id, User user) {
        //TODO add logic to update user
        this.repository.save(user);
        return user;
    }

    @CrossOrigin
    @RequestMapping(value = "/user/{id}", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.DELETE)
    public Map<String,String> deleteUser(@PathVariable String id) {
        Map<String,String> result = new HashMap<>();
        if(this.repository.existsById(id)) {
            this.repository.deleteById(id);
            result.put("data","success");
        } else {
            result.put("data","failed");
        }
        return result;
    }

    @CrossOrigin
    @RequestMapping(value = "/users", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.GET)
    public List<User> getAllUsers() {
        List<User> result = new ArrayList<>();
        result = repository.findAll();
        return result;
    }
}
