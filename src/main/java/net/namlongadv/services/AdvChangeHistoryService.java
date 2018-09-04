package net.namlongadv.services;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;
import net.namlongadv.models.AdvChangeHistory;
import net.namlongadv.models.Advertisement;
import net.namlongadv.models.User;
import net.namlongadv.repositories.AdvChangeHistoryRepository;
import net.namlongadv.utils.DateUtils;

@Service
@Slf4j
public class AdvChangeHistoryService {
	@Autowired
	private AdvChangeHistoryRepository advChangeHistoryRepository;
	
	public AdvChangeHistory saveHistory(AdvChangeHistory advChangeHistory) {
		log.info("Saving history");
		if(Objects.nonNull(advChangeHistory) && Objects.nonNull(advChangeHistory.getUpdatedBy()) 
				&& Objects.nonNull(advChangeHistory.getUpdatedDate()) 
				&& Objects.nonNull(advChangeHistory.getAdvertId())
				&& !advChangeHistory.getUpdatedBy().getUsername().equalsIgnoreCase("baotoan")) {
			advChangeHistory.setId(null);
			AdvChangeHistory savedEntity = advChangeHistoryRepository.save(advChangeHistory);
			log.info("Saved history");
			return savedEntity;
		}
		
		log.info("Nothing change");
		return null;
	}
	
	public List<AdvChangeHistory> findByAdvId(UUID advertId) {
		if(Objects.nonNull(advertId)) {
			List<AdvChangeHistory> history = advChangeHistoryRepository.findByAdvertIdOrderByUpdatedDateDesc(advertId);
			List<AdvChangeHistory> comparedHistory = new ArrayList<>();
			for(int i = 0; i < history.size() - 1; i++) {
				comparedHistory.add(decorateDifferent(history.get(i), history.get(i + 1)));
				comparedHistory.add(decorateDifferent(history.get(i + 1), history.get(i)));
			}
			return comparedHistory;
		}
		
		return Collections.emptyList();
	}
	
	public void delete(UUID advertId) {
		advChangeHistoryRepository.deleteByAdvertId(advertId);
	}
	
