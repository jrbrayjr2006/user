package io.pivotal.management.user.service;

import io.pivotal.management.user.model.User;
import io.pivotal.management.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <b>Description:</b>
 * <p>
 *     Implement the data access logic and abstract it away from the controller
 * </p>
 * @author jbray
 * @version 0.1.0
 */
@Service
public class UserDataSevice {

    @Autowired
    private UserRepository userRepository;


    public User retrieveUser(String id) {
        User user = userRepository.findById(id).get();
        return user;
    }


    public User saveUser(User user) {
        user = userRepository.save(user);
        return user;
    }
}
