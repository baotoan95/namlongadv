package net.namlongadv.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;
import net.namlongadv.convertor.ProvinceConvertor;
import net.namlongadv.dto.ProvinceDTO;
import net.namlongadv.repositories.ProvinceRepository;

@Service
@Slf4j
public class ProvinceService {
	@Autowired
	private ProvinceRepository provinceRepository;
	
	@Cacheable(value = "provinces")
	public List<ProvinceDTO> findAll() {
		log.debug("Loading provinces...");
		List<ProvinceDTO> provinceDTOs = new ArrayList<>();
		provinceRepository.findAll().spliterator().forEachRemaining(province -> {
			provinceDTOs.add(ProvinceConvertor.convertToDTO(province));
		});
		return provinceDTOs;
	}
}
