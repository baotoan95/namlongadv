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

	public List<Advertisement> findByOwnerEndDateLessThanEqualOrAdvCompEndDateLessThanEqualOrderByAdvCompEndDateDescOwnerEndDateDesc(Date milestone1,
			Date milestone2);

	@Query("select distinct adv from Advertisement adv join adv.createdBy.roles roles where roles.code in :roles and "
			+ "adv.updatedDate between :start and :end")
	public Page<Advertisement> findByUpdatedDate(@Param("start") Date start, @Param("end") Date end,
			@Param("roles") List<String> roles, Pageable pageable);

	@Query("select distinct adv from Advertisement adv where "
			+ "(adv.code like concat('%',:code,'%')) "
			+ "and (adv.addressSearching like concat('%',:address,'%')) " 
			+ "and (adv.createdBy.username like concat('%',:username,'%')) "
			+ "and (adv.createdDate between :fromDate and :toDate) "
			+ "and (adv.advCompNameSearching like concat('%',:contactName,'%') "
			+ "or adv.ownerContactPersonSearching like concat('%',:contactName,'%')) "
			+ "and (:houseNo is null or adv.houseNoSearching = :houseNo) "
			+ "and (:street is null or adv.streetSearching = :street) "
			+ "and (:ward is null or adv.wardSearching = :ward) "
			+ "and (:district is null or adv.districtSearching = :district) "
			+ "and (:title is null or adv.titleSearching like concat('%',:title,'%')) "
			+ "and (:provinceCode is null or adv.provinceCode = :provinceCode)")
	public Page<Advertisement> search(
			@Param("code") String code, 
			@Param("address") String address,
			@Param("username") String username, 
			@Param("fromDate") Date from, 
			@Param("toDate") Date to,
			@Param("contactName") String contactName, 
			@Param("houseNo") String houseNo,
			@Param("street") String street,
			@Param("ward") String ward,
			@Param("district") String district,
			@Param("title") String title,
			@Param("provinceCode") String provinceCode, Pageable pageable);
	
	@Query("select distinct adv from Advertisement adv inner join adv.createdBy.roles roles where roles.code in :roles")
	public Page<Advertisement> findByRoles(@Param("roles") List<String> roles, Pageable pageable);
	
//	@Query("select adv from Advertisement adv where adv.code like concat('%',:code)")
	public Advertisement findByCode(String code);
	
}
