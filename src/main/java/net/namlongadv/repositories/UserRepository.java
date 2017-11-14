package net.namlongadv.repositories;

import java.util.UUID;

import org.springframework.data.repository.PagingAndSortingRepository;

import net.namlongadv.models.User;

public interface UserRepository extends PagingAndSortingRepository<User, UUID> {
    public User findByUsernameAndPassword(String username, String password);
    public User findByUsername(String username);
}
