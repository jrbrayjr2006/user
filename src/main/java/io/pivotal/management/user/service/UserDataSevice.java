package io.pivotal.management.user.service;

import io.pivotal.management.user.model.User;
import io.pivotal.management.user.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.*;

/**
 * <b>Description:</b>
 * <p>
 *     Implement the data access logic and abstract it away from the controller
 * </p>
 * @author jbray
 * @version 0.1.0
 */

@Slf4j
@Service
public class UserDataSevice {

    private UserRepository userRepository;

    private SecurityService securityService;

    public UserDataSevice(UserRepository userRepository, SecurityService securityService) {
        this.userRepository = userRepository;
        this.securityService = securityService;
    }


    public User retrieveUser(String id) {
        log.info("retrieving one user...");
        User user = null;
        Optional<User> userOpt = userRepository.findById(id);
        if(userOpt.isPresent()) {
            user = userOpt.get();
        }
        return user;
    }

    public List<User> retrieveAllUsers() {
        log.info("retrieving all users...");
        List<User> users = new ArrayList<>();
        users = userRepository.findAll();
        return users;
    }


    public User saveUser(User user) {
        user = userRepository.save(user);
        return user;
    }

    public User createUser(User user) {
        user.setPassword(securityService.securePassword(SecurityService.DEFAULT_PASSWORD));
        user.setSalt(securityService.getSalt());
        User createdUser = this.userRepository.insert(user);
        return createdUser;
    }

    public User updateUser(String id, String firstname, String lastname, String username) {
        User user = this.retrieveUser(id);
        user.setId(id);
        user.setFirstname(firstname);
        user.setLastname(lastname);
        user.setUsername(username);
        user = this.userRepository.save(user);
        return user;
    }

    public Boolean deleteUserById(String id) {
        log.info("deleting user " + id);
        if(this.userRepository.existsById(id)) {
            this.userRepository.deleteById(id);
            return true;
        } else {
            return false;
        }
    }

    public long getUserCount() {
        long userCount = userRepository.count();
        return userCount;
    }
}