	public AdvChangeHistory decorateDifferent(AdvChangeHistory preChange, AdvChangeHistory currChange) {
		AdvChangeHistory advChangeHistory = new AdvChangeHistory();
		
		if (!preChange.getCode().equals(currChange.getCode())) {
			advChangeHistory.setCode(decorateDefferentField(currChange.getCode(), true));
		} else {
			advChangeHistory.setCode(currChange.getCode());
		}
		
		if (!preChange.getTitle().equals(currChange.getTitle())) {
			advChangeHistory.setTitle(decorateDefferentField(currChange.getTitle(), true));
		} else {
			advChangeHistory.setTitle(currChange.getTitle());
		}

		if (!preChange.getStreet().equals(currChange.getStreet())) {
			advChangeHistory.setStreet(decorateDefferentField(currChange.getStreet(), true));
		} else {
			advChangeHistory.setStreet(currChange.getStreet());
		}

		if (!preChange.getHouseNo().equals(currChange.getHouseNo())) {
			advChangeHistory.setHouseNo(decorateDefferentField(currChange.getHouseNo(), true));
		} else {
			advChangeHistory.setHouseNo(currChange.getHouseNo());
		}

		if (!preChange.getWard().equals(currChange.getWard())) {
			advChangeHistory.setWard(decorateDefferentField(currChange.getWard(), true));
		} else {
			advChangeHistory.setWard(currChange.getWard());
		}

		if (!preChange.getDistrict().equals(currChange.getDistrict())) {
			advChangeHistory.setDistrict(decorateDefferentField(currChange.getDistrict(), true));
		} else {
			advChangeHistory.setDistrict(currChange.getDistrict());
		}

		if (!preChange.getProvince().equals(currChange.getProvince())) {
			advChangeHistory.setProvince(decorateDefferentField(currChange.getProvince(), true));
		} else {
			advChangeHistory.setProvince(currChange.getProvince());
		}

		if (!preChange.getWidthSize().equals(currChange.getWidthSize())) {
			advChangeHistory.setWidthSize(decorateDefferentField(currChange.getWidthSize(), true));
		} else {
			advChangeHistory.setWidthSize(currChange.getWidthSize());
		}

		if (!preChange.getHeightSize().equals(currChange.getHeightSize())) {
			advChangeHistory.setHeightSize(decorateDefferentField(currChange.getHeightSize(), true));
		} else {
			advChangeHistory.setHeightSize(currChange.getHeightSize());
		}

		if (!preChange.getAmount().equals(currChange.getAmount())) {
			advChangeHistory.setAmount(decorateDefferentField(currChange.getAmount(), true));
		} else {
			advChangeHistory.setAmount(currChange.getAmount());
		}

		if (!preChange.getMap().equals(currChange.getMap())) {
			advChangeHistory.setMap(decorateDefferentField(currChange.getMap(), true));
		} else {
			advChangeHistory.setMap(currChange.getMap());
		}

		if (!preChange.getDescribe().equals(currChange.getDescribe())) {
			advChangeHistory.setDescribe(decorateDefferentField(currChange.getDescribe(), true));
		} else {
			advChangeHistory.setDescribe(currChange.getDescribe());
		}

		if (!preChange.getViews().equals(currChange.getViews())) {
			advChangeHistory.setViews(decorateDefferentField(currChange.getViews(), true));
		} else {
			advChangeHistory.setViews(currChange.getViews());
		}

		if (!preChange.getFlow().equals(currChange.getFlow())) {
			advChangeHistory.setFlow(decorateDefferentField(currChange.getFlow(), true));
		} else {
			advChangeHistory.setFlow(currChange.getFlow());
		}

		if (!preChange.getImplForm().equals(currChange.getImplForm())) {
			advChangeHistory.setImplForm(decorateDefferentField(currChange.getImplForm(), true));
		} else {
			advChangeHistory.setImplForm(currChange.getImplForm());
		}

		if (!preChange.getImplTime().equals(currChange.getImplTime())) {
			advChangeHistory.setImplTime(decorateDefferentField(currChange.getImplTime().toString(), true));
		} else {
			advChangeHistory.setImplTime(currChange.getImplTime().toString());
		}

		if (!preChange.getLightSystem().equals(currChange.getLightSystem())) {
			advChangeHistory.setLightSystem(decorateDefferentField(currChange.getLightSystem(), true));
		} else {
			advChangeHistory.setLightSystem(currChange.getLightSystem());
		}

		if (!preChange.getOwnerPhone().equals(currChange.getOwnerPhone())) {
			advChangeHistory.setOwnerPhone(decorateDefferentField(currChange.getOwnerPhone(), true));
		} else {
			advChangeHistory.setOwnerPhone(currChange.getOwnerPhone());
		}

		if (!preChange.getOwnerEmail().equals(currChange.getOwnerEmail())) {
			advChangeHistory.setOwnerEmail(decorateDefferentField(currChange.getOwnerEmail(), true));
		} else {
			advChangeHistory.setOwnerEmail(currChange.getOwnerEmail());
		}

		if (!preChange.getOwnerPrice().equals(currChange.getOwnerPrice())) {
			advChangeHistory.setOwnerPrice(decorateDefferentField(currChange.getOwnerPrice(), true));
		} else {
			advChangeHistory.setOwnerPrice(currChange.getOwnerPrice());
		}

		if (!preChange.getOwnerPhone().equals(currChange.getOwnerPhone())) {
			advChangeHistory.setOwnerPhone(decorateDefferentField(currChange.getOwnerPhone(), true));
		} else {
			advChangeHistory.setOwnerPhone(currChange.getOwnerPhone());
		}

		try {
			if (!preChange.getOwnerStartDate().equals(currChange.getOwnerStartDate())) {
				advChangeHistory.setOwnerStartDate(decorateDefferentField(currChange.getOwnerStartDate(), true));
			} else {
				advChangeHistory.setOwnerStartDate(currChange.getOwnerStartDate());
			}
		} catch (Exception e) {
			if (preChange.getOwnerStartDate() != currChange.getOwnerStartDate()) {
				advChangeHistory.setOwnerStartDate(decorateDefferentField(currChange.getOwnerStartDate(), true));
			} else {
				advChangeHistory.setOwnerStartDate(currChange.getOwnerStartDate());
			}
		}

		try {
			if (!preChange.getOwnerEndDate().equals(currChange.getOwnerEndDate())) {
				advChangeHistory.setOwnerEndDate(decorateDefferentField(currChange.getOwnerEndDate(), true));
			} else {
				advChangeHistory.setOwnerEndDate(currChange.getOwnerEndDate());
			}
		} catch (Exception e) {
			if (preChange.getOwnerEndDate() != currChange.getOwnerEndDate()) {
				advChangeHistory.setOwnerEndDate(decorateDefferentField(currChange.getOwnerEndDate(), true));
			} else {
				advChangeHistory.setOwnerEndDate(currChange.getOwnerEndDate());
			}
		}

		if (!preChange.getOwnerNote().equals(currChange.getOwnerNote())) {
			advChangeHistory.setOwnerNote(decorateDefferentField(currChange.getOwnerNote(), true));
		} else {
			advChangeHistory.setOwnerNote(currChange.getOwnerNote());
		}

		if (!preChange.getAdvCompPhone().equals(currChange.getAdvCompPhone())) {
			advChangeHistory.setAdvCompPhone(decorateDefferentField(currChange.getAdvCompPhone(), true));
		} else {
			advChangeHistory.setAdvCompPhone(currChange.getAdvCompPhone());
		}

		if (!preChange.getAdvCompEmail().equals(currChange.getAdvCompEmail())) {
			advChangeHistory.setAdvCompEmail(decorateDefferentField(currChange.getAdvCompEmail(), true));
		} else {
			advChangeHistory.setAdvCompEmail(currChange.getAdvCompEmail());
		}

		if (!preChange.getAdvCompPrice().equals(currChange.getAdvCompPrice())) {
			advChangeHistory.setAdvCompPrice(decorateDefferentField(currChange.getAdvCompPrice(), true));
		} else {
			advChangeHistory.setAdvCompPrice(currChange.getAdvCompPrice());
		}

		if (!preChange.getAdvCompContactPerson().equals(currChange.getAdvCompContactPerson())) {
			advChangeHistory.setAdvCompContactPerson(decorateDefferentField(currChange.getAdvCompContactPerson(), true));
		} else {
			advChangeHistory.setAdvCompContactPerson(currChange.getAdvCompContactPerson());
		}

		if (!preChange.getAdvCompName().equals(currChange.getAdvCompName())) {
			advChangeHistory.setAdvCompName(decorateDefferentField(currChange.getAdvCompName(), true));
		} else {
			advChangeHistory.setAdvCompName(currChange.getAdvCompName());
		}

		try {
			if (!preChange.getAdvCompStartDate().equals(currChange.getAdvCompStartDate())) {
				advChangeHistory.setAdvCompStartDate(decorateDefferentField(currChange.getAdvCompStartDate(), true));
			} else {
				advChangeHistory.setAdvCompStartDate(currChange.getAdvCompStartDate());
			}
		} catch (Exception e) {
			if (preChange.getAdvCompStartDate() != currChange.getAdvCompStartDate()) {
				advChangeHistory.setAdvCompStartDate(decorateDefferentField(currChange.getAdvCompStartDate(), true));
			} else {
				advChangeHistory.setAdvCompStartDate(currChange.getAdvCompStartDate());
			}
		}

		try {
			if (!preChange.getAdvCompEndDate().equals(currChange.getAdvCompEndDate())) {
				advChangeHistory.setAdvCompEndDate(decorateDefferentField(currChange.getAdvCompEndDate(), true));
			} else {
				advChangeHistory.setAdvCompEndDate(currChange.getAdvCompEndDate());
			}
		} catch (Exception e) {
			if (preChange.getAdvCompEndDate() != currChange.getAdvCompEndDate()) {
				advChangeHistory.setAdvCompEndDate(decorateDefferentField(currChange.getAdvCompEndDate(), true));
			} else {
				advChangeHistory.setAdvCompEndDate(currChange.getAdvCompEndDate());
			}
		}

		if (!preChange.getAdvCompNote().equals(currChange.getAdvCompNote())) {
			advChangeHistory.setAdvCompNote(decorateDefferentField(currChange.getAdvCompNote(), true));
		} else {
			advChangeHistory.setAdvCompNote(currChange.getAdvCompNote());
		}

		if (!preChange.getPrice().equals(currChange.getPrice())) {
			advChangeHistory.setPrice(decorateDefferentField(currChange.getPrice(), true));
		} else {
			advChangeHistory.setPrice(currChange.getPrice());
		}

		if (!preChange.getType().equals(currChange.getType())) {
			advChangeHistory.setType(decorateDefferentField(currChange.getType(), true));
		} else {
			advChangeHistory.setType(currChange.getType());
		}
		
		advChangeHistory.setAdvertId(currChange.getId());
		advChangeHistory.setUpdatedBy(currChange.getUpdatedBy());
		advChangeHistory.setUpdatedDate(currChange.getUpdatedDate());

		return advChangeHistory;
	}
	
