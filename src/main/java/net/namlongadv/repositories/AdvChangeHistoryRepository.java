package net.namlongadv.repositories;

import java.util.List;
import java.util.UUID;

import javax.transaction.Transactional;

import org.springframework.data.repository.CrudRepository;

import net.namlongadv.models.AdvChangeHistory;

public interface AdvChangeHistoryRepository extends CrudRepository<AdvChangeHistory, UUID> {
	List<AdvChangeHistory> findByAdvertIdOrderByUpdatedDateDesc(UUID advertId);
	AdvChangeHistory findTop1ByAdvertIdOrderByUpdatedDateDesc(UUID advertId);
	@Transactional
	void deleteByAdvertId(UUID advertId);
}
