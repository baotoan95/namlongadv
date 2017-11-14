package net.namlongadv.repositories;

import java.util.UUID;

import org.springframework.data.repository.PagingAndSortingRepository;

import net.namlongadv.models.Advertisement;

public interface AdvertisementRepository extends PagingAndSortingRepository<Advertisement, UUID>{

}