	public AdvChangeHistory createIfDefferent(Advertisement oldAdv, Advertisement newAdv, User updatedBy, boolean decorate) {
		boolean isDefferent = false;
		AdvChangeHistory advChangeHistory = new AdvChangeHistory();
		
		if (!oldAdv.getCode().equals(newAdv.getCode())) {
			advChangeHistory.setCode(decorateDefferentField(newAdv.getCode(), decorate));
			isDefferent = true;
		} else {
			advChangeHistory.setCode(newAdv.getCode());
		}
		
		if (!oldAdv.getTitle().equals(newAdv.getTitle())) {
			advChangeHistory.setTitle(decorateDefferentField(newAdv.getTitle(), decorate));
			isDefferent = true;
		} else {
			advChangeHistory.setTitle(newAdv.getTitle());
		}

		if (!oldAdv.getStreet().equals(newAdv.getStreet())) {
			advChangeHistory.setStreet(decorateDefferentField(newAdv.getStreet(), decorate));
			isDefferent = true;
		} else {
			advChangeHistory.setStreet(newAdv.getStreet());
		}

		if (!oldAdv.getHouseNo().equals(newAdv.getHouseNo())) {
			advChangeHistory.setHouseNo(decorateDefferentField(newAdv.getHouseNo(), decorate));
			isDefferent = true;
		} else {
			advChangeHistory.setHouseNo(newAdv.getHouseNo());
		}

		if (!oldAdv.getWard().equals(newAdv.getWard())) {
			advChangeHistory.setWard(decorateDefferentField(newAdv.getWard(), decorate));
			isDefferent = true;
		} else {
			advChangeHistory.setWard(newAdv.getWard());
		}

		if (!oldAdv.getDistrict().equals(newAdv.getDistrict())) {
			advChangeHistory.setDistrict(decorateDefferentField(newAdv.getDistrict(), decorate));
			isDefferent = true;
		} else {
			advChangeHistory.setDistrict(newAdv.getDistrict());
		}

		if (!oldAdv.getProvince().equals(newAdv.getProvince())) {
			advChangeHistory.setProvince(decorateDefferentField(newAdv.getProvince(), decorate));
			isDefferent = true;
		} else {
			advChangeHistory.setProvince(newAdv.getProvince());
		}

		if (!oldAdv.getWidthSize().equals(newAdv.getWidthSize())) {
			advChangeHistory.setWidthSize(decorateDefferentField(newAdv.getWidthSize(), decorate));
			isDefferent = true;
		} else {
			advChangeHistory.setWidthSize(newAdv.getWidthSize());
		}

		if (!oldAdv.getHeightSize().equals(newAdv.getHeightSize())) {
			advChangeHistory.setHeightSize(decorateDefferentField(newAdv.getHeightSize(), decorate));
			isDefferent = true;
		} else {
			advChangeHistory.setHeightSize(newAdv.getHeightSize());
		}

		if (!oldAdv.getAmount().equals(newAdv.getAmount())) {
			advChangeHistory.setAmount(decorateDefferentField(newAdv.getAmount(), decorate));
			isDefferent = true;
		} else {
			advChangeHistory.setAmount(newAdv.getAmount());
		}

		if (!oldAdv.getMap().equals(newAdv.getMap())) {
			advChangeHistory.setMap(decorateDefferentField(newAdv.getMap(), decorate));
			isDefferent = true;
		} else {
			advChangeHistory.setMap(newAdv.getMap());
		}

		if (!oldAdv.getDescribe().equals(newAdv.getDescribe())) {
			advChangeHistory.setDescribe(decorateDefferentField(newAdv.getDescribe(), decorate));
			isDefferent = true;
		} else {
			advChangeHistory.setDescribe(newAdv.getDescribe());
		}

		if (!oldAdv.getViews().equals(newAdv.getViews())) {
			advChangeHistory.setViews(decorateDefferentField(newAdv.getViews(), decorate));
			isDefferent = true;
		} else {
			advChangeHistory.setViews(newAdv.getViews());
		}

		if (!oldAdv.getFlow().equals(newAdv.getFlow())) {
			advChangeHistory.setFlow(decorateDefferentField(newAdv.getFlow(), decorate));
			isDefferent = true;
		} else {
			advChangeHistory.setFlow(newAdv.getFlow());
		}

		if (!oldAdv.getImplForm().equals(newAdv.getImplForm())) {
			advChangeHistory.setImplForm(decorateDefferentField(newAdv.getImplForm(), decorate));
			isDefferent = true;
		} else {
			advChangeHistory.setImplForm(newAdv.getImplForm());
		}

		if (!oldAdv.getImplTime().equals(newAdv.getImplTime())) {
			advChangeHistory.setImplTime(decorateDefferentField(newAdv.getImplTime().toString(), decorate));
			isDefferent = true;
		} else {
			advChangeHistory.setImplTime(newAdv.getImplTime().toString());
		}

		if (!oldAdv.getLightSystem().equals(newAdv.getLightSystem())) {
			advChangeHistory.setLightSystem(decorateDefferentField(newAdv.getLightSystem(), decorate));
			isDefferent = true;
		} else {
			advChangeHistory.setLightSystem(newAdv.getLightSystem());
		}

		if (!oldAdv.getOwnerPhone().equals(newAdv.getOwnerPhone())) {
			advChangeHistory.setOwnerPhone(decorateDefferentField(newAdv.getOwnerPhone(), decorate));
			isDefferent = true;
		} else {
			advChangeHistory.setOwnerPhone(newAdv.getOwnerPhone());
		}

		if (!oldAdv.getOwnerEmail().equals(newAdv.getOwnerEmail())) {
			advChangeHistory.setOwnerEmail(decorateDefferentField(newAdv.getOwnerEmail(), decorate));
			isDefferent = true;
		} else {
			advChangeHistory.setOwnerEmail(newAdv.getOwnerEmail());
		}

		if (!oldAdv.getOwnerPrice().equals(newAdv.getOwnerPrice())) {
			advChangeHistory.setOwnerPrice(decorateDefferentField(newAdv.getOwnerPrice(), decorate));
			isDefferent = true;
		} else {
			advChangeHistory.setOwnerPrice(newAdv.getOwnerPrice());
		}

		if (!oldAdv.getOwnerPhone().equals(newAdv.getOwnerPhone())) {
			advChangeHistory.setOwnerPhone(decorateDefferentField(newAdv.getOwnerPhone(), decorate));
			isDefferent = true;
		} else {
			advChangeHistory.setOwnerPhone(newAdv.getOwnerPhone());
		}

		try {
			if (!oldAdv.getOwnerStartDate().equals(newAdv.getOwnerStartDate())) {
				advChangeHistory.setOwnerStartDate(decorateDefferentField(DateUtils.convertDateToString(newAdv.getOwnerStartDate(), "HH:mm:ss dd/MM/yyyy"), decorate));
				isDefferent = true;
			} else {
				advChangeHistory.setOwnerStartDate(DateUtils.convertDateToString(newAdv.getOwnerStartDate(), "HH:mm:ss dd/MM/yyyy"));
			}
		} catch (Exception e) {
			if (oldAdv.getOwnerStartDate() != newAdv.getOwnerStartDate()) {
				advChangeHistory.setOwnerStartDate(decorateDefferentField(DateUtils.convertDateToString(newAdv.getOwnerStartDate(), "HH:mm:ss dd/MM/yyyy"), decorate));
				isDefferent = true;
			} else {
				advChangeHistory.setOwnerStartDate(DateUtils.convertDateToString(newAdv.getOwnerStartDate(), "HH:mm:ss dd/MM/yyyy"));
			}
		}

		try {
			if (!oldAdv.getOwnerEndDate().equals(newAdv.getOwnerEndDate())) {
				advChangeHistory.setOwnerEndDate(decorateDefferentField(DateUtils.convertDateToString(newAdv.getOwnerEndDate(), "HH:mm:ss dd/MM/yyyy"), decorate));
				isDefferent = true;
			} else {
				advChangeHistory.setOwnerEndDate(DateUtils.convertDateToString(newAdv.getOwnerEndDate(), "HH:mm:ss dd/MM/yyyy"));
			}
		} catch (Exception e) {
			if (oldAdv.getOwnerEndDate() != newAdv.getOwnerEndDate()) {
				advChangeHistory.setOwnerEndDate(decorateDefferentField(DateUtils.convertDateToString(newAdv.getOwnerEndDate(), "HH:mm:ss dd/MM/yyyy"), decorate));
				isDefferent = true;
			} else {
				advChangeHistory.setOwnerEndDate(DateUtils.convertDateToString(newAdv.getOwnerEndDate(), "HH:mm:ss dd/MM/yyyy"));
			}
		}

		if (!oldAdv.getOwnerNote().equals(newAdv.getOwnerNote())) {
			advChangeHistory.setOwnerNote(decorateDefferentField(newAdv.getOwnerNote(), decorate));
			isDefferent = true;
		} else {
			advChangeHistory.setOwnerNote(newAdv.getOwnerNote());
		}

		if (!oldAdv.getAdvCompPhone().equals(newAdv.getAdvCompPhone())) {
			advChangeHistory.setAdvCompPhone(decorateDefferentField(newAdv.getAdvCompPhone(), decorate));
			isDefferent = true;
		} else {
			advChangeHistory.setAdvCompPhone(newAdv.getAdvCompPhone());
		}

		if (!oldAdv.getAdvCompEmail().equals(newAdv.getAdvCompEmail())) {
			advChangeHistory.setAdvCompEmail(decorateDefferentField(newAdv.getAdvCompEmail(), decorate));
			isDefferent = true;
		} else {
			advChangeHistory.setAdvCompEmail(newAdv.getAdvCompEmail());
		}

		if (!oldAdv.getAdvCompPrice().equals(newAdv.getAdvCompPrice())) {
			advChangeHistory.setAdvCompPrice(decorateDefferentField(newAdv.getAdvCompPrice(), decorate));
			isDefferent = true;
		} else {
			advChangeHistory.setAdvCompPrice(newAdv.getAdvCompPrice());
		}

		if (!oldAdv.getAdvCompContactPerson().equals(newAdv.getAdvCompContactPerson())) {
			advChangeHistory.setAdvCompContactPerson(decorateDefferentField(newAdv.getAdvCompContactPerson(), decorate));
			isDefferent = true;
		} else {
			advChangeHistory.setAdvCompContactPerson(newAdv.getAdvCompContactPerson());
		}

		if (!oldAdv.getAdvCompName().equals(newAdv.getAdvCompName())) {
			advChangeHistory.setAdvCompName(decorateDefferentField(newAdv.getAdvCompName(), decorate));
			isDefferent = true;
		} else {
			advChangeHistory.setAdvCompName(newAdv.getAdvCompName());
		}

		try {
			if (!oldAdv.getAdvCompStartDate().equals(newAdv.getAdvCompStartDate())) {
				advChangeHistory.setAdvCompStartDate(decorateDefferentField(DateUtils.convertDateToString(newAdv.getAdvCompStartDate(), "HH:mm:ss dd/MM/yyyy"), decorate));
				isDefferent = true;
			} else {
				advChangeHistory.setAdvCompStartDate(DateUtils.convertDateToString(newAdv.getAdvCompStartDate(), "HH:mm:ss dd/MM/yyyy"));
			}
		} catch (Exception e) {
			if (oldAdv.getAdvCompStartDate() != newAdv.getAdvCompStartDate()) {
				advChangeHistory.setAdvCompStartDate(decorateDefferentField(DateUtils.convertDateToString(newAdv.getAdvCompStartDate(), "HH:mm:ss dd/MM/yyyy"), decorate));
				isDefferent = true;
			} else {
				advChangeHistory.setAdvCompStartDate(DateUtils.convertDateToString(newAdv.getAdvCompStartDate(), "HH:mm:ss dd/MM/yyyy"));
			}
		}

		try {
			if (!oldAdv.getAdvCompEndDate().equals(newAdv.getAdvCompEndDate())) {
				advChangeHistory.setAdvCompEndDate(decorateDefferentField(DateUtils.convertDateToString(newAdv.getAdvCompEndDate(), "HH:mm:ss dd/MM/yyyy"), decorate));
				isDefferent = true;
			} else {
				advChangeHistory.setAdvCompEndDate(DateUtils.convertDateToString(newAdv.getAdvCompEndDate(), "HH:mm:ss dd/MM/yyyy"));
			}
		} catch (Exception e) {
			if (oldAdv.getAdvCompEndDate() != newAdv.getAdvCompEndDate()) {
				advChangeHistory.setAdvCompEndDate(decorateDefferentField(DateUtils.convertDateToString(newAdv.getAdvCompEndDate(), "HH:mm:ss dd/MM/yyyy"), decorate));
				isDefferent = true;
			} else {
				advChangeHistory.setAdvCompEndDate(DateUtils.convertDateToString(newAdv.getAdvCompEndDate(), "HH:mm:ss dd/MM/yyyy"));
			}
		}

		if (!oldAdv.getAdvCompNote().equals(newAdv.getAdvCompNote())) {
			advChangeHistory.setAdvCompNote(decorateDefferentField(newAdv.getAdvCompNote(), decorate));
			isDefferent = true;
		} else {
			advChangeHistory.setAdvCompNote(newAdv.getAdvCompNote());
		}

		if (!oldAdv.getPrice().equals(newAdv.getPrice())) {
			advChangeHistory.setPrice(decorateDefferentField(newAdv.getPrice(), decorate));
			isDefferent = true;
		} else {
			advChangeHistory.setPrice(newAdv.getPrice());
		}

		if (!oldAdv.getType().equals(newAdv.getType())) {
			advChangeHistory.setType(decorateDefferentField(newAdv.getType(), decorate));
			isDefferent = true;
		} else {
			advChangeHistory.setType(newAdv.getType());
		}
		
		advChangeHistory.setAdvertId(newAdv.getId());
		advChangeHistory.setUpdatedBy(updatedBy);
		advChangeHistory.setUpdatedDate(newAdv.getUpdatedDate());

		if(isDefferent) {
			return advChangeHistory;
		}
		return null;
	}
	
