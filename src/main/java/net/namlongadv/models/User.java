package net.namlongadv.models;

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
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.annotations.GenericGenerator;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "users")
@Setter
@Getter
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
    @Size(min = 5, message = "Tên tài khoản phải lớn hơn hoặc bằng 5 ký tự")
    @NotNull(message = "Vui lòng nhập tên tài khoản")
    private String username;
    @NotNull(message = "Vui lòng nhập mật khẩu")
    private String password;
    @NotNull(message = "Vui lòng nhập tên")
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
    private Date createdDate = new Date();
}
