package net.namlongadv.repositories;

import java.util.UUID;

import org.springframework.data.repository.CrudRepository;

import net.namlongadv.entities.UserRole;

public interface RoleRepository extends CrudRepository<UserRole, UUID> {

}
