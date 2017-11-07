package net.namlongadv.models;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "users")
@Setter
@Getter
public class User implements Serializable {
    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    @Id
    private String username;
    private String password;
    private String name;
    private String email;
    private String department;
    private String phone;
    private boolean accountNonLocked;
    @ManyToMany(targetEntity = UserRole.class, fetch = FetchType.EAGER)
    @JoinTable(name = "user_role", joinColumns = {
            @JoinColumn(name = "username", nullable = false, updatable = false) }, inverseJoinColumns = {
                    @JoinColumn(name = "role", nullable = false, updatable = false) })
    private List<UserRole> roles;
}
