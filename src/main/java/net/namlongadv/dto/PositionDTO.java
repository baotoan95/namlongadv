package net.namlongadv.dto;

import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PositionDTO {
	private UUID advId;
	private float latitude;
	private float longitude;
	private String title;
	private String avatar;
}
