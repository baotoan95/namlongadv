package net.namlongadv.dto;

import lombok.Getter;
import lombok.Setter;
import net.namlongadv.models.User;

@Setter
@Getter
public class UserDTO {
    private User user;
    private String newPassword;
}
