package net.namlongadv.repositories;

import org.springframework.data.repository.CrudRepository;

import net.namlongadv.models.User;

public interface UserRepository extends CrudRepository<User, String> {
    public User findByUsernameAndPassword(String username, String password);
}
