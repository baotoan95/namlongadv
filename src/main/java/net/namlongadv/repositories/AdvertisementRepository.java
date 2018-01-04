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
	@Query("select distinct adv from Advertisement adv join adv.createdBy.roles roles where roles.code in :roles and "
			+ "concat(upper(adv.houseNo), ', ', upper(adv.street), ', ', upper(adv.ward), ', ', upper(adv.district), ', ', upper(adv.province)) "
			+ "like concat('%',upper(:address),'%')")
	public Page<Advertisement> findByAddress(@Param("address") String address, @Param("roles") List<String> roles, Pageable pageable);

	public List<Advertisement> findByOwnerEndDateLessThanEqualOrAdvCompEndDateLessThanEqual(Date milestone1,
			Date milestone2);

	@Query("select distinct adv from Advertisement adv join adv.createdBy.roles roles where roles.code in :roles and "
			+ "adv.updatedDate between :start and :end")
	public Page<Advertisement> findByUpdatedDate(@Param("start") Date start, @Param("end") Date end,
			@Param("roles") List<String> roles,Pageable pageable);

	@Query("select distinct adv from Advertisement adv join adv.createdBy.roles roles where roles.code in :roles and "
			+ "(:address is null or (concat(upper(adv.houseNo), ', ', upper(adv.street), ', ', upper(adv.ward), ', ', upper(adv.district), ', ', upper(adv.province)) "
			+ "like concat('%',:address,'%'))) " + "and (:code is null or upper(adv.code) = :code) "
			+ "and (:username is null or adv.createdBy.username = :username) "
			+ "and (adv.updatedDate between :fromDate and :toDate) "
			+ "and ((:contactName is null or upper(adv.ownerContactPerson) like concat('%',:contactName,'%')) "
			+ "or (:contactName is null or upper(adv.advCompName) like concat('%',:contactName,'%')))")
	public Page<Advertisement> search(@Param("code") String code, @Param("address") String address,
			@Param("username") String username, @Param("fromDate") Date from, @Param("toDate") Date to,
			@Param("contactName") String contactName, @Param("roles") List<String> roles, Pageable pageable);
	
	@Query("select distinct adv from Advertisement adv inner join adv.createdBy.roles roles where roles.code in :roles")
	public Page<Advertisement> findByRoles(@Param("roles") List<String> roles, Pageable pageable);

}
