package net.namlongadv.models;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;
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
}
