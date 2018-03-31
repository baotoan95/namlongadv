package net.namlongadv.repositories;

import java.util.List;
import java.util.UUID;

import org.springframework.data.repository.CrudRepository;

import net.namlongadv.models.AdvImage;

public interface AdvImageRepository extends CrudRepository<AdvImage, UUID> {
	public List<AdvImage> findByAdvertisement_Id(UUID advId);
	public int countByIdAndAdvertisement_IdNot(UUID imageId, UUID advId);
}
