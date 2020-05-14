package io.pivotal.management.user.controller;

import io.pivotal.management.user.model.User;
import io.pivotal.management.user.repository.UserRepository;
import io.pivotal.management.user.service.SecurityService;
import io.pivotal.management.user.service.UserDataSevice;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@Api( value = "RestoreAPI")
@RestController
public class UserController {

    private UserRepository repository;

    private SecurityService securityService;

    private UserDataSevice userDataSevice;


    public UserController(UserRepository repository, SecurityService securityService, UserDataSevice userDataSevice) {
        this.repository = repository;
        this.securityService = securityService;
        this.userDataSevice = userDataSevice;
    }

    @ApiOperation(
            value = "Service heartbeat",
            notes = "This operation verifies that the service is active"
    )
    @ApiResponses( value = {@ApiResponse(code = 200, message = "OK, service available")} )
    @CrossOrigin
    @RequestMapping(value = "/", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.GET)
    public Map<String,String> serviceUp() {
        Map<String,String> result = new HashMap<>();
        result.put("data","success");
        return result;
    }

    @ApiOperation(
            value = "Count users",
            notes = "This operation returns the number of users currently in the database"
    )
    @ApiResponses( value = {@ApiResponse(code = 200, message = "")} )
    @CrossOrigin
    @RequestMapping(value = "/count", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.GET)
    public long getCount() {
        return this.repository.count();
    }

    @ApiOperation(
            value = "Get user by ID",
            notes = "This operation gets a specified user with a matching ID"
    )
    @ApiResponses( value = {@ApiResponse(code = 200, message = "")} )
    @CrossOrigin
    @RequestMapping(value = "/user/{id}", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.GET)
    public User getUser(@PathVariable String id) {
        User user = new User();
        user.setId(id);
        Optional<User> userOpt = this.repository.findById(id);
        if(userOpt.isPresent()) {
            user = userOpt.get();
        }
        return user;
    }

    @ApiOperation(
            value = "Create user",
            notes = "This operation creates a new user record"
    )
    @ApiResponses( value = {@ApiResponse(code = 200, message = "")} )
    @CrossOrigin
    @RequestMapping(value = "/user/{firstname}/{lastname}/{username}", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.POST)
    public User createUser(@PathVariable String firstname, @PathVariable String lastname, @PathVariable String username) {
        //Map<String,String> result = new HashMap<>();
        User user = new User(firstname,lastname,username, securityService.securePassword(SecurityService.DEFAULT_PASSWORD), securityService.getSalt());
        User result = this.repository.insert(user);
        return user;
    }

    @ApiOperation(
            value = "Update user",
            notes = "This operation updates an existing user's data"
    )
    @ApiResponses( value = {@ApiResponse(code = 200, message = "")} )
    @CrossOrigin
    @RequestMapping(value = "/user/{id}/{firstname}/{lastname}/{username}", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.PUT)
    public User updateUser(@PathVariable String id, @PathVariable String firstname, @PathVariable String lastname, @PathVariable String username) {
        User user = this.userDataSevice.updateUser(id,firstname,lastname,username);
        return user;
    }

    @ApiOperation(
            value = "Delete specified user",
            notes = "This operation deletes a user with the matching submitted id"
    )
    @ApiResponses( value = {@ApiResponse(code = 200, message = "")} )
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

    @ApiOperation(
            value = "Get all users",
            notes = "Return all user data that is currently in the database"
    )
    @ApiResponses( value = {@ApiResponse(code = 200, message = "OK, users retrieved")} )
    @CrossOrigin
    @RequestMapping(value = "/users", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.GET)
    public List<User> getAllUsers() {
        List<User> result = new ArrayList<>();
        result = repository.findAll();
        return result;
    }
}
