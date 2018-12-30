package net.namlongadv.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import net.namlongadv.entities.Advertisement;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class AdvertisementWrapperDTO {
	private List<Advertisement> advs;
}
