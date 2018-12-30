package net.namlongadv.repositories;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import net.namlongadv.entities.Advertisement;

public interface HotRepository extends JpaRepository<Advertisement, UUID> {
	@Query(value = "alter table adv_change_history alter column title type text, "
			+ "alter column street type text, "
			+ "alter column house_No type text, "
			+ "alter column ward type text, "
			+ "alter column district type text, "
			+ "alter column province type text, "
			+ "alter column width_size type text, "
			+ "alter column height_Size type text, "
			+ "alter column amount type text, "
			+ "alter column map type text, "
			+ "alter column describe type text, "
			+ "alter column views type text, "
			+ "alter column flow type text, "
			+ "alter column impl_Time type text, "
			+ "alter column impl_Form type text, "
			+ "alter column light_System type text, "
			+ "alter column owner_Phone type text, "
			+ "alter column owner_Email type text, "
			+ "alter column owner_Price type text, "
			+ "alter column owner_Contact_Person type text, "
			+ "alter column owner_Start_Date type text, "
			+ "alter column owner_End_Date type text, "
			+ "alter column owner_Note type text, "
			+ "alter column adv_Comp_Phone type text, "
			+ "alter column adv_Comp_Email type text, "
			+ "alter column adv_Comp_Price type text, "
			+ "alter column adv_Comp_Contact_Person type text, "
			+ "alter column adv_Comp_Name type text, "
			+ "alter column adv_Comp_Start_Date type text, "
			+ "alter column adv_Comp_End_Date type text, "
			+ "alter column adv_Comp_Note type text, "
			+ "alter column price type text, "
			+ "alter column type type text", nativeQuery = true)
	public void modifyDataTypeAdvHistory();
	
	@Query(value = "alter table advertisements alter column title type text, "
			+ "alter column street type text, "
			+ "alter column house_No type text, "
			+ "alter column ward type text, "
			+ "alter column district type text, "
			+ "alter column province type text, "
			+ "alter column width_size type text, "
			+ "alter column height_Size type text, "
			+ "alter column amount type text, "
			+ "alter column map type text, "
			+ "alter column describe type text, "
			+ "alter column views type text, "
			+ "alter column flow type text, "
			+ "alter column impl_Time type text, "
			+ "alter column impl_Form type text, "
			+ "alter column light_System type text, "
			+ "alter column owner_Phone type text, "
			+ "alter column owner_Email type text, "
			+ "alter column owner_Price type text, "
			+ "alter column owner_Contact_Person type text, "
			+ "alter column owner_Start_Date type text, "
			+ "alter column owner_End_Date type text, "
			+ "alter column owner_Note type text, "
			+ "alter column adv_Comp_Phone type text, "
			+ "alter column adv_Comp_Email type text, "
			+ "alter column adv_Comp_Price type text, "
			+ "alter column adv_Comp_Contact_Person type text, "
			+ "alter column adv_Comp_Name type text, "
			+ "alter column adv_Comp_Start_Date type text, "
			+ "alter column adv_Comp_End_Date type text, "
			+ "alter column adv_Comp_Note type text, "
			+ "alter column price type text, "
			+ "alter column type type text", nativeQuery = true)
	public void modifyDataTypeAdv();
}
