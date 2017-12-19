package io.pivotal.management.user.repository;

import io.pivotal.management.user.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRepository extends MongoRepository<User,Long> {
}
