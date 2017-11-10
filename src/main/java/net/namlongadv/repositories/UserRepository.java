package net.namlongadv.repositories;

import java.util.UUID;

import org.springframework.data.repository.CrudRepository;

import net.namlongadv.models.User;

public interface UserRepository extends CrudRepository<User, UUID> {
    public User findByUsernameAndPassword(String username, String password);
    public User findByUsername(String username);
}
