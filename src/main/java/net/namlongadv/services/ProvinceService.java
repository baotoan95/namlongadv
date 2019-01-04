package net.namlongadv.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import net.namlongadv.convertor.ProvinceConvertor;
import net.namlongadv.dto.ProvinceDTO;
import net.namlongadv.repositories.ProvinceRepository;

@Service
public class ProvinceService {
	@Autowired
	private ProvinceRepository provinceRepository;
	
	public List<ProvinceDTO> findAll() {
		List<ProvinceDTO> provinceDTOs = new ArrayList<>();
		provinceRepository.findAll().spliterator().forEachRemaining(province -> {
			provinceDTOs.add(ProvinceConvertor.convertToDTO(province));
		});
		return provinceDTOs;
	}
}
