package net.namlongadv.repositories;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import net.namlongadv.entities.Advertisement;

public interface AdvertisementRepository extends PagingAndSortingRepository<Advertisement, UUID>, JpaSpecificationExecutor<Advertisement> {
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

	@Query("select distinct adv from Advertisement adv inner join adv.createdBy.roles roles where roles.code in :roles")
	public Page<Advertisement> findByRoles(@Param("roles") List<String> roles, Pageable pageable);
	
	public Advertisement findByCode(String code);
	
}