	private String decorateDefferentField(String value, boolean decorate) {
		if(decorate) {
			return "<b>" + value + "</b>";
		}
		return value;
	}
	
	public AdvChangeHistory convertToAdvChangeHistory(Advertisement adv) {
		AdvChangeHistory advChangeHistory = new AdvChangeHistory();
		advChangeHistory.setCode(adv.getCode());
		advChangeHistory.setTitle(adv.getTitle());
		advChangeHistory.setStreet(adv.getStreet());
		advChangeHistory.setHouseNo(adv.getHouseNo());
		advChangeHistory.setWard(adv.getWard());
		advChangeHistory.setDistrict(adv.getDistrict());
		advChangeHistory.setProvince(adv.getProvince());
		advChangeHistory.setWidthSize(adv.getWidthSize());
		advChangeHistory.setHeightSize(adv.getHeightSize());
		advChangeHistory.setAmount(adv.getAmount());
		advChangeHistory.setMap(adv.getMap());
		advChangeHistory.setDescribe(adv.getDescribe());
		advChangeHistory.setViews(adv.getViews());
		advChangeHistory.setFlow(adv.getFlow());
		advChangeHistory.setImplForm(adv.getImplForm());
		try {
		advChangeHistory.setImplTime(adv.getImplTime().toString());
		} catch (Exception e) {
		}
		advChangeHistory.setLightSystem(adv.getLightSystem());
		advChangeHistory.setOwnerPhone(adv.getOwnerPhone());
		advChangeHistory.setOwnerEmail(adv.getOwnerEmail());
		advChangeHistory.setOwnerPrice(adv.getOwnerPrice());
		advChangeHistory.setOwnerPhone(adv.getOwnerPhone());
		try {
		advChangeHistory
				.setOwnerStartDate(DateUtils.convertDateToString(adv.getOwnerStartDate(), "HH:mm:ss dd/MM/yyyy"));
		} catch (Exception e) {
		}
		try {
		advChangeHistory
				.setOwnerEndDate(DateUtils.convertDateToString(adv.getOwnerEndDate(), "HH:mm:ss dd/MM/yyyy"));
		} catch (Exception e) {
		}
		advChangeHistory.setOwnerNote(adv.getOwnerNote());
		advChangeHistory.setAdvCompPhone(adv.getAdvCompPhone());
		advChangeHistory.setAdvCompEmail(adv.getAdvCompEmail());
		advChangeHistory.setAdvCompPrice(adv.getAdvCompPrice());
		advChangeHistory.setAdvCompContactPerson(adv.getAdvCompContactPerson());
		advChangeHistory.setAdvCompName(adv.getAdvCompName());
		try {
		advChangeHistory.setAdvCompStartDate(
				DateUtils.convertDateToString(adv.getAdvCompStartDate(), "HH:mm:ss dd/MM/yyyy"));
		} catch (Exception e) {
		}
		try {
		advChangeHistory
				.setAdvCompEndDate(DateUtils.convertDateToString(adv.getAdvCompEndDate(), "HH:mm:ss dd/MM/yyyy"));
		} catch (Exception e) {
		}
		advChangeHistory.setAdvCompNote(adv.getAdvCompNote());
		advChangeHistory.setPrice(adv.getPrice());
		advChangeHistory.setType(adv.getType());
		advChangeHistory.setAdvertId(adv.getId());
		advChangeHistory.setUpdatedBy(adv.getCreatedBy());
		advChangeHistory.setUpdatedDate(adv.getUpdatedDate());
		
		return advChangeHistory;
	}
}
