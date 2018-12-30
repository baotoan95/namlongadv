package net.namlongadv.entities;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Table(name = "persistent_logins")
@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class PersistentLogins {
	@Id
	private String series;
	private String username;
    private String token;
    private Date lastUsed;
}
