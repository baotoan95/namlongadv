package net.namlongadv.services;

import java.util.UUID;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import net.namlongadv.repositories.AdvImageRepository;

@Service
public class HotService {
	@Autowired
	private AdvImageRepository advImageRepository;
	
	@Transactional
	public void deleteImage(UUID imageId) {
		advImageRepository.deleteById(imageId);
	}
}
