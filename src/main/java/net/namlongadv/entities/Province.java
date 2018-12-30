package net.namlongadv.entities;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "provinces")
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Province {
	@Id
	private String code;
	private String name;
}
