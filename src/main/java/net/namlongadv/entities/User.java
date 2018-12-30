package net.namlongadv.entities;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "users")
@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class User implements Serializable {
    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    private UUID id;
    private String username;
    private String password;
    private String name;
    private String email;
    private String department;
    private String phone;
    private boolean accountNonLocked;
    @ManyToMany(targetEntity = UserRole.class, fetch = FetchType.EAGER)
    @JoinTable(name = "user_role", joinColumns = {
            @JoinColumn(name = "id", nullable = false, updatable = false) }, inverseJoinColumns = {
                    @JoinColumn(name = "role", nullable = false, updatable = false) })
    private List<UserRole> roles;
    private Date createdDate;
    private Date updatedDate;
    
    @PreUpdate
    public void onPreUpdate() {
    	updatedDate = new Date();
    }
    
    @PrePersist
    public void onPrePersist() {
    	createdDate = new Date();
    	updatedDate = new Date();
    }
}
