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
			+ "upper(adv.addressSearching) like upper(concat('%',:address,'%'))")
	public Page<Advertisement> findByAddress(@Param("address") String address, @Param("roles") List<String> roles, Pageable pageable);
	
	public Page<Advertisement> findByHouseNoIgnoreCaseAndStreetIgnoreCaseAndWardIgnoreCaseAndDistrictIgnoreCaseAndProvinceIgnoreCase(String houseNo,
			String street, String ward, String district, String province, Pageable pageable);

	public List<Advertisement> findByOwnerEndDateLessThanEqualOrAdvCompEndDateLessThanEqual(Date milestone1,
			Date milestone2);

	@Query("select distinct adv from Advertisement adv join adv.createdBy.roles roles where roles.code in :roles and "
			+ "adv.updatedDate between :start and :end")
	public Page<Advertisement> findByUpdatedDate(@Param("start") Date start, @Param("end") Date end,
			@Param("roles") List<String> roles, Pageable pageable);

	@Query("select distinct adv from Advertisement adv join adv.createdBy.roles roles where roles.code in :roles and "
			+ "upper(adv.addressSearching) like upper(concat('%',:address,'%')) " 
			+ "and (upper(adv.code) like upper(concat('%',:code,'%'))) "
			+ "and (upper(adv.createdBy.username) like upper(concat('%',:username,'%'))) "
			+ "and (adv.updatedDate between :fromDate and :toDate) "
			+ "and (upper(adv.advCompNameSearching) like upper(concat('%',:contactName,'%')) "
			+ "or upper(adv.ownerContactPersonSearching) like upper(concat('%',:contactName,'%')))")
	public Page<Advertisement> search(@Param("code") String code, @Param("address") String address,
			@Param("username") String username, @Param("fromDate") Date from, @Param("toDate") Date to,
			@Param("contactName") String contactName, @Param("roles") List<String> roles, Pageable pageable);
	
	@Query("select distinct adv from Advertisement adv join adv.createdBy.roles roles where roles.code in :roles and "
			+ "upper(adv.addressSearching) like upper(concat('%',:address,'%')) " 
			+ "and (upper(adv.code) like upper(concat('%',:code,'%'))) "
			+ "and (upper(adv.createdBy.username) like upper(concat('%',:username,'%'))) "
			+ "and (adv.updatedDate between :fromDate and :toDate) "
			+ "and (upper(adv.advCompNameSearching) like upper(concat('%',:contactName,'%')) "
			+ "or upper(adv.ownerContactPersonSearching) like upper(concat('%',:contactName,'%'))) "
			+ "and adv.provinceCode = :province_code")
	public Page<Advertisement> search(@Param("code") String code, @Param("address") String address,
			@Param("username") String username, @Param("fromDate") Date from, @Param("toDate") Date to,
			@Param("contactName") String contactName, @Param("province_code") String province_code, @Param("roles") List<String> roles, Pageable pageable);
	
	@Query("select distinct adv from Advertisement adv inner join adv.createdBy.roles roles where roles.code in :roles")
	public Page<Advertisement> findByRoles(@Param("roles") List<String> roles, Pageable pageable);
	
	@Query("select adv from Advertisement adv where adv.code like concat('%','-',:code)")
	public Advertisement checkCode(@Param("code") String code);

}
