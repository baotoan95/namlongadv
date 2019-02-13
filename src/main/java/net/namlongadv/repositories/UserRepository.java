package net.namlongadv.repositories;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import net.namlongadv.entities.User;

public interface UserRepository extends PagingAndSortingRepository<User, UUID>, JpaSpecificationExecutor<User> {
    public User findByUsernameAndPassword(String username, String password);
    public User findFirstByUsername(String username);
}
