package net.namlongadv.repositories;

import java.util.List;
import java.util.UUID;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import net.namlongadv.models.AdvImage;

public interface AdvImageRepository extends CrudRepository<AdvImage, UUID> {
	public List<AdvImage> findByAdvertisement_Id(UUID advId);
	public int countByIdAndAdvertisement_IdNot(UUID imageId, UUID advId);
	
	@Transactional
	@Modifying
	@Query("delete from AdvImage adv where adv.id = :imageId")
	public void deleteById(@Param(value = "imageId") UUID imageId);
}
