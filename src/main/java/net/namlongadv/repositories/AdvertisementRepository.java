package net.namlongadv.repositories;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import net.namlongadv.models.Advertisement;

public interface AdvertisementRepository extends PagingAndSortingRepository<Advertisement, UUID> {
	@Query("select adv from Advertisement adv where "
			+ "concat(upper(adv.houseNo), ', ', upper(adv.street), ', ', upper(adv.ward), ', ', upper(adv.district), ', ', upper(adv.province)) "
			+ "like concat('%',upper(:address),'%')")
	public Page<Advertisement> findByAddressOrderByStartDate(@Param("address") String address, Pageable pageable);
	
	public Page<Advertisement> findByCodeContainingOrderByStartDate(@Param("code") String code, Pageable pageable);
	
	public List<Advertisement> findByEndDateLessThanEqual(Date milestone);
	
	public Page<Advertisement> findByUpdatedDateBetween(Date start, Date end, Pageable pageable);
	
	@Query("select adv from Advertisement adv where "
			+ "(:address is null or concat(upper(adv.houseNo), ', ', upper(adv.street), ', ', upper(adv.ward), ', ', upper(adv.district), ', ', upper(adv.province)) "
			+ "like concat('%',:address,'%')) "
			+ "and (:code is null or adv.code = :code) "
			+ "and (:username is null or adv.createdBy.username = :username) "
			+ "and (adv.updatedDate between :fromDate and :toDate)")
	public Page<Advertisement> search(
			@Param("code") String code, 
			@Param("address") String address, 
			@Param("username") String username,
			@Param("fromDate") Date from,
			@Param("toDate") Date to,
			Pageable pageable);
	
}
