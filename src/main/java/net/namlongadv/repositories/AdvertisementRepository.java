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
	public Page<Advertisement> findByAddress(@Param("address") String address, Pageable pageable);

	public List<Advertisement> findByOwnerEndDateLessThanEqualOrAdvCompEndDateLessThanEqual(Date milestone1,
			Date milestone2);

	public Page<Advertisement> findByUpdatedDateBetween(Date start, Date end, Pageable pageable);

	@Query("select adv from Advertisement adv where "
			+ "(:address is null or (concat(upper(adv.houseNo), ', ', upper(adv.street), ', ', upper(adv.ward), ', ', upper(adv.district), ', ', upper(adv.province)) "
			+ "like concat('%',:address,'%'))) " + "and (:code is null or upper(adv.code) = :code) "
			+ "and (:username is null or adv.createdBy.username = :username) "
			+ "and (adv.updatedDate between :fromDate and :toDate) "
			+ "and ((:contactName is null or upper(adv.ownerContactPerson) like concat('%',:contactName,'%')) "
			+ "or (:contactName is null or upper(adv.advCompName) like concat('%',:contactName,'%')))")
	public Page<Advertisement> search(@Param("code") String code, @Param("address") String address,
			@Param("username") String username, @Param("fromDate") Date from, @Param("toDate") Date to,
			@Param("contactName") String contactName, Pageable pageable);

}
